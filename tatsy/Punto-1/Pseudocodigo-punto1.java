import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Vehiculo {
  public List<MedioDeComunicacion> mediosDeComunicacion = new ArrayList<MedioDeComunicacion>();
  private Posicion posicion;
  private EstadoVehiculo estado;

  public Vehiculo(Posicion posicion) {
    this.posicion = posicion;
    this.estado = EstadoVehiculo.LIBRE;
  }

  public void notificar() {
    mediosDeComunicacion.forEach(
        medio -> medio.notificar());
  }

  public void agregarMedio(MedioDeComunicacion medio) {
    mediosDeComunicacion.add(medio);
  }

  public void sacarMedio(MedioDeComunicacion medio) {
    mediosDeComunicacion.remove(medio);
  }

  public boolean estaCercaDe(Solicitud solicitud) {
    return this.posicion.esPosicionCercana(solicitud.getPosicion());
  }

  public void liberarVehiculo() {
    this.estado = EstadoVehiculo.LIBRE;
  }

  public void ocuparVehiculo() {
    this.estado = EstadoVehiculo.OCUPADO;
    notificar();
  }

}
// -------------------------------------

public class Pasajero {
  public String nombre;
  public List<Solicitud> solicitudes;

  public String getNombre() {
    return nombre;
  }

  public void agregarSolicitudAPasajero(Solicitud solicitud) {
    solicitudes.add(solicitud);
  }

  public boolean contieneSolicitud(Solicitud solicitud) {
    return solicitudes.stream().anyMatch(
        s -> s.getHora().equals(solicitud.getHora())
            && s.getPosicion().equals(solicitud.getPosicion())
            && s.getEstado().equals(solicitud.getEstado()));
  }

  public boolean esMismoPasajero(Pasajero pasajero) {
    return this.getNombre() == pasajero.getNombre();
    // No es buena practica matchear con el nombre
    // Pero con el dise�o pedido no nos queda de otra
  }
}

public class Pasajeros {
  public List<Pasajero> pasajeros = new ArrayList<>();
  private static final Pasajeros INSTANCE = new Pasajeros();

  public static Pasajeros getInstance() {
    return INSTANCE;
  }

  public void agregarPasajero(Pasajero pasajero) {
    this.pasajeros.add(pasajero);
  }

  public void sacarPasajero(Pasajero pasajero) {
    this.pasajeros.remove(pasajero);
  }

  public void agregarSolicitudAPasajero(Solicitud solicitud, Pasajero pasajero) {
    Pasajero pasajeroIndicado = pasajeros.stream().filter(
        p -> p.esMismoPasajero(pasajero)).findFirst().get();
    pasajeroIndicado.agregarSolicitudAPasajero(solicitud);
  }
}

public class Flota {
  public List<Vehiculo> vehiculos = new ArrayList<>();
  private static final Flota INSTANCE = new Flota();

  public static Flota getInstance() {
    return INSTANCE;
  }

  public Vehiculo hayarVehiculoCercaDeSolicitud(Solicitud solicitud) {
    return vehiculos.stream().filter(v -> v.estaCercaDe(solicitud)).findFirst().get();
  }

  public boolean existeVehiculoCercaDeSolicitud(Solicitud solicitud) {
    return this.hayarVehiculoCercaDeSolicitud(solicitud) != null;
  }

  // TODO: Ejecutar con tarea programada segun lo pedido en consigna
  public Vehiculo conseguirVehiculoDisponibleCercano(Solicitud solicitud) {
    if (existeVehiculoCercaDeSolicitud(solicitud)) {
      solicitud.aceptarSolicitud();
      Vehiculo vehiculoHayado = hayarVehiculoCercaDeSolicitud(solicitud);
      vehiculoHayado.ocuparVehiculo();
      return vehiculoHayado;
    } else {
      solicitud.rechazarSolicitud();
      return null;
    }
  }
}

enum EstadoVehiculo {
  OCUPADO,
  LIBRE,
}

public class Posicion {
  // No importa como funciona
  public boolean esPosicionCercana(Posicion pos) {
    // TODO: completar cuando se sepa el funcionamiento, mientras tanto se podria
    // mockear
    return true;
  }
}

public class Solicitud {
  public LocalDateTime hora;
  public Posicion posicion;
  public EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;

  public LocalDateTime getHora() {
    return hora;
  }

  public Posicion getPosicion() {
    return posicion;
  }

  public EstadoSolicitud getEstado() {
    return estado;
  }

  public void rechazarSolicitud() {
    estado = EstadoSolicitud.RECHAZADO;
  }

  public void aceptarSolicitud() {
    estado = EstadoSolicitud.ACEPTADO;
  }
}

enum EstadoSolicitud {
  RECHAZADO,
  ACEPTADO,
  PENDIENTE,
}

// -------------------------------------
public interface MedioDeComunicacion {
  public void notificar();
}

public class SMS implements MedioDeComunicacion {
  public void notificar() {
  }
}

public class Radio implements MedioDeComunicacion {
  public void notificar() {
  }
}

public class Web implements MedioDeComunicacion {
  public void notificar() {
  }
}
// -------------------------------------