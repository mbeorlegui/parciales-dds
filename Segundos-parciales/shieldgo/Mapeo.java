@MappedSuperclass
abstract class PersistentEntity {
  @Id
  @GeneratedValue
  Long id;
}

@Embeddable
class Coordenada {
  int x;
  int y;
  int nivel;
}

@Entity
class Ubicacion extends PersistentEntity {
  String nombre;
  
  @Embedded
  Coordenada coordenada;
  // Pongo JoinColumn para aclarar 
  // a hibernate que no cree una tabla rara
  // Tambien aclaro el nombre de la columna con name
  @OneToMany @JoinColumn(name="ubicacion_id")
  // La columna tiene que tener el nombre de la clase actual
  // Ya que se proyecta en la tabla destino (item)
  List<Item> items;
}

@Entity
class Personaje extends PersistentEntity {
  String nombre;
  int monedas;
  @ManyToMany
  @OrderColumn
  List<Ubicacion> ubicacionesVisitadas;
  @OneToMany
  List<Item> inventario;
  @OneToOne
  Arma armaEnUso;
  @Enumerated
  TipoPersonaje tipoPersonaje;
  
  @Embedded
  Coordenada coordenada;
}

@Inheritance(SINGLE_TABLE)
abstract class Item extends PersistentEntity {

}

@Entity
class Regalo extends Item {
}
@Entity
class BolsaDeMonedas extends Item {
}
@Entity
class Accesorio extends Item {
}
@Entity
class Arma extends Item {
}

enum TipoPersonaje {
  HECHICERO {},
  GUERRERO {},
  COMERCIANTE {}
}