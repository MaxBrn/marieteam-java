import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Pdf {

    public static void createPdf(String path, List<BateauVoyageur> bateaux) throws IOException, DocumentException {
        // Création du document avec des marges
        Document document = new Document();
        document.setMargins(0, 0, 110, 100);  // Marges pour le document
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));

        // Ajouter l'événement pour la gestion de l'en-tête et de la numérotation des pages
        writer.setPageEvent(new HeaderFooterEvent());

        document.open();

        // Ajouter le titre centré en haut de la première page
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
        Paragraph title = new Paragraph("Rapport des Bateaux Voyageurs", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);
        title.setSpacingAfter(20);  // Espacement après le titre
        document.add(title);

        // Espacement avant de commencer la liste des bateaux
        document.add(Chunk.NEWLINE);

        // Parcourir la liste des bateaux et ajouter leur description avec l'image
        for (BateauVoyageur bateauVoyageur : bateaux) {
            // Créer un tableau pour disposer le descriptif et l'image côte à côte
            PdfPTable table = new PdfPTable(2);  // Deux colonnes (descriptif et image)
            table.setWidthPercentage(80); // 100% de la largeur du document
            table.setWidths(new float[]{1, 1});// Chaque colonne occupe 50% de la largeur du tableau
            table.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Colonne 1 : Descriptif du bateau
            PdfPCell descriptifCell = new PdfPCell(new Paragraph(bateauVoyageur.toString()));
            descriptifCell.setBorder(0);  // Pas de bordure pour le texte
            descriptifCell.setPadding(10);  // Espacement interne pour centrer le texte dans la cellule
            table.addCell(descriptifCell);

            // Colonne 2 : Image du bateau
            PdfPCell imageCell = new PdfPCell();
            if (bateauVoyageur.getImage() != null) {
                Image imageBateau = Image.getInstance(Objects.requireNonNull(Pdf.class.getResource(bateauVoyageur.getImage())));
                imageBateau.scaleToFit(200, 200);  // Ajuster la taille de l'image si nécessaire
                imageCell.addElement(imageBateau);
            }
            imageCell.setBorder(0);  // Pas de bordure pour l'image
            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);  // Centrer l'image
            table.addCell(imageCell);

            // Ajouter la table au document
            document.add(table);

            // Ligne de séparation grise de 70% de la largeur du document
            Chunk lineSeparator = new Chunk(new LineSeparator(0.5f, 70, BaseColor.GRAY, Element.ALIGN_CENTER, -1));
            document.add(lineSeparator);

            // Ajouter un espace après chaque bateau
            document.add(Chunk.NEWLINE);
        }

        // Fermer le document
        document.close();
    }

    // Classe interne pour gérer l'en-tête et la numérotation des pages
    static class HeaderFooterEvent extends PdfPageEventHelper {
        Image logo;

        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            try {
                // Chargement de l'image avec getResource pour un chemin relatif
                logo = Image.getInstance(Objects.requireNonNull(getClass().getResource("/logoMarieteam.png")));
                logo.scaleToFit(80, 80);
            } catch (BadElementException | IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            // Ajouter le logo dans l'en-tête à chaque page
            logo.setAlignment(Image.ALIGN_CENTER);
            logo.setAbsolutePosition((document.getPageSize().getWidth() - logo.getScaledWidth()) / 2, document.getPageSize().getTop() - 70);
            try {
                document.add(logo);
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            // Ajouter le numéro de page centré en bas de la page
            Phrase footer = new Phrase("Page " + writer.getPageNumber(), FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL));
            ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, footer,
                    (document.left() + document.right()) / 2, document.bottom() - 10, 0);
        }
    }
}
