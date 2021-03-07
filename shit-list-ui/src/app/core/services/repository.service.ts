import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Repository } from '../domain/repository.module';
import { MatTableDataSource } from '@angular/material/table';
import { ConfirmDialogComponent } from '../../shared/components/confirm-dialog/confirm-dialog.component';
import { MatDialog } from '@angular/material/dialog';

@Injectable({
  providedIn: 'root'
})
export class RepositoryService {
  private readonly endpoint;

  private dataSource = new MatTableDataSource();

  constructor(private httpClient: HttpClient, private snackBar: MatSnackBar, private dialog: MatDialog) {
    this.endpoint = `${environment.api}/repository`;

    this.findAll().subscribe((repositories: Repository[]) => {
      this.dataSource.data = repositories;
    });
  }

  save(repository: Repository): void {
    this.httpClient.post<Repository>(`${this.endpoint}/`, repository).subscribe((response) => {
      this.snackBar.open('Successfully saved repository', undefined, {
        duration: 3000
      });

      this.updateTableData(response);
    });
  }

  findAll(): Observable<Repository[]> {
    return this.httpClient.get<Repository[]>(`${this.endpoint}/`);
  }

  findById(id: string): Observable<Repository> {
    return this.httpClient.get<Repository>(`${this.endpoint}/${id}`);
  }

  deleteRepository(repository: Repository): void {
    this.dialog
      .open(ConfirmDialogComponent)
      .afterClosed()
      .subscribe((shouldDelete) => {
        if (shouldDelete) this.delete(repository);
      });
  }

  tableData() {
    return this.dataSource;
  }

  private delete(repository: Repository): void {
    const data = this.dataSource.data.filter((value: any, key) => {
      return value.id != repository.id;
    });

    this.dataSource.data = data;

    this.httpClient.delete(`${this.endpoint}/${repository.id}`).subscribe();

    this.snackBar.open('Repository deleted');
  }

  private updateTableData(value: Repository) {
    const data = this.dataSource.data;
    data.push(value);

    this.dataSource.data = data;
  }
}
