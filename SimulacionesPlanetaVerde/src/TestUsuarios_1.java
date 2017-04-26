/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JorgeP
 */
public class TestUsuarios_1 {
    public static void main(String[] args) {
        Usuario.crearUsuarioInicial();
        Usuario.crearUsuario("Antonio");
        Usuario.crearUsuario("Bernardo",1);
        Usuario.crearUsuario("Carlos",1);        
        Usuario.crearUsuario("Darío",1);
        Usuario.crearUsuario("Ezequiel",1);
        Usuario.crearUsuario("Fernando",1 );
        Usuario.crearUsuario("Gerardo",1);   
        Usuario.crearUsuario("Héctor",1);  
        Usuario.crearUsuario("Ignacio",1);   
        Usuario.crearUsuario("José",1);  
        Usuario.crearUsuario("Kevin",1);   
        Usuario.crearUsuario("Luis");  
        
        for(Usuario x : Usuario.usuarios.values()) {
            System.out.println(x); 
            System.out.println("I-> " + x.izquierdo);
            System.out.println("D-> " + x.derecho);
        }
    }
}
