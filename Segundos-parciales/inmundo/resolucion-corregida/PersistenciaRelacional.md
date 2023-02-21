# A - Persistencia relacional

Decisiones tomadas:
- En el pseodo código se omiten los IDs. Para facilitar esto, puedo crear la siguiente clase y que todas las clases mapeadas con @Entity hereden de ella:
```java
@MappedSuperclass
class Identificable {
  @Id @GeneratedValue
  Long id;
}
```
- Las interfaces se mapean como clases abstractas.
- En este modelo, la posición debe embebesere ya que es un ValueObject, o sea que solamente nos interesa la latitud y la longitud de los lugares, y no tiene sentido hacer una tabla sólo para almacenar esto. Debido a esta decisión, en la tabla Lugar, todos los registros tendrán una latitud y una longitud.
- El Enum de Categoria se embebe como un @Enumerated.
- Teniendo una tabla Lugar con todos los lugares es como cumplimos con el requerimiento de la edicion de las descripciones.
- Mapeo de herencia del Asiento: Como podemos ver, el modelo solo muestra consultas polimorficas, o sea, solo se consulta por los Asientos en general, sin discriminar por las subclases, por lo que esto quiere decir que NO debemos usar TABLE_PER_CLASS, ya que la query para trar todos los asientos seria muy lenta ya que requiere de un Union. Descartada esta opcion, vamos a analizar las otras dos:
  * JOINED: Tiene como ventaja que los atributos propios de cada clase hija quedarian separadas en dos tablas, y se podrian consultar por separado, o sea, no habria atributos en NULL, y como desventaja tendria que el JOIN necesario para trar todos los asientos, haría un poco más costosa la consulta que teniendo una sola tabla.
  * SINGLE_TABLE: Tiene como ventaja que traería todos los asientos con una query de bajo costo, pero se tendrían bastantes atributos en NULL (5 de 8 en el mejor de los casos, y 4 de 8 en el peor de los casos). NOTA: el análisis de los NULLS se hizo sin contar las PKs ni las FKs, pero en el caso de que se contarasn, la proporción de NULLs sería menor todavía.
  * DECISION: FInalmente decido usar SINGLE_TABLE ya que las consultas son sólo polimórficas y prefiero tener más atributos en NULL centralizados en una sola tabla a que estar pagando el costo del JOIN.
  * ACLARACIÓN: Tomo a Asiento como una clase Abstracta, ya que los asientos pueden serl del tipo PrimeraClase o Turista.
- El tipo de Pasajero tambien conviene hacerlo como SINGLE_TABLE, ya que la consulta es polimorfica (solo se consulta por un Pasajero en particular, pero sin saber de qué subtipo es el mismo), y además sólo se tendría un atributo en NULL.
- Presumo que un Pasajero puede estar en espero en muchos asientos, por lo que sería un `@ManyToMany`.
- Relacion entre Viaje y Lugar: Ya que los lugares se pueden repetir entre viajes (ejemplo: Puedo tener un vuelo de Buenos Aires a San Pablo, y tener uno de Santiago a San Pablo, y en ambos casos se estaria leyendo el mismo registro de la tabla Lugar para el destino), debo hacer una relación `@ManyToOne` desde Viaje, y para los lugares Destino y Origen.
- Debido a que necesito respetar el orden para las reservas de los asientos, agrego un `@OrderCollumn` en la relacion de muchos a muchos entre Asiento y Pasajero (esto hace que se agregue el orden como un entero en la tabla).
- Un viaje tiene muchos asientos, y un asiento puede estar presente en muchos viajes, por lo que tendre que hacer un muchos a muchos.