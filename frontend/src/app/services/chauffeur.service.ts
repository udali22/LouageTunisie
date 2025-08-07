import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { 
  Chauffeur, 
} from '../models/chauffeur.model';

export interface ChauffeurSearchFilters {
  nom?: string;
  email?: string;
  telephone?: string;
  noteMinimale?: number;

}
export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  number: number; // current page index (0-based)
  size: number;
  first?: boolean;
  last?: boolean;
  empty?: boolean;
}


@Injectable({
  providedIn: 'root'
})
export class ChauffeurService {
  private baseUrl = 'http://localhost:8080/api/v1';
  private chauffeursSubject = new BehaviorSubject<Chauffeur[]>([]);
  public chauffeurs$ = this.chauffeursSubject.asObservable();

  constructor(private http: HttpClient) {}

  getChauffeurs(
    page: number = 1,
    limit: number = 100,
    filters?: ChauffeurSearchFilters
  ): Observable<Page<Chauffeur>> {
    let params = new HttpParams()
      .set('page', (page-1).toString())
      .set('limit', limit.toString());

    if (filters) {
      Object.keys(filters).forEach(key => {
        const value = (filters as any)[key];
        if (value !== undefined && value !== null && value !== '') {
          if (value instanceof Date) {
            params = params.set(key, value.toISOString());
          } else {
            params = params.set(key, value.toString());
          }
        }
      });
    }

    return this.http
      .get<Page<Chauffeur>>(`${this.baseUrl}/chauffeurs`, { params })
      .pipe(tap(response => this.chauffeursSubject.next(response.content)));
  }

  getActiveChauffeurs(): Observable<Chauffeur[]> {
    return this.http.get<Chauffeur[]>(`${this.baseUrl}/sessions/chauffeurs-actifs`);
  }

  getChauffeursCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/chauffeurs/count`);
  }
  getActiveChauffeursCount():Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/sessions/chauffeurs-actifs/count`);
  }

  getChauffeur(id: number): Observable<Chauffeur> {
    return this.http.get<Chauffeur>(`${this.baseUrl}/chauffeurs/${id}`);
  }

  

  // Exemple de suppression
  deleteChauffeur(id: number): Observable<any> {
  return this.http.delete(`${this.baseUrl}/chauffeurs/${id}`, {
    responseType: 'text'
  });
}
}


