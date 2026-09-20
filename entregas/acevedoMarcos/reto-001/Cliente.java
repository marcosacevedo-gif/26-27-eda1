public class Cliente {

    private int minutoLlegada;
    private boolean preferente;
    private int conocido;

    public Cliente(int minutoLlegada, boolean preferente, int conocido) {
        this.minutoLlegada = minutoLlegada;
        this.preferente = preferente;
        this.conocido = conocido;
    }

    public int minutosEnCola(int minutoActual) {
        return minutoActual - minutoLlegada;
    }

    public boolean esPreferente() {
        return preferente;
    }

    public int obtenerConocido() {
        return conocido;
    }

    public boolean tieneConocido() {
        return conocido >= 0;
    }
}