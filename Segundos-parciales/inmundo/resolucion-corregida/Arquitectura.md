# C - Arquitectura

Preguntas:
1. El primer diagrama de arquitectura planteado es correcto, pero el segundo no, ya que la cola de mensajes no le puede pegar directamente al servicio de facturación. Debería haber una instancia intermedia que se encargue de procesar los mensajes de la cola, y los envíe al servicio. Además, es necesario aclarar que la arquitectura 1 quiere de Sticky Sessions en el balanceador de carga, ya que hay muchas instancias de InMundo (presupongo que el modelo requiere de un login, ya que se utiliza un servicio de facturación, el cual generalmente requiere de un usuario logueado).
2. La primera arquitectura es factible, pero la segunda no debido a lo que dijimos en el punto anterior, y también es necesario aclarar que la misma debe soportar mensajes asincrónicos en la única instancia de InMundo que tiene, debido a la cola de mensajes que utiliza.

Comparación de arquitecturas:
3. SPOFs: Son los puntos de mi arquitectura qe, en caso de caerse por alguna razón, dejaría de funcionar mi aplicación. Teniendo en cuenta que el servicio de facturación es externo, y por lo tanto no podemos hacer nada con respecto a este SPOF, cara arquitectura tiene los siguientes SPOFs:
  - **Arquitectura 1**: El balanceador de carga, y la base de datos.
  - **Arquitectura 2**: El módulo InMundo, la base de datos, y la cola de mensajes.
Realizado este análisis, podríamos decir que la arquitectura 1 tiene mayor tolerancia a fallos en cuanto a lo que respecta la caída de las distintas instancias de InMundo, ya que al caerse una el balanceador comenzará a utilizar otra, y además tiene menos SPOFs.
También cabe aclarar que la arquitectura 2 tiene mayor tolerancia a fallos del servicio externo, ya que en caso de estar caído, la cola de mensajes se podrá seguir llenando, y una vez que el servicio se encuentre reestablecido se podrá procesar lo que se encuentra encolado. En caso de que la arquitectura 1 tenga el servicio caído, quedarán sin funcionamiento todas las instancias de InMundo.

4. En cada arquitectura:
  - **Arquitectura 1**: En esta arquitectura se puede escalar verticalmente mejorando cada uno de las instancias de InMundo que se encuentran, y tambien horizontalmente, agregando más insntacias de InMundo conectadas al LoadBalancer y a la BD. También pueden hacerse ambos escalamientos juntos (escalamiento slectivo).
  - **Arquitectura 2**: En esta arquitectura sólo se puede escalar verticalmente, mejorando la única instancia de InMundo, ya que en caso de escalar horizontalmente, habría que migrar a una arquitectura del tipo 1.
 