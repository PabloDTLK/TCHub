export class Variante {
  nombre: string;
  descripcion: string;
  imagen: string;
  precioMedio: number;
  universalId: string;
  edicionMultiversalId: string;
  idiomaCodigo: string;

  constructor(
    nombre: string,
    descripcion: string,
    imagen: string,
    precioMedio: number,
    universalId: string,
    edicionMultiversalId: string,
    idiomaCodigo: string
  ) {
    this.nombre = nombre;
    this.descripcion = descripcion;
    this.imagen = imagen;
    this.precioMedio = precioMedio;
    this.universalId = universalId;
    this.edicionMultiversalId = edicionMultiversalId;
    this.idiomaCodigo = idiomaCodigo;
  }
}
  