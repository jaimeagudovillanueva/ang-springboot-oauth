import {Config} from '../config/config';


export class UtilsService {

  /**
   * Devuelve el valor de un parámetro de configuración
   *
   * @param path
   * @param environment
   * @returns {any}
   */
  static getConfigParam(path: string, environment = 'global'): any {
    return UtilsService.getDescendantProp(Config, environment + "." + path);
  }


  /**
   * Devuelve una propiedad anidada en un objeto
   *
   * @param obj
   * @param path
   */
  static getDescendantProp = (obj, path) => (
    path.split('.').reduce((acc, part) => acc && acc[part], obj)
  );

  /**
   * Parsea un error que viene de la llamada al servicio rest
   *
   * @param err
   * @returns {string}
   */
  static parseErrorRest(err) {

    let mensaje_error: string;
    let error_code = null;
    let error_message = null;
    let json_respuesta = null;
    let duplicados = [];
    let enlazado = [];

    if (err._body) {
      try {
        json_respuesta = JSON.parse(err._body);
        if (json_respuesta.error) {

          if(json_respuesta.error.code) {

            if(json_respuesta.error.code===403||json_respuesta.error.code===401)return null;

            error_code = json_respuesta.error.code;
            error_message = json_respuesta.error.message;
            mensaje_error = 'Ocurrió un error en la petición de datos: (' + error_code + ') - ' + error_message;
            for (let error  of json_respuesta.error.exception) {
              mensaje_error += "\n";
              mensaje_error += error.message;
            }

            /**
             * Comprobamos cosas
             */
            //Excepción de elemento duplicado
            duplicados = mensaje_error.match(/Duplicate entry \'(.*)\' for/);
            if (duplicados != null) {
              if (duplicados.length >= 2) {
                mensaje_error = 'Elemento ' + duplicados[1] + ' duplicado.';
              }
            }


            enlazado = mensaje_error.match(/a foreign key constraint fails \(`(.*)`\./);
            if (enlazado != null) {
              if (enlazado.length >= 2) {
                mensaje_error = 'Existe una realación con el elemento ' + enlazado[1] + '.';
              }
            }


          } else {

            if (json_respuesta.error) {
              mensaje_error += json_respuesta.error;
              if (json_respuesta.error_description) {
                mensaje_error = json_respuesta.error_description;
              }
            }

          }


        }
      } catch (e) {

        try {
          json_respuesta = JSON.parse(err);
          mensaje_error = json_respuesta.error;

        } catch (e) {
          mensaje_error = 'Error grave en la petición de datos (1): ' + err.toString();
        }

      }
    } else{

      if(err.status)
      {
        if(err.status==404)
        {
             mensaje_error = 'El elemento no existe en la base de datos';
        }

      }
      else{
        mensaje_error = 'Error no identificado';
        console.log(err);
      }

    }

    return mensaje_error;
  }

  /**
 * Devuelve true si alguno de los elemento del array 1 está dentro del array2
 */
  static inArray(array_1: Array<any>,array_2: Array<any>)
  {
    return array_1.some((elemento_1,indice_1,array_datos_1)=>{
            let found=false;
            array_2.forEach(elemento_2=>{
                  if(elemento_2==elemento_1)found=true;
            });
            return found;
      });
  }

  /**
   * Dada una fecha, la devuelve formateada ('dd/MM/yyyy')
   * @param datePipe
   * @param date
   * @returns {any}
   */
  static getFomatedDate(datePipe, date)
  {
    let fecha_formateada = datePipe.transform(date, 'dd/MM/yyyy');

    if (fecha_formateada) return fecha_formateada;
    else return null;
  }

  public constructor() {
  }
}
