package org.comicteam.forms;

import java.io.*;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.stage.*;

public class ClipartForm extends Application
{
    @Override
    public void start (Stage primaryStage)
    {
        Parent root;

        try
        {
            root = FXMLLoader.load(getClass().getResource("/fxml/clipart.fxml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Ajout d'un modèle de type Clipart");
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        primaryStage.getIcons().add(new Image(String.valueOf(getClass().getResource("/images/clipart.png"))));

        primaryStage.showAndWait();
    }
}