package by.bntu.eugenekulik.adit.controller;


import by.bntu.eugenekulik.adit.dto.ResponseMessage;
import by.bntu.eugenekulik.adit.service.AccountRecoveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/accountrecovery", produces = "application/json")
public class AccountRecoveryController {


  private AccountRecoveryService accountRecoveryService;

  public AccountRecoveryController(AccountRecoveryService accountRecoveryService) {
    this.accountRecoveryService = accountRecoveryService;
  }

  @PostMapping
  public ResponseEntity<ResponseMessage> createEvent(@RequestParam String email){
    accountRecoveryService.createAccountRecoveryEvent(email);
    return ResponseEntity.ok(new ResponseMessage("create event",200));
  }

  @PostMapping("/password")
  public ResponseEntity<ResponseMessage> changePassword(@RequestParam String token,@RequestParam String password){
    accountRecoveryService.changePassword(token,password);
    return ResponseEntity.ok(new ResponseMessage("change password success", 200));
  }

}
