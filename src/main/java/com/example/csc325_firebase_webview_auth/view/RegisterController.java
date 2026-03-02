package com.example.csc325_firebase_webview_auth.view;

import com.example.csc325_firebase_webview_auth.model.FirebaseAuthService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

/**
 * Controller for RegisterView.fxml
 * Handles new account creation using Firebase.
 */
public class RegisterController {

    private static final String FIREBASE_WEB_API_KEY = "AIzaSyCmFt40pPY_-1Q2Mwa0_gNknO8xLZUHuB4";

    private final FirebaseAuthService auth =
            new FirebaseAuthService(FIREBASE_WEB_API_KEY);

    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label statusLabel;

    /**
     * Called when Register button is pressed.
     */
    @FXML
    private void onRegister() {

        statusLabel.setText("");

        String email = emailField.getText().trim();
        String pass = passwordField.getText();
        String confirm = confirmPasswordField.getText();

        // Basic validation
        if (email.isEmpty() || pass.isEmpty() || confirm.isEmpty()) {
            statusLabel.setText("Fill out all fields.");
            return;
        }

        if (!pass.equals(confirm)) {
            statusLabel.setText("Passwords do not match.");
            return;
        }

        if (pass.length() < 6) {
            statusLabel.setText("Password must be at least 6 characters.");
            return;
        }

        try {
            String responseJson = auth.signUp(email, pass);

            System.out.println("REGISTER SUCCESS: " + responseJson);

            // After successful registration, return to login
            App.setRoot("/files/LoginView.fxml");

        } catch (IOException | InterruptedException e) {
            statusLabel.setText("Registration failed. Email may already exist.");
            e.printStackTrace();
        }
    }

    /**
     * Return to Login screen.
     */
    @FXML
    private void goToLogin() {
        try {
            App.setRoot("/files/LoginView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}