package com.austine.blog.service.auth;




import com.austine.blog.model.auth.OauthClientDetails;
import com.austine.blog.model.auth.Permission;
import com.austine.blog.model.auth.Role;
import com.austine.blog.model.auth.User;
import com.austine.blog.repository.PermissionRepository;
import com.austine.blog.repository.RoleRepository;
import com.austine.blog.repository.UserDetailRepository;
import com.austine.blog.repository.oauthrepository.OauthClientDetailsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OauthServiceImpl implements OauthService {

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    PermissionRepository permissionRepository;


    @Autowired
    RoleRepository roleRepository;


    @Autowired
    OauthClientDetailsRepo oauthClientDetailsRepo;


    @Override
    public User saveUser(User user) {
        return userDetailRepository.save(user);
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userDetailRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userDetailRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userDetailRepository.findAll();
    }

    @Override
    public Permission savePermission(Permission permission) {
        return permissionRepository.save(permission);
    }

    @Override
    public Role saveRole(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public OauthClientDetails saveClientDetails(OauthClientDetails details) {
        return oauthClientDetailsRepo.save(details);
    }

    @Override
    public void saveAllPermission(List<Permission> list) {
        permissionRepository.saveAll(list);
    }

    @Override
    public void saveAllRoles(List<Role> list) {
        roleRepository.saveAll(list);
    }

    @Override
    public List<Role> findAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void deleteUserRole(Long id) {

    }

    @Override
    public Optional<User> findUserById(Long userId) {
        return userDetailRepository.findUsersById(userId);
    }


}
