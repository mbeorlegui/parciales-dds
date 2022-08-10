package hitbag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag implements Contenido {
  public List<Contenido> contenidos = new ArrayList<Contenido>();

  public void agregarContenido(Contenido contenido) {
    contenidos.add(contenido);
  }

  public List<Contenido> getContenidosMultimedia() {
    // return this.contenidos.stream().flatMap(t ->
    // t.getContenidosMultimedia().stream());
    return null;
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
  public abstract void ejecutar();
}

public static void main(String[] args) {
  // 1.a Creo un contenido:
  Contenido video = new Video("Video");
  Contenido imagen = new Imagen("Imagen");
  Contenido cancion = new Cancion("Cancion");
  Contenido texto = new Texto("Texto");
  // Creo un Bag:
  Bag bag = new Bag();
  bag.agregarContenido(texto);  // Use un video pero puede ser cualquiera

  // Creo un bag que va a ser hijo
  Bag bagHijo = new Bag();
  bagHijo.agregarContenido(texto);  // Use un video pero puede ser cualquiera
  bagHijo.agregarContenido(cancion);  // Use un video pero puede ser cualquiera
  // 1.b Introduzco el bagHijo dentro del bag polimorficamente
  bag.agregarContenido(bagHijo);

  // 2.a ver el listado del contenido de un Bag (tanto propio como referenciado)
  // Para esto debo "recorrer el grafo"
  bag.getContenidosMultimedia();

  // 2.b Añadir y remover colaboradores de sus Bags
  
}