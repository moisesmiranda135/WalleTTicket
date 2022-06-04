import { CompanyResponse } from './../../../../core/entity/company/companyResponse';
import { Component, Input, OnInit } from '@angular/core';
import { CompanyService } from 'src/app/core/services/company.service';
import Swal from 'sweetalert2';
import { MatDialog, MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-company-item',
  templateUrl: './company-item.component.html',
  styleUrls: ['./company-item.component.scss']
})
export class CompanyItemComponent implements OnInit {

  @Input() companyInput!: CompanyResponse;

  constructor(private companyService: CompanyService, private dialog: MatDialog) { }

  ngOnInit() {
  }

  deleteCompany(IdCompany: number) {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
    })

    swalWithBootstrapButtons.fire({
      title: '¿Estás seguro?',
      text: "Una vez la elimines no podrás volver",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, Eliminar!',
      cancelButtonText: 'No!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        this.companyService.deleteCompany(IdCompany).subscribe((e) => {
          Swal.fire({
            text: 'Categoría eliminada con éxito',
            icon: 'success',
            showConfirmButton: false,
            timer: 1500,
          }).then(() => {
            window.location.assign(`company`);
          });
        });
      } else if (
        result.dismiss === Swal.DismissReason.cancel
      ) {
      }
    })
  }
}
