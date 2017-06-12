import {Injectable} from '@angular/core';
import {Http} from '@angular/http'
import {DefaultUrlSerializer, UrlSegment, UrlTree} from '@angular/router';
import 'rxjs/add/operator/toPromise';
import {ListadoService} from "../../services/listado.service";
import {UtilsService} from "../../services/utils.service";

@Injectable()
export class PersonaService extends ListadoService {

  public baseUrl = UtilsService.getConfigParam('rest_url_base') + '/personas';

  public getList(filtroParams = null) {
    return this.search(filtroParams);
  }

  public constructor(public  http: Http) {
    super(http);
  }
}