package by.bntu.eugenekulik.adit.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class ResponseMessage {
  private String message;
  private Integer code;
}
