@MappedSuperclass
class Identificable {
  @Id @GeneratedValue
  Long Id;
}

class Viaje extends Identificable {
  @OneToMany
  Lugar destino;
  @OneToMany
  Lugar origen;
  @ManyToMany
  List<Asiento> asientos;
}

abstract class Asiento extends Identificable {
  int numero;
  @ManyToOne
  Pasajero asignado;
  @ManyToMany @OrderCollumn
  List<Pasajero> enEspera;
}

class PrimeraClase extends Asiento {
  int pulgadasTv;
  boolean tipoComando;
  boolean masajeador;
  boolean comidaGourmet; 
}

class Turista extends Asiento {
  boolean cubreCabeza;
  boolean almohada;
  boolean bolsaVomital;
}

class Lugar extends Identificable {
  @Embedded
  Posicion posicion;
  String descripcion;
  String pais;
}

class Pasajero extends Identificable {
  String nombre;
  int edad;
  String sexo;
  @OneToOne
  TipoPasajero tipoPasajero;
}

abstract class TipoPasajero extends Identificable {}

class Normal extends TipoPasajero {
  Categoria categoria;
}

class Normal extends TipoPasajero {
  int nroPaginaFrecuente;
}

@Embeddable
class Posicion {
  int latitud;
  int longitud;
}

@Enumerated
enum Categoria {A,B,C}