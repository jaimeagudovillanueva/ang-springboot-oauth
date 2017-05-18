import {Injectable} from '@angular/core';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';
import {UtilsService} from '../../services/utils.service';

import 'rxjs/Rx';

@Injectable()
export class PersonaService {

  private baseUrl: string = null;

  constructor(public http: Http) {
    this.baseUrl = UtilsService.getConfigParam("rest_url_base");
  }

  query(URL: string, params?: Array<string>): Observable<any[]> {
    let queryURL: string = `${this.baseUrl}${URL}`;
    if (params) {
      queryURL = `${queryURL}?${params.join('&')}`;
    }

    return this.http.request(queryURL).map((res: any) => res.json());
  }

  queryLink(URL: string): Observable<any[]> {
    return this.http.request(URL).map((res: any) => res.json());
  }

  getPersonas(page: number, texto?: string): Observable<any[]> {
    let parametros: Array<string> = [`page=${page}`];
    if (texto) {
     parametros.push(`filtro=${texto}`);
    } 
    
    return this.query('/personas', parametros);
  }

  getPersona(id: number): Observable<any[]> {
    return this.query(`/personas/${id}`);
  }
}

export var PERSONAS_PROVIDERS: Array<any> = [
  {provide: PersonaService, useClass: PersonaService}
];
