import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ReactiveFormsModule, FormsModule} from '@angular/forms';

import {FormularioLoginComponent} from './components/formulario-login.component';

import {LoginService} from './login.service';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule
  ],
  declarations: [
    FormularioLoginComponent
  ],
  providers: [
    LoginService
  ],
  entryComponents: []
})

export class LoginModule {
}