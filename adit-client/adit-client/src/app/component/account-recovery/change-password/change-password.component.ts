import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {HttpClient, HttpParams} from "@angular/common/http";
import {environment} from "../../../../environments/environment";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  baseUrl = environment.baseUrl;
  passwordChangeForm = new FormGroup({
    password: new FormControl('',
      [Validators.required,
        Validators.minLength(8),
        Validators.maxLength(64)]),
    confirmPassword: new FormControl('',
      [Validators.required,
        Validators.minLength(8),
        Validators.maxLength(64)])
  });

  token: string;

  constructor(private http:HttpClient,
              private route: ActivatedRoute,
              private router: Router) { }

  ngOnInit(): void {
    this.route.queryParams
      .subscribe(params=>{
        this.token = params['token'];
      })
  }

  changePassword() {
    this.http.post(this.baseUrl + "accountrecovery/password", {},{
      params: new HttpParams()
        .append('token',this.token)
        .append('password',this.passwordChangeForm.value.password!)
    }).subscribe(res=>{
      this.router.navigate(['/home']);
    })
  }
}
