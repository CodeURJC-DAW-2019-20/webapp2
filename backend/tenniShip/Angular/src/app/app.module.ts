import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpClientModule} from "@angular/common/http";

import {AppRoutingModule, routing} from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { CreatorComponent } from './creator/creator.component';
import { CreatorTournamentComponent } from './creator/creator-tournament/creator-tournament.component';
import { CreatorTeamsComponent } from './creator/creator-teams/creator-teams.component';
import { CreatorRaffleComponent } from './creator/creator-raffle/creator-raffle.component';
import { TournamentSheetComponent } from './tournament-sheet/tournament-sheet.component';
import { TournamentSheetGroupsComponent } from './tournament-sheet/tournament-sheet-groups/tournament-sheet-groups.component';
import { TournamentSheetFinalComponent } from './tournament-sheet/tournament-sheet-final/tournament-sheet-final.component';
import { TournamentSheetTeamsComponent } from './tournament-sheet/tournament-sheet-teams/tournament-sheet-teams.component';
import { TournamentSheetModalComponent } from './tournament-sheet/tournament-sheet-modal/tournament-sheet-modal.component';
import { TeamInfoComponent } from './team-info/team-info.component';


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    CreatorComponent,
    CreatorTournamentComponent,
    CreatorTeamsComponent,
    CreatorRaffleComponent,
    TournamentSheetComponent,
    TournamentSheetGroupsComponent,
    TournamentSheetFinalComponent,
    TournamentSheetTeamsComponent,
    TournamentSheetModalComponent,
    TeamInfoComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    routing,
    NgbModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
