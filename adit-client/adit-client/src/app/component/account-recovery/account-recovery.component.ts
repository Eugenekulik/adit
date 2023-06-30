import { Component, OnInit } from '@angular/core';
import {FormControl, Validators} from "@angular/forms";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-account-recovery',
  templateUrl: './account-recovery.component.html',
  styleUrls: ['./account-recovery.component.css']
})
export class AccountRecoveryComponent implements OnInit {

  baseUrl = environment.baseUrl;
  email = new FormControl('',{
    validators: [
      Validators.required,
      Validators.email
    ]
  });
  isConfirm = false;
  constructor(private http: HttpClient,
              private router:Router) { }

  ngOnInit(): void {
  }


  confirm(){
    if(this.email.value) {
      this.http.post(this.baseUrl + "accountrecovery", {}, {
        params: new HttpParams().append('email', this.email.value)
      }).subscribe(res=>{
        this.isConfirm = true;
      })
    }
  }

}
