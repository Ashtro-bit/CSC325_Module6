package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.FirebaseAuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

/**
 * Controller for LoginView.fxml
 * Handles user login using Firebase Authentication.
 */
public class LoginController {

    // Replace with your Firebase Web API Key
    private static final String FIREBASE_WEB_API_KEY = "PASTE_YOUR_WEB_API_KEY";

    // Service responsible for talking to Firebase
    private final FirebaseAuthService auth =
            new FirebaseAuthService(FIREBASE_WEB_API_KEY);

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    /**
     * Called when Login button is pressed.
     */
    @FXML
    private void onLogin() {

        statusLabel.setText(""); // clear previous messages

        String email = emailField.getText().trim();
        String pass = passwordField.getText();

        // Basic validation
        if (email.isEmpty() || pass.isEmpty()) {
            statusLabel.setText("Enter email and password.");
            return;
        }

        try {
            // Attempt Firebase authentication
            String responseJson = auth.signIn(email, pass);

            System.out.println("LOGIN SUCCESS: " + responseJson);

            // Navigate to main application screen
            App.setRoot("/files/AccessFBView.fxml");

        } catch (IOException | InterruptedException e) {
            statusLabel.setText("Login failed. Check credentials.");
            e.printStackTrace();
        }
    }

    /**
     * Navigate to Register screen.
     */
    @FXML
    private void goToRegister() {
        try {
            App.setRoot("/files/RegisterView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}