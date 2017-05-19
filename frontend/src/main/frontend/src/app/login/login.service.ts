import {Injectable} from '@angular/core';
import {Http, URLSearchParams, Response, Headers, RequestOptions } from '@angular/http';
import {Observable} from 'rxjs/Rx';
import {UtilsService} from '../services/utils.service';
import {Router} from "@angular/router";
import {NotificationsService, SimpleNotificationsComponent, PushNotificationsService} from 'angular2-notifications';
import {SessionService} from '../services/session.service'

@Injectable()
export class LoginService {

  private OauthLoginEndPointUrl = null;
  private clientId = null;
  private clientSecret = null;

  constructor(public http: Http,
              private router: Router,
              private _notificacionService: NotificationsService,
              private sessionService: SessionService) {

    this.OauthLoginEndPointUrl = UtilsService.getConfigParam('oauth_login_end_point_url');
    this.clientId = UtilsService.getConfigParam('oauth_client_id');
    this.clientSecret = UtilsService.getConfigParam('oauth_client_secret');
  }

  login(username, password): Observable<any> {

    let params: URLSearchParams = new URLSearchParams();
    params.set('username', username);
    params.set('password', password);
    params.set('grant_type', 'password');

    let headers = new Headers({
        "Content-Type": "application/x-www-form-urlencoded; charset=UTF-8",
        "Accept": "application/json",
        "Authorization": "Basic " + btoa(this.clientId + ':' + this.clientSecret)
    });

    let options = new RequestOptions({ headers: headers });

    return this.http.post(this.OauthLoginEndPointUrl, params, options
    ).map(this.handleData)
      .catch(this.handleError);
  }

  private handleData(res: Response) {
    let body = res.json();
    return body;
  }

  private handleError(error: any) {
    let errMsg = UtilsService.parseErrorRest(error);
    return Observable.throw(errMsg);
  }

  public logout(noMsg:boolean=false) {

    /**
     * Eliminar las cosas de ssession storage
     */

    this.sessionService.clear();
    if(noMsg==false)this._notificacionService.success("Logout", 'Sesión cerrada');
    this.router.navigateByUrl('/login',{skipLocationChange:true});
  }
}