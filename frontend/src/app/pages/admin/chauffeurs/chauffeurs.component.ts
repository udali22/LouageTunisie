import { Component, OnInit } from '@angular/core';
import { IonicModule, AlertController, ToastController, ModalController } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Chauffeur } from '../../../models/chauffeur.model';
import { ChauffeurService } from '../../../services/chauffeur.service';

@Component({
  selector: 'app-chauffeurs',
  templateUrl: './chauffeurs.component.html',
  styleUrls: ['./chauffeurs.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule]
})
export class ChauffeursComponent implements OnInit {
  // Data properties
  chauffeurs: Chauffeur[] = [];
  filteredChauffeurs: Chauffeur[] = [];
  paginatedChauffeurs: Chauffeur[] = [];
  selectedChauffeurs: number[] = [];

  // Statistics
  totalChauffeurs = 0;
  activeChauffeurs = 0;
  pendingValidation = 0;
  averageRating = 0;

  // Search and filters
  searchQuery = '';
 

  // Pagination
  currentPage = 1;
  itemsPerPage = 10;
  totalPages = 1;

  // View mode
  viewMode: 'table' | 'grid' = 'table';

  // Sorting
  sortField = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  // Loading state
  isLoading = false;

  constructor(
    private chauffeurService: ChauffeurService,
    private alertController: AlertController,
    private toastController: ToastController,
    private modalController: ModalController
  ) {}

  ngOnInit() {
    this.loadChauffeurs();
  }

  loadChauffeurs() {
    this.isLoading = true;
     this.chauffeurService.getChauffeurs().subscribe({
    next: (response) => {
       this.chauffeurs = response.content;
       this.filteredChauffeurs = [...this.chauffeurs];
       this.calculateStatistics();
       this.updatePagination();
       this.isLoading = false;
     },
      error: (error) => {
       console.error('Error loading chauffeurs:', error);
       this.showToast('Erreur lors du chargement des chauffeurs', 'danger');
       this.filteredChauffeurs = [...this.chauffeurs];
    this.calculateStatistics();
    this.updatePagination();
    this.isLoading = false;
       }
   });
  }

  calculateStatistics() {
    this.totalChauffeurs = this.chauffeurs.length;
    this.chauffeurService.getActiveChauffeursCount().subscribe({
      next: (count) => {
        this.activeChauffeurs = count;
      },
      error: (err) => {
        console.error('Erreur lors de la récupération du nombre de chauffeurs actifs :', err);
      }
    });
  }

  onSearch() {
    this.applyFilters();
  }

  onFilterChange() {
    this.applyFilters();
  }

  applyFilters() {
    this.filteredChauffeurs = this.chauffeurs.filter(chauffeur => {
      const matchesSearch = !this.searchQuery ||
        chauffeur.nom.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        chauffeur.prenom.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        chauffeur.email.toLowerCase().includes(this.searchQuery.toLowerCase()) ||
        chauffeur.telephone.includes(this.searchQuery);

     

      
      

      return matchesSearch 
    });

    this.currentPage = 1;
    this.updatePagination();
  }

  resetFilters() {
    this.searchQuery = '';
    this.filteredChauffeurs = [...this.chauffeurs];
    this.currentPage = 1;
    this.updatePagination();
  }

  sortBy(field: string) {
    if (this.sortField === field) {
      this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc';
    } else {
      this.sortField = field;
      this.sortDirection = 'asc';
    }

    this.filteredChauffeurs.sort((a, b) => {
      let aValue = (a as any)[field];
      let bValue = (b as any)[field];

      if (field === 'dateInscription') {
        aValue = new Date(aValue).getTime();
        bValue = new Date(bValue).getTime();
      }

      if (aValue < bValue) return this.sortDirection === 'asc' ? -1 : 1;
      if (aValue > bValue) return this.sortDirection === 'asc' ? 1 : -1;
      return 0;
    });

    this.updatePagination();
  }

  updatePagination() {
    this.totalPages = Math.ceil(this.filteredChauffeurs.length / this.itemsPerPage);
    const startIndex = (this.currentPage - 1) * this.itemsPerPage;
    const endIndex = startIndex + this.itemsPerPage;
    this.paginatedChauffeurs = this.filteredChauffeurs.slice(startIndex, endIndex);
  }

  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.updatePagination();
    }
  }

  getPageNumbers(): number[] {
    const pages: number[] = [];
    const maxVisiblePages = 5;
    let startPage = Math.max(1, this.currentPage - Math.floor(maxVisiblePages / 2));
    let endPage = Math.min(this.totalPages, startPage + maxVisiblePages - 1);

    if (endPage - startPage + 1 < maxVisiblePages) {
      startPage = Math.max(1, endPage - maxVisiblePages + 1);
    }

    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }

    return pages;
  }

  



 

  

  async deleteChauffeur(chauffeur: Chauffeur) {
    const alert = await this.alertController.create({
      header: 'Confirmer la suppression',
      message: `Êtes-vous sûr de vouloir supprimer le chauffeur ${chauffeur.prenom} ${chauffeur.nom} ?`,
      buttons: [
        {
          text: 'Annuler',
          role: 'cancel'
        },
        {
          text: 'Supprimer',
          role: 'destructive',
          handler: () => {
            this.performDeleteChauffeur(chauffeur);
          }
        }
      ]
    });

    await alert.present();
  }

  private async performDeleteChauffeur(chauffeur: Chauffeur) {
   
     
       this.chauffeurService.deleteChauffeur(chauffeur.id!).subscribe({

  next: async () => {
    this.chauffeurs = this.chauffeurs.filter(c => c.id !== chauffeur.id);
    this.applyFilters();
    this.calculateStatistics();

    const toast = await this.toastController.create({
      message: `Chauffeur ${chauffeur.prenom} ${chauffeur.nom} supprimé avec succès`,
      duration: 2000,
      color: 'success'
    });
    toast.present();
  },
  error: async () => {
    const toast = await this.toastController.create({
      message: 'Erreur lors de la suppression du chauffeur',
      duration: 2000,
      color: 'danger'
    });
    toast.present();
  }
});

    
  }

 

  private async showToast(message: string, color: string = 'primary') {
    const toast = await this.toastController.create({
      message,
      duration: 2000,
      color
    });
    toast.present();
  }
}
