package org.comicteam.layouts;

import java.io.*;

public class ComicLayout implements Serializable
{
    private Position position;
    private Size size;
    private ComicLayout layout;

    public ComicLayout (Position position, Size size)
    {
        this.position = position;
        this.size = size;
    }

    public Position getPosition ()
    {
        return position;
    }

    public void setPosition (Position position)
    {
        this.position = position;
    }

    public Size getSize ()
    {
        return size;
    }

    public void setSize (Size size)
    {
        this.size = size;
    }

    @Override
    public String toString ()
    {
        final StringBuilder sb = new StringBuilder("ComicLayout{");
        sb.append("position=").append(position);
        sb.append(", size=").append(size);
        sb.append('}');
        return sb.toString();
    }
}
