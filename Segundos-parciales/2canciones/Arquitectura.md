# C - Arquitectura

Compare las siguientes arquitecturas presentadas basándose en:
- Tolerancia a fallos. ¿Existen SPOFs? ¿Cuales?.
- Escalabilidad.

SPOF: Los Single Points of Failure son puntos críticos del sistema que en caso de caerse, aplicación dejará de funcionar.
En la arquitectura 1 los SPOFs son:
- La conexión a internet del usuario.
- La base de datos.
- La conexión del servidor (esto puede verse afectado por el internet, la luz, etc).
En la arquitectura 2 los SPOFs son:
- La conexión a internet del usuario.
- La base de datos.
- El balanceador de carga.

Mirando estos aspectos, podemos decir que la arquitectura 2 tiene mayor tolerancia a fallos, ya que en caso de caerse un servidor por alguna razon, los otros n-1 servidores estarán activos, a diferencia de la arquitectura 1 la cual estará atado a su único servidor donde reside.

Luego, analizando la Escalabilidad:
- Arquitectura 1: no puede escalar horizontalmente, ya que en caso de hacerlo pasaría a convertirse en la arquitectura 2 (agregando el Load Balancer, claro). Esta arquitectura sí puede escalar verticalmente, mejorando las especificaciones del servidor.
- Arquitectura 2: Esta arquitectura puede escalar tanto horizontalmente (agregando más servidores conectados al load balances), como verticalmente (mejorando las especificaciones de los servidores).

Comente brevemente en la arquitectura 2 qué consideraciones hay que tener al realizar nuestra aplicación:
Si la aplicacion es stateless no debemos tener ninguna consideracion, pero si llega a tener estado (por alguna sesion que se guarda en memoria o algo asi), deberiamos activar sticky session en el balanceador de carga. Con esto ultimo hago que las sesiones se guarden en la base de datos.