import { Component, Input, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { filter } from 'rxjs/operators';
import { ChauffeurService } from 'src/app/services/chauffeur.service';
import { ClientService } from 'src/app/services/client.service';

interface MenuItem {
  title: string;
  icon: string;
  route: string;
  badge?: number;
  active?: boolean;
}

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule]
})
export class SidebarComponent implements OnInit {
  @Input() collapsed = false;
  
  activeClientsCount: number = 0;
  activeChauffeursCount: number = 0;
  menuItems: MenuItem[] = [
    {
      title: 'Dashboard',
      icon: 'grid-outline',
      route: '/admin/dashboard'
    },
    {
      title: 'Clients',
      icon: 'people-outline',
      route: '/admin/clients',
    
    },
    {
      title: 'Chauffeurs',
      icon: 'car-outline',
      route: '/admin/chauffeurs',
    
    },
    {
      title: 'Stations',
      icon: 'bus-outline',
      route: '/admin/stations'
    },
    {
      title: 'Réclamation',
      icon: 'warning-outline',
      route: '/admin/reservations',
    },
    {
      title: 'Rapports',
      icon: 'bar-chart-outline',
      route: '/admin/reports'
    },
  ];

  constructor(private router: Router,private clientService: ClientService,private chauffeurService:ChauffeurService) {
    // Listen to route changes to update active menu item
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe((event: NavigationEnd) => {
        this.updateActiveMenuItem(event.url);
      });

    // Set initial active menu item
    this.updateActiveMenuItem(this.router.url);
  }
  ngOnInit() {
    this.hetActiveClients();
    this.hetActiveChauffeurs();
  }

  private updateActiveMenuItem(currentUrl: string) {
    this.menuItems.forEach(item => {
      item.active = currentUrl.includes(item.route);
    });
  }

  navigateTo(route: string) {
    this.router.navigate([route]);
  }
  hetActiveClients(){
    this.clientService.getClientsActifCount().subscribe({
    next: (count) => {
      this.activeClientsCount = count;
      this.menuItems[1].badge = count; // garde cette ligne si tu veux afficher un badge
    },
    error: (err) => {
      console.error('Erreur lors de la récupération du nombre de clients actifs :', err);
    }
  });
  }
  hetActiveChauffeurs(){
    this.chauffeurService.getActiveChauffeursCount().subscribe({
      next: (count) => {
        this.activeChauffeursCount = count;
        

      },
      error: (err) => {
        console.error('Erreur lors de la récupération du nombre de chauffeurs actifs :', err);
      }
    });
  }

}

