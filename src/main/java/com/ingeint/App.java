package com.ingeint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        Settings settings = Settings.getInstance();
        try {
            settings.load("application.properties");
            logger.info("starting app setting: {}", settings);
        } catch (IOException e) {
            logger.error("error starting app", e);
        }
    }
}
