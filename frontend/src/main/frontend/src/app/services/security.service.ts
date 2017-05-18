import {Injectable} from '@angular/core';
import {Http} from '@angular/http'
import {DefaultUrlSerializer, UrlSegment, UrlTree} from '@angular/router';
import 'rxjs/add/operator/toPromise';
import {Config} from '../config/config'
import {UtilsService} from './utils.service';
import {SessionService} from './session.service';

@Injectable()
export class SecurityService {

 constructor(private sessionService: SessionService)
 {
 }

  /**
   * Añade en el array de roles los roles heredados, según el árbol de herencia
   * de Symfony
   */
  private addRolesHeredados($array_roles)
  {

    console.log(this.sessionService);
  }

 /**
  * Esta funcion transforma los roles del tipo 
  * ROLE_CONSULTOR__ALBACETE EN ROLE_CONSULTOR_ 
  * Quitándoles el ambito  
  */
  private getRolesUsuario(roles_usuario: Array<any>)
  {
      let array_roles_arbol_return: Array<any>=[];      
      let array_roles_return: Array<any>=[];      

      roles_usuario.forEach((rol:string)=>{

          let rol_ambito=rol.split('__');

          // Tiene ámbito
          if(rol_ambito.length>1)
          {
              array_roles_return.push(rol_ambito[0]+'_');
          }
          else{
            // Not tiene ambito
              array_roles_return.push(rol_ambito[0]);
          }          
      });

      return {
        'roles': array_roles_return,
      }
  }

public isGranted(roles)
{
    let roles_usuario=this.sessionService.getRoles();  
    if(UtilsService.inArray(roles,this.getRolesUsuario(roles_usuario).roles)) 
    {
      return true;
    }
    return false;
}

/**
 * Devuelve verdero si se puede navegar a la url que se le pase con el método/s indicado/s GET,POST,PUT...
 */
  public canNavigateTo(url: string, metodos_url_ng: Array<any>)
  {
    if(this.sessionService.authenticated!==true)
    {
      if(url=='/login')return true;
      else return false;
    }

    let acceso_permitido=false;       

    this.sessionService.getAccessControl().forEach(element=>{

         let pattern: string=  element[0].path;
         pattern=pattern.replace(/\/rest/,'');
         let matches=url.match(pattern);
         let roles_usuario=this.sessionService.getRoles();       
         /**
          * Coincidencia en URL
          */
         if(matches)
         {
            let metodos_sf: Array<any>=element[0].methods;
            let roles_sf: Array<any>=element[1];            
           
             if(UtilsService.inArray(roles_sf,this.getRolesUsuario(roles_usuario).roles)) 
             {
                /**
                 * Coincidencia con Método GET PUT POST...
                 */
                if(UtilsService.inArray(metodos_url_ng,metodos_sf))
                {
                    acceso_permitido=true;
                }
             }
         }         
    });
    
   return acceso_permitido;
  }
}