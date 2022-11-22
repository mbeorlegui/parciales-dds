# A - Persistencia Relacional

Mapeos:
- Para guardar el enum de Visibilidad, lo embebo en Playlist utilizando `@Enumerated`, ya que lo unico que necesito es el valor del mismo, el cual sera un ID. O sea, se agrega la columna visibilidad_id a la tabla de Playlist.
- Decido embeber Estadistica, ya que es un valuie object en el cual solo me interesa saber la cantidad de likes y de dislikes, y no vale la pena realizar un join solo para traer estos datos, por lo que lo mas conveniente es tratarlo como dos columnas dentro de la entidad Contenido.
- Para la Clasificacion puedo optar por convertirla en una clase abstracta o en un enum con comportamiento. En este caso particular me voy a decantar por la segunda opcion, ya que todas las clases que es stateless, esto quiere decir que las clases hijas no tienen propiedades. Por esto, cuando utilizo la clasificacion la llamo con `@Enumerated`
- La posición de un contenido en la playlist es relevante: Gracias a este requerimiento, sabemos que la lista de Contenido tiene que tener un `@OrderColumn`, y tener en cuenta que a la tabla de Contenido se le agrega una columna de order.
- Contenido unicamente utiliza consultas polimorficas, por lo que logicamente lo que conviene hacer es un SingleTable. Ademas, la proporcion de nulls es baja (2/8 sin contar PKs ni Fks), asi que es una razón más para hacerlo así.
- Persisto imagen con un @Lob, pues es un binario.