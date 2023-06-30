package by.bntu.eugenekulik.adit.security;

import by.bntu.eugenekulik.adit.dao.jpa.UserRepository;
import by.bntu.eugenekulik.adit.domain.jpa.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class JwtUserDetailsService implements UserDetailsService {
  @Autowired
  private UserRepository userRepository;
  public JwtUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }
  @Override
  public UserDetails loadUserByUsername(String username)
      throws UsernameNotFoundException {
    User user = userRepository.findByLogin(username);
    if (user != null) {
      return new JwtUserDetails(user);
    }
    throw new UsernameNotFoundException(
        "User '" + username + "' not found");
  }


  private record JwtUserDetails(User user) implements UserDetails {
    @Override
      public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
      }

      @Override
      public String getPassword() {
        return user.getPassword();
      }

      @Override
      public String getUsername() {
        return user.getLogin();
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
}
