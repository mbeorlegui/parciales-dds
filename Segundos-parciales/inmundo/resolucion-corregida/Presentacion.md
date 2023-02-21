# B - Presentacion

## Rutas

- Listado de vuelos:
```
Ruta: GET /vuelos
Busqueda de vuelos entre fechas: GET /vuelos?desde=<fechaDesde>&hasta=<fechaHasta> -> Usamos query string para enviar estos parámetros
Detalle de un vuelo: GET /vuelos/<id_vuelo>
```

- Detalles de un vuelo:
```
Ruta: GET /vuelos/<id_vuelo>
Agregar de favoritos: POST /user/me/favs/add 
Quitar de favoritos: POST /user/me/favs/delete
Agregar al carrito: POST /user/me/carrito
Aclaración: en los tres casos anteriores, se debe enviar la informacion del vuelo (se envía el ID del mismo) por el body en request
```

## Componentes HTML utilizados
- Como realizamos las cosas con formularios, utilizamos un <form>.
- Los botones que realizan acciones (agregar al carrito, agregar/quitas favoritos, buscar), son inputs de tipo submit (form submit): <input type="submit">
- Los selectores de fechas son inputs del tipo date: <input type="date">
- Los titulos podrian ser etiquetas <h1> (el "Viajá a: Campus")
- Las imagenes se insertan con la etiqueta <img>. Lo mismo podria ser con la estrella de favoritos, la cual tiene un form submit escondido detras.
- Los textos se modelan con un <p>
- El ver mas es un texto con un anchor <a>, el cual redirige a donde se elija.
- Para el maquetado se utilizan <div>, los cuales tendran su estilo que lo haremos con CSS (no entra en el alcance del parcial).

## Limitación técnica
Una limitación técnica que puede haber es el hecho de que se debe enviar todo con FORMS. En este caso, debería adaptarse todo para que funcione con GET y POST, por lo que todos los ejemplos realizados anteriormente fueron realizados de esa manera.
En caso de que no existiera esta limitación, habría cosas que se podrían solucionar con PATCH o DELETE