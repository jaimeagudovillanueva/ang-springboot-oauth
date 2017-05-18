import {Component, OnInit, EventEmitter, Output, forwardRef, Inject, OnDestroy} from '@angular/core';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {UtilsService} from '../../services/utils.service'
import {Http, Headers, RequestOptions} from '@angular/http'
import {ActivatedRoute, Router} from "@angular/router";

//Popup emergente notificando acción
import {NotificationsService, SimpleNotificationsComponent, PushNotificationsService} from 'angular2-notifications';

import {SessionService} from '../../services/session.service';
import {LoginService} from '../login.service';
import DateTimeFormat = Intl.DateTimeFormat;

@Component({
  selector: 'formulario-login',
  templateUrl: './formulario-login.component.html'
})

export class FormularioLoginComponent implements OnInit {

  public boton_acceder_disabled=false;

  constructor(private _notificacionService: NotificationsService,
              private route: ActivatedRoute,
              private router: Router,
              private  http: Http,
              private loginService: LoginService,
              private sessionService: SessionService) {
  }

  login(event, username, password) {

    this.boton_acceder_disabled=true;
    event.preventDefault();
    this.loginService.login(username, password)
      .subscribe(
        response => {
          /**
           * Guadamos en sesión
           */
          this.sessionService.setSessionFromRequest(username, response);
          this._notificacionService.success('Login',"Acceso permitido");
          this.router.navigateByUrl('home',{skipLocationChange:true});
        },
        err => {
          this._notificacionService.error('Login',err);
          this.boton_acceder_disabled=false;
        }
      );
  }

  logout()
  {
    this.loginService.logout();
  }

  ngOnInit() {
    let url_actual: string = this.route.snapshot.url[0].path;   
    if(url_actual.indexOf("expired")!=-1)
    {
        this.loginService.logout(true);
    } 
  }

  ngOnDestroy() {
  }
}