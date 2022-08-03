# Agencia de Radiotaxis

Parcial completo [aquí](https://docs.google.com/document/d/1q0nP2koMy8q2WvYmaLLTt5D6jh057DkA7Kc4YjBJDng/edit#)

¡Acaba de llegar a la oficina de 2Diseños un nuevo cliente!

TATSY, agencia de Radiotaxis, cuenta con varios operadores que atienden llamados de pasajeros las 24hs. Cada pasajero indica dónde debe ser recogido, y mientras están al teléfono, los operadores buscan en un sistema el vehículo disponible más cercano; si no lo consiguen, se cancela la solicitud.


## Primera Parte - Diseño

TATSY quiere mejorar su negocio, de forma que los pasajeros puedan solicitar también vehículos para un momento posterior. Por ejemplo: Yanina, a las 4pm solicita un taxi para las 8pm, en la intersección de Cabildo y Juramento. 

Dado que TATSY no puede reservar con antelación vehículos, desea mejorar su sistema (desarrollado por Mistlav, consultora que dejó el proyecto) de forma que le permita empezar a buscar automáticamente móviles para las solicitudes programadas: en concreto, se pide que faltando 15 minutos para la hora, una vez por minuto y hasta que se cumpla la hora, intente conseguir un vehículo disponible cercano. Nuevamente, si no lo consigue se cancela la solicitud. 

Además, TATSY desea simplificar la búsqueda de vehículos: el sistema deberá automáticamente buscar (y asignar, si puede) un vehículo en cuanto se cargue una solicitud. 

Por último, cada vez que se asigna un vehículo, se debe marcar a éste como ocupado, y notificarlo automáticamente por radio, SMS o a través de una aplicación Web, según lo haya configurado el dueño del vehículo (no son importantes los detalles de implementación de cada medio de comunicación). No existe una forma automática de liberar un vehículo; los taxistas se comunican por radio con los operadores y éstos lo liberan. 

**NOTA**: Tené en cuenta este sistema cuenta con una clase Flota que presenta una interfaz. La Flota se encarga además de mantener siempre actualizada las posiciones de cada vehículo (No es importante de qué forma lo consigue).

Se pide comunicar mediante prosa, código, y diagramas de clase, estados y/o secuencia una solución que permita:
1. Cargar solicitudes de vehículos tanto para el momento actual como para uno posterior.
2. Poder buscar y asignar automáticamente el vehículo disponible más cercano, para cualquiera de los dos tipos de solicitudes. 
3. Automáticamente notificar a los vehículos asignados y marcarlos como ocupados, y poder liberar manualmente a un vehículo

## Segunda Parte - Nuevo requerimiento

Para mejorar la experiencia de los pasajeros que hayan solicitado un vehículo con antelación, si después de asignado aparece otro más cercano dentro del período de 15 minutos antes de la hora, la solicitud debe ser reasignada. Esto puede ocurrir por dos motivos:
- Un vehículo fue liberado manualmente en las inmediaciones del punto de encuentro
- Un vehículo se acercó a las inmediaciones del punto de encuentro

Por ejemplo: 
1. Anabela solicitó un vehículo para las 4:00 PM. 
2. A las 3:45 PM el sistema empezará a tratar de asignar la solicitud. 
3. A las 3:48 PM, se encuentra y asigna un vehículo de patente ABC123, a 19 cuadras, y se continúa buscando uno más cercano. 
4. A las 3:51 PM, se encuentra y reasigna un vehículo de patente DEF456, a 2 cuadras, y se continúa buscando
5. A las 4 PM se deja de buscar.

Se pide comunicar las modificaciones al modelo de dominio para soportar este requerimiento.

## Tercera Parte - comparativa

Se pide proponer una forma diferente de resolver el requerimiento 1.1, 1.3 o 2, y compararla.