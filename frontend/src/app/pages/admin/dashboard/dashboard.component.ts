import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { IonicModule } from '@ionic/angular';
import { CommonModule } from '@angular/common';
import { ClientService } from '../../../services/client.service';
import { ChauffeurService } from '../../../services/chauffeur.service';
import { StatisticsService } from '../../../services/statistics.service';


interface Statistic {
  label: string;
  value: string;
  icon: string;
  gradient: string;
  change: string;
  changeType: 'positive' | 'negative';
  changeIcon: string;
}

interface Activity {
  text: string;
  time: string;
  icon: string;
  color: string;
}

interface Performer {
  name: string;
  avatar: string;
  trips: number;
  rating: number;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss'],
  standalone: true,
  imports: [IonicModule, CommonModule]
})
export class DashboardComponent implements OnInit  {
  @ViewChild('revenueChart', { static: false }) revenueChart!: ElementRef<HTMLCanvasElement>;
  @ViewChild('bookingsChart', { static: false }) bookingsChart!: ElementRef<HTMLCanvasElement>;


  
  statistics: Statistic[] = [
    {
      label: 'Total Utilisateur',
      value: '1,247',
      icon: 'people-outline',
      gradient: 'linear-gradient(135deg, #667eea, #764ba2)',
      change: '+12.5%',
      changeType: 'positive',
      changeIcon: 'trending-up-outline'
    },
    {
      label: 'Total Voitures',
      value: '89',
      icon: 'car-sport-outline',
      gradient: 'linear-gradient(135deg, #f093fb, #f5576c)',
      change: '+8.2%',
      changeType: 'positive',
      changeIcon: 'trending-up-outline'
    },
    {
      label: 'Total Stations',
      value: '156',
      icon: 'bus-outline',
      gradient: 'linear-gradient(135deg, #4facfe, #00f2fe)',
      change: '+15.3%',
      changeType: 'positive',
      changeIcon: 'trending-up-outline'
    },
  ];
  recentActivities: Activity[] = [
    {
      text: 'Nouveau client inscrit: Ahmed Ben Ali',
      time: 'Il y a 5 minutes',
      icon: 'person-add-outline',
      color: 'linear-gradient(135deg, #667eea, #764ba2)'
    },
    {
      text: 'Trajet terminé: Tunis → Sousse',
      time: 'Il y a 12 minutes',
      icon: 'checkmark-circle-outline',
      color: 'linear-gradient(135deg, #43e97b, #38f9d7)'
    },
    {
      text: 'Nouveau chauffeur approuvé: Mohamed Triki',
      time: 'Il y a 25 minutes',
      icon: 'car-outline',
      color: 'linear-gradient(135deg, #f093fb, #f5576c)'
    },
    {
      text: 'Paiement reçu: 45 DT',
      time: 'Il y a 1 heure',
      icon: 'cash-outline',
      color: 'linear-gradient(135deg, #4facfe, #00f2fe)'
    }
  ];
  topPerformers: Performer[] = [
    {
      name: 'Karim Mansouri',
      avatar: 'assets/images/driver1.jpg',
      trips: 127,
      rating: 5
    },
    {
      name: 'Sami Bouazizi',
      avatar: 'assets/images/driver2.jpg',
      trips: 98,
      rating: 5
    },
    {
      name: 'Nabil Cherif',
      avatar: 'assets/images/driver3.jpg',
      trips: 87,
      rating: 4
    },
    {
      name: 'Youssef Karray',
      avatar: 'assets/images/driver4.jpg',
      trips: 76,
      rating: 4
    }
  ];

  constructor(
    private statsService: StatisticsService,
    private clientService: ClientService,
    private chauffeurService: ChauffeurService
  ) {}

  ngOnInit(): void {
    this.loadStatistics();
  }

  loadStatistics() {
    // Utiliser les nouvelles APIs pour les clients et chauffeurs actifs
    this.statsService.getUsersCount().subscribe({
      next: (count) => {
        this.statistics[0].value = count.toString();
      },
      error: (err) => {
        console.error("Erreur chargement clients actifs", err);
      
      }
    });

  
        this.statsService.getCarsCount().subscribe({
          next: (count) => {
            this.statistics[1].value = count.toString();
          },
          error: (err2) => {
            console.error("Erreur chargement chauffeurs", err2);
          }
        });

    this.statsService.getStationsCount().subscribe({
      next: (count) => {
        this.statistics[2].value = count.toString();
      },
      error: (err) => {
        console.error("Erreur chargement trajets", err);
      }
    });
  }

    


  
   
  }


 
