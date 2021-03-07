import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatSnackBar } from '@angular/material/snack-bar';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Configuration } from '../domain/configuration.module';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {
  private readonly endpoint;

  constructor(private httpClient: HttpClient, private snackBar: MatSnackBar) {
    this.endpoint = `${environment.api}/configuration`;
  }

  save(configuration: Configuration): void {
    this.httpClient.post<Configuration>(`${this.endpoint}/`, configuration).subscribe(() => {
      this.snackBar.open('Successfully updated configuration', undefined, {
        duration: 3000
      });
    });
  }

  find(): Observable<Configuration> {
    return this.httpClient.get<Configuration>(`${this.endpoint}/`);
  }
}
