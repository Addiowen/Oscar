package com.compulynx.compas.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.compulynx.compas.models.User;
import com.compulynx.compas.models.extras.UsersToActivate;
import com.compulynx.compas.models.extras.UsersToDeactivate;
import com.compulynx.compas.repositories.UserRepository;
import com.compulynx.compas.models.extras.UsersToVerify;
import com.compulynx.compas.security.AES;

/**
 * @author KENSON
 *
 */
@Service
public class UserService implements UserDetailsService {
  @Autowired private UserRepository userRepository;

  private Gson gson = new Gson();

  public List<User> getUsers() {
    return userRepository.findAll();
  }

  public List<User> getApprovedUsers() {
    return userRepository.findAllApproved();
  }

  public User save(User user) {
    return userRepository.save(user);
  }

  public User findByAfisId(String afisId) {
    return null;
  }

  public User authUser(User user) throws Exception {
    // System.out.println(user.getPassword());
    System.out.println("User Password####" + user.getPassword());
    System.out.println("User Username####" + user.getUsername());
    System.out.println("User Email####" + user.getEmail());
    return userRepository.findByUsernameAndPassword(
    user.getUsername(), AES.encrypt(user.getPassword()));
//    return userRepository.findByEmail(user.getUsername());
//   return userRepository.findByUsername(user.getUsername());
  }

  public User addUser(User user) {
    return userRepository.save(user);
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public List<UsersToVerify> getUsersToVerify() {
    return userRepository.getUsersToVerify();
  }

  public int verifyUsers(Long userId, Long approver) {
    return userRepository.approveUsers(userId, approver, "V", true);
  }

  public int rejectUsers(Long userId, Long approver) {
    return userRepository.approveUsers(userId, approver, "R", false);
  }

  public User findById(String id) {
    return userRepository.findById(id);
  }

  public int SetUserLoggedin(Long userId) {
    return userRepository.SetUserLoggedin(userId);
  }

  public int toggleDelete(Long userId, boolean deleteValue) {
    return userRepository.toggleUserDelete(userId, deleteValue);
  }

  public int SetUserLoggedOut(String username) {
    return userRepository.SetUserLoggedOut(username);
  }

  public User updateUsers(User newUser, String principal) {
    User originalUser = findById(newUser.getId().toString());
    System.out.println(originalUser);
    User updatedBy = findByUsername(principal);
    String updateJson = createUpdateJson(originalUser, newUser);
    String updatedUserJson = gson.toJson(newUser);
    originalUser.setUpdateJson(updateJson);
    originalUser.setUpdatedBy(updatedBy.getId().intValue());
    originalUser.setUpdatedAt(new Date());
    originalUser.setApproved("N");
    originalUser.setStatus(false);
    originalUser.setUpdatedUserJson(updatedUserJson);
    return userRepository.save(originalUser);
  }

  public int approveUpdate(User newUser, String principal) {
    User approver = findByUsername(principal);
    return userRepository.updateUsers(
    		newUser.getFullName(),
            newUser.getFirstName(),
            newUser.getLastName(),
            newUser.getMiddleName(),
            newUser.getEmail(),
            newUser.getPhone(),
            newUser.getGroup(),
            newUser.getBranchId(),
            newUser.getId(),
            newUser.isStatus(),
            approver.getId().intValue());
  }

  public int updatePassword(String username, String password) {
System.out.println("updatePassword  "+username);
    User fetchedUser = userRepository.findByEmail(username);

    String[] usedPasswords = new String[0];

    if (!(fetchedUser.getUsedPasswords() == null)) {
      usedPasswords = fetchedUser.getUsedPasswords().split("\\s+");
    }

    List<String> passwordsList = new ArrayList<>();

    for (int i = 0; i < usedPasswords.length; i++) {
      passwordsList.add(usedPasswords[i]);
    }

    if (fetchedUser != null){

      // Compare the list to what is being saved. If already available, alert problem. Else, proceed with saving.
      if (passwordsList.size() == 0) {
        System.out.println("there are no saved passwords");
        userRepository.saveCurrentPasswordInList(password, username);

        return userRepository.updatePassword(username, password, new Date());

      } else if(passwordsList.size() < 5) {

        System.out.println("Less than 5 passwords saved");
        if (checkIfPasswordExists(usedPasswords, password)) {
          return -1;
        } else {
          if (createNewPasswordList(passwordsList, password, username) == 1){
            return userRepository.updatePassword(username, password, new Date());
          } else {
            return 0;
          }
        }

      } else if(passwordsList.size() == 5){
        System.out.println("5 passwords saved");

        if (checkIfPasswordExists(usedPasswords, password)) {
          return -1;
        } else {

          passwordsList.remove(0);

          if (createNewPasswordList(passwordsList, password, username) == 1){
            return userRepository.updatePassword(username, password, new Date());
          } else {
            return 0;
          }

        }
      }
    }
    return 0;
  }

  private int createNewPasswordList(List<String> passwordsList,String password, String username) {
    System.out.println("running createNewPasswordList");
    passwordsList.add(password);
    System.out.println(passwordsList.toString());
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < passwordsList.size(); i++) {
      if ( i < passwordsList.size() -1){
        sb.append(passwordsList.get(i));
        sb.append(" ");
      }
      if (i == passwordsList.size() -1) sb.append(passwordsList.get(i));
    }
    System.out.println(sb.toString());
    return userRepository.saveCurrentPasswordInList(sb.toString(), username);
  }


  public int rejectUpdate(User newUser, String principal) {
    User approver = findByUsername(principal);
    return userRepository.rejectUpdate(newUser.getId(), approver.getId().intValue());
  }

  public int deactivateUser(User user) {
    User u = userRepository.findByUserId(user.getId());
    if (u != null) {
      u.setStatus(false);
      User u1 = userRepository.save(u);
      if (u1 != null) {
        return 1;
      }
    }
    return 0;
  }

  public List<UsersToDeactivate> getUsersToDeactivate() {
    return userRepository.getUsersToDeactivate();
  }

  public List<UsersToActivate> getUsersToActivate() {
    return userRepository.getUsersToActivate();
  }

  public int activateUser(User user) {
    User u = userRepository.findByUserId(user.getId());
    if (u != null) {
      u.setStatus(true);
      User u1 = userRepository.save(u);
      if (u1 != null) {
        return 0;
      }
    }
    return 0;
  }

  // fetch enrolled users for report
  public List<User> getEnrolledUsers(String fromDate, String toDate) {
    return userRepository.getAll(fromDate, toDate);
  }

  private String createUpdateJson(User originalUser, User finalUser) {
    String prefix = "{";
    String suffix = "}";

    if (originalUser.getFirstName() != null
            && !originalUser.getFirstName().equalsIgnoreCase(finalUser.getFirstName())) {
      prefix += "\"fullName\":" + finalUser.getFirstName() + ",";
    }
    if (originalUser.getEmail() != null
            && !originalUser.getEmail().equalsIgnoreCase(finalUser.getEmail())) {
      prefix += "\"email\":" + finalUser.getEmail() + ",";
    }
    if (originalUser.getPhone() != null
            && !originalUser.getPhone().equalsIgnoreCase(finalUser.getPhone())) {
      prefix += "\"phone\":" + finalUser.getPhone() + ",";
    }
    if (originalUser.getGroup() != finalUser.getGroup()) {
      prefix += "\"groupId\":" + finalUser.getGroup() + ",";
    }
    if (originalUser.getBranchId() != null
            && !originalUser.getBranchId().equalsIgnoreCase(finalUser.getBranchId())) {
      prefix += "\"branchId\":" + finalUser.getBranchId() + ",";
    }
    if (originalUser.isStatus() != finalUser.isStatus()) {
      prefix += "\"status\":" + finalUser.isStatus() + ",";
    }

    return prefix.substring(0, prefix.length() - 1) + suffix;
  }

  private boolean checkIfPasswordExists(String[] usedPasswords, String password) {

    for (String item : usedPasswords) {
      if (item.equalsIgnoreCase(password)){
        return true;
      }
    }
    return false;

  }

  // For spring security
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    User userEntity = userRepository.findByEmail(username);
    if (userEntity == null) throw new UsernameNotFoundException(username);

    return new org.springframework.security.core.userdetails.User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
  }


}
