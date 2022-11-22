# A - Persistencia Relacional


Mapeos:
- Utilizo el MappedSuperclass para no repetir los IDs 
- Para guardar el enum de estado, lo embebo en Orden utilizando `@Enumerated`, ya que lo unico qu enecesito es el valor del mismo, el cual sera un ID. O sea, se agrega la columna estado_id a la tabla de Orden.
- Direccion es un `Embeddable`, ya que es un value object, porque no me interesa la entidad en si, si no los valores que tiene la misma. De esta forma, se agregan las columnas calle, codigoPostal y ciudad a las tablas de Clientes y Ordenes.
- Los repositorios siempre son servicios, por lo que no debo persistirlos, ya que ellos solo se encargan de interactuar con la base. En el caso de RepositorioFabricantes, es el encargado de interactuar con la tabla Fabricantes. Debido a esto, utilizo `@Service`.
- El MIDIConverter es Singleton, por lo que no debe persistirse, no tiene sentido hacerlo.
- Las modalidades de entrega no tienen estado, pero como dice la consigna que se contempla agregar nuevas modalidades de entrega que sean Stateful, entonces tenemos que tener en cuenta esto. En este caso, descartamos los enums con comportamiento con los @Enumerated por esta razon. Entonces, tendremos que convertirlo a una clase abstracta.
- Sobre el punto anterior, lo mejor es hacer single table, ya que los joins contra las tablas de CorreoArgentino y ACargoDelFabricante no aportarian en nada, ya que solamente tienen la FK de ModalidadDeEntrega, por lo que conviene hacer SingleTable.
- Mapeo de la herencia de Orden:
  - El cron que selecciona las ordenes de tipo Personalizada, es una consulta no polimorfica, cosa que influye en la decision del mapeo de Orden.
  - Cliente tiene una lista de Ordenes, aportando una consulta polimorfica a la herencia de Ordenes.
  - En este caso, toma un poco mas de peso las consultas polimorficas que la no polimorfica, ya que no sabemos con precisión cuántas clases stateful se agregarán. Esta es la razon por la cual usamos SingleTable.
  - En el punto B se aclara que se necesitan listar todas las ordenes en estado pendiente (WHERE estado_id = x), por lo que esto refuerza la decision de usar SingleTable
- Para la Melodia creo que lo mas conveniente es embeberla dentro de DeCatalogo (y por consecuencia, tambien dentro de Orden porque use SingleTable), ya que es un Value Object. Esto quiere decir, solo necesitamos el valor de esta clase. Tener la abstraccion Melodia dentro de la base no nos aporta nada, e incluso agrega una tabla por demas que es innecesaria. De esta forma, a la entidad DeCatalogo deberiamos agregarle todas las propiedades de Melodia.
