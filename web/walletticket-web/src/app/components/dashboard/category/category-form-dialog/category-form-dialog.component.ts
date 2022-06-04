import { ShopList } from './../../../../core/util/iconsList/shopList';
import { TecnologyList } from './../../../../core/util/iconsList/tecnologyList';
import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { MatDialogRef, MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { CategoryResponse } from 'src/app/core/entity/category/categoryListResponse';
import { CommunicationList } from 'src/app/core/util/iconsList/communicationList';

@Component({
  selector: 'app-category-form-dialog',
  templateUrl: './category-form-dialog.component.html',
  styleUrls: ['./category-form-dialog.component.scss']
})
export class CategoryFormDialogComponent implements OnInit {

  category = {} as CategoryResponse;

  iconListCommunication = CommunicationList;
  iconListTecnology = TecnologyList;
  iconListShop = ShopList;

  constructor(private dialogRefe: MatDialogRef<CategoryFormDialogComponent>,
    private dialog: MatDialog, private fb: FormBuilder,  @Inject(MAT_DIALOG_DATA) public data: { category: CategoryResponse,}) { }

  ngOnInit(): void {
    if (this.data) {
      this.category = this.data.category
    }
  }

  saveCategory() {
    this.dialogRefe.close({
      event: 'ok',
      category: this.category,
    });
  }

  changeIconValue(iconValue: string) {
    this.category.icon = iconValue;
  }

  closeDialog() {
    this.dialogRefe.close();
  }

  generateIconsCommunication() : Array<string> {
    var listIcons = Object.keys(this.iconListCommunication);
    return listIcons.slice(listIcons.length / 2);
  }

  generateIconsTecnology() : Array<string> {
    var listIcons = Object.keys(this.iconListTecnology);
    return listIcons.slice(listIcons.length / 2);
  }

  generateIconsShop() : Array<string> {
    var listIcons = Object.keys(this.iconListShop);
    return listIcons.slice(listIcons.length / 2);
  }

}
