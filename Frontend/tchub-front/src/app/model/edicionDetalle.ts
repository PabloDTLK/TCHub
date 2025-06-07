import { Variante } from "./variante";

export class EdicionDetalle {
  fechaLanzamiento: Date;
  autor: string;
  multiversalId: string;
  cartaOmniversalId: string;
  juegoCodigo: string;
  variantes: Variante[];

  constructor(fechaLanzamiento: Date, autor: string, multiversalId: string, cartaOmniversalId: string,juegoCodigo: string , variantes: Variante[]) {
    this.fechaLanzamiento = fechaLanzamiento;
    this.autor = autor;
    this.multiversalId = multiversalId;
    this.cartaOmniversalId = cartaOmniversalId;
    this.juegoCodigo = juegoCodigo;
    this.variantes = variantes;
  }

}
