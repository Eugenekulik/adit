import {Component, OnInit} from '@angular/core';
import {AdvertisementService} from "../../service/advertisement.service";
import {Advertisement} from "../../domain/advertisement";
import {AuthorizationService} from "../../service/authorization.service";
import {environment} from "../../../environments/environment";


@Component({
  selector: 'app-favourites',
  templateUrl: './favourites.component.html',
  styleUrls: ['./favourites.component.css']
})
export class FavouritesComponent implements OnInit {


  favourites: Advertisement[];
  baseUrl = environment.baseUrl;


  constructor(public advertisementService: AdvertisementService,
              public auth: AuthorizationService) {
  }

  ngOnInit(): void {
    let user = this.auth.getUser();
    if (user != null) {
      this.advertisementService.getFavourites(user.userId)
        .subscribe((res: any) => {
          this.favourites = res;
        })
    }
  }

  deleteFromFavourites(advertisement: Advertisement) {
    let user = this.auth.getUser();
    if(user != null) {
      this.advertisementService
        .deleteFromFavourites(user.userId.toString(), advertisement.id.toString())
        .subscribe(res=>{
          console.log(res)
          this.favourites.splice(this.favourites.indexOf(advertisement),1);
        })
    }
  }
}
