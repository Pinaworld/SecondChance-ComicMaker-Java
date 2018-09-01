package org.comicteam.helpers;

import java.io.*;

import javafx.scene.*;
import javafx.scene.image.*;

public class ExternalDocumentHelper
{
    public static Image getImage (Node node)
    {
        File file = FXMLHelper.chooseFile(node);

        if (file == null)
        {
            return null;
        }

        try
        {
            return new Image(new FileInputStream(file));
        }
        catch (FileNotFoundException e)
        {
            return null;
        }
    }
}