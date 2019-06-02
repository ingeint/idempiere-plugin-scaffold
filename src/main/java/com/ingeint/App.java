package com.ingeint;

import java.io.IOException;

public class App {

    public static void main(String[] args) {
        Settings settings = Settings.getInstance();
        try {
            settings.load("application.properties");
            System.out.println(settings);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
