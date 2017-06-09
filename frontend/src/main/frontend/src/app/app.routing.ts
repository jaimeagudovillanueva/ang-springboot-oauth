import { Routes, RouterModule } from '@angular/router';

import { FormularioLoginComponent } from './login/components/formulario-login.component';
import { SeleccionPerfilComponent } from './login/components/seleccion-perfil.component';
import { PrincipalComponent } from './principal/principal.component';
import { ListadoPersonasComponent } from './personas/components/listado/persona-listado.component';
import { PersonaComponent } from './personas/components/persona.component';

const ROUTES: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full'},
  { path: 'login', component: FormularioLoginComponent },
  { path: 'perfil', component: SeleccionPerfilComponent },
  { path: 'home', component: PrincipalComponent },
  { path: 'personas', component: ListadoPersonasComponent },
  { path: 'personas/:id', component: PersonaComponent} 
]
export const routing = RouterModule.forRoot(ROUTES);