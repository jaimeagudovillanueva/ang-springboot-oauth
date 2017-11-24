import {Injectable} from '@angular/core';
import {Http, Headers, RequestOptions} from '@angular/http'
import {DefaultUrlSerializer, UrlSegment, UrlTree} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import 'rxjs/add/operator/toPromise';

@Injectable()
export abstract class ListadoService {

  public baseUrl: string = null;

  private headers: Headers = null;

  private access_token = null;

  public static parseParamsToQueryString(queryParams): string {
    let query_string: string = new UrlSegment('', queryParams).toString();
    query_string = query_string.replace(/;/g, '&');
    return query_string;
  }

  public search(filtroParams) {

    return this.http.post(this.baseUrl, JSON.stringify(filtroParams))
    .toPromise()
    .then(
        resultados => resultados.json()
      )
      .catch(
        ListadoService.ocurrioUnError
      );
  }
  
  public request(queryParams, filtroParams) {
    return this.http.request(this.baseUrl + "?do=it"
      + ListadoService.parseParamsToQueryString(queryParams)
      + ListadoService.parseParamsToQueryString(filtroParams)
    )
      .toPromise()
      .then(
        resultados => resultados.json()
      )
      .catch(
        ListadoService.ocurrioUnError
      );
  }

  public abstract getList(filtroParams): Object;

  public get(id: number)
  {
    return this.http.get((id===null)?this.baseUrl:this.baseUrl+'/'+id)
      .toPromise()
      .then(
        resultados => resultados.json()
      )
      .catch(
        ListadoService.ocurrioUnError
      );
  }

  constructor(public  http: Http) {
  }

  public static ocurrioUnError(error) {
    return Promise.reject(error.message || error);
  }
  
  public queryLink(URL: string): Observable<any[]> {
    return this.http.request(URL).map((res: any) => res.json());
  }

}
