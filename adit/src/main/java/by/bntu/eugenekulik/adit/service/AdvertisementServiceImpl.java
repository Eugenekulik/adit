package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.dao.AdvertisementRepository;
import by.bntu.eugenekulik.adit.dao.CategoryRepository;
import by.bntu.eugenekulik.adit.dao.UserRepository;
import by.bntu.eugenekulik.adit.entity.Advertisement;
import by.bntu.eugenekulik.adit.entity.Category;
import by.bntu.eugenekulik.adit.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
  public Page<Advertisement> getPage(int page) {
    return advertisementRepository.findAllByOrderByNameAsc(PageRequest.of(page, 10));
  }

  @Override
  public Page<Advertisement> findByUser(Long userId, int page) {
    Optional<User> user = userRepository.findById(userId);
    if(user.isPresent()){
      return advertisementRepository.findByUserOrderByPlacedAt(user.get(), PageRequest.of(page,10));
    }
    return new PageImpl<>(Collections.emptyList());
  }

  @Override
  public Advertisement save(Advertisement advertisement) {
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
  public Page<Advertisement> search(String words, Integer page) {
    return advertisementRepository
        .findByNameContainsIgnoreCaseOrderByNameAsc(words, PageRequest.of(page, 10));
  }
  @Override
  public Page<Advertisement> getByCategory(Long categoryId, Integer page) {
    Set<Category> categories = recurciveChildren(categoryRepository.findById(categoryId).get());
    return advertisementRepository
        .findByCategories(categories.stream()
            .mapToLong(x -> x.getCategoryId())
            .toArray(), PageRequest.of(page, 10));
  }
  public Set<Category> recurciveChildren(Category category) {
    Set<Category> categories = categoryRepository.findByParentOrderByNameAsc(category);
    categories.add(category);
    return categories;
  }
}