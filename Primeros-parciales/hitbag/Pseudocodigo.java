package hitbag;

import java.util.*;

public class Usuario {
  public String nombre;
}

public class Bag implements Contenido {
  public List<Contenido> contenidos = new ArrayList<Contenido>();
  public List<Hit> hits = new ArrayList<Hit>();
  public List<Hit> hitRequests = new ArrayList<Hit>();
  public List<AccionHit> historialDeAcciones = new ArrayList<AccionHit>();
  public List<Usuario> colaboradores = new ArrayList<Usuario>();
  public Usuario duenio;

  public void agregarContenido(Contenido contenido) {
    contenidos.add(contenido);
  }

  public List<Contenido> getContenidosMultimedia() {
    // return this.contenidos.stream().flatMap(t ->
    // t.getContenidosMultimedia().stream());
    return null;
  }

  public void modificar(Hit hit) {
    System.out.println("Implementar");
  }

  public void volverAlPunto(Hit hit) {
    // Debo quedarme con el hit del punto, y con todos los anteriores
    // public List<Hit> listaFiltrada = hits.stream().dropWhile(h -> h != hit);
    // Y luego aplicarlos con el forEach, en orden inverso
    // Collections.sort(listaFiltrada,Collections.reverseOrder()).forEach(h->h.deshacerSobre(this));
  }
  // De la forma que implementamos este metodo, vamos a tener que guardar un historial con los hits
  // Por lo que cada vez que se ejecute una accion, debemos agregarla al historial del Bag

  public void agregarAccionAHistorial(AccionHit accion){
    historialDeAcciones.add(accion);
  }

  public Bag verComoEstaba(Hit hit) {
    Bag comoEstaba = (Bag) this.clone();
    comoEstaba.volverAlPunto(hit);
    // Como volverAlPunto tiene efecto sobre sí mismo
    // debemos crear una copia del objeto y luego devolverla
    return comoEstaba;
  }

  public void realizarHitRequest(Hit hit){
    hitRequests.add(hit);
  }

  public void aceptarHitRequest(Hit hit){
    // Validar que pertenezca a la lista de hit requests
    // si no pertenece arrojar excepcion
    hitRequests.remove(hit);
    this.modificar(hit);
  }

  public List<Hit> getHitRequests() {
    return hitRequests;
  }
}

public interface Contenido {
  String getTitulo();

  void setTitulo(String titulo);

  List<Contenido> getContenidosMultimedia();
}

public abstract class ContenidoMultimedia implements Contenido {
  public String titulo;
  public String descripcion;
  public String url;

  public ContenidoMultimedia(String titulo) {
    this.titulo = titulo;
  }

  @Override
  public List<Contenido> getContenidosMultimedia() {
    return Collections.singletonList(this);
    // Devuelva una lista con sí mismo --> [this]
  }
}

public class Imagen extends ContenidoMultimedia {
  public String titulo;
  public String descripcion;
  public String url;

  public Imagen(String titulo) {
    super(titulo);
  }
}

public class Video extends ContenidoMultimedia {
  public String titulo;
  public String descripcion;
  public String url;

  public Video(String titulo) {
    super(titulo);
  }
}

public class Cancion extends ContenidoMultimedia {
  public String titulo;
  public String descripcion;
  public String url;

  public Cancion(String titulo) {
    super(titulo);
  }
}

public class Texto implements Contenido {
  String titulo;
  String contenido;

  public Texto(String titulo) {
    this.titulo = titulo;
  }
}

public interface AccionHit {
  public abstract void ejecutarSobre(Bag bag);
}

public class CambiarNombre implements AccionHit {
  public String viejoNombre;
  public String nuevoNombre;

  public CambiarNombre(String viejoNombre, String nuevoNombre) {
    this.nuevoNombre = nuevoNombre;
    this.viejoNombre = viejoNombre;
  }

  public void ejecutarSobre(Bag bag) {

  }
}

public class AgregarContenido implements AccionHit {
  Contenido contenido;

  public void ejecutarSobre(Bag bag) {

  }
}

public class QuitarContenido implements AccionHit {
  Contenido contenido;

  public void ejecutarSobre(Bag bag) {

  }
}

public class Hit {
  public List<AccionHit> acciones = new ArrayList<AccionHit>();
  public Contenido contenido;

  public Hit(List<AccionHit> acciones, Contenido contenido) {
    this.acciones = acciones;
    this.contenido = contenido;
  }

  public void ejecutarSobre(Bag bag) {
    this.acciones.stream().forEach(a -> a.ejecutarSobre(bag));
    bag.agregarAccionAHistorial(this);
  }

}

public static void main(String[] args) {
  // 1.a Creo un contenido:
  Contenido video = new Video("Video");
  Contenido imagen = new Imagen("Imagen");
  Contenido cancion = new Cancion("Cancion");
  Contenido texto = new Texto("Texto");

  // Creo un Bag:
  Bag bag = new Bag();
  bag.agregarContenido(texto);

  // Creo un bag que va a ser hijo
  Bag bagHijo = new Bag();
  bagHijo.agregarContenido(texto);
  bagHijo.agregarContenido(cancion);
  // 1.b Introduzco el bagHijo dentro del bag polimorficamente
  bag.agregarContenido(bagHijo);

  // 2.a ver el listado del contenido de un Bag (tanto propio como referenciado)
  // Para esto debo "recorrer el grafo"
  bag.getContenidosMultimedia();

  // Creo un hit
  List<AccionHit> acciones = new ArrayList<AccionHit>();
  acciones.add(new CambiarNombre("viejoNombre", "nuevoNombre"));
  Hit unHit = new Hit(acciones, cancion);
  // La modificacion es sobre un Contenido (en este caso una cancion)
  // y es ejecutada sobre un Bag
  // 2.c realizar un Hit con cambios en un Bag que posee
  unHit.ejecutarSobre(bag);

  // 2.g Deshacer cambios: necesitamos agregar la capacidad de volver atras
  bag.volverAlPunto(unHit);
}