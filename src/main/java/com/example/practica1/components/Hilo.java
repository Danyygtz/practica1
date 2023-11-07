package com.example.practica1.components;

import javafx.scene.control.ProgressBar;

import java.util.Random;

public class Hilo extends Thread {

    private ProgressBar pgbCorredores;

    public Hilo(String nombre, ProgressBar pgb){
        this.setName(nombre);
        this.pgbCorredores = pgb;
    }

    @Override
    public void run() {
        super.run();
        try {
            double avance = 0;
            while (avance <= 1){
                sleep((long) (Math.random() * 1500));
                avance += Math.random()/10;
                pgbCorredores.setProgress(avance);
               // System.out.println("Corredor "+this.getName()+ " llego al KM "  + this.getName() + " " + i);
            }
            //System.out.println("Corredor "+this.getName()+" llego a la meta ");
    }catch(Exception e){

    }
}
}