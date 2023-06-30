package by.bntu.eugenekulik.adit.service.impl;

import by.bntu.eugenekulik.adit.dao.jpa.AdvertisementRepository;
import by.bntu.eugenekulik.adit.dao.jpa.CategoryRepository;
import by.bntu.eugenekulik.adit.dao.jpa.UserRepository;
import by.bntu.eugenekulik.adit.domain.jpa.Advertisement;
import by.bntu.eugenekulik.adit.domain.jpa.Category;
import by.bntu.eugenekulik.adit.domain.jpa.User;
import by.bntu.eugenekulik.adit.service.AdvertisementService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {


  private AdvertisementRepository advertisementRepository;
  private final CategoryRepository categoryRepository;
  private final UserRepository userRepository;

  public AdvertisementServiceImpl(AdvertisementRepository advertisementRepository, CategoryRepository categoryRepository,
                                  UserRepository userRepository) {
    this.advertisementRepository = advertisementRepository;
    this.categoryRepository = categoryRepository;
    this.userRepository = userRepository;
  }
  @Override
  public Page<Advertisement> getPage(int page, String field, Boolean direction) {
    if(field == null)field = "name";
    if(direction == null) direction = true;
    return advertisementRepository.findAll(PageRequest.of(page, 10,
            Sort.by(direction?Sort.Direction.ASC:Sort.Direction.DESC, field))
        );
  }

  @Override
  public Page<Advertisement> findByUser(Long userId, int page, String field, Boolean direction) {
    Optional<User> user = userRepository.findById(userId);
    if(field == null) field = "placedAt";
    if(direction == null) direction = true;
    if(user.isPresent()){
      return advertisementRepository.findByUser(user.get(),
          PageRequest.of(page,10,
              Sort.by(direction?Sort.Direction.ASC:Sort.Direction.DESC, field))
          );
    }
    return new PageImpl<>(Collections.emptyList());
  }

  @Override
  public Advertisement save(Advertisement advertisement) {
    advertisement.setStatus(1);
    return advertisementRepository.save(advertisement);
  }
  @Override
  public Optional<Advertisement> getAdvertisement(Long id) {
    return advertisementRepository.findById(id);
  }
  @Override
  @Transactional
  public Optional<Advertisement> updateAdvertisement(Advertisement advertisement) {
    return advertisementRepository.findById(advertisement.getAdId())
        .map(adv -> advertisementRepository.save(advertisement));
  }
  @Override
  public Optional<Advertisement> changeStatus(Long id, int status) {
    return (!validate(status)) ?
        Optional.empty() :
        Optional.ofNullable(advertisementRepository.findById(id))
            .map(advertisement ->
            {
              advertisement.get().setStatus(status);
              return advertisement.get();
            })
            .map(advertisementRepository::save);
  }
  @Override
  @Transactional
  public Page<Advertisement> search(String words, Integer page, String field, Boolean direction) {
    if(field == null)field = "name";
    if(direction == null)direction = true;
    return advertisementRepository
        .findByNameContainsIgnoreCaseAndStatus(words,2, PageRequest.of(page, 10,
            Sort.by(direction?Sort.Direction.ASC:Sort.Direction.DESC, field)));
  }
  @Override
  public Page<Advertisement> getByCategory(Long categoryId, Integer page, String field, Boolean direction) {
    if(categoryId == 0 ) return advertisementRepository.findByStatus(2,PageRequest.of(page,10,
        Sort.by(direction?Sort.Direction.ASC:Sort.Direction.DESC,field)));
    Optional<Category> category = categoryRepository.findById(categoryId);
    if(category.isEmpty())
      return new PageImpl<>(Collections.emptyList(),PageRequest.of(page,10),0);
    Set<Category> categories = recursiveChildren(categoryRepository.findById(categoryId).get());
    return advertisementRepository
        .findByCategories(categories.stream()
            .mapToLong(x -> x.getCategoryId())
            .toArray(),2, PageRequest.of(page, 10,
            Sort.by(direction?Sort.Direction.ASC:Sort.Direction.DESC,field)));
  }
  public Set<Category> recursiveChildren(Category category) {
    Set<Category> categories = categoryRepository.findByParentOrderByNameAsc(category)
        .stream().flatMap(child->recursiveChildren(child).stream()).collect(Collectors.toSet());
    categories.add(category);
    return categories;
  }
}