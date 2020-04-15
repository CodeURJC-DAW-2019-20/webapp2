import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { Router } from "@angular/router";
import { UserService } from "../shared-services/user.service";
import { User } from "../model/user.model";
import { ImageService } from '../shared-services/image.service';

@Component({
	selector: 'app-header',
	templateUrl: './header.component.html',
	styleUrls: []
})
export class HeaderComponent implements OnInit {

	currentUser: User;
	indexPage = true;   // Should change when the current page is not index
	scrolled = false;

	constructor(private router: Router, public userService: UserService,private imageService: ImageService) {
		router.events.subscribe(
			data => {
				this.indexPage = (router.url === "/TenniShip"); // Makes header black if the user is not on the index page
				this.setScrolled();
			}
		);
	}


	ngOnInit(): void {
		this.userService.currentUser.subscribe(x => this.currentUser = x);
		if (this.currentUser != null) {
			this.userService.logged = true; 
			if (!this.userService.getIsAdmin()) {
				this.imageService.getTeamImage(this.userService.currentUserValue.team,0).subscribe(
					image => {
					  this.userService.createImageFromBlob(image)
					}
				  );
			}
		}
	}

	logout(): Promise<any> {
		return new Promise((resolve, reject) => {
			this.userService.logout().subscribe(
				res => {
					// remove user from local storage to log user out
					localStorage.removeItem('currentUser');
					this.userService.currentUserSubject.next(null);
					this.userService.logged = false;
					this.router.navigate(['', 'TenniShip']);
				},
				error => {
					console.log("marcos");
				}
			)
		})
	}
	// Header scroll class
	// This makes the header smaller when the window is not at the top of the page

	setScrolled(): void {
		this.scrolled = (window.pageYOffset > 100);
	}
}


