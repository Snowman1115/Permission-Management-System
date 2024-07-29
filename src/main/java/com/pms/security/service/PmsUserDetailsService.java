package com.pms.security.service;

import com.pms.entity.Permission;
import com.pms.entity.User;
import com.pms.service.IPermissionService;
import com.pms.service.IUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Spring Security User Authentication Class
 * @author Sn0w_15
 * @since 2024-07-24
 */
@Component
public class PmsUserDetailsService implements UserDetailsService {

    @Resource
    private IUserService userService;

    @Resource
    IPermissionService permissionService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Search For User
        User user = userService.findUserByUsername(username);
        // If User Not Exist Return UserNotFoundException
        if (user == null) {
            throw new UsernameNotFoundException("Username Or Credential Incorrect!");
        }
        // Query Current Login User Permission List
        List<Permission> permissionList = permissionService.getPermissionListByUserId(user.getId());
        // Get Permission Code
        List<String> codeList = permissionList
                                    .stream()
                                    .filter(Objects::nonNull)
                                    .map(Permission::getCode)
                                    .filter(Objects::nonNull)
                                    .collect(Collectors.toList());
        // Convert Permission Code into Array
        String[] strings = codeList.toArray(new String[codeList.size()]);
        List<GrantedAuthority> authorityList =  AuthorityUtils.createAuthorityList(strings);
        // Set Authorities for User
        user.setAuthorities(authorityList);
        // Set User's Menu Info
        user.setPermissionList(permissionList);
        return user;
    }

}
