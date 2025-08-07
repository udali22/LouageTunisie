import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { Client, CreateClientRequest, UpdateClientRequest, Reservation } from '../models/client.model';

export interface ClientSearchFilters {
  nom?: string;
  email?: string;
  telephone?: string;
  ville?: string;
  statut?: 'actif' | 'inactif' | 'suspendu';
  dateInscriptionDebut?: Date;
  dateInscriptionFin?: Date;
}

export interface PaginatedResponse<T> {
  data: T[];
  total: number;
  page: number;
  limit: number;
  totalPages: number;
}
// src/app/models/page.model.ts
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
export class ClientService {
  private baseUrl = 'http://localhost:8080/api/v1'; // Remplacez par votre URL backend
  private clientsSubject = new BehaviorSubject<Client[]>([]);
  public clients$ = this.clientsSubject.asObservable();

  constructor(private http: HttpClient) {}

  // Get all clients with pagination and filters
  getClients(page: number = 1, limit: number = 10, filters?: ClientSearchFilters): Observable<Page<Client>> {
  let params = new HttpParams()
    .set('page', Math.max(0, page - 1).toString()) // Convert to 0-based index
    .set('limit', limit.toString());

  if (filters) {
  Object.keys(filters).forEach(key => {
    const value = (filters as any)[key];
    if (value !== undefined && value !== null && value !== '') {
      if (typeof value === 'string' && value.trim() === '') {
        return; // ignorer les chaînes vides même avec des espaces
      }
      params = params.set(key, value instanceof Date ? value.toISOString() : value.toString());
    }
  });
}


  return this.http.get<Page<Client>>(`${this.baseUrl}/clients`, { params }).pipe(
    tap(response => {
      this.clientsSubject.next(response.content); // Use response.content for Spring Page
    })
  );
}


  // Get active clients (API endpoint for active clients)
  getActiveClients(): Observable<Client[]> {
    return this.http.get<Client[]>(`${this.baseUrl}sessions/clients-actifs`);
  }

  // Get count of active clients
  getClientsCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/clients/count`);
  }
  getClientsActifCount(): Observable<number> {
  return this.http.get<number>(`${this.baseUrl}/sessions/clients-actifs/count`);
}


  // Get client by ID
  getClient(id: number): Observable<Client> {
    return this.http.get<Client>(`${this.baseUrl}/clients/${id}`);
  }

  // Create new client
  createClient(client: CreateClientRequest): Observable<Client> {
    return this.http.post<Client>(`${this.baseUrl}/clients`, client).pipe(
      tap(() => {
        // Refresh clients list
        this.refreshClients();
      })
    );
  }

  // Update client
  updateClient(id: number, client: UpdateClientRequest): Observable<Client> {
    return this.http.put<Client>(`${this.baseUrl}/clients/${id}`, client).pipe(
      tap(() => {
        // Refresh clients list
        this.refreshClients();
      })
    );
  }

  // Delete client
  deleteClient(id: number): Observable<any> {
    return this.http.delete(`${this.baseUrl}/clients/${id}`, {
    responseType: 'text'
  }).pipe(
      tap(() => {
        this.refreshClients();
      })
    )
  }


  // Get client reservations
  getClientReservations(clientId: number): Observable<Reservation[]> {
    return this.http.get<Reservation[]>(`${this.baseUrl}/clients/${clientId}/reservations`);
  }

  // Search clients
  searchClients(query: string): Observable<Client[]> {
    const params = new HttpParams().set('search', query);
    return this.http.get<Client[]>(`${this.baseUrl}/clients/search`, { params });
  }

  // Get client statistics
  getClientStats(): Observable<any> {
    return this.http.get(`${this.baseUrl}/clients/stats`);
  }

  // Export clients data
  exportClients(format: 'csv' | 'excel' = 'csv'): Observable<Blob> {
    const params = new HttpParams().set('format', format);
    return this.http.get(`${this.baseUrl}/clients/export`, {
      params,
      responseType: 'blob'
    });
  }

  // Activate/Deactivate client
  toggleClientStatus(id: number, statut: 'actif' | 'inactif' | 'suspendu'): Observable<Client> {
    return this.http.patch<Client>(`${this.baseUrl}/clients/${id}/status`, { statut }).pipe(
      tap(() => {
        this.refreshClients();
      })
    );
  }

  // Send notification to client
  sendNotification(clientId: number, message: string, type: 'email' | 'sms' = 'email'): Observable<any> {
    return this.http.post(`${this.baseUrl}/clients/${clientId}/notify`, { message, type });
  }

  // Get clients with most reservations
  getTopClients(limit: number = 10): Observable<Client[]> {
    const params = new HttpParams().set('limit', limit.toString());
    return this.http.get<Client[]>(`${this.baseUrl}/clients/top`, { params });
  }
  

  // Private method to refresh clients list
  private refreshClients(): void {
    this.getClients().subscribe();
  }
  

  // Mock data for development (remove when backend is ready)
  getMockClients(): Client[] {
    return [
      {
        id: 1,
        nom: 'Ben Ali',
        prenom: 'Ahmed',
        email: 'ahmed.benali@email.com',
        telephone: '+216 20 123 456',
        cin: 12345678,
       
      },
      {
        id: 2,
        nom: 'Trabelsi',
        prenom: 'Fatma',
        email: 'fatma.trabelsi@email.com',
        telephone: '+216 22 987 654',
        cin: 87654321,
        
      },
      {
        id: 3,
        nom: 'Karray',
        prenom: 'Mohamed',
        email: 'mohamed.karray@email.com',
        telephone: '+216 25 555 777',
        cin: 11223344,
  
      }
    ];
  }
}
