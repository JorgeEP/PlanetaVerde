/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JorgeP
 */
public class TestUsuarios_2 {
    public static void main(String[] args) {
        Usuario pv = Usuario.crearUsuarioInicial();
        Usuario.crearUsuario("user1");
        for(int i=2; i<=8191; i++) {
            Usuario.crearUsuario("user"+i,1);
        }
        
        System.out.printf("%,12.2f %,12.2f %n", Usuario.totalRecibido, Usuario.participacionPV);
        for(Usuario x : Usuario.usuarios.values()) {
            System.out.println(x); 
        }
    }
}
