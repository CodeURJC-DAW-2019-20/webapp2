import { Injectable, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { newTournament } from '../model/newtournament';

@Injectable()
export class CreatorService {

    urlTournament: string;
    urlTeams: string;
    urlImage: string;

    public tournaments: string[]; 
    public teams: string[] = [];
    public active: string = "tournament";

    private finalTournament: string;
    private finalTeams: string [];

    public selectedFiles: FileList;

    constructor (private http: HttpClient) {
        this.urlTournament = "/api/tenniship/tournaments";
        this.urlTeams = "/api/tenniship/teams";
        this.urlImage = "/image";
    }

   public getTournaments () {
        return this.http.get(this.urlTournament).pipe(
            map( response => this.extractTournamentNames(response)),
            catchError(error => Observable.throw('Server error'))
        );        
    } 

    public extractTournamentNames (response) {
        return (response as any).map(t => t.name);
    }

    public existTournament(tournament: string):boolean  {
        return this.tournaments.indexOf(tournament) > -1;
    }

    public getTeams() {
        return this.http.get(this.urlTeams).pipe(
            map(response => this.extractTeamNames(response)),
            catchError(error => Observable.throw('Server error'))
        );
    }

    public extractTeamNames (response) {
        return (response as any).map(t => t.teamName)
    }

    public existTeam(team: string):boolean  {
        return this.teams.indexOf(team) > -1;
    }

    public newTournament (tournament: newTournament) {
        return this.http.post(this.urlTournament,tournament).pipe(
            map(response => response),
            catchError(error => Observable.throw('Server error'))
        );
    }

    public upload(file: File){
		const formData: FormData = new FormData();
        formData.append('imageFile', file);
		return this.http.post(this.urlTournament+"/"+this.finalTournament+this.urlImage,formData);
    }
    

    public getFinalTournament() {
        return this.finalTournament;
    }

    public setFinalTournament(tournament: string) {
        this.finalTournament = tournament;
    }

    public getFinalTeams() {
        return this.finalTeams;
    }

    public setFinalTeams(teams: string[]) {
        this.finalTeams = teams;
    }
}    