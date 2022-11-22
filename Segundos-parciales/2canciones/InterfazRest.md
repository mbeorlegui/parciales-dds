# B - Interfaz REST

## Pantalla de usuarios
Se puede consultar y editar (botón guardar)

O sea, la rutas mas conveniente para la consulta es la siguiente:
```
- GET /usuarios/yo
- GET /perfil
```
Al guardar (recordemos que PUT es para reemplazar informacion):
```
Lo mas correcto:
  - PUT /usuarios/yo
  - PUT /perfil
Limitacion de formularios:
  - POST /usuarios/yo
  - POST /perfil
```
Tambien podria usarse PATCH en vez de PUT.
Cabe destacar que los formularios solo usan GET y POST, por lo que estamos obligados a usar un POST para editar la informacion de un usuario.

## Reproductor de canciones
Para buscar las canciones por nombre:
```
- GET /canciones
  - Como busco por nombre uso un queryParams(search)
  - GET /canciones?nombre=despacito
- Una vez que la haya seleccionado:
  - GET /canciones/:id
```

Para reproducir determinada cancion:
```
- POST /canciones/:id/reproducir
```
Cabe aclarar que uso POST porque necesito sumar la cantidad de reproducciones, por lo que eso es un efecto.

Para poner/sacar me gusta:
```
- POST /canciones/:id/like
```
Nuevamente lo que conviene es hacer un PUT, pero como es un formulario utilizo un POST por esta limitacion.
La accion de like podria ser un contador que alterne entre poner y sacar el me gusta.


## Editor de listas de reproducción
1. Se puede cambiar el nombre de la lista
Para traer una lista utilizo la siguiente ruta:
```
- GET /playlist/:id
```

2. Se puede eliminar canciones de la lista (esto tiene efecto al instante, sin tener que hacer guardar)
```
- DELETE /playlist/:id/canciones/:idCancion -> Esta forma no se puede aplicar por la limitacion de los forms
- POST /playlist/:id/canciones/:idCancion/remove
```

3. Se puede guardar los cambios (por ahora el único cambio que se guardará es el cambio del nombre)
Para cambiar el nombre de la misma se necesita apretar el boton "Guardar":
```
- POST /playlist/:id
  - El nuevo nombre debe viajar en el Body de la request
```
