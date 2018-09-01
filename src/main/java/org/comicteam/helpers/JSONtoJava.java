package org.comicteam.helpers;

import java.util.*;

import org.comicteam.*;
import org.comicteam.layouts.*;
import org.comicteam.models.*;
import org.comicteam.models.Text;
import org.comicteam.models.ballons.*;
import org.comicteam.models.ballons.pointers.*;
import org.json.simple.*;

import javafx.scene.canvas.*;
import javafx.scene.image.*;
import javafx.scene.text.*;

public class JSONtoJava
{
    private static int pageIndex;

    public static ComicBook javaBook (JSONObject j)
    {
        pageIndex = -1;

        List<ComicPage> pages = new ArrayList<>();

        for (Object page : (List<Object>) j.get("pages"))
        {
            pages.add(javaPage((JSONObject) page));
        }

        return new ComicBook(
                String.valueOf(j.get("name")),
                String.valueOf(j.get("serie")),
                (ArrayList) j.get("authors"),
                String.valueOf(j.get("description")),
                javaSize((JSONObject) j.get("size")),
                pages
        );
    }

    private static ComicPage javaPage (JSONObject j)
    {
        List<ComicPanel> panels = new ArrayList<>();

        for (Object panel : (List<Object>) j.get("panels"))
        {
            panels.add(javaPanel((JSONObject) panel));
        }

        pageIndex++;

        return new ComicPage(
                pageIndex,
                panels
        );
    }

    private static ComicPanel javaPanel (JSONObject j)
    {
        List<ComicModel> models = new ArrayList<>();

        for (Object model : (List<Object>) j.get("models"))
        {
            JSONObject j2 = (JSONObject) model;

            if (j2.containsKey("ComicModel"))
            {
                System.out.println("ComicModel");
                models.add(javaModel((JSONObject) model));
            }
            else if (j2.containsKey("SpeechBalloon"))
            {
                System.out.println("SpeechBalloon");
                models.add(javaSpeechBalloon((JSONObject) model));
            }
            else if (j2.containsKey("ThoughtBalloon"))
            {
                System.out.println("ThoughtBalloon");
                models.add(javaThoughtBalloon((JSONObject) model));
            }
            else if (j2.containsKey("Caption"))
            {
                System.out.println("Caption");
                models.add(javaCaption((JSONObject) model));
            }
            else
            {
                System.out.println("null");
            }
        }

        return new ComicPanel(
                models,
                javaLayout((JSONObject) j.get("Layout"))
        );
    }

    private static ComicModel javaModel (JSONObject j)
    {
        j = (JSONObject) j.get("ComicModel");

        ComicLayout layout = javaLayout((JSONObject) j.get("Layout"));
        Canvas canvas = null;

        if (j.containsKey("Canvas"))
        {
            canvas = new Canvas(MM.toPx(layout.getSize().getHorizontal()), MM.toPx(layout.getSize().getVertical()));

            String name = j.get("Canvas").toString();

            Image image = CMFile.cmfile.getImage(name);

            canvas.getGraphicsContext2D().drawImage(image, 0, 0);
            canvas.setLayoutX(MM.toPx(layout.getPosition().getHorizontal()));
            canvas.setLayoutY(MM.toPx(layout.getPosition().getVertical()));
        }

        ComicPanel comicPanel = FXMLHelper.getSelectedComicPanel();

        return new ComicModel(
                String.valueOf(j.get("name")),
                canvas,
                comicPanel,
                javaLayout((JSONObject) j.get("Layout")),
                (int) ((long) j.get("background"))
        );
    }

    private static Balloon javaSpeechBalloon (JSONObject j)
    {
        j = (JSONObject) j.get("SpeechBalloon");
        ComicPanel comicPanel = FXMLHelper.getSelectedComicPanel();

        return new SpeechBalloon(
                comicPanel,
                null,
                javaLayout((JSONObject) j.get("Layout")),
                (int) ((long) j.get("background")),
                javaText((JSONObject) j.get("Text")),
                javaSpeechBalloonPointer((JSONObject) j.get("SpeechBalloonPointer"))
        );
    }

    private static Balloon javaThoughtBalloon (JSONObject j)
    {
        ComicPanel comicPanel = FXMLHelper.getSelectedComicPanel();

        return new ThoughtBalloon(
                comicPanel,
                null,
                javaLayout((JSONObject) j.get("Layout")),
                (int) ((long) j.get("background")),
                javaText((JSONObject) j.get("Text")),
                javaThoughtBalloonPointer((JSONObject) j.get("ThoughtBalloonPointer"))
        );
    }

    private static Balloon javaCaption (JSONObject j)
    {
        j = (JSONObject) j.get("Caption");
        ComicPanel comicPanel = FXMLHelper.getSelectedComicPanel();

        return new Caption(
                comicPanel,
                null,
                javaLayout((JSONObject) j.get("Layout")),
                (int) ((long) j.get("background")),
                javaText((JSONObject) j.get("Text")),
                null
        );
    }

    private static BalloonPointer javaSpeechBalloonPointer (JSONObject j)
    {
        return new SpeechBalloonPointer(
                javaLayout((JSONObject) j.get("Layout"))
        );
    }

    private static BalloonPointer javaThoughtBalloonPointer (JSONObject j)
    {
        return new ThoughtBalloonPointer(
                javaLayout((JSONObject) j.get("Layout")),
                (int) ((long) j.get("bubblesCount"))
        );
    }

    private static Text javaText (JSONObject j)
    {
        ComicLayout layout = null;

        if (j.containsKey("Layout"))
        {
            layout = javaLayout((JSONObject) j.get("Layout"));
        }
        ComicPanel comicPanel = FXMLHelper.getSelectedComicPanel();

        return new Text(
                comicPanel,
                null,
                layout,
                (int) ((long) j.get("background")),
                String.valueOf(j.get("String")),
                javaFont((JSONObject) j.get("Font"))
        );
    }

    private static Font javaFont (JSONObject j)
    {
        return new Font(
                String.valueOf(j.get("name")),
                (double) j.get("size")
        );
    }

    private static ComicLayout javaLayout (JSONObject j)
    {
        Position p = null;
        Size s = null;

        if (j.containsKey("Position"))
        {
            p = javaPosition((JSONObject) j.get("Position"));
        }

        if (j.containsKey("Size"))
        {
            s = javaSize((JSONObject) j.get("Size"));
        }

        return new ComicLayout(
                p,
                s
        );
    }

    private static Position javaPosition (JSONObject j)
    {
        return new Position(
                (int) ((long) j.get("horizontal")),
                (int) ((long) j.get("vertical"))
        );
    }

    private static Size javaSize (JSONObject j)
    {
        return new Size(
                (int) ((long) j.get("horizontal")),
                (int) ((long) j.get("vertical"))
        );
    }
}