package kg.attractor.java;

import kg.attractor.java.lesson44.Booklender;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            new Booklender("localhost", 9889).start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
