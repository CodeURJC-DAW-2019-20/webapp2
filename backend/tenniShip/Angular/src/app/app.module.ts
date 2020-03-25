import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import {NgbModule} from '@ng-bootstrap/ng-bootstrap';
import {HttpClientModule} from "@angular/common/http";
import {CarouselModule} from "ngx-bootstrap";
import {NgxChartsModule, PieChartModule} from "@swimlane/ngx-charts";
import { GraphicPieComponent } from './team-info/graphic-pie/graphic-pie.component';
import { MatchesPaginatedComponent } from './team-info/matches-paginated/matches-paginated.component';
import { ReactiveFormsModule } from '@angular/forms'

import { AppRoutingModule, routing } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import {RegisterMatchComponent} from "./register-match/register-match.component";
import {SelectTournamentComponent} from "./select-tournament/select-tournament.component";
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
import { LoginComponent } from './login/login.component';
import { LoginTitleComponent } from './login/login-title/login-title.component';
import { LoginFormComponent } from './login/login-form/login-form.component';
import { SignupComponent } from './signup/signup.component';
import {LoginService} from './service/login.service';


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
    LoginComponent,
    LoginTitleComponent,
    LoginFormComponent,
    SignupComponent,
    TeamInfoComponent,
    GraphicPieComponent,
    MatchesPaginatedComponent,
    RegisterMatchComponent,
    SelectTournamentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    routing,
    NgbModule,
    HttpClientModule,
    CarouselModule,
    NgxChartsModule,
    ReactiveFormsModule,
    PieChartModule
  ],
  providers: [LoginService],
  bootstrap: [AppComponent,TeamInfoComponent,GraphicPieComponent]
})
export class AppModule { }
