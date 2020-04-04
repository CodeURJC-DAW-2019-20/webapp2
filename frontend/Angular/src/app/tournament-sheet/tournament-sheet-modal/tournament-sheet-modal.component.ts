import { Component, Type, Input } from '@angular/core';
import { Router} from '@angular/router';
import {TournamentSheetService} from "../tournament-sheet.service";
import {TournamentSheetModalService} from "./tournament-sheet-modal-service.service";
import { NgbActiveModal, NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'ngbd-modal-confirm',
  template: `
  <div class="modal-header">
    <h5 class="modal-title">Tournament Deletion</h5>
    <button type="button" class="close" aria-describedby="modal-title" (click)="modal.dismiss('Cross click')">
      <span aria-hidden="true">&times;</span>
    </button>
  </div>
  <div class="modal-body">
    <h6>Are you sure on deleting the tournament?</h6>
  </div>
  <div class="modal-footer">
    <button type="button" class="btn btn-secondary" (click)="modal.dismiss('cancel click')">Cancel</button>
    <button type="button" class="btn buttonown" (click)="modal.close('Ok click'); deleteAndGoHome()">Delete</button>
  </div>
  `
})

export class NgbdModalConfirm {
  public tournamentName;

  constructor(public router: Router, public modal: NgbActiveModal,
    private tournamentSheetService: TournamentSheetService,
    private communicator: TournamentSheetModalService) {
    }

  deleteAndGoHome() {
    this.tournamentSheetService.deleteTournamentSheet(this.communicator.getTournamentName())
    .subscribe(
      res => {
        console.log(res)
        this.router.navigate(['/TenniShip']);
      }
    );
  }
}

const MODALS: {[name: string]: Type<any>} = {
  focusFirst: NgbdModalConfirm,
};

@Component({
  selector: 'app-tournament-sheet-modal',
  templateUrl: './tournament-sheet-modal.component.html',
  styleUrls: []
})

export class TournamentSheetModalComponent {
  @Input() tournamentName: string;

  constructor(public _modalService: NgbModal, public communicator: TournamentSheetModalService) {}

  open(name: string) {
    this.communicator.setTournamentName(this.tournamentName);
    this._modalService.open(MODALS[name]);
  }
}
