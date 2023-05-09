package by.bntu.eugenekulik.adit.service;

import by.bntu.eugenekulik.adit.dao.AddressRepository;
import by.bntu.eugenekulik.adit.dao.AdvertisementRepository;
import by.bntu.eugenekulik.adit.dao.CategoryRepository;
import by.bntu.eugenekulik.adit.entity.Advertisement;
import by.bntu.eugenekulik.adit.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdvertisementServiceImpl implements AdvertisementService {

  @Autowired
  AdvertisementRepository advertisementRepository;
  @Autowired
  private AddressRepository addressRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Override
  public Page<Advertisement> getPage(int page){
    return advertisementRepository.findAll(PageRequest.of(page,10));
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
        .map(adv->advertisementRepository.save(advertisement));
  }

  @Override
  public Optional<Advertisement> changeStatus(Long id, int status){
    return (!validate(status))?
        Optional.empty():
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
  public Page<Advertisement> search(String words,Integer page) {
    return advertisementRepository
        .findByNameContainsIgnoreCase(words, PageRequest.of(page,10));
  }
  public Set<Category> recurciveChildren(Category category){
    Set<Category> categories = categoryRepository.findByParent(category);
    categories.add(category);
    return categories;
  }
}
