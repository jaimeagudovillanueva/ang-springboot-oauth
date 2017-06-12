import { SessionService } from '../../services/session.service';
import { OnInit, ViewContainerRef, ViewChild, Input, ComponentFactoryResolver } from '@angular/core';
import { FormGroup } from "@angular/forms";
import { UtilsService } from '../../services/utils.service'
import { SecurityService } from '../../services/security.service';
import { Router, UrlSerializer } from '@angular/router';
import { DatePipe } from "@angular/common";

export abstract class ListadoComponent implements OnInit {

  //Accedemos al filtro del componente hijo (listado.marcas)
  @ViewChild('dynamicTarget', {read: ViewContainerRef})
  protected dynamicTarget: ViewContainerRef;

  datePipe: DatePipe;

  cargando: boolean = false;
  mensaje_error: string;

  opciones_paginacion: Array<number> = [5, 10, 50, 100, 200];

  page: number = 1;
  protected limit: number;
  pages: number;
  total: number;
  desde: number;
  hasta: number;

  filtros: Object = {};

  public contenidoFormularioFiltro: FormGroup;

  numero_paginas_intermedias: number = 3;
  array_paginas: Array<number> = [];

  resultados: Array<Object>;

  campo_unico: string;

  acciones: Array<any>;//Opciones que tendrá cada resultado o registro en la tabla
  columnas: Array<any>;//Elementos que contendrá cada columna de los resultados
  columna_orden: string;//Valor por el que se está ordenando la columna
  columna_orden_tipo: string = 'ASC';


  public canNavigateTo(path: string, methods: Array<any>, identificador:string=null)
  {
    var tree=this.router.createUrlTree([path,identificador]);
    var url= this.urlSerializer.serialize(tree);
    return this.securityService.canNavigateTo(path,methods);
  }


  listado() {
    if (this.contenidoFormularioFiltro) {
      this.filtros = {};
      for (let entry in this.contenidoFormularioFiltro.controls) {
        this.filtros[entry] = this.contenidoFormularioFiltro.controls[entry].value;
      }
      this.contenidoFormularioFiltro = null;
    } else {
      this.filtros['page'] = this.page;
    }

     this.filtros['orderBy'] = this.columna_orden;
     this.filtros['orderByType'] = this.columna_orden_tipo;
     this.filtros['limit'] = this.limit;

     console.log(this.filtros);

    this.listadoService.getList(
      this.filtros
    ).then(
      resultados => {

        this.resultados = resultados.content;
        this.page = resultados.page.number + 1;
        this.limit = resultados.page.size;
        this.pages = resultados.page.totalPages - 1;
        this.total = resultados.page.totalElements;


        this.hasta = (this.page * this.limit);
        if (this.hasta > this.total) this.hasta = this.total;
        this.desde = 1 + (this.page * this.limit) - this.limit;

        let iniciop = this.page - this.numero_paginas_intermedias;
        if (iniciop < 1) iniciop = 1;

        let finalp = this.page  + this.numero_paginas_intermedias;

        if(iniciop==1)finalp=this.page+ (this.numero_paginas_intermedias*2)-this.page+1;

        if (finalp > this.pages) finalp = this.pages;

        this.array_paginas = [];
        for (let pagina_actual = iniciop; pagina_actual <= finalp; pagina_actual++) {
          this.array_paginas.push(pagina_actual);
        }
        this.mensaje_error = null;
      }
    ).catch((err) => {
      this.mensaje_error = UtilsService.parseErrorRest(err);
    });
  }

  constructor(public listadoService,
              public resolver: ComponentFactoryResolver,
              public securityService: SecurityService,
              public router: Router,
              public urlSerializer: UrlSerializer
              ) {

    this.datePipe= new DatePipe('es');

    this.limit = +sessionStorage.getItem('limit');
    if (this.limit==0){
      sessionStorage.setItem('limit', "10");
      this.limit=10;
    }
  }

  private getDescendantProp = (obj, path) => (
    path.split('.').reduce((acc, part) => acc && acc[part], obj)
  );

  //Formateamos la salida del campo
  public smprint(objeto, path, tipo=null) {

    if (tipo == 'roles'){

       let cadena='';
       objeto[path].toString().split(',').forEach(e=>{
          e = e.replace("__", " ");
          e = e.replace("ROLE_", "");
          e = e.replace("_", " ");

          cadena+= '<span class="small">' + e + '</span><br/>';
       });

       return cadena;

    } else if (tipo == 'date'){
      return UtilsService.getFomatedDate(this.datePipe, this.getDescendantProp(objeto, path))
    } else {

      if (objeto.datos[path]) {
        return objeto.datos[path];
      }
      return this.getDescendantProp(objeto, path);
    }
  }

  ngOnInit() {
    this.listado();
  }

  public clickSiguiente() {
    this.page++;
    if (this.page > this.pages) this.page = this.pages;
    this.listado();
  }

  public clickPagina(pagina) {
    this.page = pagina;
    this.listado();
  }

  public clickPrimera() {
    this.page = 1;
    this.listado();
  }

   public clickUltima() {
    this.page = this.pages;
    this.listado();
  }


  public clickResultadosPorPagina(resultados) {
    this.page = 1;
    this.limit = resultados;
    sessionStorage.setItem('limit', this.limit.toString());
    this.listado();
  }

  public clickAnterior() {
    this.page--;
    if (this.page < 1) this.page = 1;
    this.listado();
  }

  public clickTituloColumna(event) {

    /**
     * Con esto extraemos el id
     */
    var target = event.target || event.srcElement || event.currentTarget;
    var idAttr = target.attributes.id;
    var idColumna = idAttr.nodeValue;

    if (this.columna_orden == idColumna) {
      if (this.columna_orden_tipo == 'ASC') this.columna_orden_tipo = 'DESC';
      else this.columna_orden_tipo = 'ASC';
    } else {
      this.columna_orden = idColumna;
    }
    this.listado();
  }

}
