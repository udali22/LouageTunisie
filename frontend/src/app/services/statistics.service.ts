import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {
  private baseUrl = 'http://localhost:8080/api/v1'

  constructor(private http: HttpClient) {}
   getUsersCount(): Observable<Number> {
    return this.http.get<Number>(`${this.baseUrl}/utilisateurs/count`);
  }
   getCarsCount(): Observable<Number> {
    return this.http.get<Number>(`${this.baseUrl}/vehicules/count`);
  }
  getStationsCount(): Observable<Number> {
    return this.http.get<Number>(`${this.baseUrl}/stations/count`);
  }
}
