package com.example.csc325_firebase_webview_auth.model;

import java.io.IOException;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;

/**
 * FirebaseAuthService handles communication with Firebase Authentication
 * using Firebase's REST API (Email/Password authentication).
 *
 * This is required because we are building a desktop JavaFX app,
 * not an Android/Web app that has a built-in Firebase SDK.
 */
public class FirebaseAuthService {

    // Web API Key from Firebase Console (safe to expose in client apps)
    private final String apiKey;

    // Java 11+ built-in HTTP client
    private final HttpClient client = HttpClient.newHttpClient();

    /**
     * Constructor accepts Firebase Web API Key
     */
    public FirebaseAuthService(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * Sign in existing user with email and password.
     * Returns Firebase JSON response (contains idToken, etc.)
     */
    public String signIn(String email, String password)
            throws IOException, InterruptedException {

        String url =
                "https://identitytoolkit.googleapis.com/v1/accounts:signInWithPassword?key="
                        + apiKey;

        String body = json(email, password);

        return post(url, body);
    }

    /**
     * Create a new Firebase user account.
     */
    public String signUp(String email, String password)
            throws IOException, InterruptedException {

        String url =
                "https://identitytoolkit.googleapis.com/v1/accounts:signUp?key="
                        + apiKey;

        String body = json(email, password);

        return post(url, body);
    }

    /**
     * Sends HTTP POST request to Firebase Auth endpoint.
     */
    private String post(String url, String body)
            throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                .build();

        HttpResponse<String> response =
                client.send(request, HttpResponse.BodyHandlers.ofString());

        // If request successful, return JSON response
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            return response.body();
        }

        // Otherwise throw error so controller can display message
        throw new IOException(
                "Auth failed: HTTP " + response.statusCode() + " -> " + response.body());
    }

    /**
     * Creates JSON body required by Firebase Auth API.
     */
    private String json(String email, String password) {
        return "{"
                + "\"email\":\"" + escape(email) + "\","
                + "\"password\":\"" + escape(password) + "\","
                + "\"returnSecureToken\":true"
                + "}";
    }

    /**
     * Escapes special characters for safe JSON formatting.
     */
    private String escape(String s) {
        return s.replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}