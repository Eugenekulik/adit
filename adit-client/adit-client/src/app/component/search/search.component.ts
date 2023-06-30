import {Component, OnInit} from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {Advertisement} from "../../domain/advertisement";
import {ActivatedRoute} from "@angular/router";
import {environment} from "../../../environments/environment";
import {AdvertisementService} from "../../service/advertisement.service";
import {ImageService} from "../../service/image.service";
import {AddressService} from "../../service/address.service";

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit {
  page = 0;
  totalPages: number;

  //Sort attributes
  field = 'name';
  direction = true;
  set words(value: string) {
    this._words = value;
    this.find(this.page,this.field,this.direction);
  }
  get words():string{
    return this._words;
  }

  private _words:string;

  constructor(private http:HttpClient,
              private route:ActivatedRoute,
              private advertisementService:AdvertisementService,
              public imageService:ImageService,
              public addressService:AddressService) {
  }

  advertisements: Advertisement[] = [];
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.words = params['words'];
    });
  }
  find(page: number, field:string, direction:boolean){
    this.page = page;
    this.field = field;
    this.direction = direction;
    this.advertisementService.search(page,this._words,this.field,this.direction)
      .subscribe((res:any)=>{
        this.advertisements = res.content;
        this.totalPages = res.totalPages;
      })
  }
}
