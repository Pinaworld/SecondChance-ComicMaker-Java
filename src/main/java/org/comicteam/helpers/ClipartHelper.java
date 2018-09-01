package org.comicteam.helpers;

import java.io.*;
import java.net.*;
import java.util.*;

import org.json.*;

import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.*;

import javafx.scene.image.*;

public class ClipartHelper
{
    private static final String CLIPART_URL = "http://openclipart.org/search/json";
    private static JSONObject requestObject;
    private static JSONArray imagesJson;
    private static List<Image> images;

    static
    {
        images = new ArrayList<>();
    }

    public static void request (String keyword)
    {
        try
        {
            String url = String.format("%s/?query=%s&sort=downloads", CLIPART_URL, keyword);
            requestObject = Unirest.get(url).asJson().getBody().getObject();

            url = String.format("%s/?query=%s&sort=downloads&amount=%s", CLIPART_URL, keyword, getResultsCount());
            requestObject = Unirest.get(url).asJson().getBody().getObject();

            imagesJson = requestObject.getJSONArray("payload");
            images = new ArrayList<>();

            for (int i = 0; i < imagesJson.length(); i++)
            {
                images.add(getImage(i));
                //System.out.println(i + 1 + "   " + getLoadedPagesCount());
            }
        }
        catch (UnirestException e)
        {
            e.printStackTrace();
        }
    }

    private static int getResultsCount ()
    {
        return requestObject.getJSONObject("info").getInt("results");
    }

    public static int getLoadedPagesCount ()
    {
        return images.size() / 4;
    }

    public static Image getImageTopLeft (int page)
    {
        return images.get(page * 4);
    }

    public static Image getImageTopRight (int page)
    {
        return images.get(page * 4 + 1);
    }

    public static Image getImageBottomLeft (int page)
    {
        return images.get(page * 4 + 2);
    }

    public static Image getImageBottomRight (int page)
    {
        return images.get(page * 4 + 3);
    }

    private static Image getImage (int index)
    {
        if (imagesJson.length() <= index)
        {
            return null;
        }

        String url = imagesJson.getJSONObject(index).getJSONObject("svg").getString("png_thumb");

        try (InputStream in = new URL(url).openStream())
        {
            return new Image(in);
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}