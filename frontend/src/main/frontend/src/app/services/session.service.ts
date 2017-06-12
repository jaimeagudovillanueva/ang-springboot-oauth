
import {Injectable} from '@angular/core';
import {Http} from '@angular/http'
import {DefaultUrlSerializer, UrlSegment, UrlTree} from '@angular/router';
import 'rxjs/add/operator/toPromise';
import {Config} from '../config/config'
import {UtilsService} from './utils.service';
import {SecurityService} from './security.service';

@Injectable()
export class SessionService {

  static instance: SessionService;
  static isCreating: Boolean = false;

  public authenticated: boolean;

  private perfilSeleccionado: Object = null;

  private access_token: string;

  private refresh_token: string;

  private roles: Array <any>;

  private scope: string;

  private expires_in: number;

  private login_time: number;

  private username: string;

  private session_time_out: number=null;

  public getPerfilSeleccionado(): Object
  {
    return this.perfilSeleccionado;
  } 

  public getUsername(): string
  {
    return this.username;
  }

  public getAccessToken(): string
  {
    return this.access_token;
  }

  public getRoles(): Array<any>
  {
    return this.roles;
  }

  public getTimeOutMin(): number
  {
    return this.session_time_out===null ? null: Math.round(this.session_time_out/60);    
  }

  public getTimeOut(): number
  {  
    return this.session_time_out;
  }


  public expandirContenido()
  {
      localStorage.setItem('width','none');
  }
  

  public expandirContraerContenido()
  {
     if(localStorage.getItem('width')=='none')
     {        
         localStorage.setItem('width','1000px');
     }
     else{      
         localStorage.setItem('width','none'); 
     }
  }
  

  public getContainerWidth(): string
  {
    if(localStorage.getItem('width'))return localStorage.getItem('width');
    else return "1000px";
  }

  public constructor() {
    if (SessionService.instance == null) {
      console.log("Creando sessionService");
      if (!SessionService.isCreating) {
        throw new Error("You can't call new in Singleton instances! Call SingletonService.getInstance() instead.");
      }
      else
      {      
          setInterval(() => {   
            if(this.session_time_out!==null)this.session_time_out-=1;            
          },1000);      
      }

    }
    else return SessionService.instance;
  }


  static getInstance() {
    if (SessionService.instance == null) {
      SessionService.isCreating = true;
      SessionService.instance = new SessionService();      
      SessionService.instance.reloadFromSessionStore();
      SessionService.isCreating = false;
    }

    return SessionService.instance;
  }


  public reloadFromSessionStore()
  {
    if(localStorage.getItem('access_token') != null)
    {
      this.access_token = localStorage.getItem('access_token');
      this.refresh_token =localStorage.getItem('refresh_token');
      this.roles = JSON.parse(localStorage.getItem('roles'));
      this.scope = localStorage.getItem('scope');
      this.expires_in = +localStorage.getItem('expires_in');
      this.login_time = +localStorage.getItem('login_time');    
      let ahora=Math.round(new Date().getTime()/1000);
      this.username= localStorage.getItem('username');     
      this.session_time_out=this.expires_in-(ahora-this.login_time); 
      this.authenticated = true;
      this.perfilSeleccionado = localStorage.getItem('permiso');
    }
  }

  public setSessionFromRequest(username: string, response: any) {
    localStorage.setItem('username', username);
    localStorage.setItem('access_token', response.access_token);
    localStorage.setItem('refresh_token', response.refresh_token);
    localStorage.setItem('roles', JSON.stringify(response.user.permisos));
    localStorage.setItem('scope', response.scope);
    localStorage.setItem('expires_in', response.expires_in);
    localStorage.setItem('login_time', Math.round((new Date().getTime()/1000)).toString());

    this.access_token = response.access_token;
    this.refresh_token = response.refresh_token;
    this.scope = response.scope;
    this.roles = response.user.permisos;
    this.expires_in = response.expires_in;   
    this.username = response.username;

    this.login_time = new Date().getTime()/1000;
    let ahora = new Date().getTime()/1000;
    this.authenticated = true;

    this.session_time_out=this.expires_in-(ahora-this.login_time); 
  }

  public setPermisoSeleccionado(response: any) {
    localStorage.setItem('permiso', response);
    this.perfilSeleccionado = response;
  }

  public clear() {

    localStorage.clear();
    
    this.refresh_token = null;
    this.roles = null;
    this.scope = null;
    this.expires_in = null;
    this.access_token = null;
    this.login_time = null;
    this.authenticated = false;
    this.session_time_out = null;
    this.username = null;
    this.perfilSeleccionado = null;
  }
}