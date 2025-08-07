import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface RegisterRequest {
  nom: string;
  prenom: string;
  email: string;
  motDePasse: string;
  role: string;
  telephone: number;
  cin: number;
  permis?: string;
  matricule?: string;
  numLicence?: number;
}

export interface LoginRequest {
  email: string;
  motDePasse: string;
}

// ✅ Adapter la réponse à ce que tu reçois vraiment (UUID + rôle)
export interface AuthResponse {
  token: string; // session ID
  role: string;
}

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient) {}

  register(data: RegisterRequest): Observable<any> {
    return this.http.post(`${this.baseUrl}/register`, data);
  }

  login(credentials: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.baseUrl}/login`, credentials);
  }


  saveSession(token: string, role: string): void {
    localStorage.setItem('sessionToken', token);
    localStorage.setItem('userRole', role);
  }

  getToken(): string | null {
    return localStorage.getItem('sessionToken');
  }

  getUserRole(): string | null {
    return localStorage.getItem('userRole');
  }

  isLoggedIn(): boolean {
    return !!this.getToken();
  }

  logout(): void {
    localStorage.removeItem('sessionToken');
    localStorage.removeItem('userRole');
    
  }
}
