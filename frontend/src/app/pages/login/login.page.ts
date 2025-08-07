import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import {
  IonContent,
  IonText,
  IonInput,
  IonButton,
} from '@ionic/angular/standalone';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  standalone: true,
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    IonContent,
    IonInput,
    IonButton,
    IonText
  ]
})
export class LoginPage implements OnInit {
  loginForm = this.fb.group({
    email: ['', [Validators.required, Validators.email]],
    motDePasse: ['', Validators.required],
  });

  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {}

  ngOnInit() {}

 onLogin() {
  if (this.loginForm.valid) {
    const credentials = this.loginForm.value;

    this.http.post<{ token: string, role: string }>(
      'http://localhost:8080/api/auth/login',
      credentials
    ).subscribe({
      next: (res) => {
         console.log('Réponse login :', res); // <== ajoute ceci
          this.authService.saveSession(res.token, res.role);


        
        if (res.role === 'ADMIN') {
          this.router.navigate(['/admin/dashboard']);
        } else {
          this.router.navigate(['/home']);
        }
      },
      error: (err) => {
        console.error('Erreur de connexion', err);
        this.errorMessage = 'Email ou mot de passe invalide.';
      }
    });
  }
}


  loginWithGoogle() {
    // 🔧  rediriger vers backend 
    window.location.href = 'http://localhost:8080/oauth2/authorization/google';
  }

  loginWithFacebook() {
    // 🔧  rediriger vers backend 
    window.location.href = 'http://localhost:8080/oauth2/authorization/facebook';
  }
  goToRegister(){
    this.router.navigate(['/register']);
  }
}
