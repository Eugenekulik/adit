import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { HeaderComponent } from './component/header/header.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { HomeComponent } from './component/home/home.component';
import {RouterModule} from "@angular/router";
import { AboutComponent } from './component/about/about.component';
import { SearchComponent } from './component/search/search.component';
import { LoginComponent } from './component/login/login.component';
import {ReactiveFormsModule,FormsModule} from "@angular/forms";
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import { AdvertisementComponent } from './component/advertisement/advertisement.component';
import {AuthGuard} from "./service/auth.guard";
import {AuthInterceptor} from "./service/auth.interceptor";
import {AuthenticationService} from "./service/authentication.service";
import {FontAwesomeModule} from "@fortawesome/angular-fontawesome";
import { AdministrateComponent } from './component/administrate/administrate.component';
import { AdministrateAdvertisementComponent } from './component/administrate/administrate-advertisement/administrate-advertisement.component';
import { AdministrateUserComponent } from './component/administrate/administrate-user/administrate-user.component';
import { AdministrateCategoryComponent } from './component/administrate/administrate-category/administrate-category.component';
import { AdministrateAddressComponent } from './component/administrate/administrate-address/administrate-address.component';
import { AdministrateFeatureComponent } from './component/administrate/administrate-feature/administrate-feature.component';
import { AdvStatusPipe } from './service/pipe/adv-status.pipe';
import { AdvertisementEditComponent } from './component/administrate/administrate-advertisement/advertisement-edit/advertisement-edit.component';
@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    HomeComponent,
    AboutComponent,
    SearchComponent,
    LoginComponent,
    AdvertisementComponent,
    AdministrateComponent,
    AdministrateAdvertisementComponent,
    AdministrateUserComponent,
    AdministrateCategoryComponent,
    AdministrateAddressComponent,
    AdministrateFeatureComponent,
    AdvStatusPipe,
    AdvertisementEditComponent,
  ],
  imports: [
    BrowserModule,
    NgbModule,
    AppRoutingModule,
    RouterModule,
    ReactiveFormsModule,
    FormsModule,
    HttpClientModule,
    FontAwesomeModule,
  ],
  providers: [
    AuthGuard,
    {
      provide:HTTP_INTERCEPTORS,
      useClass:AuthInterceptor,
      multi:true
    },
    AuthenticationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
