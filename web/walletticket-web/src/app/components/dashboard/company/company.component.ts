import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Sort } from '@angular/material/sort';
import { CompanyResponse } from 'src/app/core/entity/company/companyResponse';
import { CompanyService } from 'src/app/core/services/company.service';
import Swal from 'sweetalert2';
import { CreateCompanyComponent } from './create-company/create-company.component';

@Component({
  selector: 'app-company',
  templateUrl: './company.component.html',
  styleUrls: ['./company.component.scss']
})
export class CompanyComponent implements OnInit {

  formData = new FormData();

  companyList: CompanyResponse[] = [];

  constructor(private companyService: CompanyService, private dialog: MatDialog, private _liveAnnouncer: LiveAnnouncer) { }


  ngOnInit(): void {
    this.generateList();
  }

  generateList() {
    this.companyService.getList().subscribe( (result) => {
      this.companyList = result;
    })
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  viewCompanyImage(image: string){
    window.open(image, "_blank");
  }

  createCompany() {
    const dialogRef = this.dialog.open(CreateCompanyComponent, {
      width: '80vw',
      autoFocus: false,
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result?.event === 'ok') {
        this.companyService.createCompany(result.company, result.image).subscribe((c) => {
            Swal.fire({
              text: 'Categoría creada con Éxito',
              icon: 'success',
              showConfirmButton: false,
              timer: 2000,
            }).then(() => {
              window.location.assign(`company`);
            });
          });
      }
    });
  }
}
