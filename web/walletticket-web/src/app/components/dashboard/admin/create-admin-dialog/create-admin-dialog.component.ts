import { UserDto } from '../../../../core/entity/user/UserDto';
import { Component, Inject, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-create-admin-dialog',
  templateUrl: './create-admin-dialog.component.html',
  styleUrls: ['./create-admin-dialog.component.scss']
})
export class CreateAdminDialogComponent implements OnInit {

  admin = {} as UserDto;
  dialogsTitle = true;

  hide = true;
  hide2 = true;

  constructor(private dialogRefe: MatDialogRef<CreateAdminDialogComponent>, @Inject(MAT_DIALOG_DATA) public data: { admin: UserDto},
    private dialog: MatDialog,) { }

  ngOnInit() {
    if (this.data) {
      this.admin = this.data.admin
    }
  }

  saveForm() {
    this.dialogRefe.close({
      event: 'ok',
      admin: this.admin,
    })
  }

  closeDialog() {
    this.dialogRefe.close();
  }

}

