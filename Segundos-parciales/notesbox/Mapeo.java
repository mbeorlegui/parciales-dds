@MappedSuperclass
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
  @OneToMany
  List<Orden> ordenes;
}

@Embeddable
class Direccion extends PersistentEntity {
  String calle;
  String codigoPostal;
  String ciudad;
}

class Fabricante extends PersistentEntity {
  Bool aceptaTrabajo;
  int copiasMinimas;
  int diasDeDemora;
  int capacidadDisponible;
}

@Service
class RepositorioFabricantes {

}

@Inheritance(SINGLE_TABLE)
@Entity
abstract class Orden extends PersistentEntity {
  int copias;
  @Embedded
  Direccion direccion;
  @ManyToOne
  ModalidadDeEntrega modalidadDeEntrega;
  @Enumerated
  Estado estado;
}

@Entity
class Personalidada extends Orden {
  String secuenciaMidi;
  @Lob
  byte[] secuenciaDeAudio;

  public void convertir() {
    // Usa un singleton
  }
}

@Inheritance(SINGLE_TABLE)
@Entity
abstract class ModalidadDeEntrega extends PersistentEntity {

}

@Entity
class CorreoArgentino extends ModalidadDeEntrega {

}

@Entity
class ACargoDeFabricante extends ModalidadDeEntrega {

}

@Entity
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