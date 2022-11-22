﻿@MappedSuperclass
abstract class PersistentEntity {
  @Id
  @GeneratedValue
  Long id;
}

@Entity
class Cliente extends PersistentEntity {
  @Embedded
  Direccion direccion;
  String nombre;
  String apellido;
  List<Orden> ordenes;
}

@Embeddable
class Direccion extends PersistentEntity {
  String calle;
  String codigoPostal;
  String ciudad;
}

class Fabricante extends PersistentEntity {

}

@Service
class RepositorioFabricantes {

}

@Inheritance(SINGLE_TABLE)
abstract class Orden extends PersistentEntity {
  int copias;
  @Embedded
  Direccion direccion;
}

class Personalidada extends Orden {
  String secuenciaMidi;
  @Lob
  byte[] secuenciaDeAudio;

  public void convertir(){
    // Usa un singleton
  }
}

@Inheritance(SINGLE_TABLE)
abstract class ModalidadDeEntrega extends PersistentEntity {

}

class CorreoArgentino extends ModalidadDeEntrega {
  
}

class ACargoDeFabricante extends ModalidadDeEntrega {
  
}

class DeCatalogo extends Orden {
  @Embedded
  Melodia melodia;
}

@Embeddable
class Melodia {
  String nombre;
  String secuenciaMidi;
  @Lob
  byte[] imagenDePortada;
}

@Enumerated
enum Estado {
  // ...
}

class MidiConverter {
  // Singleton: No debe persistirse, no tiene sentido.
  private static final MidiConverter INSTANCE = new MidiConverter();

  public static MidiConverter getInstance() {
    return INSTANCE;
  }
}