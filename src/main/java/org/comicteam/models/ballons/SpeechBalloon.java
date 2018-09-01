package org.comicteam.models.ballons;

import java.io.*;

import org.comicteam.layouts.*;
import org.comicteam.models.*;
import org.comicteam.models.ballons.pointers.*;

import javafx.scene.canvas.*;

public final class SpeechBalloon extends Balloon implements Serializable
{
    public SpeechBalloon (ComicPanel comicPanel, Canvas canvas, ComicLayout layout, int background, Text text, BalloonPointer pointer)
    {
        super(comicPanel, canvas, layout, background, text, pointer);
    }
}
