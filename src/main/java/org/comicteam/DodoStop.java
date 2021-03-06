package org.comicteam;

import java.util.*;

import org.comicteam.helpers.*;
import org.comicteam.layouts.*;
import org.comicteam.models.*;
import org.comicteam.models.Text;
import org.comicteam.models.ballons.*;
import org.comicteam.models.ballons.pointers.*;

import javafx.scene.text.*;

public class DodoStop
{
    private static final String name = "Bulletin Météo";
    private static final String serie = "Les Profs";
    private static final String strAuthors = "Pica;Mauricet;Erroc";
    private static final String description = "La vie des profs haut en couleur";
    private static final Size size = new Size(150, 150);
    private static List<String> authors;

    static
    {
        authors = new ArrayList<>();
        for (String author : strAuthors.split(";"))
        {
            authors.add(author);
        }
    }

    public static ComicBook getBook ()
    {
        List<ComicPage> pages = new ArrayList<>();
        pages.add(getPage());
        pages.add(new ComicPage(2));
        pages.add(new ComicPage(3));
        pages.add(new ComicPage(4));
        pages.add(new ComicPage(5));
        pages.add(new ComicPage(6));

        return new ComicBook(
                name,
                serie,
                authors,
                description,
                size,
                pages
        );
    }

    public static ComicPage getPage ()
    {
        return new ComicPage(1, getPanels());
    }

    public static List<ComicPanel> getPanels ()
    {
        List<ComicPanel> panels = new ArrayList<>();
        panels.add(getPanel1());
        panels.add(getPanel2());
        panels.add(getPanel3());

        return panels;
    }

    public static ComicPanel getPanel1 ()
    {
        ComicPanel panel = new ComicPanel(
                new ComicLayout(
                        new Position(5, 5),
                        new Size(40, 40)
                )
        );

        return panel;
    }

    private static ComicPanel getPanel2 ()
    {
        return new ComicPanel(
                new ComicLayout(
                        new Position(50, 5),
                        new Size(40, 40)
                )
        );
    }

    public static ComicPanel getPanel3 ()
    {
        List<ComicModel> models = new ArrayList<>();
        models.add(getCaption());
        models.add(getSpeech());

        return new ComicPanel(
                models,
                new ComicLayout(
                        new Position(5, 50),
                        new Size(100, 40)
                )
        );
    }

    public static Balloon getCaption ()
    {
        ComicPanel comicPanel = FXMLHelper.getSelectedComicPanel();

        return new Caption(
                comicPanel,
                null,
                new ComicLayout(
                        new Position(0, 0),
                        new Size(85, 5)
                ),
                0,
                new Text(
                        comicPanel,
                        null,
                        null,
                        0,
                        "Comme beaucoup de profs, vous êtes insomniaque...",
                        new Font("Arial", 10)
                ),
                null
        );
    }

    public static Balloon getSpeech ()
    {
        ComicPanel comicPanel = FXMLHelper.getSelectedComicPanel();

        return new SpeechBalloon(
                comicPanel,
                null,
                new ComicLayout(
                        new Position(50, 30),
                        new Size(30, 30)
                ),
                0,
                new Text(
                        comicPanel,
                        null,
                        null,
                        0,
                        "Ron\nZZZ...",
                        new Font("Arial", 10)
                ),
                new SpeechBalloonPointer(
                        new ComicLayout(
                                new Position(30, 70),
                                null
                        )
                )
        );
    }
}