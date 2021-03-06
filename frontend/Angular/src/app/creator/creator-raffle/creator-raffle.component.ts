import { Component, OnInit } from '@angular/core';
import { CreatorService } from '../creator.service';
import { newTournament } from '../../model/newtournament';
import { catchError } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { ImageService } from 'src/app/shared-services/image.service';
import {SpinnerService} from "../../shared-services/spinner.service";
import {PageLengthService} from "../../shared-services/page-length.service";

@Component({
	selector: 'app-creator-raffle',
	templateUrl: './creator-raffle.component.html',
	styleUrls: []
})
export class CreatorRaffleComponent implements OnInit {

	public raffled: boolean = false;
	public currentFile: File;
	public teamsImage: any[] = new Array(18);

	constructor(private creatorService: CreatorService, private imageService:ImageService,
              private pageLengthService: PageLengthService) {

  }

	ngOnInit(): void {
		for (let i = 0; i < this.teamsImage.length; i++) {
			this.imageService.getTeamImage(this.creatorService.finalTeams[i],0).subscribe(
				image => this.createImageFromBlob(image,i),
				error => this.handleError(error)
			);
		}
		this.pageLengthService.updatePageLength();
	}

	public createImageFromBlob (image: Blob, i:number){
		let reader = new FileReader();
    	reader.addEventListener("load", () => {
        	this.teamsImage[i] = reader.result;
    	}, false);

		if (image) {
		reader.readAsDataURL(image);
		}
	}

	public getTeams() {
		return this.creatorService.getFinalTeams();
	}

	public getTournament() {
		return this.creatorService.getFinalTournament();
	}

	public upload() {
		this.currentFile = this.creatorService.selectedFiles.item(0);
		this.creatorService.upload(this.currentFile).subscribe(
			_=>_ ,
		error => this.handleError(error)
		);
	}

	public raffle() {
		let tournamentName: string = this.creatorService.getFinalTournament();
		let teams: string [] = this.creatorService.getFinalTeams();
		let newTour: newTournament = { tournamentName, teams };
		this.creatorService.newTournament(newTour).subscribe(
			_ => { this.raffled = true;
				this.upload();
			},
			catchError(error => Observable.throw('Server error'))
		);
	}

	public getTeamImage(i:number) {
		return this.teamsImage[i];
	}

	public handleError(error: any) {
		console.error(error);
	}
}
