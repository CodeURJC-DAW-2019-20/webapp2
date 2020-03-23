import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CarouselModule } from 'ngx-bootstrap/carousel';

import {HomeComponent} from "./home/home.component";
import {TeamInfoComponent} from "./team-info/team-info.component";

const routes: Routes = [
  {path: 'TenniShip', component: HomeComponent, },
  {path: 'TenniShip/Team/:team_id', component:TeamInfoComponent},

]
export const routing = RouterModule.forRoot(routes);
//Aqui van las rutas de los componentes
//Más tarde, a la hora de poner links, los links cambian el componente activo

@NgModule({
  imports: [RouterModule.forRoot(routes), CarouselModule.forRoot()],
  exports: [RouterModule]
})
export class AppRoutingModule { }
