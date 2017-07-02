import java.util.*;

public class Modelo {
    final int     MAX_USUARIOS;
    final int     MAX_COLAS;
    final double  PORC_PADRE  = 4.8;
    final double  PORC_ABUELO = 48;
   
    int[] cuota = {12, 
                   12, 24, 
                   12, 24, 36, 
                   12, 24, 36, 60, 
                   12, 24, 36, 60, 96,
                   12, 24, 36, 60, 96, 120,
                   12, 24, 36, 60, 96, 120, 180
    };

//    int[] cuota = { 12, 18, 27, 40, 60, 90, 135, 200, 300, 450, 675, 1000};

    Queue<User>[] colaNivel;
    Procesador[]  p;
    Thread[]      t;
    List<User>    listaUsers;
    boolean       interactivo;
    
    public Modelo(int usuarios, boolean inter) {
        User.contador = 0;
        MAX_USUARIOS = usuarios;
        MAX_COLAS    = (int) Math.floor(Math.log(MAX_USUARIOS)/Math.log(4))+1;
        colaNivel  = new Queue[MAX_COLAS+1];
        p          = new Procesador[MAX_COLAS];
        t          = new Thread[MAX_COLAS];
        listaUsers = new LinkedList<>();
        interactivo = inter;
    }

    
    public void proceso() {
        // inicializar colas e hilos
        if(interactivo) System.out.println("-- " + MAX_COLAS);
        for(int i=0; i<colaNivel.length; i++) {
            colaNivel[i] = new LinkedList<>();
        }
        for(int i=0; i<p.length;i++) {
            p[i]         = new Procesador(colaNivel[i], cuota[i], PORC_PADRE, PORC_ABUELO, cuota[i+1], colaNivel[i+1], interactivo);
            t[i]         = new Thread(p[i]);           
        }
           
        User pv = new User("Planeta Verde");
        pv.padre = 0;
        colaNivel[0].add(pv);
        listaUsers.add(pv);

        for(int i=1; i<=MAX_USUARIOS; i++) {
            User u = new User("user"+i, i>1?1:0);
            listaUsers.add(u);
            colaNivel[0].add(u);
        }
        colaNivel[0].add(new User("Fin"));
       
        // Ejecutar hilos
        try {
            for(int i=0; i<MAX_COLAS; i++) {
                t[i].start(); t[i].join();
            }
            // t[MAX_COLAS-1].join();
        }
        catch(InterruptedException x) {
            System.out.println("No se cerraron los hilos");
        }
        
        // Escribir resumen de datos
        if(interactivo) {
            for(User u: listaUsers) {
                if(u.comision>0) System.out.println(u);
            }
        }
    }
    
    public int totalUsuarios() { return User.contador-2; }
    
    public double totalIngresos() {return cuota[0]*totalUsuarios() ; }
    
    public double totalPV() { return listaUsers.get(0).comision; }
    
    
    
    public static void main(String[] args) {
        boolean interactivo = true;
        Modelo            m = new Modelo(100_000, interactivo);
        m.proceso();
        System.out.println(m.totalUsuarios() + " " + m.totalIngresos() + " " + m.totalPV());
    }
}
