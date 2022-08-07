import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// 1. 
public class Proyecto {
  public String nombre;
  public List<Tarea> tareas = new ArrayList<Tarea>();

  public Proyecto(String nombre) {
    this.nombre = nombre;
  }

  public void agregarTarea(Tarea tarea) {
    tareas.add(tarea);
  }
}

// 2.

public enum EstadoTarea {
  PENDIENTE,
  POR_HACER,
  EN_PROGRESO,
  TERMINADA
}

public abstract class Tarea {
  public String nombre;
  public String descripcion;
  public EstadoTarea estado;
  public Iteracion iteracion;

  public Tarea(String nombre, String descripcion) {
    this.nombre = nombre;
    this.descripcion = descripcion;
  }

  public EstadoTarea getEstado() {
    return estado;
  }

  public void validarTarea() {

  }
}

public class Historia extends Tarea {
  public String linkImagen;

  public Historia(String nombre, String descripcion, String linkImagen) {
    super(nombre, descripcion);
    this.linkImagen = linkImagen;
  }

}

public class Fallo extends Tarea {
  public String linkImagen;

  public Fallo(String nombre, String descripcion, String linkImagen) {
    super(nombre, descripcion);
    this.linkImagen = linkImagen;
  }
}

public class Epica extends Tarea {
  private List<Tarea> tareas = new ArrayList<Tarea>();

  public Epica(String nombre, String descripcion) {
    super(nombre, descripcion);
  }

  public void agregarTarea(Tarea tarea) {
    tareas.add(tarea);
  }

  public List<Tarea> getTareasPlanas() {
    return null;
    // TODO
  }

  @Override
  public void validarTarea() {
    throw new RuntimeException("No puede ejecutarse esta accion para una Tarea Epica");
  }

}

public class Iteracion {
  public Date fechaInicio;
  public Date fechaFin;
  public List<Tarea> tareas = new ArrayList<Tarea>();

  public Iteracion(Date fechaInicio, Date fechaFin) {
    this.fechaInicio = fechaInicio;
    this.fechaFin = fechaFin;
  }

  public void agregarTarea(Tarea tarea) {
    tarea.validarTarea();
    tareas.add(tarea);
  }
}

public class NotifierAdapter {
  public void notificarAsignacionDeTarea(String email, Tarea tarea) {
    // TODO
  }
}

public class Persona {
  public String name;
  public String email;
  public List<Tarea> tareas = new ArrayList<Tarea>();
  public NotifierAdapter notifier = new NotifierAdapter();

  public void asignarTarea(Tarea tarea) {
    tareas.add(tarea);
    notifier.notificarAsignacionDeTarea(email, tarea);
  }

}

public static void main(String[] args) {
  Proyecto proyecto = new Proyecto("unNombre");
  Historia unaHistoria = new Historia("nombre", "desc", "linkImagen");
  Fallo unFallo = new Fallo("nombre", "desc", "linkImagen");
  Epica unaEpica = new Epica("nombre", "desc");
  
  // Para asignar tarea a proyecto solo agregamos la instancia de Tarea al Proyecto
  proyecto.agregarTarea(unaHistoria);
  proyecto.agregarTarea(unFallo);
  proyecto.agregarTarea(unaEpica);

  // Para agregar tarea a Epicas (por ejemplo una historia)
  unaEpica.agregarTarea(unaHistoria);

  // 4. Para ver el listado de tareas en una epica
  unaEpica.getTareasPlanas();

  // 5. Crear iteracion
  Date unDate = new Date();
  Date otroDate = new Date();
  Iteracion unaIteracion = new Iteracion(unDate, otroDate);


  unaIteracion.agregarTarea(unaHistoria);
  unaIteracion.agregarTarea(unFallo);


  // 9.
  unFallo.getEstado();
  unFallo.getEstado();
  unaEpica.getEstado();
}