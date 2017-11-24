import {Component,OnInit, ComponentFactoryResolver, ComponentRef, AfterViewInit} from '@angular/core';
import {Router,UrlSerializer} from "@angular/router";
import {FormGroup} from "@angular/forms";

import {UtilsService} from '../../../services/utils.service'
import {SecurityService} from '../../../services/security.service';

import {ListadoComponent} from '../../../base/listado/listado.component'
import {PersonaService} from '../../services/persona.service'
import {PersonaFiltroComponent} from '../filtro-listado/persona-filtro.component';

@Component({
  selector: 'listado-personas',
  templateUrl: '../../../base/listado/listado.component.html',
  providers: [PersonaService]
})

export class ListadoPersonasComponent extends ListadoComponent implements OnInit, AfterViewInit {

  private componentReference: ComponentRef<PersonaFiltroComponent>;

  constructor(public listadoService: PersonaService,
              public resolver: ComponentFactoryResolver,
              public securityService: SecurityService,
              public router: Router,
              public urlSerializer: UrlSerializer
              ) {

    super(listadoService, resolver, securityService, router, urlSerializer);
    this.acciones = [
      {
        titulo: "Editar",
        icono: "fa-edit",
        path: "editar",
        methods: ['PUT']
      },
      {
        titulo: "Eliminar",
        icono: "fa-trash-o",
        path:  "eliminar",
        methods: ['DELETE']
      }
    ];

    this.columnas = [
      {
        campo_filtro: "nombre",
        campo: "nombre",
        titulo: "Nombre",
        tipo: "string",
        width: null
      },
      {
        campo_filtro: "primerApellido",
        campo: "primerApellido",
        titulo: "Primer apellido",
        tipo: "string",
        width: null
      },
      {
        campo_filtro: "segundoApellido",
        campo: "segundoApellido",
        titulo: "Segundo Apellido",
        tipo: "string",
        width: null
      },
      {
        campo_filtro: "nif",
        campo: "nif",
        titulo: "NIF",
        tipo: "string",
        width: 100
      }
    ];

    this.columna_orden = 'primerApellido';
    this.columna_orden_tipo = 'ASC';
    this.campo_unico = 'id';
  }


  ngAfterViewInit() {
    this.componentReference.instance.nombre_filtro = 'Personas';
  }

  onEnviar(formulario: FormGroup) {
    this.contenidoFormularioFiltro = formulario;
    this.listado();
  }

  ngOnInit() {
    this.listado();
    let componentFactory = this.resolver.resolveComponentFactory(PersonaFiltroComponent);
    this.componentReference = this.dynamicTarget.createComponent(componentFactory);
  }
}