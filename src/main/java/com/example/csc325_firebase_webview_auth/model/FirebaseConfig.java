package com.example.csc325_firebase_webview_auth.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads Firebase settings from a local properties file
 */
public class FirebaseConfig {

    public static String getApiKey() {
        Properties props = new Properties();

        try (InputStream in = FirebaseConfig.class.getResourceAsStream("/firebase.properties")) {
            if (in == null) {
                throw new RuntimeException("firebase.properties not found in src/main/resources");
            }
            props.load(in);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load firebase.properties", e);
        }

        String key = props.getProperty("firebase.apiKey");
        if (key == null || key.isBlank()) {
            throw new RuntimeException("firebase.apiKey missing in firebase.properties");
        }
        return key.trim();
    }
}