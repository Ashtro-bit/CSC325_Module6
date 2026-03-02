package com.example.csc325_firebase_webview_auth.view;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashController implements Initializable {

    @FXML private ImageView logoImage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Image img = new Image(getClass().getResourceAsStream("/files/profile_empty.png"));
            if (logoImage != null) logoImage.setImage(img);
        } catch (Exception ignored) {
        }

        PauseTransition delay = new PauseTransition(Duration.seconds(2));
        delay.setOnFinished(e -> goToMain());
        delay.play();
    }

    private void goToMain() {
        try {
            App.setRoot("/files/AccessFBView.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}