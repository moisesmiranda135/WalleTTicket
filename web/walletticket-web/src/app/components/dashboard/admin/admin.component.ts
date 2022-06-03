import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UserResponse } from 'src/app/core/entity/user/UserResponse';
import { AdminService } from 'src/app/core/services/admin.service';
import { EmployeesService } from 'src/app/core/services/employees.service';
import Swal from 'sweetalert2';
import { CreateEmployeeDialogComponent } from '../employees/create-employee-dialog/create-employee-dialog.component';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  dataSource = new MatTableDataSource<UserResponse>();
  displayedColumns: string[] = ['id', 'email', 'name', 'lastName', 'accions'];

  constructor(private adminService: AdminService, private dialog: MatDialog, private _liveAnnouncer: LiveAnnouncer) { }

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit() {
    this.generateList();
  }

  generateList() {
		this.adminService.getAdminList().subscribe((e) => {
      this.dataSource.data = e
    });
	}

  createAdmin() {
    const dialogRef = this.dialog.open(CreateEmployeeDialogComponent, {
      width: '80vw',
      autoFocus: false,
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((result) => {
			if (result?.event === 'ok') {
				this.adminService.createAdmin(result?.employee).subscribe(() => {
					Swal.fire({
						text: 'El empleado  creado con exito',
						icon: 'success',
						showConfirmButton: false,
						timer: 2000,
					}).then(() => {
						this.generateList();
					});
				});
			}
		});
  }

  deleteAdmin(id: number) {
    const swalWithBootstrapButtons = Swal.mixin({
      customClass: {
        confirmButton: 'btn btn-success',
        cancelButton: 'btn btn-danger'
      },
     
    })

    swalWithBootstrapButtons.fire({
      title: '¿Estás seguro?',
      text: "Borrarás el usuario de manera permanente",
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Si, borrar usuario!',
      cancelButtonText: 'No!',
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        Swal.fire({
          title:'Usuario Borrado con Éxito',
          icon:'success',
          showConfirmButton: false,
          timer: 1500
        })
        this.adminService.deleteAdmin(id).subscribe(() => {
        })

      } else if (
        result.dismiss === Swal.DismissReason.cancel
      ) {
        Swal.fire({
          title:'Operación Cancelada',
          icon:'error',
          showConfirmButton: false,
          timer: 1500
        })
      }
    }).then(() => {
      this.generateList();
    });;
  }

  announceSortChange(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value;
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }
}
