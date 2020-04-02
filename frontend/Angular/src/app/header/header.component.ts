import { Component, OnInit } from '@angular/core';
import { Router } from "@angular/router";
import { UserService } from "../service/user.service";
import { User } from "../model/user.model";
import { HttpClient } from '@angular/common/http';
import { ImageService } from '../service/image.service';

@Component({
	selector: 'app-header',
	templateUrl: './header.component.html',
	styleUrls: []
})
export class HeaderComponent implements OnInit {

	admin: boolean = true;
	currentUser: User;
	indexPage = true;   // Should change when the current page is not index
	scrolled = false;
	teamImage;

	constructor(private router: Router, private userService: UserService,private imageService:ImageService) {
		router.events.subscribe(
			data => {
				this.indexPage = (router.url === "/TenniShip"); // Makes header black if the user is not on the index page
				this.setScrolled();
			}
		);
		this.userService.currentUser.subscribe(
			x => {
				this.currentUser = x
				this.imageService.getTeamImage(this.currentUser.team,0).subscribe(
					image => this.createImageFromBlob(image)
				)
			},
			error => console.log(error)
		);
	}


	ngOnInit(): void {
		
	}

	logout(): Promise<any> {
		return new Promise((resolve, reject) => {
			this.userService.logout().subscribe(
				res => {
					console.log(res);

					// remove user from local storage to log user out
					localStorage.removeItem('currentUser');
					this.userService.currentUserSubject.next(null);
					
					this.router.navigate(['', 'TenniShip']);
				},
				error => {
					console.log("marcos");
				}
			)
		})
	}

	public createImageFromBlob (image: Blob){
		let reader = new FileReader();
		reader.addEventListener("load", () => {
			this.teamImage = reader.result;
		}, false);

		if (image) {
			reader.readAsDataURL(image);
		}
	}

	// Header scroll class
	// This makes the header smaller when the window is not at the top of the page

	setScrolled(): void {
		this.scrolled = (window.pageYOffset > 100);
	}

	getTeam(){    
		return this.currentUser.team;
	}

	getIsAdmin() {    
		return this.currentUser.roles.indexOf('ROLE_ADMIN') !== -1;
	}

}


