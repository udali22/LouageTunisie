import { Component, OnInit } from '@angular/core';
import { IonicModule, AlertController, ToastController, ModalController } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StationService } from 'src/app/services/station.service';
import { Station } from 'src/app/models/station.model';

@Component({
  standalone:true,
  selector: 'app-stations',
  templateUrl: './stations.component.html',
  styleUrls: ['./stations.component.scss'],
  imports: [IonicModule, CommonModule, FormsModule]
})
export class StationsComponent  implements OnInit {
   searchQuery = '';
  filteredStations: any[] = [];
  stations: any[] = [];

  constructor( private stationService : StationService,
    private alertController: AlertController,
    private toastController: ToastController,
    private modalController: ModalController
  ) { }

  ngOnInit() {
    this.onSearch();
    this.loadStation();
  }
  applyFilters() {
    this.filteredStations = this.stations.filter(station => {
      const matchesSearch = !this.searchQuery ||
        station.nom.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        station.ville.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        station.adresse.toLowerCase().includes(this.searchQuery.toLowerCase()) ;
      return matchesSearch 
    });
   

}
onSearch(){
    this.applyFilters();
   }
   loadStation(){
    this.stationService.getStations().subscribe({
      next: (response) => {
        this.stations = response;
        this.filteredStations = [...this.stations];
      },
      error: (error) => {
        console.error('Error loading stations:', error);
      }
    });
   }
   async deleteStation(station: Station) {
  const alert = await this.alertController.create({
    header: 'Confirmer la suppression',
    message: `Êtes-vous sûr de vouloir supprimer la station "${station.nom}" située à ${station.ville} ?`,
    buttons: [
      {
        text: 'Annuler',
        role: 'cancel'
      },
      {
        text: 'Supprimer',
        role: 'destructive',
        handler: () => {
          this.performDeleteStation(station);
        }
      }
    ]
  });

  await alert.present();
}

async performDeleteStation(station: Station) {
  this.stationService.deleteStation(station.id!).subscribe({
    next: async () => {
      this.stations = this.stations.filter(s => s.id !== station.id);
      this.applyFilters();
      
      const toast = await this.toastController.create({
        message: 'Station supprimée avec succès.',
        duration: 2000,
        color: 'success'
      });
      await toast.present();
    },
    error: async (err) => {
      console.error('Erreur lors de la suppression de la station :', err);
      
      const toast = await this.toastController.create({
        message: 'Erreur lors de la suppression.',
        duration: 2000,
        color: 'danger'
      });
      await toast.present();
    }
  });
}


}
