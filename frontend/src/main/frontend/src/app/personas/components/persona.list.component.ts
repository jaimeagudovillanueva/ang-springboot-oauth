import {Component, OnInit} from '@angular/core';
import {Router,ActivatedRoute} from '@angular/router';

import {PersonaService} from '../services/persona.service';

@Component({
  selector: 'personas',
  templateUrl: './persona.list.component.html'
})
export class PersonaListComponent implements OnInit {

  pagina: string;
  resultados: Object;

  datosPagina: Object;
  siguiente: string;
  anterior: string;
  primera: string;
  ultima: string;

  textoFiltro: string;

  constructor(private personaService: PersonaService,
              private router: Router,
              private route: ActivatedRoute) {
    this.route
      .queryParams
      .subscribe(params => { this.pagina = params['page'] || ''; });

    this.textoFiltro = '';
  }

  range = (paginaActual, total) => { 
         let ultimo = paginaActual < total -5 ? paginaActual + 5 : total;
         let primero = paginaActual > 5 ? paginaActual - 5 : 1;
         let a = []; for(let i = primero; i <= ultimo; i++) { a.push( i) } return a;
  }

  ngOnInit(): void {
    this.buscarPersonas(1);
  }

  buscarPersonas(page: number, filtro?: string): void {
    this.textoFiltro = filtro ? filtro : '';
  }

  procesaResultados(res: any): void {
    this.resultados = null;
    this.vaciaDatosNavegacion();
    if (res && res.content) {
      this.resultados = res.content;

      this.rellenaDatosPaginacion(res);
    }
  }

  detallePersona(id: number): void {
    this.router.navigate(['/personas/', id])
  }

  rellenaDatosPaginacion(res: any): void {
    this.datosPagina = res.page;
    
    res.links.forEach(element => {
         if (element.rel === 'prev') {
            this.anterior = element.href;
         } else if (element.rel == 'next') {
             this.siguiente = element.href;
         } else if (element.rel == 'first') {
             this.primera = element.href;
         } else if (element.rel == 'last') {
             this.ultima = element.href;
         }
    });
  }

  irPagina(URL: string): void {
      if (URL) {
      }
  }

  vaciaDatosNavegacion(): void {
      this.anterior = null;
      this.siguiente = null;
      this.primera = null;
      this.ultima = null;
  }
}