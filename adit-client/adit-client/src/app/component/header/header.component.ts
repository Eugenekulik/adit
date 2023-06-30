import { Component, OnInit } from '@angular/core';
import {AuthorizationService} from "../../service/authorization.service";
import {Router} from "@angular/router";
import {Advertisement} from "../../domain/advertisement";
import {faSearch, faSignIn, faSignOut} from "@fortawesome/free-solid-svg-icons"


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  advertisements: Advertisement[] = [];
  words: string = '';
  isMenuCollapse = true;
  faSearch = faSearch;
  faIn = faSignIn;
  faOut = faSignOut;
  constructor(public authorizationService:AuthorizationService,
              private router:Router) {}
  ngOnInit(): void {
  }
  logout() {
    console.log(this.authorizationService.isSignIn());
    this.authorizationService.clear();
    this.router.navigate(['/home']);
  }
  searchAdvertisement(){
    this.router.navigate(['/search',this.words]);
  }
}
