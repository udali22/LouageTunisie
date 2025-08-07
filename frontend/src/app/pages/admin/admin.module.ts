import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { IonicModule } from '@ionic/angular';
import { AdminRoutingModule } from './admin-routing.module';

import { LayoutComponent } from './layout/layout.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ClientsComponent } from './clients/clients.component';
import { ChauffeursComponent } from './chauffeurs/chauffeurs.component';

@NgModule({
  declarations: [

  ],
  imports: [
     LayoutComponent,
    SidebarComponent,
    DashboardComponent,
    ClientsComponent,
    ChauffeursComponent,
    CommonModule,
    IonicModule,
    AdminRoutingModule,
  ],
  exports: [
    LayoutComponent,
    SidebarComponent,
    DashboardComponent,
    ClientsComponent,
    ChauffeursComponent
  ]
})
export class AdminModule {
  
}
