package com.compulynx.compas.controllers;

import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.compulynx.compas.utils.ActiveDirectory;
import com.compulynx.compas.utils.JwtUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compulynx.compas.Constants.ResponseCodes;
import com.compulynx.compas.Responses.ErrorResponse;
import com.compulynx.compas.config.PropsConfig;
import com.compulynx.compas.config.ResourceConfig;
import com.compulynx.compas.handlers.CustomResponse;
import com.compulynx.compas.handlers.UserResponse;
import com.compulynx.compas.models.AuditTrail;
import com.compulynx.compas.models.GenericResponse;
import com.compulynx.compas.models.User;
import com.compulynx.compas.models.extras.UsersToActivate;
import com.compulynx.compas.models.extras.UsersToDeactivate;
import com.compulynx.compas.models.extras.UsersToVerify;
import com.compulynx.compas.security.AES;
import com.compulynx.compas.services.AuditTrailService;
import com.compulynx.compas.services.GeneratePassword;
import com.compulynx.compas.services.SendMail;
import com.compulynx.compas.services.UserService;

//@CrossOrigin(origins = "http://localhost:4200")
/**
 * @author KENSON
 *
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = ResourceConfig.IPRINT_API_v1)
public class UserController {
	private static final Logger logger = Logger.getLogger(UserController.class.getName());
  private Gson gson = new Gson();
  @Autowired private UserService userService;
  @Autowired private GeneratePassword generatepassword;
  @Autowired private SendMail sendMail;
  @Autowired private AuditTrailService auditTrailService;
  public AuditTrail auditTrail;
  @Autowired
  private JwtUtil jwtTokenUtil;



  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
  Date date = new Date();
  @Autowired HttpServletRequest servReq;


  @Autowired private PropsConfig propsConfig;

  private String module = "User";

  public UserController(SendMail sendMail) {
    // TODO Auto-generated constructor stub
    this.sendMail = sendMail;
  }

  @GetMapping("/getPassExpiry")
  public ResponseEntity<?> getPasswordExpiration() {
    try {
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(
              new GenericResponse(
                  true, "{\"passExpiryDays\": " + propsConfig.getPassExpiryDays() + "}"));
    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(new ErrorResponse(false, "303", "Error Getting Expiration"));
    }
  }

  @GetMapping(value = "/test")
  public ResponseEntity test() {
    return ResponseEntity.status(201).body("ok..");
  }

  @GetMapping(value = "/sysusers")
  public ResponseEntity<?> getSysUsers() throws Exception {

    System.out.println("System users ####");

    List<User> users = userService.getUsers();
    for (User usr : users) {
      usr.setPassword(AES.decrypt(usr.getPassword()));
    }

    if (users.isEmpty()) {
      return new ResponseEntity<>(
          new CustomResponse(CustomResponse.APIV, 404, false, "no users found"), HttpStatus.OK);
    }

    return new ResponseEntity<>(
        new CustomResponse(CustomResponse.APIV, 200, true, "found users", new HashSet<>(users)),
        HttpStatus.OK);
  }

  @GetMapping(value = "/findAllApproved")
  public ResponseEntity<?> findAllApproved() {
    try {
      List<User> approvedUsers = userService.getApprovedUsers();

      if (approvedUsers.isEmpty()) {
        return new ResponseEntity<>(
            new CustomResponse(CustomResponse.APIV, 404, false, "no users found"), HttpStatus.OK);
      }

      return new ResponseEntity<>(
          new CustomResponse(
              CustomResponse.APIV, 200, true, "found users", new HashSet<>(approvedUsers)),
          HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      return new ResponseEntity<>(
          new CustomResponse(CustomResponse.APIV, 404, false, "Error loading users"),
          HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @PostMapping(value = "/sysusers/auth")
  public ResponseEntity<?> authUser(@RequestBody User user, HttpServletRequest rawRequest)
      throws Exception {
	  
//	  mtumuramye
//	  Password88
    String message = "User Login";
    System.out.println(user);
    User userpro = userService.authUser(user);
    System.out.println(userpro);
    String actionStatus = "Success";
    ResponseEntity responseEntity;
    String failReason = null;
    System.out.println("--Start--");
    System.out.println("--End--");
    try {
      if (userpro == null) {
        actionStatus = "Failed";
        failReason = "Invalid User credentials";
        responseEntity =
            new ResponseEntity<>(
                new UserResponse(
                		UserResponse.APIV,
                		409, 
                		false, 
                		"invalid user credentials"
                		),
                HttpStatus.OK);
      } else if (userpro.getLogged_in() == 1) {
        actionStatus = "Failed";
        failReason = "Multiple login detected";
        responseEntity =
            new ResponseEntity<>(
                new UserResponse(UserResponse.APIV, 409, false, "User is already Logged in"),
                HttpStatus.OK);
      } else if (!userpro.getApproved().equalsIgnoreCase("V") || userpro.isStatus() == false) {
        actionStatus = "Failed";
        failReason = "Attempted login to inactive account";
        responseEntity =
            new ResponseEntity<>(
                new UserResponse(
                		 UserResponse.APIV,
                    201,
                    false,
                    "user specified is neither verified or active, kindly ensure "
                            + " your account is verified and active"
                   ),
                HttpStatus.OK);
      } else if (userpro.getDeleted() != null && userpro.getDeleted() == true) {
        actionStatus = "Failed";
        failReason = "Attempted login to Deleted account";
        responseEntity =
            new ResponseEntity<>(
                new UserResponse(
                		UserResponse.APIV,
                    201,
                    false,
                    "User specified is Deleted, Please contact administrator"
                    ),
                HttpStatus.OK);
      } 
       //set active dir failed response
       //add authentication with active directory
//		else if(LoginActiveDirectory(userpro.getEmail(),user.getPassword())) { 
//			System.out.println("Email from response :"+userpro.getEmail());
//		      actionStatus = "Failed";
//		      failReason = "Failed to authenticate on Active directory";
//		      String warning1="Username/Password incorrect Or ";
//		      String warning2=" Account is disabled Or ";
//		      String warning3=" You have not been created in the AD.";
//		      responseEntity =
//		            new ResponseEntity<>(
//		              new UserResponse(
//		              UserResponse.APIV,
//		              201,
//		              false,
//		              warning1+"\n"+warning2+"\n"+warning3+"\n"),
//		       HttpStatus.OK);
//		}
      else if (userpro != null && userpro.isStatus() != false ) { // && activedirSuccessResponse) {
    	  
    	// Logged IN
        //Generate Token then send it to Front-end as part of response
        String jwt = jwtTokenUtil.generateToken(userpro.getUsername());
        System.out.println("This is the username");
        System.out.println(userpro.getUsername());

        responseEntity =
            new ResponseEntity<>(
                new UserResponse(
                    "successfully authenticated!", 
                    200, 
                    true,
                    UserResponse.APIV, 
                    userpro, 
                    jwt),
                HttpStatus.OK);
        userService.SetUserLoggedin(userpro.getId());
      } else {
        actionStatus = "Failed";
        failReason = "Invalid User credentials";
        responseEntity =
            new ResponseEntity<>(
                new UserResponse(
                		UserResponse.APIV, 
                		409,
                		false,
                		"invalid user credentials"
                		),
                HttpStatus.OK);
      }
    } catch (Exception e) {
      actionStatus = "Failed";
      failReason = e.getMessage();
      responseEntity =
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse(false, "500", e.getMessage()));
      e.printStackTrace();
    }
    //sendAuditTrail(user.getUsername(), message, rawRequest, actionStatus, failReason);
    return responseEntity;
  }

  /**
 * @param username
 * @param password
 * @return
 */
	public boolean LoginActiveDirectory(String userName, String password) {
		String path = System.getProperty("catalina.base") + "/conf/application.properties";
		ActiveDirectory ad = new ActiveDirectory(path, userName, password);

		try {
			if (ad.login()) {
				logger.info("Login Successful");
				return true;

			} else {
				logger.info("Failed to log in.");
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		} finally {
		}
	}
	/**
	* @param username
	* @param password
	* @return
	*/
	@SuppressWarnings("static-access")
	@PostMapping(value = "/sysusers/create")
	  public ResponseEntity<?> addUser(
	      @RequestBody User user,
	      @RequestHeader("principal") String principal,
	      HttpServletRequest rawRequest) {
	    String actionStatus = "Success";
	    ResponseEntity responseEntity;
	    String failReason = null;
	    String newValue = null;
	    User username = userService.findByUsername(user.getUsername());
	    try {
	      if (username != null && user.getId() == 0) {
	        actionStatus = "Failed";
	        failReason = "Duplicate user creation detected";
	        responseEntity =
	            new ResponseEntity<>(
	                new UserResponse(UserResponse.APIV, 203, false, "username already exists!"),
	                HttpStatus.OK);
	      } else {
	        // generate password
	        String password = generatepassword.generatePassword(8);
	        
	        user.setPassword(AES.encrypt(password));
	        user.setCreatedAt(new Date());
	        user.setPasswordUpdate(new Date());
	        user.setFirstLoggin(true);
	        user.setDeleted(false);
	        User usr = userService.addUser(user);
	        if (usr == null) {
	          actionStatus = "Failed";
	          failReason = "Failed to add user";
	          responseEntity =
	              new ResponseEntity<>(
	                  new CustomResponse(UserResponse.APIV, 409, false, "failed to add user"),
	                  HttpStatus.OK);
	        } else {
	          newValue = usr.toString();
	          responseEntity =
	              new ResponseEntity<>(
	                  new CustomResponse(CustomResponse.APIV, 201, true, "User created successfully"),
	                  HttpStatus.OK);
	        }
	      }
	    } catch (Exception e) {
	      actionStatus = "Failed";
	      failReason = e.getMessage();
	      e.printStackTrace();
	      responseEntity =
	          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	              .body(new ErrorResponse(false, "500", e.getMessage()));
	    }
	    auditTrailService.saveAuditTrail(
	        principal, rawRequest, module, "Create new user", null, newValue, actionStatus, failReason);
	    return responseEntity;
	  }

  @PostMapping(value = "/sysusers/update")
  public ResponseEntity<?> updateUser(
      @RequestBody User user,
      @RequestHeader("principal") String principal,
      HttpServletRequest rawRequest)
      throws Exception {
    String actionStatus = "Success";
    ResponseEntity responseEntity;
    String failReason = null;
    String newValue = user.toString();
    try {
      userService.updateUsers(user, principal);
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(CustomResponse.APIV, 201, true, "User updated successfully"),
              HttpStatus.OK);
    } catch (Exception e) {
      e.printStackTrace();
      actionStatus = "failed";
      failReason = e.getMessage();
      responseEntity =
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse(false, "500", e.getMessage()));
    }
    String actionString = "Update User " + user.getUsername();
    auditTrailService.saveAuditTrail(
        principal, rawRequest, module, actionString, null, newValue, actionStatus, failReason);
    return responseEntity;
  }

  @SuppressWarnings("unused")
  @PostMapping(value = "/sysusers/print/auth")
  public ResponseEntity<?> printAuthUser(@RequestBody User user) throws Exception {

    User userpro = userService.findByUsername(user.getUsername());
    System.out.println(userpro);
    if (userpro == null) {
      return new ResponseEntity<>(
          new UserResponse("invalid user credentials", 409, false, UserResponse.APIV),
          HttpStatus.OK);
    } else if (!userpro.getApproved().equalsIgnoreCase("V") || userpro.isStatus() == false) {
      return new ResponseEntity<>(
          new UserResponse(
              "user specified is neither verified nor active, kindly ensure "
                  + " you verified and active ",
              201,
              false,
              UserResponse.APIV),
          HttpStatus.OK);
    } else if (userpro != null) {
      System.out.println("email" + user.getEmail());
      HttpSession session = servReq.getSession(true);
      session.setAttribute("authUser", user);
      System.out.println("authUser: " + session.getAttribute("authUser"));
      return new ResponseEntity<>(
          new UserResponse("successfully authenticated!", 200, true, UserResponse.APIV, userpro),
          HttpStatus.OK);
    } else {
      return new ResponseEntity<>(
          new UserResponse("invalid user credentials", 409, false, UserResponse.APIV),
          HttpStatus.OK);
    }
  }

  @GetMapping(value = "/users/toverify")
  public ResponseEntity<?> getUsersToVerify(
      // @RequestParam("fromDt")
      // @DateTimeFormat(pattern="yyyy-MM-dd")
      // Date fromDt,
      // @RequestParam(value="toDt")
      // @DateTimeFormat(pattern="yyyy-MM-dd")
      // Date toDt
      ) {
    System.out.println("works...");
    List<UsersToVerify> users = userService.getUsersToVerify();
    System.out.println(users);
    if (users.isEmpty()) {
      return new ResponseEntity<>(
          new CustomResponse(
              CustomResponse.APIV, 201, false, "no users found to verify", new HashSet<>(users)),
          HttpStatus.OK);
    }
    return new ResponseEntity<>(
        new CustomResponse(
            CustomResponse.APIV, 200, true, "found users to verify", new HashSet<>(users)),
        HttpStatus.OK);
  }

  @PostMapping(value = "/users/verifyusers")
  public ResponseEntity<?> verifyUsers(
      @RequestParam String veriStatus,
      @RequestBody List<User> users,
      @RequestHeader("principal") String principal,
      HttpServletRequest rawRequest) {
    System.out.println("************ In Method **********");
    String actionStatus = "Success";
    ResponseEntity responseEntity = null;
    String responseMessage = "";
    String failReason = null;
    List<String> verifiedUsers = new ArrayList<>();
    User approver = userService.findByUsername(principal);
    int upuser = -1;
    if (users.size() > 0) {
      for (User user : users) {
        User dbUser = userService.findById(user.getId().toString());
        if (!isNullOrEmpty(dbUser.getUpdateJson())) {
          System.out.println("************ In Correct **********");
          try {
            User newUser = gson.fromJson(dbUser.getUpdatedUserJson(), User.class);
            if ("V".equalsIgnoreCase(veriStatus)) {
              userService.approveUpdate(newUser, principal);
              verifiedUsers.add(dbUser.getUsername());
              responseMessage = "User Approved Successfully";
            } else {
              userService.rejectUpdate(newUser, principal);
              verifiedUsers.add(dbUser.getUsername());
              responseMessage = "User Rejected Successfully";
            }
          } catch (Exception ex) {
            actionStatus = "Failed";
            failReason = ex.getMessage();
            responseEntity =
                new ResponseEntity<>(
                    new CustomResponse(
                        CustomResponse.APIV,
                        201,
                        false,
                        "Error Approving User Update " + ex.getMessage()),
                    HttpStatus.OK);
            break;
          }
        } else {
          System.out.println("user id ##" + user.getId());
          if ("V".equalsIgnoreCase(veriStatus)) {
            upuser = userService.verifyUsers(user.getId(), approver.getId());
            verifiedUsers.add(dbUser.getUsername());
          } else {
            upuser = userService.rejectUsers(user.getId(), approver.getId());
            verifiedUsers.add(dbUser.getUsername());
          }
          //          User approvedUser = userService.findById(user.getId().toString());

          if (upuser < 0) {
            actionStatus = "Failed";
            failReason = "Problem approving users";
            responseEntity =
                new ResponseEntity<>(
                    new CustomResponse(
                        CustomResponse.APIV,
                        201,
                        false,
                        "there was problem approving users, kindly retry "),
                    HttpStatus.OK);
            break;
          } else {
            try {
              User usr = userService.findById(String.valueOf(user.getId()));
              if ("V".equalsIgnoreCase(veriStatus)) {
              }
            } catch (Exception ex) {
              actionStatus = "Failed";
              failReason = ex.getMessage();
              responseEntity =
                  new ResponseEntity<>(
                      new CustomResponse(
                          CustomResponse.APIV,
                          201,
                          false,
                          "Error Sending Email " + ex.getMessage()),
                      HttpStatus.OK);
              break;
            }
          }
        }
      }
      if ("Success".equals(actionStatus)) {
        responseEntity =
            new ResponseEntity<>(
                new CustomResponse(CustomResponse.APIV, 200, true, responseMessage),
                HttpStatus.OK);
      }
    } else {
      actionStatus = "Failed";
      failReason = "There was a problem updating users";
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(
                  CustomResponse.APIV,
                  201,
                  false,
                  "There was problem updating users, kindly retry "),
              HttpStatus.OK);
    }
    String actionString = "Verify Users: [";
    for (String userName : verifiedUsers) {
      actionString += userName + ",";
    }
    auditTrailService.saveAuditTrail(
        principal,
        rawRequest,
        module,
        actionString.substring(0, actionString.length() - 1) + "]",
        null,
        null,
        actionStatus,
        failReason);
    return responseEntity;
  }

  @GetMapping(value = "/fetchall")
  public ResponseEntity getAllUsers() {
    List<User> users = new ArrayList<>();

    userService
        .getUsers()
        .forEach(
            user -> {
              try {
                users.add(user);
              } catch (Exception e) {
                e.printStackTrace();
              }
            });
    return ResponseEntity.status(201).body(users);
  }

  @PostMapping(value = "/sysusers/logout")
  public ResponseEntity<?> UserLogout(@RequestBody String user, HttpServletRequest rawRequest) {
    String actionStatus = "Success";
    ResponseEntity responseEntity;
    String failReason = null;
    try {
      System.out.println("User logged out: " + user);
      userService.SetUserLoggedOut(user);
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(CustomResponse.APIV, 201, true, "User Logged out"), HttpStatus.OK);
    } catch (Exception e) {
      actionStatus = "Failed";
      failReason = e.getMessage();
      responseEntity =
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse(false, "500", e.getMessage()));
    }
    auditTrailService.saveAuditTrail(
        user, rawRequest, module, "User Log out", null, null, actionStatus, failReason);
    return responseEntity;
  }

  // logout a logged-in user
  @PostMapping(
      value = "/sysusers/logUserOut",
      consumes = "application/json",
      produces = "application/json")
  public ResponseEntity<?> LogUserOut(
      @RequestBody User user,
      @RequestHeader("principal") String principal,
      HttpServletRequest rawRequest) {
    String actionStatus = "Success";
    ResponseEntity responseEntity;
    String failReason = null;
    try {
      System.out.println("User logged out: " + user.getUsername());
      userService.SetUserLoggedOut(user.getUsername());
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(CustomResponse.APIV, 201, true, "You have logged the user out"),
              HttpStatus.OK);
    } catch (Exception e) {
      actionStatus = "Failed";
      failReason = e.getMessage();
      responseEntity =
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse(false, "500", e.getMessage()));
      e.printStackTrace();
    }
    auditTrailService.saveAuditTrail(
        principal, rawRequest, module, "User Log out", null, null, actionStatus, failReason);
    return responseEntity;
  }

  @PostMapping(value = "/sysusers/changePassword")
  public ResponseEntity<?> updatePassword(
      @RequestBody User user,
      HttpServletRequest rawRequest,
      @RequestHeader("principal") String principal)
      throws Exception {
    String actionStatus = "Success";
    ResponseEntity responseEntity;
    String failReason = null;
    try {
    	
    	System.out.println("changepass "+user.getUsername());
      int i = userService.updatePassword(user.getUsername(), AES.encrypt(user.getPassword()));

      if (i > 0) {
        responseEntity =
            new ResponseEntity<>(
                new CustomResponse(CustomResponse.APIV, 200, true, "Password updated successfully"),
                HttpStatus.OK);
      } else if(i == -1) {
        actionStatus = "Failed";
        failReason = "Password could not be changed.";
        responseEntity =
                new ResponseEntity<>(
                        new CustomResponse(
                                CustomResponse.APIV,
                                201,
                                true,
                                "Oops! Password "

                                        + " could not be changed. Please ensure you have not used it before then try again"),
                        HttpStatus.OK);
      }

      else {
        actionStatus = "Failed";
        failReason = "User not found";
        responseEntity =
            new ResponseEntity<>(
                new CustomResponse(
                    CustomResponse.APIV,
                    201,
                    true,
                    "Oops! User "
                        + user.getUsername()
                        + " Not found. Please Validate user name and try again"),
                HttpStatus.OK);
      }
    } catch (Exception e) {
      actionStatus = "Failed";
      failReason = e.getMessage();
      responseEntity =
          ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
              .body(new ErrorResponse(false, "500", e.getMessage()));
    }
    auditTrailService.saveAuditTrail(
        principal,
        rawRequest,
        module,
        "Update password for user " + user.getUsername(),
        null,
        null,
        actionStatus,
        failReason);
    return responseEntity;
  }

  // fetch users to deactivate
  @GetMapping(value = "/users/todeactivate")
  public ResponseEntity<?> getUsersToDeactivate() {
    System.out.println("deactivation works...");
    List<UsersToDeactivate> users = userService.getUsersToDeactivate();
    if (users.isEmpty()) {
      return new ResponseEntity<>(
          new CustomResponse(
              CustomResponse.APIV,
              201,
              false,
              "no users found to deactivate",
              new HashSet<>(users)),
          HttpStatus.OK);
    }
    return new ResponseEntity<>(
        new CustomResponse(
            CustomResponse.APIV, 200, true, "found users to deactivate", new HashSet<>(users)),
        HttpStatus.OK);
  }

  // fetch users to deactivate
  @GetMapping(value = "/users/toactivate")
  public ResponseEntity<?> getUsersToActivate() {
    System.out.println("activation works...");
    List<UsersToActivate> users = userService.getUsersToActivate();
    if (users.isEmpty()) {
      return new ResponseEntity<>(
          new CustomResponse(
              CustomResponse.APIV, 201, false, "no users found to activate", new HashSet<>(users)),
          HttpStatus.OK);
    }
    return new ResponseEntity<>(
        new CustomResponse(
            CustomResponse.APIV, 200, true, "found users to activate", new HashSet<>(users)),
        HttpStatus.OK);
  }

  // user account de-activation
  @PostMapping(value = "/users/deactivateusers")
  public ResponseEntity<?> deactivateUsers(
      @RequestBody List<User> users,
      @RequestHeader("principal") String principal,
      HttpServletRequest rawRequest) {
    String actionStatus = "Success";
    ResponseEntity responseEntity = null;
    String failReason = null;
    if (users.size() > 0) {
      for (User user : users) {
        int upuser = userService.deactivateUser(user);
        if (upuser < 0) {
          actionStatus = "Failed";
          failReason = "Problem deactivating user";
          responseEntity =
              new ResponseEntity<>(
                  new CustomResponse(
                      CustomResponse.APIV,
                      201,
                      false,
                      "there was problem deactiving user, kindly retry "),
                  HttpStatus.OK);
          break;
        }
      }
      if ("Success".equals(actionStatus)) {
        responseEntity =
            new ResponseEntity<>(
                new CustomResponse(CustomResponse.APIV, 200, true, "user deactivated successfully"),
                HttpStatus.OK);
      }
    } else {
      actionStatus = "Failed";
      failReason = "There was a problem deactivating users";
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(
                  CustomResponse.APIV,
                  201,
                  false,
                  "There was problem deactivating users, kindly retry "),
              HttpStatus.OK);
    }
    auditTrailService.saveAuditTrail(
        principal, rawRequest, module, "Deactivate Users", null, null, actionStatus, failReason);
    return responseEntity;
  }

  // account re-activation
  @PostMapping(value = "/users/activateusers")
  public ResponseEntity<?> activateUsers(
      @RequestBody List<User> users,
      @RequestHeader("principal") String principal,
      HttpServletRequest rawRequest) {
    String actionStatus = "Success";
    ResponseEntity responseEntity = null;
    String failReason = null;
    if (users.size() > 0) {
      for (User user : users) {
        int upuser = userService.activateUser(user);
        if (upuser < 0) {
          actionStatus = "Failed";
          failReason = "There was a problem activating user";
          responseEntity =
              new ResponseEntity<>(
                  new CustomResponse(
                      CustomResponse.APIV,
                      201,
                      false,
                      "there was problem activating user, kindly retry "),
                  HttpStatus.OK);
          break;
        }
      }
      if ("Success".equals(actionStatus)) {
        responseEntity =
            new ResponseEntity<>(
                new CustomResponse(CustomResponse.APIV, 200, true, "user activated successfully"),
                HttpStatus.OK);
      }
    } else {
      actionStatus = "Failed";
      failReason = "There was a problem activating users";
      responseEntity =
          new ResponseEntity<>(
              new CustomResponse(
                  CustomResponse.APIV,
                  201,
                  false,
                  "There was problem activating users, kindly retry "),
              HttpStatus.OK);
    }

    auditTrailService.saveAuditTrail(
        principal, rawRequest, module, "Deactivate Users", null, null, actionStatus, failReason);
    return responseEntity;
  }

  // daily user reports preview
  @GetMapping(value = "/users/report")
  public ResponseEntity<?> getUsersReport(
      @RequestParam(value = "fromDate") String fromDate,
      @RequestParam(value = "toDate") String toDate) {
    try {
      System.out.println("From Date:: " + fromDate);
      System.out.println("To Date:: " + toDate);

      List<User> user = userService.getEnrolledUsers(fromDate, toDate);

      System.out.println("Enrolled Users: " + user);
      if (user.size() <= 0) {
        System.out.println("No Data");
        return new ResponseEntity<>(user, HttpStatus.OK);
      }

      System.out.println("Has User Data");
      return new ResponseEntity<>(user, HttpStatus.OK);
    } catch (Exception e) {
      System.out.println(e.getMessage());
      return new ResponseEntity<>(
          new CustomResponse(CustomResponse.APIV, 200, false, "Server error processing request"),
          HttpStatus.OK);
    }
  }
  



  @PostMapping("/users/findByUsername")
  public ResponseEntity<?> findUserByUsername(@RequestBody User user) {
    try {
      User foundUser = userService.findByUsername(user.getUsername());
      if (foundUser == null) {
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(
                new ErrorResponse(
                    false,
                    ResponseCodes.TRANSACTION_NOT_FOUND,
                    "Could not find user with user name " + user.getUsername()));
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(true, foundUser));

    } catch (Exception e) {
      e.printStackTrace();
      return ResponseEntity.status(HttpStatus.CREATED)
          .body(new ErrorResponse(false, ResponseCodes.GENERAL_ERROR, "Failed to get user"));
    }
  }

  @GetMapping("/users/deleteById/{userId}")
  public ResponseEntity<?> deleteUser(
      @PathVariable String userId,
      @RequestHeader("principal") String principal,
      HttpServletRequest rawRequest) {
    String username = "";
    User user = userService.findById(userId);
    username = user == null ? userId : user.getUsername();
    Long id = Long.parseLong(userId);
    String actionStatus = "Success";
    ResponseEntity responseEntity;
    String failReason = null;
    String newValue = null;
    try {
      userService.toggleDelete(id, true);
      newValue = "Deleted";
      responseEntity =
          ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(true, "success"));
    } catch (Exception ex) {
      actionStatus = "Failed";
      failReason = ex.getMessage();
      responseEntity =
          ResponseEntity.status(HttpStatus.CREATED)
              .body(new ErrorResponse(false, "303", ex.getMessage()));
    }
    auditTrailService.saveAuditTrail(
        principal,
        rawRequest,
        module,
        "Delete User of username " + username,
        "Undeleted",
        newValue,
        actionStatus,
        failReason);
    return responseEntity;
  }

  @GetMapping("/users/undeleteById/{userId}")
  public ResponseEntity<?> undeleteUser(
      @PathVariable String userId,
      @RequestHeader("principal") String principal,
      HttpServletRequest rawRequest) {
    String username = "";
    User user = userService.findById(userId);
    username = user == null ? userId : user.getUsername();
    Long id = Long.parseLong(userId);
    String actionStatus = "Success";
    ResponseEntity responseEntity;
    String failReason = null;
    String newValue = null;
    try {
      userService.toggleDelete(id, false);
      newValue = "Undeleted";
      responseEntity =
          ResponseEntity.status(HttpStatus.CREATED).body(new GenericResponse(true, "success"));
    } catch (Exception ex) {
      actionStatus = "Failed";
      failReason = ex.getMessage();
      responseEntity =
          ResponseEntity.status(HttpStatus.CREATED)
              .body(new ErrorResponse(false, "303", ex.getMessage()));
    }
    auditTrailService.saveAuditTrail(
        principal,
        rawRequest,
        module,
        "Undelete User of username " + username,
        "Deleted",
        newValue,
        actionStatus,
        failReason);
    return responseEntity;
  }

  // Send audit trail
  public void sendAuditTrail(
      String principal,
      String message,
      HttpServletRequest rawRequest,
      String actionStatus,
      String failReason) {
      auditTrailService.saveAuditTrail(
        principal, rawRequest, module, message, null, null, actionStatus, failReason);
  }

  private boolean isNullOrEmpty(String updateJson) {
    System.out.println("************ In Check **********");
    System.out.println(updateJson);
    return updateJson == null || "".equalsIgnoreCase(updateJson) || updateJson.isEmpty();
  }
  
  public static void main(String[] args) {
	  Random RANDOM = new SecureRandom();
		String ALPHABET = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

	  StringBuilder returnValue = new StringBuilder(8);

		for (int i = 0; i < 8; i++) {
			returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}


		
	  String password = new String(returnValue);
	  
      try {
//    	  password: V9RIssZ4
//    	  Y3_6B0d8T1X01ic0bScDtA
    	   String newpass="Agent@pbu2023";
    	  System.out.println("password: "+password);
		System.out.println("encrypted password : "+AES.encrypt(newpass));
		
		
		System.out.println(AES.decrypt("7B72qaZFwTN0gOyTaM-9Lg"));
		
		
		
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}

}
  
  
}
