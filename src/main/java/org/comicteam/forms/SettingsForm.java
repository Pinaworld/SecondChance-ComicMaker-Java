package org.comicteam.forms;

import java.io.*;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.*;

public class SettingsForm extends Application
{

    @Override
    public void start (Stage primaryStage)
    {
        Parent root;

        try
        {
            root = FXMLLoader.load(getClass().getResource("/fxml/settings.fxml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(root, 600, 500);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Paramètres");
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/settings.png"))));

        primaryStage.showAndWait();
    }
}
