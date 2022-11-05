package jovami;

import jovami.exercises.*;

import java.util.LinkedList;

/**
 * Main
 */
public class Main {

    public static void main(String[] args) {
        App.getInstance(); // init App

        var exercises = new LinkedList<Runnable>();
        exercises.add(new Exercise1());
        exercises.add(new Exercise3());
        exercises.add(new Exercise4());
        /* exercises.add(new Exercise2());
        exercises.add(new Exercise5()); */

        exercises.forEach(r -> {
            System.out.printf("<----- %s ----->\n", r.getClass().getSimpleName());
            r.run();
            System.out.println();
        });
    }
}
