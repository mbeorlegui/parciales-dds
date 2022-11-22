# Hitbug

Link del parcial [aquí](https://docs.google.com/document/d/1TngwZCctCp4qKsdw89HLUc_GLSb6mxZ7_lJTTj3TOLM/edit#heading=h.6n8wkiq886kr)

Share your music, share your history, share your emotions…

“La idea era que se llamara Hitbag, ya que tenés toda tu información musical en Bags, pero al registrar el dominio hubo un bug se ve…”, nos cuenta entre risas el creador de esta novedosa red social musical que busca revolucionar la vida de las personas dejando rastro de su actividad musical.

## Dominio
Según nos cuenta su creador, la idea de esta nueva plataforma es muy simple: Poder hacer listas de reproducción (playlists) colaborativas, e ir almacenando su historia. A cada una de estas listas, se las ha dado en llamar Bags.

Un Bag puede contener diversos tipos de contenido: Imágenes, video, texto y música. Los videos, imágenes y canciones tienen asociados un nombre o título, una descripción (puede estar vacía) y una URL al contenido multimedia. Los textos, simplemente tienen un título y su contenido.

Asimismo, un Bag también puede incluir todo el contenido de otro Bag, referenciándolo: si el contenido del Bag referenciado cambia, estos cambios deben reflejarse en el Bag que lo referencia de forma transparente. 

Todos los Bags pertenecen a cierto usuario, que puede editar su contenido, y tienen a su vez una lista de colaboradores que también pueden hacerlo. Cada cambio realizado sobre un Bag es atómico y se lo denomina “Hit” (de forma similar a los sistemas de versionado). Editar el contenido involucra agregar o remover contenido, o editar un específico contenido. Por ejemplo, un Hit puede contener el agregado de una canción y el cambio del título de una imagen.

Dado que es necesario poder conocer el estado de un Bag en el pasado, debe ser posible navegar el historial de Hits realizados y situarse en uno en particular, viendo al Bag como era en ese entonces.

Finalmente, los colaboradores también pueden hacer “Hit Requests”: un Hit Request es un Hit sugerido que no se aplica efectivamente hasta tanto el dueño no lo apruebe, es decir que puede estar pendiente, rechazado o aprobado. 


## Requerimientos

Los requerimientos concretos son:
1. Un Bag debe poder:
  - almacenar cualquier contenido multimedia
  - referenciar el contenido de otros Bags

2. Un usuario debe poder:
  - crear un Bag y ver el listado de su contenido (tanto propio como referenciado)
  - añadir y remover colaboradores de sus Bags
  - realizar un Hit con cambios en un Bag que posee
  - realizar Hits en un Bag del que es colaborador
  - realizar Hit Requests en un Bag del que es colaborador
  - aprobar o descartar Hit Requests en un bag que posee
  - ver el estado de un Bag en el pasado, parado en un cierto Hit. Por ejemplo, ver cómo estaba el Bag "Favoritos" cuando se hizo la modificación "Agrego la película de Bob Esponja"
  - deshacer los últimos N Hits o aplicar un Hit Request rechazado

* Se pide diseñar una solución que resuelva los requerimientos planteados y comunicarla utilizando prosa, diagramas, pseudocódigo,etc.
* Se pide, asimismo para el último requerimiento (2-h), diseñar una solución alternativa y compararla con la primera según su simplicidad y otra cualidad de diseño elegida.
* Bajo la solución que propongas, si tenemos un Bag "Rock Nacional" y otro "Canciones Bizarras" el cual contiene el tema "Solo le pido a Dios": ¿puede un mismo Hit mover "Solo le pido a Dios" a "Rock Nacional"?
* Si es posible, contar por qué. Si no, explicar a alto nivel qué cambios sería necesario hacer a la solución para soportarlo

