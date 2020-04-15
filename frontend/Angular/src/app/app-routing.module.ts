import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CarouselModule } from 'ngx-bootstrap/carousel';

import { HomeComponent } from "./home/home.component";
import { CreatorComponent } from "./creator/creator.component";
import { TournamentSheetComponent } from "./tournament-sheet/tournament-sheet.component";
import { LoginComponent } from "./login/login.component";
import { SignupComponent } from './signup/signup.component';
import { TeamInfoComponent } from "./team-info/team-info.component";
import { ErrorsComponent } from './errors/errors.component';
import { SelectTournamentComponent } from "./select-tournament/select-tournament.component";
import { RegisterMatchComponent } from "./register-match/register-match.component";
import {AuthGuard} from "./auth/auth.guard";
import {AdminRegisterMatchComponent} from "./register-match/admin-register-match.component";


const routes: Routes = [
  {path: 'TenniShip', component: HomeComponent },
  {path: 'TenniShip/Creator', component: CreatorComponent, canActivate: [AuthGuard]},
  {path: 'TenniShip/Team/:team_id', component:TeamInfoComponent},
  {path: 'TenniShip/Tournament/:tournament_id', component: TournamentSheetComponent},
  {path: 'TenniShip/SignIn', component: LoginComponent},
  {path: 'TenniShip/SignUp', component: SignupComponent},
  {path: 'TenniShip/Error', component: ErrorsComponent},
  {path: 'TenniShip/RegisterMatch/Tournaments', component: SelectTournamentComponent, canActivate: [AuthGuard]},
  {path: 'TenniShip/RegisterMatch/Tournaments/:tournament_id', component: RegisterMatchComponent, canActivate: [AuthGuard]},
  {path: 'TenniShip/ADMIN/Tournaments/:tournament_id/EditMatches/:group_id', component: AdminRegisterMatchComponent, canActivate: [AuthGuard]},
  { path: '', redirectTo: 'TenniShip', pathMatch: 'full' },
  {path: '**', redirectTo:'TenniShip/Error'}
];

export const routing = RouterModule.forRoot(routes);
// Aqui van las rutas de los componentes
// Más tarde, a la hora de poner links, los links cambian el componente activo

@NgModule({

  imports: [RouterModule.forRoot(routes), CarouselModule.forRoot()],
  exports: [RouterModule]
})
export class AppRoutingModule { }
