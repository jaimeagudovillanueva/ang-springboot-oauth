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
  // shouldDetach distingue las vistas que se deben guardar cacheadas(por ejemplo listados con paginacion y filtros)
  { path: 'personas', component: ListadoPersonasComponent, data: {shouldDetach: true} },
  // shouldKeep distingue las vistas que mantienen las páginas cacheadas
  { path: 'personas/:id', component: PersonaComponent, data: {shouldKeep: true}}
]
// Los parametros shouldDetach y shouldKepp se podrían llamar como se quiera, no son palabras reservadas. 
// Se hace referencia a ellos en app.reuse.strategy.ts 
export const routing = RouterModule.forRoot(ROUTES);