package com.edafa.ExpenseTracker.services;


import com.edafa.ExpenseTracker.Repository.RoleRepository;
import com.edafa.ExpenseTracker.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public List<Role> findAll(){
        return roleRepository.findAll();
    }
    public Role findById(Long id){
        return  roleRepository.findById(id).orElse(null) ;
    }
    public Role save(Role entity){
        return roleRepository.save(entity);
    }

    public Role findByName(String name){
        return roleRepository.findByName(name) ;
    }

}
