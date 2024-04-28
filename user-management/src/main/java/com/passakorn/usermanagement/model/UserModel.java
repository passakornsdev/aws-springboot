package com.passakorn.usermanagement.model;

import com.passakorn.usermanagement.enumerate.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Document("user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements UserDetails {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;
    @Transient
    private String password;
    private String firstName;
    private String lastName;
    private List<RoleEnum> roleList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    private List<GrantedAuthority> getGrantedAuthorityList() {
        if (roleList == null || roleList.isEmpty()) {
            return new ArrayList<>();
        }
        return roleList
                .stream()
                .map((roleEnum -> new SimpleGrantedAuthority(roleEnum.toString()))).collect(Collectors.toList());
    }
}
