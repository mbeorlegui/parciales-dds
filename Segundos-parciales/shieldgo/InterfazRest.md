# B - Interfaz REST
Además del posibilitar el juego de ShieldGo en sí, el servidor deberá:
1. Permitir el acceso a los administradores, para que realicen la gestión (CRUD) de ubicaciones. Puntualmente, deberán poder:
    - Listar las ubicaciones actuales
    - Crear nuevas ubicaciones, coordenadas y nivel en el que se encuentra.
    - Editar una ubicación, ajustando sus coordenadas y nivel.
    - Crear un ítem en una ubicación dada. 

<p align="center">
  <img src="UI.png" />
</p>
2. Exponer un API REST para buscar y consultar estadísticas públicas de cada personaje en juego, para que se puedan desarrollar otros negocios en torno al juego.

Te pedimos:
* Indicar las URLs a utilizar y su verbo HTTP, en el caso de enviar información que no sea parte de la ruta (queryParams o cuerpo) indicarlo también, para los siguientes elementos:
  - Pantallas a realizar.
  - Acciones indicadas de las pantallas presentadas.
  - Acciones indicadas para la API REST que hay que exponer.
* Si hubiera una limitación técnica por la cual hayas tenido que adaptar una ruta o un verbo. Justificar adecuadamente

1. 
- Listar ubicaciones actuales:
```
GET /ubicaciones
```
- Crear nuevas ubicaciones, coordenadas y nivel en el que se encuentra.
```
POST /ubicaciones/add
Se debe enviar por body toda la informacion para realizar el POST.
Por ejemplo en este caso: 
{
  'nombre': <nombre>,
  'coordenadaX': <x>,
  'coordenadaY: <y>,
  'nivel': <nivel>
}
```
- Editar una ubicación, ajustando sus coordenadas y nivel.
```
Esto deberia resolverse con un PATCH, pero por la limitacion de forms, debe hacerse con un POST.
POST /ubicaciones/<id>
Por body debe enviarse toda la informacion:
{
  'coordenadaX': <x>,
  'coordenadaY: <y>,
  'nivel': <nivel>
}
- Crear un ítem en una ubicación dada.
POST /ubicaciones/<id>/agregarItem
{
  'tipoItem': <tipoItem>,
  ...
}
```