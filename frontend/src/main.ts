import { bootstrapApplication } from '@angular/platform-browser';
import {
  RouteReuseStrategy,
  provideRouter,
  withPreloading,
  PreloadAllModules
} from '@angular/router';
import { IonicRouteStrategy, provideIonicAngular } from '@ionic/angular/standalone';
import { provideHttpClient, withInterceptors } from '@angular/common/http'; // ✅ update ici
import { routes } from './app/app.routes';
import { AppComponent } from './app/app.component';
import { addIcons } from 'ionicons';
import {
  gridOutline,
  peopleOutline,
  carOutline,menuOutline,
  chevronDownOutline,
  logOutOutline,
  carSportOutline,
  mapOutline,
  calendarOutline,
  barChartOutline,
  settingsOutline,
  personAddOutline,
  checkmarkCircleOutline,
  cashOutline,
  star,
  starOutline,
  trashOutline,
  chevronUpOutline,
  searchOutline,addOutline, downloadOutline, timeOutline, refreshOutline,
  warningOutline,busOutline
} from 'ionicons/icons';
import { authInterceptor } from './app/interceptors/auth.interceptor';
import { SessionExpiredInterceptor } from './app/interceptors/session-expired.interceptor'; 

addIcons({
  gridOutline,
  peopleOutline,
  carOutline,'menu-outline': menuOutline,
  'chevron-down-outline': chevronDownOutline,
  'log-out-outline': logOutOutline,
  'car-sport-outline': carSportOutline,
  'map-outline': mapOutline,
  'calendar-outline': calendarOutline,
  'bar-chart-outline': barChartOutline,
  'settings-outline': settingsOutline,
  'person-add-outline': personAddOutline,
  'checkmark-circle-outline': checkmarkCircleOutline,
  'cash-outline': cashOutline,
  'star': star,
  'star-outline': starOutline,
  'trash-outline': trashOutline,
  'chevron-up-outline': chevronUpOutline,
  'search-outline': searchOutline, 'add-outline': addOutline,
  'download-outline': downloadOutline,
  'time-outline': timeOutline,
  'refresh-outline': refreshOutline, 
  'warning-outline': warningOutline,
  'bus-outline': busOutline,
});

bootstrapApplication(AppComponent, {
  providers: [
    { provide: RouteReuseStrategy, useClass: IonicRouteStrategy },
    provideIonicAngular(),
    provideRouter(routes, withPreloading(PreloadAllModules)),
    provideHttpClient(
      withInterceptors([authInterceptor , SessionExpiredInterceptor]) // ✅ injection de l'intercepteur ici
    )
  ],
});
