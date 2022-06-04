import { AuthService } from 'src/app/core/services/auth.service';
import { CreateAdminDialogComponent } from './create-admin-dialog/create-admin-dialog.component';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { UserResponse } from 'src/app/core/entity/user/UserResponse';
import { AdminService } from 'src/app/core/services/admin.service';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent implements OnInit {

  dataSource = new MatTableDataSource<UserResponse>();
  displayedColumns: string[] = ['id', 'email', 'name', 'lastName', 'accions'];

  constructor(private adminService: AdminService, private dialog: MatDialog, private _liveAnnouncer: LiveAnnouncer, private router: Router) { }

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
    const dialogRef = this.dialog.open(CreateAdminDialogComponent, {
      width: '80vw',
      autoFocus: false,
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((result) => {
			if (result?.event === 'ok') {
				this.adminService.createAdmin(result?.admin).subscribe(() => {
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

  editAdmin(row: UserResponse, id: number) {
    const dialogRef = this.dialog.open(CreateAdminDialogComponent, {
      width: '80vw',
      autoFocus: false,
      disableClose: true,
      data: {
        admin: row,
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result?.event === 'ok') {
        this.adminService.editAdmin(result.admin, id).subscribe((c) => {
          Swal.fire({
            text: 'Administrador editado con Éxito',
            icon: 'success',
            showConfirmButton: false,
            timer: 2000,
          }).then( () => {
            window.location.assign(`admin`);
          });
        });
      }
    });
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
