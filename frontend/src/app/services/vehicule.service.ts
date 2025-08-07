import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { tap } from 'rxjs/operators';
import { Vehicule } from '../models/chauffeur.model';

export interface PaginatedResponse<T> {
  data: T[];
  total: number;
  page: number;
  limit: number;
  totalPages: number;
}

@Injectable({
  providedIn: 'root'
})
export class VehiculeService {
  private baseUrl = 'http://localhost:8080/api/v1'; // Remplacez par votre URL backend
  private vehiculesSubject = new BehaviorSubject<Vehicule[]>([]);
  public vehicules$ = this.vehiculesSubject.asObservable();

  constructor(private http: HttpClient) {}

  // Get all vehicules with pagination
  getVehicules(page: number = 1, limit: number = 10): Observable<PaginatedResponse<Vehicule>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('limit', limit.toString());

    return this.http.get<PaginatedResponse<Vehicule>>(`${this.baseUrl}/vehicules`, { params }).pipe(
      tap(response => {
        this.vehiculesSubject.next(response.data);
      })
    );
  }

  // Get vehicule by ID
  getVehicule(id: number): Observable<Vehicule> {
    return this.http.get<Vehicule>(`${this.baseUrl}/vehicules/${id}`);
  }

  // Get vehicule by matricule
  getVehiculeByMatricule(matricule: string): Observable<Vehicule> {
    return this.http.get<Vehicule>(`${this.baseUrl}/vehicules/matricule/${matricule}`);
  }

  // Create new vehicule
  createVehicule(vehicule: Omit<Vehicule, 'id'>): Observable<Vehicule> {
    return this.http.post<Vehicule>(`${this.baseUrl}/vehicules`, vehicule).pipe(
      tap(() => {
        this.refreshVehicules();
      })
    );
  }

  // Update vehicule
  updateVehicule(id: number, vehicule: Partial<Vehicule>): Observable<Vehicule> {
    return this.http.put<Vehicule>(`${this.baseUrl}/vehicules/${id}`, vehicule).pipe(
      tap(() => {
        this.refreshVehicules();
      })
    );
  }

  // Delete vehicule
  deleteVehicule(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/vehicules/${id}`).pipe(
      tap(() => {
        this.refreshVehicules();
      })
    );
  }

  // Search vehicules
  searchVehicules(query: string): Observable<Vehicule[]> {
    const params = new HttpParams().set('search', query);
    return this.http.get<Vehicule[]>(`${this.baseUrl}/vehicules/search`, { params });
  }

  // Private method to refresh vehicules list
  private refreshVehicules(): void {
    this.getVehicules().subscribe(response => {
      this.vehiculesSubject.next(response.data);
    });
  }

  // Mock data for development (remove when backend is ready)
  getMockVehicules(): Vehicule[] {
    return [
      {
        id: 1,
        matricule: '123TUN456',
        marque: 'Peugeot',
        modele: '208',
        capacite: 4,
        annee: 2020,
        couleur: 'Blanc',
        plaqueImmatriculation: '123TUN456',
        nombrePlaces: 4,
        typeCarburant: 'essence',
        climatisation: true,
        assurance: {
          compagnie: 'STAR',
          numeroPolice: 'POL123456',
          dateExpiration: new Date('2024-12-31')
        },
        contreTechnique: {
          dateExpiration: new Date('2024-06-30'),
          valide: true
        }
      },
      {
        id: 2,
        matricule: '789TUN012',
        marque: 'Renault',
        modele: 'Clio',
        capacite: 4,
        annee: 2019,
        couleur: 'Gris',
        plaqueImmatriculation: '789TUN012',
        nombrePlaces: 4,
        typeCarburant: 'diesel',
        climatisation: true,
        assurance: {
          compagnie: 'AMI',
          numeroPolice: 'POL789012',
          dateExpiration: new Date('2024-11-30')
        },
        contreTechnique: {
          dateExpiration: new Date('2024-05-15'),
          valide: true
        }
      }
    ];
  }
}
