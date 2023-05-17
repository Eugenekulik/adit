package by.bntu.eugenekulik.adit.dto;

import by.bntu.eugenekulik.adit.entity.Image;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ImageDto {
  private long id;

  private String filename;



  public Image toImage(){
    Image image = new Image();
    image.setId(id);
    image.setFilename(filename);
    return image;
  }
  public static ImageDto fromImage(Image image){
    return ImageDto.builder()
        .id(image.getId())
        .filename(image.getFilename())
        .build();
  }
}
