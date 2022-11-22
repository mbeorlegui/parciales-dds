import java.sql.Date;

@Enumerated
enum Visibilidad {
  PRIVADA,
  PUBLICA,
  NO_LISTADA
}

@Embeddable
public class Estadistica {
  int likes;
  int dislikes;
}

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Contenido {
  int reproducciones;
  @Lob
  byte[] imagenDeTapa;
  @Embedded
  Estadistica estadistica;
  @Enumerated
  Clasificacion clasificacion;
  @OneToOne
  Usuario duenio;
}

@Entity
public class Podcast implements Contenido {
  Date fechaInicio;
  Date fechaFin;
}

@Entity
public class Cancion implements Contenido {
  Date fechaSubida;
  Time duracion;
}

@Entity
public class Playlist {
  @OneToOne
  Usuario duenio;
  @OneToMany
  List<Usuario> suscriptores;
  @OneToMany
  @OrderColumn
  List<Contenido> contenido;
}

// Transformo Clasificacion en un enum con comportamiento
enum Clasificacion {
  MENORES {},
  ADOLESCENTE {},
  ADULTOS {}
}

@Entity
public class Usuario {
  String nombre;
}