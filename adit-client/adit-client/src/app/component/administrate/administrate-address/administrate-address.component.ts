import { Component, OnInit } from '@angular/core';
import {Address} from "../../../domain/address";
import {HttpClient, HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-administrate-address',
  templateUrl: './administrate-address.component.html',
  styleUrls: ['./administrate-address.component.css']
})
export class AdministrateAddressComponent implements OnInit {

  addresses: Address[] = [];
  totalPages: number;
  page = 0;
  constructor(private http: HttpClient) { }
  ngOnInit(): void {
    this.getAddresses(this.page);
  }

  getAddresses(page: number){
    this.page = page;
    return this.http.get("http://localhost:8080/address", {
      params: new HttpParams().append('page', page)
    }).subscribe((res:any) => {
      this.addresses = res.content;
      this.totalPages = res.totalPages;
    });
  }

  deleteAddress(address: Address) {
    const index = this.addresses.indexOf(address);
    if(index>-1){
      this.addresses.splice(index,1);
    }
    this.http.delete("http://localhost:8080/address/" + address.addressId).subscribe()
  }
}
