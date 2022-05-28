import { Component, OnInit, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSort, Sort } from '@angular/material/sort';
import { MatTableDataSource } from '@angular/material/table';
import { CategoryResponse } from 'src/app/core/entity/category/categoryListResponse';
import { CategoryService } from 'src/app/core/services/category.service';
import Swal from 'sweetalert2';
import { CategoryFormDialogComponent } from './category-form-dialog/category-form-dialog.component';
import { RouterLinkWithHref } from '@angular/router';
import { LiveAnnouncer } from '@angular/cdk/a11y';
import { MatPaginator } from '@angular/material/paginator';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {

  formData = new FormData();

  dataSource = new MatTableDataSource<CategoryResponse>();
  displayedColumns: string[] = ['id','title','icon', 'iconImage', 'actions'];

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private categoryService: CategoryService, private dialog: MatDialog, private _liveAnnouncer: LiveAnnouncer) { }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  ngOnInit(): void {
    this.generateList();
  }

  generateList() {
    this.categoryService.getList().subscribe( (result) => {
      this.dataSource.data = result;
    })
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

  deleteCategory(IdCategory: number) {
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
        this.categoryService.deleteCategory(IdCategory).subscribe((e) => {
          Swal.fire({
            text: 'Categoría eliminada con éxito',
            icon: 'success',
            showConfirmButton: false,
            timer: 1500,
          }).then(() => {
            window.location.reload();
          });
        });
      } else if (
        result.dismiss === Swal.DismissReason.cancel
      ) {
      }
    })
  }

  createCategory() {
    const dialogRef = this.dialog.open(CategoryFormDialogComponent, {
      width: '80vw',
      autoFocus: false,
      disableClose: true,
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result?.event === 'ok') {
        this.categoryService.createCategory(result.category).subscribe((c) => {
            Swal.fire({
              text: 'Categoría creada con Éxito',
              icon: 'success',
              showConfirmButton: false,
              timer: 2000,
            });
            window.location.assign(`category`);
          });
      }
    });
  }

  editCategory(row: CategoryResponse) {
    const dialogRef = this.dialog.open(CategoryFormDialogComponent, {
      width: '80vw',
      autoFocus: false,
      disableClose: true,
      data: {
        category: row,
      }
    });

    dialogRef.afterClosed().subscribe((result) => {
      if (result?.event === 'ok') {
        this.categoryService.createCategory(result.category).subscribe((c) => {
            Swal.fire({
              text: 'Categoría creada con Éxito',
              icon: 'success',
              showConfirmButton: false,
              timer: 2000,
            });
          });
      }
      window.location.assign(`category`);
    });
  }
}
