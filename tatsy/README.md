# Agencia de Radiotaxis

Parcial completo [aqu�](https://docs.google.com/document/d/1q0nP2koMy8q2WvYmaLLTt5D6jh057DkA7Kc4YjBJDng/edit#)

�Acaba de llegar a la oficina de 2Dise�os un nuevo cliente!

TATSY, agencia de Radiotaxis, cuenta con varios operadores que atienden llamados de pasajeros las 24hs. Cada pasajero indica d�nde debe ser recogido, y mientras est�n al tel�fono, los operadores buscan en un sistema el veh�culo disponible m�s cercano; si no lo consiguen, se cancela la solicitud.


## Primera Parte - Dise�o

TATSY quiere mejorar su negocio, de forma que los pasajeros puedan solicitar tambi�n veh�culos para un momento posterior. Por ejemplo: Yanina, a las 4pm solicita un taxi para las 8pm, en la intersecci�n de Cabildo y Juramento. 

Dado que TATSY no puede reservar con antelaci�n veh�culos, desea mejorar su sistema (desarrollado por Mistlav, consultora que dej� el proyecto) de forma que le permita empezar a buscar autom�ticamente m�viles para las solicitudes programadas: en concreto, se pide que faltando 15 minutos para la hora, una vez por minuto y hasta que se cumpla la hora, intente conseguir un veh�culo disponible cercano. Nuevamente, si no lo consigue se cancela la solicitud. 

Adem�s, TATSY desea simplificar la b�squeda de veh�culos: el sistema deber� autom�ticamente buscar (y asignar, si puede) un veh�culo en cuanto se cargue una solicitud. 

Por �ltimo, cada vez que se asigna un veh�culo, se debe marcar a �ste como ocupado, y notificarlo autom�ticamente por radio, SMS o a trav�s de una aplicaci�n Web, seg�n lo haya configurado el due�o del veh�culo (no son importantes los detalles de implementaci�n de cada medio de comunicaci�n). No existe una forma autom�tica de liberar un veh�culo; los taxistas se comunican por radio con los operadores y �stos lo liberan. 

**NOTA**: Ten� en cuenta este sistema cuenta con una clase Flota que presenta una interfaz. La Flota se encarga adem�s de mantener siempre actualizada las posiciones de cada veh�culo (No es importante de qu� forma lo consigue).

Se pide comunicar mediante prosa, c�digo, y diagramas de clase, estados y/o secuencia una soluci�n que permita:
1. Cargar solicitudes de veh�culos tanto para el momento actual como para uno posterior.
2. Poder buscar y asignar autom�ticamente el veh�culo disponible m�s cercano, para cualquiera de los dos tipos de solicitudes. 
3. Autom�ticamente notificar a los veh�culos asignados y marcarlos como ocupados, y poder liberar manualmente a un veh�culo

## Segunda Parte - Nuevo requerimiento

Para mejorar la experiencia de los pasajeros que hayan solicitado un veh�culo con antelaci�n, si despu�s de asignado aparece otro m�s cercano dentro del per�odo de 15 minutos antes de la hora, la solicitud debe ser reasignada. Esto puede ocurrir por dos motivos:
- Un veh�culo fue liberado manualmente en las inmediaciones del punto de encuentro
- Un veh�culo se acerc� a las inmediaciones del punto de encuentro

Por ejemplo: 
1. Anabela solicit� un veh�culo para las 4:00 PM. 
2. A las 3:45 PM el sistema empezar� a tratar de asignar la solicitud. 
3. A las 3:48 PM, se encuentra y asigna un veh�culo de patente ABC123, a 19 cuadras, y se contin�a buscando uno m�s cercano. 
4. A las 3:51 PM, se encuentra y reasigna un veh�culo de patente DEF456, a 2 cuadras, y se contin�a buscando
5. A las 4 PM se deja de buscar.

Se pide comunicar las modificaciones al modelo de dominio para soportar este requerimiento.

## Tercera Parte - comparativa

Se pide proponer una forma diferente de resolver el requerimiento 1.1, 1.3 o 2, y compararla.