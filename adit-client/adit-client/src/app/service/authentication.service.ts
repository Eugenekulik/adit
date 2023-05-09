import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {
  http: HttpClient;
  requestHeader = new HttpHeaders({
    "No-Auth":"True"
  })
  constructor(http: HttpClient) {
    this.http = http;
  }
  public generateToken(loginData:any) {
    return this.http.post("http://localhost:8080/auth",
      loginData, {headers:this.requestHeader});
  }
  public registrate(registrationData: any) {
    return this.http.post("http://localhost:8080/register",
      registrationData, {headers:this.requestHeader});
  }
}
