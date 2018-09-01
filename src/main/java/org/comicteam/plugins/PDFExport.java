package org.comicteam.plugins;

import javax.imageio.*;

import java.awt.image.*;
import java.io.*;

import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.graphics.image.*;
import org.comicteam.*;
import org.comicteam.controllers.*;
import org.comicteam.helpers.*;
import org.comicteam.layouts.*;

import javafx.embed.swing.*;
import javafx.print.PageLayout;
import javafx.print.*;
import javafx.scene.control.*;
import javafx.scene.image.*;

public class PDFExport extends Plugin
{

    public PDFExport ()
    {
        super("PDF Export", "1.0", "This plugin is to convert a comic to pdf");
    }

    @Override
    public void action ()
    {
        Button pdf = new Button("Export pdf");
        pdf.setPrefHeight(50);

        Button print = new Button("Print");
        print.setPrefHeight(50);

        if (MenuController.controller != null)
        {
            MenuController.controller.pluginsBox.getChildren().add(pdf);
            MenuController.controller.pluginsBox.getChildren().add(print);
        }

        pdf.setOnAction(e -> {
            writePDDocument();
        });

        print.setOnAction(e -> {
            printPDDocument(print);
        });
    }

    public PDPage writePage (PDDocument document)
    {
        WritableImage wi = new WritableImage(
                (int) EditorController.controller.editorPane.getWidth(),
                (int) EditorController.controller.editorPane.getHeight());

        wi = EditorController.controller.editorPane.snapshot(null, wi);
        BufferedImage bi = SwingFXUtils.fromFXImage(wi, null);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try
        {
            ImageIO.write(bi, "png", baos);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        PDPage page = new PDPage();
        PDPageContentStream stream = null;
        try
        {
            stream = new PDPageContentStream(document, page);

            PDImageXObject x = PDImageXObject.createFromByteArray(document, baos.toByteArray(), null);
            stream.drawImage(x, 0, 0, page.getArtBox().getWidth(), page.getArtBox().getHeight());
            stream.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return page;
    }

    public PDDocument getPDDocument ()
    {
        int currentPage = CMFile.cmfile.currentPage;

        PDDocument doc = new PDDocument();

        for (ComicPage page : CMFile.cmfile.book.getPages())
        {
            WorkingController.controller.selectPage(page);
            doc.addPage(writePage(doc));
        }

        WorkingController.controller.selectPage(CMFile.cmfile.book.getPages().get(currentPage));

        return doc;
    }

    public void writePDDocument ()
    {
        PDDocument doc = getPDDocument();

        try
        {
            doc.save(new File(String.format("%s/%s.pdf", SettingsHelper.get("savePath"), CMFile.cmfile.book.getName())));
            doc.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void printPDDocument (Button print)
    {
        int currentPage = CMFile.cmfile.currentPage;
        Printer printer = Printer.getDefaultPrinter();
        PrinterJob job = PrinterJob.createPrinterJob(printer);

        if (job == null)
        {
            System.out.println("Printer not found");
            return;
        }

        /*for (ComicPage page : CMFile.cmfile.book.getPages()) {PageLayout
            WorkingController.controller.selectPage(page);

            job.showPrintDialog(print.getScene().getWindow())//.printPage(EditorController.controller.editorPane);
        }*/

        PageLayout pl = printer.createPageLayout(Paper.A4, PageOrientation.PORTRAIT, 0, 0, 0, 0);

        job.printPage(pl, EditorController.controller.editorPane);

        //job.showPrintDialog(print.getScene().getWindow());

        job.endJob();

        WorkingController.controller.selectPage(CMFile.cmfile.book.getPages().get(currentPage));
    }
}