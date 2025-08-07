import { Component, OnInit, HostListener } from '@angular/core';
import { Router } from '@angular/router';
import { SidebarComponent } from '../sidebar/sidebar.component';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.scss'],
  standalone: true,
  imports: [
    CommonModule,
    IonicModule,
    SidebarComponent
  ]
})
export class LayoutComponent implements OnInit {
  sidebarCollapsed = false;
  showUserMenu = false;

  constructor(private router: Router) {}

  ngOnInit() {
    // Check screen size on init
    this.checkScreenSize();
  }

  toggleSidebar() {
    this.sidebarCollapsed = !this.sidebarCollapsed;
  }

  toggleUserMenu() {
    this.showUserMenu = !this.showUserMenu;
  }

  logout() {
    localStorage.clear();
    sessionStorage.clear();
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.checkScreenSize();
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: any) {
    // Close user menu when clicking outside
    if (!event.target.closest('.user-profile') && !event.target.closest('.user-menu')) {
      this.showUserMenu = false;
    }
  }

  private checkScreenSize() {
    // Auto-collapse sidebar on mobile devices
    if (window.innerWidth < 768) {
      this.sidebarCollapsed = true;
    }
  }
}
