package jovami;

import jovami.exercicios.*;

import java.util.LinkedList;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        App.getInstance(); // init App

        var exercicios = new LinkedList<Runnable>();
        exercicios.add(new Exercicio1());
        exercicios.add(new Exercicio2());
        exercicios.add(new Exercicio3());
        exercicios.add(new Exercicio4());
        exercicios.add(new Exercicio5());

        exercicios.forEach(r -> {
            System.out.printf("<----- %s ----->\n", r.getClass().getSimpleName());
            r.run();
            System.out.println();
        });
    }
}
