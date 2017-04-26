/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author JorgeP
 */
public class TestModelo {
    public static void main(String[] args){
        int n = 0;
        System.out.printf("%10s %14s %14s %10s %n", "Usuarios", "Total Ing", "Total PV", "PORC");
        while(n<1_000_000) {
            n+=10_000;
            Modelo m = new Modelo(n, false);
            long t1 = System.currentTimeMillis();
            m.proceso();
            long t2 = System.currentTimeMillis();
            double t = (t2-t1)/1000.0;
            
            System.out.printf("%,10d %,14.2f %,14.2f %10.2f%% %,8.0f %n", m.totalUsuarios(), m.totalIngresos(), 
                                                               m.totalPV(), m.totalPV()/m.totalIngresos()*100, t);
            
        }
    }
}
