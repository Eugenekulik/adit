import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Advertisement} from "../../domain/advertisement";
import {AdvertisementService} from "../../service/advertisement.service";
import {AddressService} from "../../service/address.service";
import {ImageService} from "../../service/image.service";
import {AuthorizationService} from "../../service/authorization.service";
import {faStar} from "@fortawesome/free-solid-svg-icons";

@Component({
  selector: 'app-advertisement',
  templateUrl: './advertisement.component.html',
  styleUrls: ['./advertisement.component.css']
})
export class AdvertisementComponent implements OnInit {

  advertisement: Advertisement;
  faStar = faStar;
  isFavourite = false;

  constructor(private route: ActivatedRoute,
              private advertisementService: AdvertisementService,
              public addressService: AddressService,
              public imageService: ImageService,
              public auth: AuthorizationService) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params=>{
      const id = params['id'];
      this.advertisementService.getAdvertisement(id).subscribe((responce:any)=>{
        console.log(responce);
        this.advertisement = responce;
      })
    })
    let user = this.auth.getUser();
    if(user == null) return;
    let favourites;
    this.advertisementService.getFavourites(user.userId).subscribe((res:any) =>{
      favourites = res as Advertisement[];
      this.isFavourite = favourites.findIndex(adv => adv.id == this.advertisement.id) > -1;
      console.log(this.isFavourite);
    })
  }

  addToFavourites(advertisement: Advertisement) {
    let user = this.auth.getUser();
    if (user != null) {
      this.advertisementService
        .addToFavorites(user.userId.toString(), advertisement.id.toString())
        .subscribe(res=>{
          console.log(res);
          this.isFavourite = true;
        });

    }
  }

  deleteFromFavourites(advertisement: Advertisement) {
    let user = this.auth.getUser();
    if(user != null){
      this.advertisementService
        .deleteFromFavourites(user.userId.toString(),advertisement.id.toString())
        .subscribe(res=>{
          console.log(res);
          this.isFavourite = false;
        })

    }
  }
}
