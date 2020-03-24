import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms'
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

import { AppRoutingModule, routing } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { TeamInfoComponent } from './team-info/team-info.component';
import { HttpClientModule } from "@angular/common/http";
import { LoginComponent } from './login/login.component';
import { LoginTitleComponent } from './login/login-title/login-title.component';
import { LoginFormComponent } from './login/login-form/login-form.component';
import { SignupComponent } from './signup/signup.component';
import {UserService} from './service/user.service';
import {CarouselModule} from "ngx-bootstrap";
import {NgxChartsModule, PieChartModule} from "@swimlane/ngx-charts";
import { GraphicPieComponent } from './team-info/graphic-pie/graphic-pie.component';
import { MatchesPaginatedComponent } from './team-info/matches-paginated/matches-paginated.component';

@NgModule({
  declarations: [
    AppComponent, HeaderComponent,
    FooterComponent, HomeComponent,
    LoginComponent, LoginTitleComponent,
    LoginFormComponent, SignupComponent,
    TeamInfoComponent,
    GraphicPieComponent,
    MatchesPaginatedComponent
  ],
  imports: [
    BrowserModule, NgbModule,
    AppRoutingModule, HttpClientModule,
    routing, ReactiveFormsModule, CarouselModule,NgxChartsModule, PieChartModule
  ],
  providers: [UserService],
  bootstrap: [AppComponent,TeamInfoComponent,GraphicPieComponent]
})
export class AppModule { }
