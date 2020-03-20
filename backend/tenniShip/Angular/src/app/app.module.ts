import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {ReactiveFormsModule} from '@angular/forms'

import {AppRoutingModule, routing} from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { LoginTitleComponent } from './login/login-title/login-title.component';
import { LoginFormComponent } from './login/login-form/login-form.component';
import { SignupComponent } from './signup/signup.component';

@NgModule({
  declarations: [
    AppComponent, HeaderComponent, FooterComponent, HomeComponent, LoginComponent, LoginTitleComponent, LoginFormComponent, SignupComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    routing,
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
