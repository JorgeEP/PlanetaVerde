import java.util.*;

public class Procesador implements Runnable {
    Queue<User> cola;       // Cola de la que recibe los usuarios
    Queue<User> nextCola;   // Cola a la que pasan los que tienen los recursos acumulados
    Map<Integer, Nodo> mapa = new HashMap<>();
    double porcentajePadre; // Porcentaje del aporte a pagar al padre
    double porcentajeAbuelo;// Porcentaje del aporte a pagar al abuelo
    int    pagoActual;      // Valor del aporte en este ciclo
    int    siguientePago;   // Valor del aporte en el siguiente ciclo
    Nodo   raiz;            // Raíz del árbol
    boolean interactivo;    // Determina si se muestran o no los pasos intermedios
    
    public Procesador(Queue c, int pago, double porcP, double porcA, int sigP, Queue nc, boolean inter) {
        cola             = c;
        nextCola         = nc;
        pagoActual       = pago;
        porcentajePadre  = porcP/100.0;
        porcentajeAbuelo = porcA/100.0;
        siguientePago    = sigP;
        interactivo      = inter;
    }
    
    @Override
    public void run() {
        if(interactivo) System.out.println("Iniciando proceso de los que pagaron " + pagoActual+ "("+(cola.size()-2)+")");
        while(true) {
            User u = cola.poll();
            
            // Si no hay todavía usuarios en la cola se esperan 50 milisegundos
            if(u==null) {
                try { Thread.sleep(5);} 
                catch(InterruptedException x) { }
                continue;
            }
            
            // Si se llega al final de la cola se cierra el proceso y se guarda "Fin" en la cola siguiente
            if(u.nombre.equals("Fin")) {
                nextCola.add(u);
                if(interactivo) System.out.println("Este es el fin para los que pagaron "+pagoActual);
                break;
            }
            
            // Se crea un nuevo nodo del arbol binario de este proceso
            Nodo n = new Nodo();
            n.usuario = u;
            if(raiz==null) {
                raiz = n;
                raiz.padre = n;
                mapa.put(u.numero, n);
                nextCola.add(raiz.usuario);
                continue;
            }
            
            // Verifica el nodo que será el padre
            Nodo r = mapa.get(u.padre);             // Busca si tiene nodo padre
            if(r==null) r=mapa.get(u.referencia);   // Si no tiene asignado padre, ubica el recomendador
            mapa.put(u.numero, n);                  // Inserta el nodo en el mapa

            r = menorHijo(r);
            if(r.izq==null) r.izq = n;
            else r.der = n;
 
            n.padre = r;
            if(n.usuario.padre==-1) n.usuario.padre = r.usuario.numero;
            
            // Asignar pagos
            User padre  = n.padre.usuario;
            User abuelo = n.padre.padre.usuario;
            
            double comisionP = pagoActual*porcentajePadre;
            double comisionA = pagoActual*porcentajeAbuelo;
            
            padre.comision  += comisionP;
            abuelo.comision += comisionA;
            raiz.usuario.comision += (pagoActual - comisionP - comisionA);
            
            if(padre.comision >= siguientePago) {
                if(!nextCola.contains(padre)) {
                    nextCola.add(padre);
                    padre.comision-=siguientePago;
                }
            }
            
            if(abuelo.comision >= siguientePago) {
                if(!nextCola.contains(abuelo)) {
                    nextCola.add(abuelo);
                    abuelo.comision-=siguientePago;
                }
            }
        }
        // mostrar Arbol
        // mostrarArbol(raiz);
    }
    
    Nodo menorHijo(Nodo n) {
        if(n.izq==null || n.der==null) return n;
        Nodo mhi = menorHijo(n.izq);
        Nodo mhd = menorHijo(n.der);
        Nodo mh  = (mhi.usuario.numero < mhd.usuario.numero) ? mhi : mhd;
        return mh;
    }
    
    public void mostrarArbol(Nodo n) {
        if(n==null) return;
        System.out.print(n.usuario.numero+ "<-" + n.padre.usuario.numero + "<-" + n.padre.padre.usuario.numero + " : ");
        if(n.izq !=null) System.out.print(n.izq.usuario.numero + " - ");
        if(n.der !=null) System.out.print(n.der.usuario.numero);
        System.out.println();
        mostrarArbol(n.izq);
        mostrarArbol(n.der);
    }
    
}
