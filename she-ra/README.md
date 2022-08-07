# She-Ra

Link del parcial [aqu�](https://www.utnianos.com.ar/foro/attachment.php?aid=21610).

Grayskullssian, reconocida Software Factory, est� emprendiendo el desarrollo de She-Ra: una herramienta que revolucionar� la administraci�n de tareas, errores e incidentes (issues) en proyectos de software.

## Contexto

### Tareas

She-Ra es una plataforma que permitir� que quienes la usen carguen diferentes tareas relacionadas al desarrollo de software. Las Tareas pueden ser: Historias (requerimientos nuevos), Fallos (errores resultantes de las pruebas) o �picas (Funcionalidades grandes del sistema que pueden dividirse en pedazos m�s peque�os). Por ejemplo, una �pica podr�a ser �Administraci�n de Contratos�, una Historia �Como Usuario deseo crear un contrato� y un fallo �Creaci�n de contratos no guarda el precio�.

Una Tarea siempre tiene un nombre y una descripci�n y es parte de un proyecto. Adem�s, las Historias y Fallos contienen una estimaci�n en d�as o puntos, una persona asignada, opcionalmente una imagen (URL externa) y pueden estar asociadas a una Iteraci�n, que es el per�odo de tiempo en el cual se planifica hacerlas.

A su vez, las �picas engloban Historias y Fallos en unidades conceptuales. Una �pica puede estar compuesta por un sinn�mero de subtareas, que pueden ser historias, fallos, u otras �picas (que a su vez pueden tener subtareas de cualquier tipo y as� sucesivamente).

Finalmente, una tarea puede estar en uno de varios estados, a saber: Pendiente, Por Hacer, En Progreso, o Terminada.

### Asignaciones

Tener una tarea asignada implica trabajo por hacer. Es por esto que se desea notificar a las personas en sus tel�fonos cuando una Tarea (historia o fallo) se les asigna.

Para ello, la plataforma sobre la que se construye SheRa incorpora la siguiente biblioteca externa de notificaciones: 

```java
public interface Notifier {
  void notify(email, message);
}
```


No se planea soportar diversos medios de notificaci�n, pero s� cabe aclarar que esta biblioteca se encuentra en fase beta, por lo cual tanto su implementaci�n como interfaz pueden cambiar en los pr�ximos meses.

### Estimaciones y Validaciones

Para poder pasar al estado �Por Hacer�, una tarea (historia o fallo) debe estar lista. Esto significa que el Product Owner (PO) (el rol encargado de administrar todas las tareas) haya validado que la tarea est� completa, y que la estimaci�n sea razonable.

Mientras una tarea est� �Pendiente�, alguien del equipo de desarrollo podr� ingresar una estimaci�n. Esta estimaci�n puede ser aprobada o rechazada por quien es PO. Si es rechazada, se descarta y quien es PO puede reescribir la tarea con mayor detalle para lograr una estimaci�n m�s adecuada. Entonces alguien del equipo podr� reestimar la tarea, que nuevamente quedar� pendiente de ser aprobada por quien es PO. 

Una vez que la estimaci�n es aprobada, se vuelve definitiva. Reci�n entonces qui�n es PO podr� marcar la tarea como �Por Hacer�.

Por �ltimo, para agilizar el proceso, se desea que todas las tareas que hayan estado Pendientes y estimadas por m�s de un mes aproximadamente, sean autom�ticamente marcadas como �Por Hacer�

## Requerimientos Detallados

1. Como Product Owner, deseo poder crear un proyecto, indicando su nombre.
2. Como Product Owner, deseo poder cargar una Tarea especificando su tipo, nombre, descripci�n, proyecto al que pertenece e imagen si es una Historia o Fallo.
3. Como Product Owner, deseo poder asociar subtareas (historias, fallos o sub�picas) a una �pica.
4. Como Product Owner, deseo poder ver el listado plano de todas las Historias o Fallos asociadas a una �pica, ya sea directamente o a trav�s de sub�picas.
5. Como gerente de proyecto, deseo poder crear una Iteraci�n, especificando su fecha de inicio y de fin.
6. Como gerente de proyecto, deseo poder asociar una Tarea (historia o fallo) a una Iteraci�n
7. Como gerente de proyecto, deseo poder ver todas las tareas planeadas para una Iteraci�n
8. Como gerente de proyecto, deseo conocer el estado de cada Tarea.
9. Como integrante del equipo, deseo poder asignar una tarea (historia o fallo) a otra persona.
10. Como integrante del equipo, deseo recibir una notificaci�n cuando una Tarea me es asignada
11. Como integrante del equipo, deseo poder agregar una estimaci�n tentativa a una Historia o Fallo.
12. Como PO, deseo evitar que una Historia o Fallo se marque �Por Hacer� si no tiene una estimaci�n definitiva.
13. Como PO deseo rechazar una estimaci�n de una Historia o Fallo
14. Como PO deseo aceptar una estimaci�n de una Historia o Fallo y que esta sea definitiva
15. Como PO deseo poder marcar una historia como �Por Hacer�
16. Como PO deseo que las tareas que hayan estado Pendientes por m�s de un mes y tengan una estimaci�n definitiva, se marquen �Por hacer� de forma autom�tica.