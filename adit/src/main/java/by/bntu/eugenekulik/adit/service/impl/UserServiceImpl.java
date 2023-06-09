package by.bntu.eugenekulik.adit.service.impl;

import by.bntu.eugenekulik.adit.dao.jpa.AdvertisementRepository;
import by.bntu.eugenekulik.adit.dao.jpa.RoleRepository;
import by.bntu.eugenekulik.adit.dao.jpa.UserRepository;
import by.bntu.eugenekulik.adit.domain.jpa.Advertisement;
import by.bntu.eugenekulik.adit.domain.jpa.Role;
import by.bntu.eugenekulik.adit.domain.jpa.User;
import by.bntu.eugenekulik.adit.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

  private final PasswordEncoder encoder;
  private final UserRepository userRepository;

  private final AdvertisementRepository advertisementRepository;
  private final RoleRepository roleRepository;

  public UserServiceImpl(PasswordEncoder encoder, UserRepository userRepository,
                         AdvertisementRepository advertisementRepository,
                         RoleRepository roleRepository) {
    this.encoder = encoder;
    this.userRepository = userRepository;
    this.advertisementRepository = advertisementRepository;
    this.roleRepository = roleRepository;
  }

  @Override
  public Page<User> getPage(int page) {
    return userRepository.findAllByOrderByLoginAsc(PageRequest.of(page, 10));
  }

  @Override
  public Optional<User> findUserById(Long id){
    return userRepository.findById(id);
  }

  @Override
  public User authorize(String username, String password) {
    User current = userRepository.findByLogin(username);
    if(current != null){
      if(encoder.matches(password,current.getPassword())) {
        return current;
      }
      else throw new CredentialsExpiredException("bad password");
    }
    throw new UsernameNotFoundException("user with name " + username + " not found");
  }

  @Override
  public void deleteUser(Long id) {
    userRepository.deleteById(id);
  }

  @Override
  public User update(final User user) {
    user.setRoles(user.getRoles().stream()
        .map(Role::getRoleId)
        .map(roleRepository::findById)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .toList());
    user.setPassword(userRepository
        .findById(user.getUserId())
        .orElseThrow(
            ()->new RuntimeException("cat't find and update user with id: " + user.getUserId()))
        .getPassword());
    userRepository.save(user);
    return user;
  }

  @Override
  public  Optional<User> create(User user){
    user.setPassword(encoder.encode(user.getPassword()));
    Role role = roleRepository.findByName("client");
    user.setRoles(List.of(role));
    user = userRepository.save(user);
    return Optional.ofNullable(user);
  }

  @Override
  public void addAdvertisementToFavourites(Long userId, Long advertisementId) {
    Optional<User> user = userRepository.findById(userId);
    Optional<Advertisement> advertisement = advertisementRepository.findById(advertisementId);
    if(user.isPresent() && advertisement.isPresent()){
      User userToSave = user.get();
      if(userToSave.getFavourites().stream().anyMatch(x->x.getAdId().equals(advertisementId)))return;
      userToSave.getFavourites().add(advertisement.get());
      userRepository.save(userToSave);
    }
  }
  @Override
  public void deleteAdvertisementFromFavourites(Long userId, Long advertisementId) {
    Optional<User> user = userRepository.findById(userId);
    Optional<Advertisement> advertisement = advertisementRepository.findById(advertisementId);
    if(user.isPresent() && advertisement.isPresent()){
      User userToSave = user.get();
      userToSave.getFavourites().remove(advertisement.get());
      userRepository.save(userToSave);
    }
  }


}
