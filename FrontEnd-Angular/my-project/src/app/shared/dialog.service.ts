import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ConfirmationDialogComponent } from '../confirmation-dialog/confirmation-dialog.component';
import { CreateAssetTypeDialogComponent } from '../create-asset-type-dialog/create-asset-type-dialog.component';


@Injectable({
  providedIn: 'root'
})
export class DialogService {

  assetType: string;

  constructor(private dialog: MatDialog) { }

  openConfirmDialog() {
    return this.dialog.open(ConfirmationDialogComponent, {
      width: '390px',
      panelClass: 'confirm-dialog-container',
      disableClose: true,
      position: { top: "10px" },
      data: {
        message: "msg"
      }
    });
  }

  openNewAssetTypeDialog() {
    return this.dialog.open(CreateAssetTypeDialogComponent, {
      width: '390px',
      panelClass: 'confirm-dialog-container',
      disableClose: true,
      position: { top: "20px" },
      data: {
        assetType: this.assetType
      }
    });
  }

}
