import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from "./home/home.component";
import {LoginComponent} from "./login/login.component";
import {LoginTitleComponent} from "./login/login-title/login-title.component";
import {LoginFormComponent} from "./login/login-form/login-form.component";

const routes: Routes = [
  {path: 'TenniShip', component: HomeComponent},
  {path: 'TenniShip/SignIn', component: LoginComponent}
]
export const routing = RouterModule.forRoot(routes);
//Aqui van las rutas de los componentes
//Más tarde, a la hora de poner links, los links cambian el componente activo

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
