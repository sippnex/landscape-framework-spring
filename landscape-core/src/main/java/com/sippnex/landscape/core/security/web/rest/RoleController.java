package com.sippnex.landscape.core.security.web.rest;

import com.sippnex.landscape.core.security.domain.Role;
import com.sippnex.landscape.core.security.repository.RoleRepository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @RequestMapping(value="", method= RequestMethod.GET)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @RequestMapping("{id}")
    public Role getRoleById(@PathVariable("id") String id) {
        return roleRepository.findById(id).orElse(null);
    }

}
