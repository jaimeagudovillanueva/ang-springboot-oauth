import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule,  ReactiveFormsModule } from '@angular/forms';
import { Http, HttpModule } from '@angular/http';
import { LoginModule } from './login/login.module';
import { RouteReuseStrategy } from '@angular/router';

import { NotificationsService, SimpleNotificationsModule } from 'angular2-notifications';
import { routing } from './app.routing';
import { CustomReuseStrategy } from './app.reuse.strategy';

import { AppComponent } from './app.component';
import { PlantillaComponent } from './base/plantilla.component';
import { PrincipalComponent } from './principal/principal.component';
import { PersonaComponent } from './personas/components/persona.component';
import { ListadoPersonasComponent } from './personas/components/listado/persona-listado.component';
import { PersonaFiltroComponent } from './personas/components/filtro-listado/persona-filtro.component';

import { SessionService } from './services/session.service';
import { sessionFactoryProvider } from './services/sessionFactoryProvider';
import { SecurityService } from './services/security.service';
import { HttpService } from './services/http.service';

@NgModule({
  declarations: [
    AppComponent,
    PlantillaComponent,
    PrincipalComponent,
    ListadoPersonasComponent,
    PersonaComponent,
    PersonaFiltroComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    ReactiveFormsModule,
    HttpModule,
    routing,
    LoginModule,
    SimpleNotificationsModule.forRoot()
  ],
  providers: [
    NotificationsService,
    SecurityService,
    {provide: SessionService, useFactory: sessionFactoryProvider}, 
    {provide: Http, useClass: HttpService},
    {provide: RouteReuseStrategy, useClass: CustomReuseStrategy}
  ],
  entryComponents: [
    PersonaFiltroComponent
  ],
  bootstrap: [AppComponent]
})
export class AppModule {}