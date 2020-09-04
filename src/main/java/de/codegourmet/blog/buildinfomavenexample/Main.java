package de.codegourmet.blog.buildinfomavenexample;

import java.io.InputStream;
import java.io.IOException;

import java.util.Properties;

public class Main {

    public static void main(String[] args) throws IOException {
        Properties buildInfo = new Properties();

        try (InputStream inputStream = Main.class.getResourceAsStream("/buildinfo.properties")) {
            buildInfo.load(inputStream);
        }

        System.out.println("build date: " + buildInfo.getProperty("date"));
        System.out.println("build version: " + buildInfo.getProperty("version"));
    }

}
