package org.comicteam.helpers;

import java.io.*;
import java.net.*;

import org.comicteam.controllers.*;
import org.comicteam.forms.*;
import org.json.*;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.*;

public class UpdateHelper
{
    public static String HOST_URL = "http://127.0.0.1:8080/updates";
    public static String CURRENT_VERSION = "1.0";

    public static String getLatestVersionString ()
    {
        try
        {
            JSONArray versions = Unirest.get(HOST_URL).asJson().getBody().getArray();
            JSONObject latest = versions.getJSONObject(versions.length() - 1);

            return latest.getString("version");
        }
        catch (UnirestException e)
        {
            e.printStackTrace();
        }

        return "";
    }

    public static boolean newVersionIsAvailable ()
    {
        if (Double.valueOf(CURRENT_VERSION) < Double.valueOf(getLatestVersionString()))
        {
            return true;
        }

        return false;
    }

    public static void writeLatestVersionFile ()
    {
        String url = String.format("%s/last", HOST_URL);

        try (InputStream in = new URL(url).openStream())
        {
            String path = MainForm.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            FileOutputStream fos = new FileOutputStream(path);

            //fos.write(in.readAllBytes());
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        FXMLHelper.closeAllWindows(WorkingController.controller.pane);
    }
}
