package org.comicteam.forms;

import java.io.*;

import javafx.application.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.stage.*;

public class SpeechBubbleForm extends Application
{
    @Override
    public void start (Stage primaryStage)
    {
        Parent root;

        try
        {
            root = FXMLLoader.load(getClass().getResource("/fxml/speechbubble.fxml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        Scene scene = new Scene(root, 600, 400);

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Ajout d'une bulle");
        primaryStage.initModality(Modality.APPLICATION_MODAL);

        primaryStage.showAndWait();
    }
}
