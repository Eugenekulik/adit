import { Component } from '@angular/core';
import {AuthenticationService} from "../../service/authentication.service";
import {AuthorizationService} from "../../service/authorization.service";
import {Router} from "@angular/router";
import {FormControl, FormGroup, NgForm} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent{

  registrateForm = new FormGroup({
    login: new FormControl(''),
    password: new FormControl(''),
    confPassword: new FormControl(''),
    firstName: new FormControl(''),
    lastName: new FormControl(''),
    age: new FormControl(''),
    phone: new FormControl(''),
    email: new FormControl('')
});
  constructor(private authenticationService: AuthenticationService,
              private authorizationService:AuthorizationService,
              private router:Router) {}


  doLogin(loginForm: NgForm) {
    console.log(loginForm.value);
    this.authenticationService.generateToken(loginForm.value).subscribe(
      (response:any) => {
        console.log(response);
        this.authorizationService.setToken(response.token);
        this.authorizationService.setRoles(response.user.roles);
        this.authorizationService.setUser(response.user);
        this.router.navigate(['/home']);
      }, (error) => {
        console.log(error)
      }
    );
  }

  registration(){
      this.authenticationService.registrate(this.registrateForm.value).subscribe((data: any) => {
        this.authorizationService.setToken(data.token);
        this.authorizationService.setRoles(data.user.roles);
        this.authorizationService.setUser(data.user);
        document.getElementById('closeModal')?.click();
        this.router.navigate(['/home']);
      }, (error) => {
        console.log(error)
      });
  }

}
