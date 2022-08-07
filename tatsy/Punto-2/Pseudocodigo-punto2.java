import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Vehiculo {
  public List<MedioDeComunicacion> mediosDeComunicacion = new ArrayList<MedioDeComunicacion>();
  private Posicion posicion;
  private EstadoVehiculo estado;
  private Solicitud solicitudPendiente;

  public Vehiculo(Posicion posicion) {
    this.posicion = posicion;
    this.estado = EstadoVehiculo.LIBRE;
  }

  public Posicion getPosicion() {
    return posicion;
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

  public boolean estaDisponible() {
    return this.estado == EstadoVehiculo.LIBRE;
  }

  public void liberarVehiculo() {
    this.estado = EstadoVehiculo.LIBRE;
    this.solicitudPendiente = null;
  }

  public void ocuparVehiculo(Solicitud solicitud) {
    this.estado = EstadoVehiculo.OCUPADO;
    this.solicitudPendiente = solicitud;
    notificar();
  }

  public int distanciaA(Posicion posicion) {
    // TODO: A implementar cuando se sepa el la implementación de posicion
    // mientras tanto deberia mockearse
    return 0;
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
    // Pero con el diseño pedido no nos queda de otra
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
  private static CalculadoraDeDistancia calculadoraDeDistancia = new CalculadoraDeDistancia();

  public static Flota getInstance() {
    return INSTANCE;
  }

  public Vehiculo hayarVehiculoDisponibleMasCercaDeSolicitud(Solicitud solicitud) {
    return vehiculos.stream()
        .filter(v -> v.estaDisponible())
        .orderBy(v1, v2 -> v1.distanciaA(solicitud) <= v2.distanciaA(solicitud))
        .findFirst()
        .get();
  }

  public boolean existeVehiculoDisponibleCercaDeSolicitud(Solicitud solicitud) {
    return this.hayarVehiculoDisponibleMasCercaDeSolicitud(solicitud) != null;
  }

  // TODO: Ejecutar con tarea programada segun lo pedido en consigna
  public Vehiculo conseguirVehiculoDisponibleCercano(Solicitud solicitud) {
    if (existeVehiculoDisponibleCercaDeSolicitud(solicitud)) {
      Vehiculo vehiculoHayado = hayarVehiculoDisponibleMasCercaDeSolicitud(solicitud);
      if (solicitud.getVehiculoAsignado() == null) {
        vehiculoHayado.ocuparVehiculo(solicitud);
        solicitud.setVehiculo(vehiculoHayado);
      } else if (vehiculoEstaMasCercaDeSolicitudQueActual(solicitud, vehiculoHayado)) {
        vehiculoHayado.ocuparVehiculo(solicitud);
        solicitud.cambiarVehiculoPor(vehiculoHayado);
      }
      solicitud.aceptarSolicitud(vehiculoHayado);
      return vehiculoHayado;
    } else {
      solicitud.rechazarSolicitud();
      return null;
    }
  }

  public boolean vehiculoEstaMasCercaDeSolicitudQueActual(Vehiculo v1, Solicitud solicitud){
    return calculadoraDeDistancia.getDistanciaEntrePosiciones(solicitud.getPosicion(), solicitud.getVehiculoAsignado().getPosicion())
         < calculadoraDeDistancia.getDistanciaEntrePosiciones(solicitud.getPosicion(), v1.getPosicion());
  }

  public Vehiculo conseguirVehiculoMasCercano(Solicitud solicitud) {
    if (existeVehiculoDisponibleCercaDeSolicitud(solicitud)) {
      Vehiculo vehiculoHayado = hayarVehiculoDisponibleMasCercaDeSolicitud(solicitud);
      solicitud.cambiarVehiculoPor(vehiculoHayado);
      return vehiculoHayado;
    }
  }

  public Vehiculo hayarVehiculoDisponibleMasCercaDeSolicitud(Solicitud solicitud) {
    return vehiculos
        .stream()
        .filter(
            v -> calculadoraDeDistancia.getDistanciaEntrePosiciones(v.getPosicion(),
                solicitud.getPosicion()) < calculadoraDeDistancia.getDistanciaEntrePosiciones(
                    solicitud.getVehiculoAsignado().getPosicion(), solicitud.getPosicion()))
        .findFirst()
        .get();
  }

  public Boolean existeVehiculoMasCercano(Solicitud solicitud) {
    return this.hayarVehiculoDisponibleMasCercaDeSolicitud(solicitud) != null;
  }

}

enum EstadoVehiculo {
  OCUPADO,
  LIBRE,
}

public class Posicion {
  CalculadoraDeDistancia calculadoraDeDistancia = new CalculadoraDeDistancia();

  // No importa como funciona
  public boolean esPosicionCercana(Posicion pos) {
    final double DISTANCIA_CERCANA = 100;
    // Supongo una distancia cercana cualquiera, que debe ser modificada cuando se
    // implemente
    // y segun los requerimientos que no estan especificados
    // TODO: completar cuando se sepa el funcionamiento, mientras tanto se podria
    // mockear
    return calculadoraDeDistancia.getDistanciaEntrePosiciones(this, pos) < DISTANCIA_CERCANA;
  }
}

public class Solicitud {
  public LocalDateTime hora;
  public Posicion posicion;
  public EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;
  public Vehiculo vehiculoAsignado;

  public LocalDateTime getHora() {
    return hora;
  }

  public Posicion getPosicion() {
    return posicion;
  }

  public EstadoSolicitud getEstado() {
    return estado;
  }

  public Vehiculo getVehiculoAsignado() {
    return vehiculoAsignado;
  }

  public void rechazarSolicitud() {
    estado = EstadoSolicitud.RECHAZADO;
  }

  public void aceptarSolicitud(Vehiculo unVehiculo) {
    estado = EstadoSolicitud.ACEPTADO;
    setVehiculo(unVehiculo);
  }

  public void setVehiculo(Vehiculo vehiculo) {
    vehiculoAsignado = vehiculo;
  }

  public void cambiarVehiculoPor(Vehiculo vehiculo) {
    this.vehiculoAsignado.liberarVehiculo();
    setVehiculo(vehiculo);
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

public class CalculadoraDeDistancia {
  public double getDistanciaEntrePosiciones(Posicion p1, Posicion p2) {
    // TODO: Implementar cuando se sepa como funciona. Se puede mockear para testear
    return 0;
  }
}