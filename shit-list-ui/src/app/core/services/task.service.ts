import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from '../../../environments/environment';
import { Configuration } from '../domain/configuration.module';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private readonly endpoint;

  constructor(private httpClient: HttpClient, private snackBar: MatSnackBar) {
    this.endpoint = `${environment.api}/tasks`;
  }

  sync() {
    this.snackBar.open('Syncing started...', undefined, {
      duration: 500
    });

    return this.httpClient.get<Configuration>(`${this.endpoint}/sync`).subscribe(() => {
      this.snackBar.open('Syncing complete', undefined, {
        duration: 3000
      });
    });
  }
}
