
import java.util.*;

public class Usuario implements Comparable<Usuario> {
    static int                  contador = 0;
    static double          totalRecibido = 0;
    static double        participacionPV = 0; 
    static int                    APORTE = 13;
    static double             PORC_PADRE = 6.4;
    static double            PORC_ABUELO = 64;
    static Map<Integer,Usuario> usuarios = new TreeMap<>();
     
    int          numero;
    String       nombre;
    int          aporte;
    double       comision;
    boolean      solitario;
    Set<Usuario> recomendados = new HashSet<>();
    Usuario      padre        = null;
    Usuario      recomendador = null;
    Usuario      izquierdo    = null;
    Usuario      derecho      = null;
    
    public Usuario() {
        Random  r = new Random();
        numero    = contador++;
        if(r.nextInt(100)<80) solitario = true;
    }
    
    Usuario menorDescendiente() {
        if(izquierdo == null ) return this;
        if(derecho == null ) return this;
        Usuario mdi = izquierdo.menorDescendiente();
        Usuario mdd = derecho.menorDescendiente();
        return (mdi.numero<mdd.numero) ? mdi : mdd ;
    }
    
    @Override
    public int compareTo(Usuario x) {
        return this.numero - x.numero;
    }
    
    @Override
    public String toString() {
        return String.format("%,10d %-20s %-10.2f %4d", numero, nombre, comision, padre.numero);
    }
    
    static Usuario crearUsuarioInicial() {
        if(contador!=0) return null;
        Usuario u = crearUsuario("Planeta Verde");
        return u;
    }
    
    static Usuario crearUsuario(String nom) {
        Usuario u = crearUsuario(nom, 0);
        return u;
    }
    
    static Usuario crearUsuario(String nom, int rec) {
        // Usar el constructor de usuario
        Usuario u = new Usuario();
        u.nombre = nom;
        u.aporte = APORTE;
        // Agregar usuario al mapa de usuarios
        Usuario.usuarios.put(u.numero, u);        
        // Si es el primer usuario no hace nada más
        if(Usuario.contador==1) {
            u.padre = u;
            return u;
        }
        
        // Busca el usuario recomendador
        Usuario r = Usuario.usuarios.get(rec);
        u.recomendador = r;
        
        // Si el recomendador no tiene hijos lo asigna como hijo
        if(r.izquierdo==null) {
            r.izquierdo = u;
            u.padre     = r;
        }
        else if(r.derecho==null) {
            r.derecho = u;
            u.padre   = r;
        }
        // Si el recomendador tiene hijos busca el menor nivel donde no tenga descendientes
        else {
            Usuario p = r.menorDescendiente();
            if(p.izquierdo==null) {
                p.izquierdo = u;
                u.padre = p;
            }
            else {
                p.derecho=u;
                u.padre = p;
            }
        }
        
        // Asignar comisiones
        Usuario.totalRecibido+=u.aporte;
        u.padre.comision+=u.aporte*Usuario.PORC_PADRE/100;
        u.padre.padre.comision+=u.aporte*Usuario.PORC_ABUELO/100;
        Usuario.participacionPV+=(u.aporte*(100-Usuario.PORC_PADRE-Usuario.PORC_ABUELO)/100);
        
        return u;
    }
}
