import {Component, OnInit } from '@angular/core';
import {FormGroup, FormControl, FormBuilder} from '@angular/forms';
import {ActivatedRoute, Router} from "@angular/router";

import {NotificationsService, SimpleNotificationsComponent, PushNotificationsService} from 'angular2-notifications';

import {LoginService} from '../login.service';
import {SessionService} from '../../services/session.service';

@Component({
  selector: 'seleccion-perfil',
  templateUrl: './seleccion-perfil.component.html'
})

export class SeleccionPerfilComponent implements OnInit {

  ambitos = [];
  perfilesAfterChangeEvent = [];
  perfilForm : FormGroup;

  constructor(private _notificacionService: NotificationsService,
              private route: ActivatedRoute,
              private router: Router,
              private loginService: LoginService,
              private sessionService: SessionService,
              private fb: FormBuilder) {   

      this.perfilForm = fb.group({
        ambito: String,
        perfil: String
      });
 }

 seleccionarPerfil() {
    const ambitoSeleccionado = this.perfilForm.get('ambito').value;   
    const perfilSeleccionado = this.perfilForm.get('perfil').value;   
    this.loginService.perfil(ambitoSeleccionado, perfilSeleccionado)
        .subscribe(
            response => {
               this.sessionService.setPermisoSeleccionado(response);
               this.router.navigateByUrl('home', {skipLocationChange:true});
            }, 
            err => {
                this._notificacionService.error('Perfil',err);
            }
        )
 }

 cambioAmbito() {

    const ambitoSeleccionado = this.perfilForm.get('ambito').value;   
    this.perfilesAfterChangeEvent = [];
    this.sessionService.getRoles().filter(rol => rol.ambito.codigo == ambitoSeleccionado)
        .forEach( rol => this.perfilesAfterChangeEvent.push(rol.perfil));    
 }

 ngOnInit() {
    this.sessionService.getRoles().forEach( rol => {
            if (!this.ambitos.find(ambito => ambito.codigo === rol.ambito.codigo)) {
                this.ambitos.push(rol.ambito)
            }
        }
    );   
 }
}