public class Fila {

    private Cliente[] clientes;
    private int numeroClientes;

    private int[] estados;
    private int numeroEstados;

    private final int MAXIMO_PERSONAS = 30;
    private final double PROBABILIDAD_ABURRIRSE = 0.3;

    private Console console;

    public Fila() {
        clientes = new Cliente[30];
        numeroClientes = 0;

        estados = new int[120];
        numeroEstados = 0;

        console = new Console();
    }

    public boolean hayGente() {
        return numeroClientes > 0;
    }

    public int obtenerNumero() {
        return numeroClientes;
    }

    public Cliente primero() {
        if (numeroClientes > 0) {
            return clientes[0];
        }

        return null;
    }

    public boolean estaLlena() {
        return numeroClientes >= MAXIMO_PERSONAS;
    }

    public boolean añadirCliente(Cliente cliente) {

        if (estaLlena()) {
            return false;
        }

        clientes[numeroClientes] = cliente;
        numeroClientes++;

        return true;
    }

    public Cliente sacar() {

        if (!hayGente()) {
            return null;
        }

        Cliente cliente = clientes[0];

        for (int i = 1; i < numeroClientes; i++) {
            clientes[i - 1] = clientes[i];
        }

        numeroClientes--;

        clientes[numeroClientes] = null;

        return cliente;
    }

    public void registrarEstado() {

        if (numeroEstados < estados.length) {
            estados[numeroEstados] = numeroClientes;
            numeroEstados++;
        }
    }

    public void mostrar() {

        console.writeln("FILA:");

        if (numeroClientes == 0) {
            console.writeln("  Vacia");
        } else {

            for (int i = 0; i < numeroClientes; i++) {

                if (clientes[i].esPreferente()) {
                    console.writeln("  Cliente " + (i + 1) + " - PREFERENTE");
                } else {
                    console.writeln("  Cliente " + (i + 1));
                }
            }
        }

        console.writeln("Longitud: " + numeroClientes + " metros");
    }

    public void comprobarAburrimiento(int minutoActual) {

        if (minutoActual < 20) {
            return;
        }

        if (minutoActual % 5 != 0) {
            return;
        }

        int i = 0;

        while (i < numeroClientes) {

            if (clientes[i].minutosEnCola(minutoActual) > 8) {

                if (Math.random() < PROBABILIDAD_ABURRIRSE) {

                    eliminarCliente(i);

                } else {
                    i++;
                }

            } else {
                i++;
            }
        }
    }

    private void eliminarCliente(int posicion) {

        for (int i = posicion + 1; i < numeroClientes; i++) {
            clientes[i - 1] = clientes[i];
        }

        numeroClientes--;

        clientes[numeroClientes] = null;
    }

    public boolean añadirPreferente(Cliente cliente) {

        if (estaLlena()) {
            return false;
        }

        int posicion = 0;

        while (posicion < numeroClientes &&
               clientes[posicion].esPreferente()) {

            posicion++;
        }

        for (int i = numeroClientes; i > posicion; i--) {
            clientes[i] = clientes[i - 1];
        }

        clientes[posicion] = cliente;
        numeroClientes++;

        return true;
    }

    public boolean colarCliente(Cliente cliente, int posicionConocido) {

        if (estaLlena()) {
            return false;
        }

        if (posicionConocido < 0 ||
            posicionConocido >= numeroClientes) {

            return false;
        }

        int posicion = posicionConocido + 1;

        for (int i = numeroClientes; i > posicion; i--) {
            clientes[i] = clientes[i - 1];
        }

        clientes[posicion] = cliente;
        numeroClientes++;

        return true;
    }

    public void entregarCompras(int posicion) {

        if (posicion >= 0 && posicion < numeroClientes) {
            eliminarCliente(posicion);
        }
    }
}