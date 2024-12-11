package com.ngocha.ecommerce.configuration;

import com.ngocha.ecommerce.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoConfig implements UserDetails {
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;

    private List<GrantedAuthority> authorities;

    public UserInfoConfig(User user) {
        this.email = user.getEmail();
        this.password = user.getPassword();

        this.authorities = new List<GrantedAuthority>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<GrantedAuthority> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(GrantedAuthority grantedAuthority) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends GrantedAuthority> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends GrantedAuthority> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public GrantedAuthority get(int index) {
                return null;
            }

            @Override
            public GrantedAuthority set(int index, GrantedAuthority element) {
                return null;
            }

            @Override
            public void add(int index, GrantedAuthority element) {

            }

            @Override
            public GrantedAuthority remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<GrantedAuthority> listIterator() {
                return null;
            }

            @Override
            public ListIterator<GrantedAuthority> listIterator(int index) {
                return null;
            }

            @Override
            public List<GrantedAuthority> subList(int fromIndex, int toIndex) {
                return List.of();
            }
        };
    }

    @Override
    public Collection<? extends  GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public String getUsername() {
        return email;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
