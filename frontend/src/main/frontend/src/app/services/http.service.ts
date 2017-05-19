import {Injectable} from '@angular/core';
import {Http, XHRBackend, RequestOptions, Request, RequestOptionsArgs, Response, Headers} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {Router} from "@angular/router";
import {NotificationsService} from "angular2-notifications";
import {SessionService} from "./session.service";


@Injectable()
export class HttpService extends Http {
  constructor(
    backend: XHRBackend,
    defaultOptions: RequestOptions,
    private router: Router,
    private _notificacionService: NotificationsService,
    private sessionService : SessionService
  ) {
    super(backend, defaultOptions); // llama a la base con las dependencias
  }

  request(request: string | Request, options?: RequestOptionsArgs): Observable<Response> {

    if (!options) {
       options = { headers: new Headers() };
    }
    this.setHeaders(options);

    let observableRequest = super
      .request(request, options)
      .catch(this.catchErrors());
    return observableRequest;
  }

  /**
   * Interceptor para errores recibidos del API
   *
   **/
  private catchErrors() {
    return (res: Response) => {

      if (res.status === 401 || res.status === 403) {

        if(typeof res['_body'] !== 'undefined')
        {
          if(res['_body'].indexOf("expired")!=-1)
          {
            this._notificacionService.error('Sesión', "La sesión ha caducado");
            this.router.navigateByUrl('/expired',{skipLocationChange:true})      
          }
          else
          {
            this._notificacionService.error('Permisos', "No disponible de los permisos necesarios para acceder y/o realizar esta acción");
            if(this.sessionService.getAccessToken()==null) {
              this.router.navigateByUrl('/login',{skipLocationChange:true})
            }
          }
          
        }else{
        
            this._notificacionService.error('Permisos', "No disponible de los permisos necesarios para acceder y/o realizar esta acción");
            if(this.sessionService.getAccessToken()==null) {
              this.router.navigateByUrl('/login',{skipLocationChange:true})
            }
        }


      }

      return Observable.throw(res);
    };
  }

  /**
   * Interceptor para la configuración de cabeceras
   **/
  private setHeaders(objectToSetHeadersTo: Request | RequestOptionsArgs) {
    let headers = objectToSetHeadersTo.headers;
    headers.set('Content-Type', 'application/json');
    headers.set('Authorization', 'Bearer ' + this.sessionService.getAccessToken());
  }
}