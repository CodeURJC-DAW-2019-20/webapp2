import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
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

	currentUser: User;
	indexPage = true;   // Should change when the current page is not index
	scrolled = false;

	constructor(private router: Router, public userService: UserService) {
		router.events.subscribe(
			data => {
				this.indexPage = (router.url === "/TenniShip"); // Makes header black if the user is not on the index page
				this.setScrolled();
			}
		);
	}


	ngOnInit(): void {
	}

	logout(): Promise<any> {
		return new Promise((resolve, reject) => {
			this.userService.logout().subscribe(
				res => {
					// remove user from local storage to log user out
					localStorage.removeItem('currentUser');
					this.userService.currentUserSubject.next(null);
					this.userService.loged = false;
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


