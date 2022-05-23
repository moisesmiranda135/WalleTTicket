import { CreateEmployeeDialogComponent } from './create-employee-dialog/create-employee-dialog.component';
import { EmployeesService } from './../../../core/services/employees.service';
import { AuthService } from './../../../core/services/auth.service';
import { UserResponse } from './../../../core/entity/user/UserResponse';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatDialog } from '@angular/material/dialog';
import Swal from 'sweetalert2';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';

@Component({
  selector: 'app-employees',
  templateUrl: './employees.component.html',
  styleUrls: ['./employees.component.scss']
})
export class EmployeesComponent implements OnInit {

  dataSource = new MatTableDataSource<UserResponse>();
  displayedColumns: string[] = ['id', 'email', 'name', 'lastName', 'accions'];

  constructor(private employeesService: EmployeesService, private dialog: MatDialog, private _liveAnnouncer: LiveAnnouncer) { }

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
		this.employeesService.getEmployeeList().subscribe((e) => {
      this.dataSource.data = e
    });
	}

  createEmployee() {
    const dialogRef = this.dialog.open(CreateEmployeeDialogComponent, {
      width: '80vw',
      autoFocus: false,
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((result) => {
			if (result?.event === 'ok') {
				this.employeesService.createEmployee(result?.employee).subscribe(() => {
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

  changeEnabled(isEnabled: boolean, id: number){
    if (isEnabled){
      this.employeesService.disbledEmployee(id).subscribe(() => {
        Swal.fire({
          text: 'El usuario deshabilitado con exito',
          icon: 'success',
          showConfirmButton: false,
          timer: 2000,
        })
        this.generateList();
      })
    } else{
      this.employeesService.enabledEmployee(id).subscribe(() => {
        Swal.fire({
          text: 'El usuario habilitado con exito',
          icon: 'success',
          showConfirmButton: false,
          timer: 2000,
        })
        this.generateList();
      })
    }
  }

  deleteEmployee(id: number) {
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
        this.employeesService.deleteEmployee(id).subscribe(() => {
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
