import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import {HomeComponent} from "./home/home.component";
import { CreatorComponent } from "./creator/creator.component";
import { TournamentSheetComponent } from "./tournament-sheet/tournament-sheet.component";

const routes: Routes = [
  {path: 'TenniShip', component: HomeComponent },
  {path: 'TenniShip/Creator', component: CreatorComponent },
  {path: 'TenniShip/Tournament', component: TournamentSheetComponent }
]
export const routing = RouterModule.forRoot(routes);
//Aqui van las rutas de los componentes
//Más tarde, a la hora de poner links, los links cambian el componente activo

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
