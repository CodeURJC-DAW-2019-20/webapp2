import { Component, Type } from '@angular/core';
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
    <button type="button" class="btn buttonown" (click)="modal.close('Ok click')">Delete</button>
  </div>
  `
})
export class NgbdModalConfirm {
  constructor(public modal: NgbActiveModal) {}
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
  constructor(private _modalService: NgbModal) {}

  open(name: string) {
    this._modalService.open(MODALS[name]);
  }
}
