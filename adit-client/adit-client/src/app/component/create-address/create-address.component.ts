import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {HttpClient} from "@angular/common/http";
import {Router} from "@angular/router";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-create-address',
  templateUrl: './create-address.component.html',
  styleUrls: ['./create-address.component.css']
})
export class CreateAddressComponent implements OnInit {

  baseUrl = environment.baseUrl;

  address = new FormGroup({
    country: new FormControl(''),
    region: new FormControl(''),
    city: new FormControl(''),
    part: new FormControl('')
  })

  constructor(private http:HttpClient,private router:Router) { }

  ngOnInit(): void {
  }

  createAddress() {
    this.http.post(this.baseUrl + 'address', {
      country: this.address.value.country,
      region: this.address.value.region,
      city: this.address.value.city,
      part: this.address.value.part
    }).subscribe((res:any)=>{
      this.router.navigate(['/administrate/address']);
    })
  }
}
