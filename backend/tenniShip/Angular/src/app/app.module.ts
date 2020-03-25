import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import {AppRoutingModule, routing} from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import {RegisterMatchComponent} from "./register-match/register-match.component";
import {SelectTournamentComponent} from "./select-tournament/select-tournament.component";

@NgModule({
  declarations: [
    AppComponent, HeaderComponent, FooterComponent, HomeComponent, RegisterMatchComponent, SelectTournamentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    routing
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
