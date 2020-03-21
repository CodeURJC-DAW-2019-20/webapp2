import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from "./home/home.component";
import {TeamInfoComponent} from "./team-info/team-info.component";
import {LoginComponent} from "./login/login.component";
import { SignupComponent } from './signup/signup.component';

const routes: Routes = [
  {path: 'TenniShip', component: HomeComponent},
  {path: 'TenniShip/SignIn', component: LoginComponent},
  {path: 'TenniShip/Team/:team_id', component:TeamInfoComponent},
  {path: 'TenniShip/SignUp', component:SignupComponent}
]
export const routing = RouterModule.forRoot(routes);
//Aqui van las rutas de los componentes
//Más tarde, a la hora de poner links, los links cambian el componente activo

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
