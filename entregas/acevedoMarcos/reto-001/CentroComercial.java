public class CentroComercial {

    private Fila fila;
    private Tiempo tiempo;
    private Caja[] cajas;
    private Console console;

    private boolean haLlegadoCliente;

    private final double PROBABILIDAD_LLEGADA_CLIENTES = 0.6;
    private final double PROBABILIDAD_CAJA_LIBRE = 0.4;

    private final int NUMERO_CAJAS = 4;

    public CentroComercial() {

        fila = new Fila();
        tiempo = new Tiempo();
        console = new Console();

        cajas = new Caja[NUMERO_CAJAS];

        for (int i = 0; i < NUMERO_CAJAS; i++) {
            cajas[i] = new Caja();
        }
    }

    public void ejecutar() {

        do {

            tiempo.avanzar();

            procesarLlegadaCliente();

            procesarAburrimiento();

            procesarAperturaCaja();

            atenderClientes();

            asignarClienteACaja();

            procesarAviso();

            fila.registrarEstado();

            mostrarEstado();

            pausar();

        } while (!tiempo.haFinalizado());

        mostrarResumen();
    }

    private void procesarLlegadaCliente() {

        haLlegadoCliente = Math.random() <
                           PROBABILIDAD_LLEGADA_CLIENTES;

        if (haLlegadoCliente) {

            boolean preferente = Math.random() < 0.2;

            Cliente cliente;

            if (preferente) {

                cliente = new Cliente(
                        tiempo.obtenerMinuto(),
                        true,
                        -1
                );

                fila.añadirPreferente(cliente);

            } else {

                cliente = new Cliente(
                        tiempo.obtenerMinuto(),
                        false,
                        -1
                );

                fila.añadirCliente(cliente);
            }
        }
    }

    private void procesarAburrimiento() {

        fila.comprobarAburrimiento(
                tiempo.obtenerMinuto()
        );
    }

    private void procesarAperturaCaja() {

        if (Math.random() < PROBABILIDAD_CAJA_LIBRE) {

            for (int i = 0; i < cajas.length; i++) {

                if (!cajas[i].estaAbierta()) {

                    cajas[i].abrir();
                    break;
                }
            }
        }
    }

    private void atenderClientes() {

        for (int i = 0; i < cajas.length; i++) {
            cajas[i].procesarAtencion();
        }
    }

    private void asignarClienteACaja() {

        for (int i = 0; i < cajas.length; i++) {

            if (cajas[i].puedeAtender() &&
                fila.hayGente()) {

                Cliente cliente = fila.sacar();

                cajas[i].añadirCliente(cliente);
            }
        }
    }

    private void procesarAviso() {

        int minuto = tiempo.obtenerMinuto();

        if (minuto % 15 == 0 &&
            fila.obtenerNumero() > 25) {

            console.writeln(
                "AVISO: pasen por esta caja en orden de fila"
            );
        }
    }

    private void mostrarEstado() {

        console.cleanScreen();

        tiempo.mostrar(haLlegadoCliente);

        fila.mostrar();

        mostrarCajas();
    }

    private void mostrarCajas() {

        for (int i = 0; i < cajas.length; i++) {

            console.write("Caja[" + (i + 1) + "]");

            cajas[i].mostrar();
        }
    }

    private void mostrarResumen() {

        int numeroClientesAtendidos = 0;

        for (int i = 0; i < cajas.length; i++) {

            numeroClientesAtendidos =
                    numeroClientesAtendidos +
                    cajas[i].clientesAtendidos();
        }

        console.writeln();
        console.writeln("========== RESUMEN ==========");
        console.writeln(
                "Numero de clientes atendidos: "
                + numeroClientesAtendidos
        );

        console.writeln(
                "Personas en fila: "
                + fila.obtenerNumero()
        );
    }

    private void pausar() {

        console.pause(1);
    }
}