import { Component, OnInit } from '@angular/core';
import { IonicModule, AlertController, ToastController } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Client } from '../../../models/client.model';
import { ClientService } from '../../../services/client.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-clients',
  templateUrl: './clients.component.html',
  styleUrls: ['./clients.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule, FormsModule]
})
export class ClientsComponent implements OnInit {
  // Données
  clients: Client[] = [];
  filteredClients: Client[] = [];
  paginatedClients: Client[] = [];

  // Statistiques
  totalClients = 0;
  activeClients = 0;
  newClientsThisMonth = 0;
  averageRating = 0;

  // Recherche
  searchQuery = '';

  // Pagination
  currentPage = 1;
  itemsPerPage = 10;
  totalPages = 1;

  // Tri
  sortField = '';
  sortDirection: 'asc' | 'desc' = 'asc';

  // État de chargement
  isLoading = false;

  constructor(
    private clientService: ClientService,
    private alertController: AlertController,
    private toastController: ToastController
  ) {}

  ngOnInit() {
    this.loadClients();
  }

 loadClients() {
  this.isLoading = true;

  this.clientService.getClients(this.currentPage, this.itemsPerPage).subscribe({
    next: (response) => {
      const clients = response.content;

      this.clients = clients.map(client => ({
        ...client,
        nombreTrajets: client.historiqueReservation?.length || 0,
        noteGlobale: 4.5, // à adapter
      }));

      this.filteredClients = [...this.clients];
      this.totalClients = response.totalElements;
      this.totalPages = response.totalPages;
      this.calculateStatistics();
      this.updatePagination();
      this.isLoading = false;
    },
    error: (error) => {
      console.error('Erreur lors du chargement des clients :', error);
      this.clients = this.clientService.getMockClients();
      this.filteredClients = [...this.clients];
      this.calculateStatistics();
      this.updatePagination();
      this.isLoading = false;
    }
  });
}


  calculateStatistics() {
    this.totalClients = this.clients.length;
    this.clientService.getClientsActifCount().subscribe({
  next: (count) => {
    this.activeClients = count;
  },
  error: (err) => {
    console.error('Erreur lors de la récupération du nombre de clients actifs :', err);
  }
});

    const currentMonth = new Date().getMonth();
    const currentYear = new Date().getFullYear();
    this.newClientsThisMonth = this.clients.filter(c => {
      if (!c.dateInscription) return false;
      const d = new Date(c.dateInscription);
      return d.getMonth() === currentMonth && d.getFullYear() === currentYear;
    }).length;

    const clientsWithRating = this.clients.filter(c => c.noteGlobale);
    if (clientsWithRating.length > 0) {
      const totalRating = clientsWithRating.reduce((sum, c) => sum + (c.noteGlobale || 0), 0);
      this.averageRating = Math.round((totalRating / clientsWithRating.length) * 10) / 10;
    }
  }

  onSearch() {
    this.applyFilters();
  }

  applyFilters() {
    this.filteredClients = this.clients.filter(client => {
      const query = this.searchQuery.toLowerCase();
      return (
        !this.searchQuery ||
        client.nom.toLowerCase().includes(query) ||
        client.prenom.toLowerCase().includes(query) ||
        client.email.toLowerCase().includes(query) ||
        client.telephone.includes(this.searchQuery)
      );
    });

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

    this.filteredClients.sort((a, b) => {
      let aValue = (a as any)[field];
      let bValue = (b as any)[field];

      if (field === 'dateInscription') {
        aValue = new Date(aValue).getTime();
        bValue = new Date(bValue).getTime();
      }

      return this.sortDirection === 'asc' ? aValue - bValue : bValue - aValue;
    });

    this.updatePagination();
  }

  updatePagination() {
    this.totalPages = Math.ceil(this.filteredClients.length / this.itemsPerPage);
    const start = (this.currentPage - 1) * this.itemsPerPage;
    this.paginatedClients = this.filteredClients.slice(start, start + this.itemsPerPage);
  }

  goToPage(page: number) {
    if (page >= 1 && page <= this.totalPages) {
      this.currentPage = page;
      this.updatePagination();
    }
  }

  getPageNumbers(): number[] {
    const pages: number[] = [];
    const maxPages = 5;
    let start = Math.max(1, this.currentPage - Math.floor(maxPages / 2));
    let end = Math.min(this.totalPages, start + maxPages - 1);

    if (end - start < maxPages - 1) {
      start = Math.max(1, end - maxPages + 1);
    }

    for (let i = start; i <= end; i++) {
      pages.push(i);
    }

    return pages;
  }

  async deleteClient(client: Client) {
    const alert = await this.alertController.create({
      header: 'Confirmer la suppression',
      message: `Êtes-vous sûr de vouloir supprimer le client ${client.prenom} ${client.nom} ?`,
      buttons: [
        { text: 'Annuler', role: 'cancel' },
        {
          text: 'Supprimer',
          role: 'destructive',
          handler: () => this.performDeleteClient(client)
        }
      ]
    });

    await alert.present();
  }

  private async performDeleteClient(client: Client) {
    this.clientService.deleteClient(client.id!).subscribe({
  next: async () => {
    this.clients = this.clients.filter(c => c.id !== client.id);
    this.applyFilters();
    this.calculateStatistics();

    const toast = await this.toastController.create({
      message: `Client ${client.prenom} ${client.nom} supprimé avec succès`,
      duration: 2000,
      color: 'success'
    });
    toast.present();
  },
 error: async (err) => {
  console.error('Erreur HTTP lors de la suppression du client :', err);
  const toast = await this.toastController.create({
    message: 'Erreur lors de la suppression du client',
    duration: 2000,
    color: 'danger'
  });
  toast.present();
}
});

  }
}
