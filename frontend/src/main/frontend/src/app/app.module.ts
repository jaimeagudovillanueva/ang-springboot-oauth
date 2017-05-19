import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule,  ReactiveFormsModule } from '@angular/forms';
import { Http, HttpModule } from '@angular/http';
import { LoginModule } from './login/login.module';

import { NotificationsService, SimpleNotificationsModule } from 'angular2-notifications';
import { routing } from './app.routing';

import { AppComponent } from './app.component';
import { PlantillaComponent } from './base/plantilla.component';
import { PrincipalComponent } from './principal/principal.component';
import { PersonaListComponent } from './personas/components/persona.list.component';
import { PersonaComponent } from './personas/components/persona.component';

import { PERSONAS_PROVIDERS } from './personas/services/persona.service';
import { SessionService } from './services/session.service';
import { sessionFactoryProvider } from './services/sessionFactoryProvider';
import { SecurityService } from './services/security.service';
import { HttpService } from './services/http.service';

@NgModule({
  declarations: [
    AppComponent,
    PlantillaComponent,
    PrincipalComponent,
    PersonaListComponent,
    PersonaComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    SimpleNotificationsModule,
    routing,
    LoginModule
  ],
  providers: [
    NotificationsService,
    PERSONAS_PROVIDERS,

    SecurityService,
    {
      provide: SessionService,
      useFactory: sessionFactoryProvider
    }, 
    {
      provide: Http,
      useClass: HttpService
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}