import { Routes, RouterModule } from '@angular/router';

import { FormularioLoginComponent } from './login/components/formulario-login.component';
import { PrincipalComponent } from './principal/principal.component';
import { PersonaListComponent } from './personas/components/persona.list.component';
import { PersonaComponent } from './personas/components/persona.component';

const ROUTES: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full'},
  { path: 'login', component: FormularioLoginComponent },
  { path: 'home', component: PrincipalComponent },
  { path: 'personas', component: PersonaListComponent },
  { path: 'personas/:id', component: PersonaComponent} 
]
export const routing = RouterModule.forRoot(ROUTES);