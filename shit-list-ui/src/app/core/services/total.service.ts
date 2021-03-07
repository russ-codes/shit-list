import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Total } from '../domain/total.module';

@Injectable({
  providedIn: 'root'
})
export class TotalService {
  private readonly endpoint;

  constructor(private httpClient: HttpClient) {
    this.endpoint = `${environment.api}/total`;
  }

  find(): Observable<Total> {
    return this.httpClient.get<Total>(`${this.endpoint}/`);
  }
}
