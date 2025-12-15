package com.austine.blog.service.auth;


import com.austine.blog.model.auth.OauthClientDetails;
import com.austine.blog.model.auth.Permission;
import com.austine.blog.model.auth.Role;
import com.austine.blog.model.auth.User;


import java.util.List;
import java.util.Optional;

public interface OauthService {
    public User saveUser(User user);

    public Optional<User> getUserById(Long id);

    public Optional<User> getUserByUsername(String username);

    public List<User> findAll();

    public Permission savePermission(Permission permission);

    public Role saveRole(Role role);

    public OauthClientDetails saveClientDetails(OauthClientDetails details);

    public void saveAllPermission(List<Permission> list);

    public void saveAllRoles(List<Role> list);

    public List<Role> findAllRoles();

    public void deleteUserRole(Long id);

    Optional<User> findUserById(Long userId);
}
