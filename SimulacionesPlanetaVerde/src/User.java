import java.util.*;

public class User {
    static int contador=0;
    int    numero;
    String nombre;
    int    padre=-1;
    int    referencia;
    double comision;
    
    public User(String n, int ref) {
        numero     = contador++;
        nombre     = n;
        referencia = ref;
        // System.out.println(nombre);
    }
    
    public User(String n) {
        this(n, 0);
    }
    
    public String toString() {
        return String.format("%,6d %-15s %10.2f", numero, nombre, comision);
    }
}
