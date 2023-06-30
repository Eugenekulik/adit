package by.bntu.eugenekulik.adit.dao.jpa;

import by.bntu.eugenekulik.adit.domain.jpa.Image;
import org.springframework.data.repository.CrudRepository;

public interface ImageRepository extends CrudRepository<Image,Long> {
}
