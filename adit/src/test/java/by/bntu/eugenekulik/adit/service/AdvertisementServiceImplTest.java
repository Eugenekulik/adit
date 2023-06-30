package by.bntu.eugenekulik.adit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import by.bntu.eugenekulik.adit.dao.jpa.AdvertisementRepository;
import by.bntu.eugenekulik.adit.dao.jpa.CategoryRepository;
import by.bntu.eugenekulik.adit.domain.jpa.Advertisement;
import by.bntu.eugenekulik.adit.service.impl.AdvertisementServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Optional;


@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class AdvertisementServiceImplTest {

  @InjectMocks
  private AdvertisementServiceImpl advertisementService;

  @Mock
  private AdvertisementRepository advertisementRepository;

  @Mock
  private CategoryRepository categoryRepository;


  @Test
  void testGetPage() {
    // Arrange
    Page<Advertisement> expectedPage = mock(Page.class);
    when(advertisementRepository.findAll(PageRequest.of(0, 10,
        Sort.by(Sort.Direction.ASC,"name")))).thenReturn(expectedPage);

    // Act
    Page<Advertisement> result = advertisementService.getPage(0,"name", true);

    // Assert
    assertEquals(expectedPage, result);
    verify(advertisementRepository).findAll(PageRequest.of(0, 10,
        Sort.by(Sort.Direction.ASC,"name")));
  }

  @Test
  void testSave() {
    // Arrange
    Advertisement advertisement = mock(Advertisement.class);
    Advertisement savedAdvertisement = mock(Advertisement.class);
    when(advertisementRepository.save(advertisement)).thenReturn(savedAdvertisement);

    // Act
    Advertisement result = advertisementService.save(advertisement);

    // Assert
    assertEquals(savedAdvertisement, result);
    verify(advertisementRepository).save(advertisement);
  }

  @Test
  void testGetAdvertisement() {
    // Arrange
    Long id = 1L;
    Advertisement expectedAdvertisement = mock(Advertisement.class);
    when(advertisementRepository.findById(id)).thenReturn(Optional.of(expectedAdvertisement));

    // Act
    Optional<Advertisement> result = advertisementService.getAdvertisement(id);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(expectedAdvertisement, result.get());
    verify(advertisementRepository).findById(id);
  }


}