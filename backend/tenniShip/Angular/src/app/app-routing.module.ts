import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CarouselModule } from 'ngx-bootstrap/carousel';

import {HomeComponent} from "./home/home.component";
import { CreatorComponent } from "./creator/creator.component";
import { TournamentSheetComponent } from "./tournament-sheet/tournament-sheet.component";
import {TeamInfoComponent} from "./team-info/team-info.component";
import {LoginComponent} from "./login/login.component";
import { SignupComponent } from './signup/signup.component';


const routes: Routes = [
  {path: 'TenniShip', component: HomeComponent },
  {path: 'TenniShip/Creator', component: CreatorComponent },
  {path: 'TenniShip/Team/:team_id', component:TeamInfoComponent},
  {path: 'TenniShip/Tournament/:tournament_id', component: TournamentSheetComponent },
  {path: 'TenniShip/SignIn', component: LoginComponent},
  {path: 'TenniShip/SignUp', component:SignupComponent}
]

export const routing = RouterModule.forRoot(routes);
// Aqui van las rutas de los componentes
// Más tarde, a la hora de poner links, los links cambian el componente activo

@NgModule({
  imports: [RouterModule.forRoot(routes), CarouselModule.forRoot()],
  exports: [RouterModule]
})
export class AppRoutingModule { }
