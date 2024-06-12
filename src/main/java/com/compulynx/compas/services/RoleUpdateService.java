package com.compulynx.compas.services;
import com.compulynx.compas.models.RoleUpdate;
import com.compulynx.compas.repositories.RoleUpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RoleUpdateService {

    @Autowired
    public RoleUpdateRepository roleUpdateRepository;

    public void addRoleRecord(RoleUpdate roleUpdate){
        roleUpdateRepository.save(roleUpdate);
    }


    public List<RoleUpdate> fetchAll() {
        System.out.println(roleUpdateRepository.findAll());
        return roleUpdateRepository.findAll();
    }

     // Addition for populating roles report
        public List<RoleUpdate> fetchAllEditedRoles() {
            // return roleUpdateRepository.findAllModifiedRoles();
            return roleUpdateRepository.findAll();
    }



    public Optional<RoleUpdate> fetchById(Long id) {
        return roleUpdateRepository.findById(id.intValue()  );
    }

    public void updateById(int id, String status, String updatedBy) {
        roleUpdateRepository.updateById(id, status, updatedBy, new Date());
    }

    public void approveRoleChange(Long id, String principal) {
        roleUpdateRepository.approveById(id, principal);
    }

    public void rejectRoleChange(Long id, String principal) {
        roleUpdateRepository.rejectById(id, principal);
    }
}
