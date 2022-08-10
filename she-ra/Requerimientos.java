import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
  public EstadoTarea estado = EstadoTarea.PENDIENTE;
  public Iteracion iteracion;

  public Tarea(String nombre, String descripcion) {
    this.nombre = nombre;
    this.descripcion = descripcion;
  }

  public EstadoTarea getEstado() {
    return estado;
  }

  public String getNombre() {
    return nombre;
  }

}

public abstract class HistoriaOFallo extends Tarea {
  public String linkImagen;
  public Estimacion estimacion;
  public Date pendienteDesde = LocalDateTime.now();

  public HistoriaOFallo(String nombre, String descripcion, String linkImagen) {
    super(nombre, descripcion);
    this.linkImagen = linkImagen;
  }

  public void setPorHacer() {
    estimacion.validarCambioDeEstado();
    estado = EstadoTarea.POR_HACER;
  }

  public List<Tarea> getHistoriasYFallos() {
    return Collections.singletonList(this); // Se devuelve a si mismo como una lista
  }

  public void setEstimacion(EstimacionTentativa unaEstimacion) {
    estimacion = unaEstimacion;
  }

  public void rechazarEstimacion() { // Solamente si es tentativa
    estimacion.validarRechazo();
    estimacion = null;
  }

  public void aceptarEstimacion() { // Solamente si es tentativa
    estimacion.validarAceptacion();
    estimacion = new EstimacionDefinitiva(estimacion.getCantidadEstimada());
  }

  public void validarTiempoEnEstadoPendiente() {
    Integer DAYS_TO_CHANGE = 30;
    if (LocalDateTime.delta(LocalDateTime.now(), this.pendienteDesde) < DAYS_TO_CHANGE) {
      estimacion.validarAceptacion();
      estado = EstadoTarea.POR_HACER;
    }
  }
}

public class Historia extends HistoriaOFallo {
  public Historia(String nombre, String descripcion, String linkImagen) {
    super(nombre, descripcion, linkImagen);
  }
}

public class Fallo extends HistoriaOFallo {
  public Fallo(String nombre, String descripcion, String linkImagen) {
    super(nombre, descripcion, linkImagen);
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

  public List<Tarea> getHistoriasYFallos() {
    // return tareas.stream().flatMap(t -> t.getHistoriasYFallos().stream());
    return null;
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
    tareas.add(tarea);
  }

  public List<Tarea> getTareas() {
    return tareas;
  }
}

public class NotifierAdapter {
  Notifier notificador;

  public void notificarAsignacionDeTarea(String email, HistoriaOFallo tarea) {
    final String mensaje = "Se le ha asignado la tarea " + tarea.getNombre();
    notificador.notify(email, mensaje);
  }
}

public class Persona {
  public String name;
  public String email;
  public List<HistoriaOFallo> tareas = new ArrayList<HistoriaOFallo>();
  public NotifierAdapter notifier = new NotifierAdapter();

  public void asignarTarea(HistoriaOFallo tarea) {
    tareas.add(tarea);
    notifier.notificarAsignacionDeTarea(this.email, tarea);
  }
}

public abstract class Estimacion {
  public Integer cantidadEstimada;

  public Estimacion(Integer cantidadEstimada) {
    this.cantidadEstimada = cantidadEstimada;
  }

  public Integer getCantidadEstimada() {
    return cantidadEstimada;
  }

  public void validarCambioDeEstado() {

  }

  public void validarRechazo() {

  }

  public void validarAceptacion() {

  }
}

public class EstimacionDefinitiva extends Estimacion {
  public EstimacionDefinitiva(Integer cantidadEstimada) {
    super(cantidadEstimada);
  }

  @Override
  public void validarRechazo() {
    throw new RuntimeException("No puede rechazarse una Estimacion Definitiva");
  }

  @Override
  public void validarAceptacion() {
    throw new RuntimeException("No puede aceptarse una Estimacion Definitiva");
  }
}

public class EstimacionTentativa extends Estimacion {
  public EstimacionTentativa(Integer cantidadEstimada) {
    super(cantidadEstimada);
  }

  @Override
  public void validarCambioDeEstado() {
    throw new RuntimeException("No puede cambiarse el estado de una Estimacion Tentativa");
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
  unaEpica.getHistoriasYFallos();

  // 5. Crear iteracion
  Date unDate = new Date();
  Date otroDate = new Date();
  Iteracion unaIteracion = new Iteracion(unDate, otroDate);

  // 6. Asignar tarea a iteracion
  unaIteracion.agregarTarea(unaHistoria);
  unaIteracion.agregarTarea(unFallo);

  // 7. Ver tareas planeadas para iteracion
  unaIteracion.getTareas();

  // 8. Ver estado de cada tarea
  unaHistoria.getEstado();
  unFallo.getEstado();
  unaEpica.getEstado();

  // 9. Asignar tarea a Persona
  Persona unaPersona = new Persona();
  unaPersona.asignarTarea(unaHistoria);  // Punto 10. dentro de metodo asignarTarea
  
}