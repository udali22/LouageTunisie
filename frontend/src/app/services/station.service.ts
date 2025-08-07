import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Station } from '../models/station.model';

@Injectable({
  providedIn: 'root'
})
export class StationService {
  private apiUrl = 'http://localhost:8080/api/v1/stations';

  constructor(private http: HttpClient) {}

  getStations(): Observable<Station[]> {
    return this.http.get<Station[]>(this.apiUrl);
  }

  addStation(station: Station): Observable<Station> {
    return this.http.post<Station>(this.apiUrl, station);
  }

  deleteStation(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`, {
    responseType: 'text'
  });
  }
}
