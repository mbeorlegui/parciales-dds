# A - Persistencia Relacional

Mapeos:
- Utilizo el MappedSuperclass para que todas las clases que hereden de PersistentEntity tengan un ID autogenerado. Esto es un mas que nada una manera de evitar que se repita codigo.
- La Coordenada conviene embeberla dentro de las tablas de Ubicacion y Personaje. En este caso hacemos esto ya que Ubicacion es un Value Object, o sea, no nos interesa en sí la identidad, si no el valor que tienen. Esto hace que nos evite hacer un Join para consultar la ubicacion de las cosas. Ademas al ser un juego, y que este atributo sea cambiante todo el tiempo, es inencesario ir guardando la infinidad de posicion de cada Personaje.
- Las instancias de Items estan en una sola ubicacion (esto indica `@OneToMany`). La decision se tomo por esto y porque el UML indica que una ubicacion tiene muchos items.
- Convierto Item a clase abstracta, ya que las interfaces no se pueden persistir. Todas las que implementan esta interfaz, ahora heredaran de una clase abstracta.
- Como podemos ver, Item utiliza consultas polimorficas (pues Personaje tiene una lista de Items), por lo que debemos elegir la estrategia mas correcta. Tambien cabe aclarar que tiene consultas NO polimorficas.
- Estrategia utilizado para el mapeo de Item:
  - Analizando TablePerConcreteClass: Se descarta de entrada, pues nuestro sistema tiene 2 consultas polimorficas y 1 no polimorfica (en el Arma del Personaje). Como las polimorficas necesita una lista de Item, necesitamos que esto sea algo que se resuelve rapido, puesto que intuimos que el Personaje necesita actualizar bastante seguido su lista de Items.
  - Analizando SingleTable: 
    - Regalo tiene como propiedades: `fraseMotivacional` y `nombre`.
    - BolsaDeMonedas tiene como propiedades: `monedas`. Accesorio tiene como propiedades: `costo` y `nombre`. 
    - Arma tiene como propiedades: `costo`, `defensa`, `ataque`, y `nombre`. 
    - O sea, la cantidad de propiedades totales son 6, y como maximo se pueden llegar a tener 4 como NotNull (asumimos que en nombre se puede usar el mismo campo para todos). Las proporciones de Nulls serian: [2/6, 1/6, 2/6, 4/6].
  - Analizando Joined: Los joins en este caso son utiles para todas las subclases, pues tienen varios campos que la clase padre (Item), no tienen. En BolsaDeMonedas no suma mucho, pues hay un solo campo que no tiene la clase padre (monedas). 
  Para consultas polimorficas tendra que hacer 4 left joins, lo cual es un punto en contra. En este caso, como las Armas las voy a buscar por ID, y no necesito un Join para esto, este metodo termina perdiendo un poco de peso. Esta es la razon por la cual elijo el metodo SingleTable.
- La propiedad `ubicacionesVisitadas` de Personaje es ManyToMany ya que una ubicacion puede ser visitada por muchos personajes, y una ubicacion puede ser visitada por muchos personajes.
- Para el TipoPersonaje, lo mas conveniente, ya que no almacena propiedades, y solo tiene IDs para identificar cada objeto, es convertir la interfaz en un Enum con comportamiento y tratarlo como un `@Enumerated`, el cual asigna a cada subclase un ID, y agrega una columna en la entidad Personaje, con solo el ID del tipo de personaje. El tipo de personaje es stateless, por eso se puede hacer esto. No tiene sentido hacer joined ya que estas joineando solo para traer registros numericos.
- Como la lista de ubicaciones tienen que estar ordenadas, utilizo OrderColumn, por lo que eso agregaria una columna de orden al DER.