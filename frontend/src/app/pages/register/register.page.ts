import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { IonicModule, ToastController } from '@ionic/angular';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [IonicModule, CommonModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage {
  registerForm: FormGroup;
  isChauffeur: boolean = false;

  constructor(
    private fb: FormBuilder,
    private http: HttpClient,
    private toastController: ToastController,
    private router: Router 
  ) {
    this.registerForm = this.fb.group({
      nom: ['', Validators.required],
      prenom: ['', Validators.required],
      telephone: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]],
      cin: ['', [Validators.required, Validators.pattern('^[0-9]{8}$')]],
      numPermis: [''],
      plaque: [''],
      numLicence: [''] // Ajouté ici
    });
  }

  toggleChauffeur(event: any) {
    this.isChauffeur = event.detail?.checked ?? event.target.checked;

    const permisCtrl = this.registerForm.get('numPermis');
    const plaqueCtrl = this.registerForm.get('plaque');
    const licenceCtrl = this.registerForm.get('numLicence');

    if (this.isChauffeur) {
      permisCtrl?.setValidators([Validators.required]);
      plaqueCtrl?.setValidators([Validators.required]);
      licenceCtrl?.setValidators([Validators.required]);
    } else {
      permisCtrl?.clearValidators();
      plaqueCtrl?.clearValidators();
      licenceCtrl?.clearValidators();
    }

    permisCtrl?.updateValueAndValidity();
    plaqueCtrl?.updateValueAndValidity();
    licenceCtrl?.updateValueAndValidity();
  }

  async onSubmit() {
    if (this.registerForm.invalid) {
      this.showToast('Veuillez remplir tous les champs obligatoires');
      return;
    }

    const formData = this.registerForm.value;

    // Construction dynamique du payload selon le rôle
    const payload: any = {
      nom: formData.nom,
      prenom: formData.prenom,
      email: formData.email,
      motDePasse: formData.password,
      role: this.isChauffeur ? 'CHAUFFEUR' : 'CLIENT',
      telephone: parseInt(formData.telephone),
      cin: parseInt(formData.cin)
    };

    if (this.isChauffeur) {
      payload.permis = formData.numPermis;
      payload.matricule = formData.plaque;
      payload.numLicence = parseInt(formData.numLicence); // évite d’envoyer Date.now()
    }

    this.http.post('http://localhost:8080/api/auth/register', payload).subscribe({
      next: async () => {
    
        this.registerForm.reset();
        this.isChauffeur = false;
        this.router.navigate(['/login']);
      },
      error: async (err) => {
        const msg = err.error?.message || 'Erreur lors de l’inscription';
        
      }
    });
    this.router.navigate(['/login']);

    console.log('Payload envoyé:', payload);
    console.log('Formulaire valide:', this.registerForm.valid);
  }

  private async showToast(message: string) {
    const toast = await this.toastController.create({
      message,
      duration: 2500,
      position: 'bottom',
      color: 'primary'
    });
    await toast.present();
  }
  goToLogin(){
    this.router.navigate(['/login']);
  }
}
