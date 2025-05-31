import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.draw.LineSeparator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

/**
 * ============================================================================
 * CLASSE PDF - GÉNÉRATION DE RAPPORTS PDF POUR LES BATEAUX VOYAGEURS
 * ============================================================================
 *
 * Cette classe utilise la bibliothèque iText pour créer des documents PDF
 * contenant un rapport détaillé de tous les bateaux voyageurs avec leurs
 * images et descriptions formatées.
 */
public class Pdf {

    /**
     * Méthode principale pour créer un rapport PDF des bateaux voyageurs
     *
     * @param path Chemin de destination du fichier PDF à créer
     * @param bateaux HashMap contenant tous les bateaux à inclure dans le rapport
     * @throws IOException En cas d'erreur d'écriture du fichier
     * @throws DocumentException En cas d'erreur lors de la création du document PDF
     */
    public static void createPdf(String path, HashMap<Integer,BateauVoyageur> bateaux) throws IOException, DocumentException {

        // === INITIALISATION DU DOCUMENT PDF ===
        // Création d'un document avec format par défaut (A4)
        Document document = new Document();

        // Configuration des marges du document (gauche, droite, haut, bas)
        // Marges importantes en haut (110) et bas (100) pour laisser place à l'en-tête et pied de page
        document.setMargins(0, 0, 110, 100);

        // Création du writer qui va écrire le contenu dans le fichier de destination
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(path));

        // === CONFIGURATION DE L'EN-TÊTE ET PIED DE PAGE ===
        // Ajout d'un gestionnaire d'événements pour gérer automatiquement
        // l'en-tête (logo) et le pied de page (numérotation) sur chaque page
        writer.setPageEvent(new HeaderFooterEvent());

        // Ouverture du document pour commencer l'écriture
        document.open();

        // === CRÉATION DU TITRE PRINCIPAL ===
        // Définition de la police pour le titre (Helvetica Bold, taille 18)
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);

        // Création du paragraphe de titre
        Paragraph title = new Paragraph("Rapport des Bateaux Voyageurs", titleFont);
        title.setAlignment(Paragraph.ALIGN_CENTER);  // Centrage horizontal
        title.setSpacingAfter(20);  // Espacement de 20 points après le titre
        document.add(title);

        // Ajout d'une ligne vide pour séparer le titre du contenu
        document.add(Chunk.NEWLINE);

        // === GÉNÉRATION DU CONTENU POUR CHAQUE BATEAU ===
        // Parcours de tous les bateaux dans la HashMap
        for (BateauVoyageur bateauVoyageur : bateaux.values()) {

            // --- CRÉATION D'UN TABLEAU POUR MISE EN PAGE ---
            // Tableau à 2 colonnes pour disposer le texte et l'image côte à côte
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80); // Le tableau occupe 80% de la largeur de la page
            table.setWidths(new float[]{1, 1}); // Chaque colonne occupe 50% du tableau
            table.setHorizontalAlignment(Element.ALIGN_CENTER); // Centrage du tableau

            // --- COLONNE 1 : DESCRIPTIF TEXTUEL DU BATEAU ---
            // Création d'une cellule contenant la description du bateau
            // (utilise la méthode toString() de BateauVoyageur)
            PdfPCell descriptifCell = new PdfPCell(new Paragraph(bateauVoyageur.toString()));
            descriptifCell.setBorder(0);  // Suppression des bordures pour un rendu propre
            descriptifCell.setPadding(10); // Espacement interne de 10 points
            table.addCell(descriptifCell);

            // --- COLONNE 2 : IMAGE DU BATEAU ---
            PdfPCell imageCell = new PdfPCell();

            // Vérification que le bateau a une image associée
            if (bateauVoyageur.getImage() != null) {
                // Chargement de l'image depuis les ressources du projet
                // Objects.requireNonNull() lance une exception si l'image n'existe pas
                Image imageBateau = Image.getInstance(Objects.requireNonNull(Pdf.class.getResource(bateauVoyageur.getImage())));

                // Redimensionnement de l'image pour qu'elle s'adapte dans un carré de 200x200 pixels
                // en conservant les proportions originales
                imageBateau.scaleToFit(200, 200);
                imageCell.addElement(imageBateau);
            }

            imageCell.setBorder(0);  // Suppression des bordures
            imageCell.setHorizontalAlignment(Element.ALIGN_CENTER);  // Centrage de l'image
            table.addCell(imageCell);

            // Ajout du tableau complet au document
            document.add(table);

            // --- SÉPARATEUR VISUEL ENTRE CHAQUE BATEAU ---
            // Création d'une ligne de séparation grise
            // Paramètres : épaisseur (0.5f), largeur (70% de la page), couleur grise
            Chunk lineSeparator = new Chunk(new LineSeparator(0.5f, 70, BaseColor.GRAY, Element.ALIGN_CENTER, -1));
            document.add(lineSeparator);

            // Ajout d'un espace vertical après chaque bateau
            document.add(Chunk.NEWLINE);
        }

        // === FINALISATION DU DOCUMENT ===
        // Fermeture du document (obligatoire pour finaliser l'écriture)
        document.close();
    }

    /**
     * ============================================================================
     * CLASSE INTERNE - GESTIONNAIRE D'EN-TÊTE ET PIED DE PAGE
     * ============================================================================
     *
     * Cette classe hérite de PdfPageEventHelper et permet de personnaliser
     * automatiquement chaque page du document avec un logo en en-tête
     * et une numérotation en pied de page.
     */
    static class HeaderFooterEvent extends PdfPageEventHelper {

        // Image du logo qui sera affichée sur chaque page
        Image logo;

        /**
         * Méthode appelée une seule fois à l'ouverture du document
         * Utilisée pour initialiser les éléments réutilisables (comme le logo)
         *
         * @param writer L'objet PdfWriter qui écrit le document
         * @param document Le document PDF en cours de création
         */
        @Override
        public void onOpenDocument(PdfWriter writer, Document document) {
            try {
                // === CHARGEMENT ET CONFIGURATION DU LOGO ===
                // Chargement du logo depuis les ressources (dossier resources du projet)
                logo = Image.getInstance(Objects.requireNonNull(getClass().getResource("/logoMarieteam.png")));

                // Redimensionnement du logo pour qu'il s'adapte dans un carré de 80x80 pixels
                logo.scaleToFit(80, 80);

            } catch (BadElementException | IOException e) {
                // Gestion d'erreur si le logo ne peut pas être chargé
                e.printStackTrace();
            }
        }

        /**
         * Méthode appelée automatiquement à la fin de chaque page
         * Utilisée pour ajouter l'en-tête (logo) et le pied de page (numérotation)
         *
         * @param writer L'objet PdfWriter qui écrit le document
         * @param document Le document PDF en cours de création
         */
        @Override
        public void onEndPage(PdfWriter writer, Document document) {

            // === AJOUT DU LOGO EN EN-TÊTE ===
            logo.setAlignment(Image.ALIGN_CENTER); // Centrage du logo

            // Calcul de la position absolue du logo :
            // - Horizontalement : centré sur la page
            // - Verticalement : 70 points depuis le haut de la page
            logo.setAbsolutePosition(
                    (document.getPageSize().getWidth() - logo.getScaledWidth()) / 2,  // Position X (centrée)
                    document.getPageSize().getTop() - 70  // Position Y (haut de page - 70 points)
            );

            try {
                document.add(logo);
            } catch (DocumentException e) {
                e.printStackTrace();
            }

            // === AJOUT DE LA NUMÉROTATION EN PIED DE PAGE ===
            // Création du texte de pied de page avec le numéro de page actuel
            Phrase footer = new Phrase(
                    "Page " + writer.getPageNumber(),
                    FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL)
            );

            // Positionnement du texte centré en bas de la page
            ColumnText.showTextAligned(
                    writer.getDirectContent(),           // Surface de dessin
                    Element.ALIGN_CENTER,                // Alignement centré
                    footer,                              // Texte à afficher
                    (document.left() + document.right()) / 2,  // Position X (centre de la page)
                    document.bottom() - 10,              // Position Y (10 points depuis le bas)
                    0                                    // Rotation (0 = pas de rotation)
            );
        }
    }
}