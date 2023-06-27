package com.bovine.taotao.admin.web.service.impl;

import com.bovine.taotao.admin.web.entity.SysUser;
import com.bovine.taotao.admin.web.mapper.SysMenuMapper;
import com.bovine.taotao.admin.web.mapper.SysRoleMapper;
import com.bovine.taotao.admin.web.service.SysMenuService;
import com.bovine.taotao.admin.web.service.SysUserService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author eden
 * @date 2023/6/24 19:08:08
 */
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getByUserName(username);
        Set<String> permissions = sysMenuService.getPermissions(sysUser.getId());
        return new LoginUserDetails(sysUser, permissions);
    }

    @Getter
    @Setter
    public static class LoginUserDetails implements UserDetails {

        private SysUser sysUser;

        private Set<String> permissions;

        public LoginUserDetails(SysUser sysUser, Set<String> permissions) {
            this.sysUser = sysUser;
            this.permissions = permissions;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return this.permissions.stream().filter(Objects::nonNull).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }

        @Override
        public String getPassword() {
            return sysUser.getPassword();
        }

        @Override
        public String getUsername() {
            return sysUser.getUsername();
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return new Date().after(sysUser.getLocked());
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return sysUser.getStatus() == 0;
        }
    }
}
