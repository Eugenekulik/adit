package by.bntu.eugenekulik.adit.security;

import by.bntu.eugenekulik.adit.dao.UserRepository;
import by.bntu.eugenekulik.adit.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

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
}
