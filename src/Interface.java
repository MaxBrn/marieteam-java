import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Interface principale de l'application Flotte Marieteam
 * Cette classe crée une interface graphique permettant de visualiser et modifier
 * les informations des bateaux voyageurs et leurs équipements
 */
public class Interface extends JFrame {

    // ========================================
    // ATTRIBUTS DE LA CLASSE
    // ========================================

    /** Label pour afficher l'image du bateau sélectionné */
    private JLabel imageLabel;

    /** Couleurs du thème de l'application */
    private static final Color PRIMARY_COLOR = new Color(24, 100, 171);      // Bleu principal
    private static final Color SECONDARY_COLOR = new Color(245, 250, 255);   // Bleu très clair pour l'arrière-plan
    private static final Color ACCENT_COLOR = new Color(0, 150, 136);        // Vert pour les boutons d'export

    /** Polices utilisées dans l'interface */
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);   // Pour les titres et boutons
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 12); // Pour le texte normal

    /** ComboBox pour sélectionner les équipements à ajouter */
    private JComboBox<Equipement> equipementComboBox;

    /** Panel contenant la liste des équipements du bateau */
    private JPanel equipListPanel;

    /** Liste des équipements actuellement assignés au bateau sélectionné */
    private List<Equipement> currentEquipements;

    /** Panel contenant le formulaire de modification - référence pour pouvoir le masquer/afficher */
    private JPanel formPanel;

    // ========================================
    // CONSTRUCTEUR PRINCIPAL
    // ========================================

    /**
     * Constructeur de l'interface principale
     * @param bateaux HashMap contenant tous les bateaux indexés par leur ID
     * @param equipementsDispo Liste de tous les équipements disponibles
     */
    public Interface(HashMap<Integer, BateauVoyageur> bateaux, List<Equipement> equipementsDispo) {

        // ========================================
        // CONFIGURATION DE LA FENÊTRE PRINCIPALE
        // ========================================

        setTitle("Flotte Marieteam");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centre la fenêtre sur l'écran
        setResizable(false);          // Empêche le redimensionnement

        // ========================================
        // CONFIGURATION DU LOOK AND FEEL
        // ========================================

        try {
            // Utilise le thème système pour un rendu natif
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

            // Personnalisation des couleurs des composants UI
            UIManager.put("ComboBox.selectionBackground", PRIMARY_COLOR);
            UIManager.put("ComboBox.selectionForeground", Color.WHITE);
            UIManager.put("Button.background", PRIMARY_COLOR);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("TextField.caretForeground", PRIMARY_COLOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // ========================================
        // CONSTRUCTION DU PANNEAU GAUCHE (FORMULAIRE)
        // ========================================

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(320, 600));

        // Bordure : ligne droite + marges internes
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        leftPanel.setBackground(Color.WHITE);

        // Titre du panneau gauche
        JLabel titleLabel = new JLabel("DÉTAILS DU BATEAU");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createVerticalStrut(15)); // Espacement vertical

        // ========================================
        // COMBOBOX DE SÉLECTION DU BATEAU
        // ========================================

        JComboBox<BateauVoyageur> comboBox = new JComboBox<>();
        comboBox.setFont(REGULAR_FONT);
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboBox.setPreferredSize(new Dimension(280, 30));
        comboBox.setMaximumSize(new Dimension(280, 30));
        comboBox.setBackground(Color.WHITE);

        // Style de la bordure
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // Ajout des éléments : null en premier (aucune sélection), puis tous les bateaux
        comboBox.addItem(null);
        for (BateauVoyageur b : bateaux.values()) comboBox.addItem(b);

        // Renderer personnalisé pour afficher les noms des bateaux
        comboBox.setRenderer(new BateauRenderer());
        leftPanel.add(comboBox);
        leftPanel.add(Box.createVerticalStrut(15));

        // ========================================
        // FORMULAIRE DE MODIFICATION
        // ========================================

        // Container principal du formulaire
        formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.setBackground(Color.WHITE);
        formPanel.setMaximumSize(new Dimension(280, Integer.MAX_VALUE));
        formPanel.setVisible(false); // Masqué par défaut (aucun bateau sélectionné)

        // --- CHAMP NOM DU BATEAU ---
        JLabel nameLabel = createFieldLabel("Nom du bateau");
        formPanel.add(nameLabel);

        JTextField nameField = createStyledTextField();
        nameField.setMaximumSize(new Dimension(280, 30));
        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(10));

        // --- CHAMP URL DE L'IMAGE ---
        JLabel urlLabel = createFieldLabel("URL de l'image");
        formPanel.add(urlLabel);

        JTextField urlField = createStyledTextField();
        urlField.setMaximumSize(new Dimension(280, 30));
        formPanel.add(urlField);
        formPanel.add(Box.createVerticalStrut(10));

        // --- LABELS POUR LES MESURES (LONGUEUR, LARGEUR, VITESSE) ---
        JPanel measureLabelPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        measureLabelPanel.setBackground(Color.WHITE);
        measureLabelPanel.setMaximumSize(new Dimension(280, 20));
        measureLabelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        measureLabelPanel.add(createFieldLabel("Longueur"));
        measureLabelPanel.add(createFieldLabel("Largeur"));
        measureLabelPanel.add(createFieldLabel("Vitesse"));

        formPanel.add(measureLabelPanel);
        formPanel.add(Box.createVerticalStrut(3));

        // --- CHAMPS POUR LES MESURES ---
        JPanel measureFieldPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        measureFieldPanel.setBackground(Color.WHITE);
        measureFieldPanel.setMaximumSize(new Dimension(280, 30));
        measureFieldPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField longueurField = createStyledTextField();
        JTextField largeurField = createStyledTextField();
        JTextField vitesseField = createStyledTextField();

        measureFieldPanel.add(longueurField);
        measureFieldPanel.add(largeurField);
        measureFieldPanel.add(vitesseField);

        formPanel.add(measureFieldPanel);
        formPanel.add(Box.createVerticalStrut(10));

        // ========================================
        // SECTION GESTION DES ÉQUIPEMENTS
        // ========================================

        // Panel pour l'ajout d'équipements (ComboBox + Bouton d'ajout)
        JPanel addEquipPanel = new JPanel(new BorderLayout(5, 0));
        addEquipPanel.setBackground(Color.WHITE);
        addEquipPanel.setMaximumSize(new Dimension(280, 30));
        addEquipPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ComboBox des équipements disponibles avec placeholder
        equipementComboBox = new JComboBox<>();
        equipementComboBox.setFont(REGULAR_FONT);
        equipementComboBox.setBackground(Color.WHITE);
        equipementComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));

        // Renderer personnalisé pour afficher un placeholder quand rien n'est sélectionné
        equipementComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));

                if (value == null && index == -1) {
                    // Placeholder affiché quand aucun élément n'est sélectionné
                    label.setText("Ajouter un équipement");
                    label.setForeground(new Color(120, 120, 120));
                } else if (value == null) {
                    label.setText("Sélectionner un équipement");
                } else {
                    label.setText(value.toString());
                }

                // Couleurs de sélection
                if (isSelected) {
                    label.setBackground(PRIMARY_COLOR);
                    label.setForeground(Color.WHITE);
                } else if (index != -1) {
                    label.setBackground(Color.WHITE);
                    label.setForeground(Color.BLACK);
                }

                return label;
            }
        });

        // Initialisation de la liste des équipements actuels
        currentEquipements = new ArrayList<>();

        // Bouton d'ajout d'équipement (avec icône)
        JButton addEquipButton = createStyledButton("", Color.WHITE, "/icons/addBlue.png");
        addEquipButton.setPreferredSize(new Dimension(30, 30));

        addEquipPanel.add(equipementComboBox, BorderLayout.CENTER);
        addEquipPanel.add(addEquipButton, BorderLayout.EAST);

        formPanel.add(addEquipPanel);
        formPanel.add(Box.createVerticalStrut(5));

        // --- LISTE SCROLLABLE DES ÉQUIPEMENTS ASSIGNÉS ---
        equipListPanel = new JPanel();
        equipListPanel.setLayout(new BoxLayout(equipListPanel, BoxLayout.Y_AXIS));
        equipListPanel.setBackground(Color.WHITE);

        JScrollPane equipScroll = new JScrollPane(equipListPanel);
        equipScroll.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        equipScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        equipScroll.setMaximumSize(new Dimension(280, 120));

        formPanel.add(equipScroll);
        formPanel.add(Box.createVerticalStrut(20));

        leftPanel.add(formPanel);
        leftPanel.add(Box.createVerticalGlue()); // Pousse les boutons vers le bas

        // ========================================
        // BOUTONS D'ACTION (EXPORT PDF + SAUVEGARDE)
        // ========================================

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(280, 40));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Bouton d'export PDF
        JButton buttonPdf = createStyledButton("Exporter", ACCENT_COLOR, "/icons/pdf.png");

        // Bouton de sauvegarde
        JButton buttonSave = createStyledButton("Sauvegarder", PRIMARY_COLOR, "/icons/diskette.png");

        buttonPanel.add(buttonPdf);
        buttonPanel.add(buttonSave);

        leftPanel.add(buttonPanel);

        // ========================================
        // CONSTRUCTION DU PANNEAU DROIT (VISUALISATION)
        // ========================================

        JPanel rightPanel = new JPanel(new BorderLayout(20, 20));
        rightPanel.setBackground(SECONDARY_COLOR);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre du panneau droit
        JLabel rightTitleLabel = new JLabel("VISUALISATION");
        rightTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        rightTitleLabel.setForeground(PRIMARY_COLOR);

        // Panel pour l'image avec cadre stylisé
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Label pour afficher l'image ou un message par défaut
        imageLabel = new JLabel("Sélectionnez un bateau", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        imageLabel.setForeground(new Color(120, 120, 120));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        rightPanel.add(rightTitleLabel, BorderLayout.NORTH);
        rightPanel.add(imagePanel, BorderLayout.CENTER);

        // ========================================
        // ASSEMBLAGE FINAL DE L'INTERFACE
        // ========================================

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // ========================================
        // LOGIQUE ET GESTIONNAIRES D'ÉVÉNEMENTS
        // ========================================

        // --- GESTIONNAIRE DE SÉLECTION DE BATEAU ---
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BateauVoyageur b = (BateauVoyageur) comboBox.getSelectedItem();

                if (b == null) {
                    // AUCUN BATEAU SÉLECTIONNÉ : masquer le formulaire et réinitialiser
                    formPanel.setVisible(false);

                    // Vider tous les champs
                    nameField.setText("");
                    urlField.setText("");
                    longueurField.setText("");
                    largeurField.setText("");
                    vitesseField.setText("");

                    // Réinitialiser la liste des équipements
                    equipListPanel.removeAll();
                    currentEquipements.clear();
                    updateEquipementComboBox(equipementsDispo);

                    // Réinitialiser l'affichage de l'image
                    imageLabel.setText("Sélectionnez un bateau");
                    imageLabel.setIcon(null);
                } else {
                    // BATEAU SÉLECTIONNÉ : afficher le formulaire et charger les données
                    formPanel.setVisible(true);

                    // Remplir les champs avec les données du bateau
                    nameField.setText(b.getNomBateau());
                    urlField.setText(b.getImage());
                    longueurField.setText(String.valueOf(b.getLongueurBateau()));
                    largeurField.setText(String.valueOf(b.getLargeurBateau()));
                    vitesseField.setText(String.valueOf(b.getVitesseBatVoy()));

                    // Réinitialiser et charger les équipements du bateau
                    equipListPanel.removeAll();
                    currentEquipements.clear();

                    if (b.getEquipements() != null && !b.getEquipements().isEmpty()) {
                        for (Equipement equip : b.getEquipements()) {
                            currentEquipements.add(equip);
                            addEquipmentToList(equipListPanel, equip);
                        }
                    }

                    // Mettre à jour la ComboBox des équipements disponibles
                    updateEquipementComboBox(equipementsDispo);

                    // CHARGEMENT ET AFFICHAGE DE L'IMAGE
                    try {
                        URL resource = (Objects.requireNonNull(getClass().getResource(b.getImage())));
                        ImageIcon originalIcon = new ImageIcon(resource);
                        Image img = originalIcon.getImage();

                        // Calcul des dimensions pour conserver le ratio d'aspect
                        int panelWidth = imagePanel.getWidth() - 20;  // Marges
                        int panelHeight = imagePanel.getHeight() - 20;

                        double imgRatio = (double) originalIcon.getIconWidth() / originalIcon.getIconHeight();
                        double panelRatio = (double) panelWidth / panelHeight;

                        int newWidth, newHeight;

                        if (panelRatio > imgRatio) {
                            // Image plus haute que large par rapport au panel
                            newHeight = panelHeight;
                            newWidth = (int) (newHeight * imgRatio);
                        } else {
                            // Image plus large que haute par rapport au panel
                            newWidth = panelWidth;
                            newHeight = (int) (newWidth / imgRatio);
                        }

                        // Redimensionnement et affichage de l'image
                        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImg);

                        imageLabel.setText("");
                        imageLabel.setIcon(scaledIcon);
                    } catch (Exception ex) {
                        // Gestion d'erreur si l'image ne peut pas être chargée
                        imageLabel.setText("Image non disponible");
                        imageLabel.setIcon(null);
                    }
                }

                // Rafraîchissement de l'interface après modification
                leftPanel.revalidate();
                leftPanel.repaint();
                imagePanel.revalidate();
                imagePanel.repaint();
            }
        });

        // --- GESTIONNAIRE D'EXPORT PDF ---
        buttonPdf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Définir le chemin de sauvegarde sur le bureau
                String userHome = System.getProperty("user.home");
                String filePath = userHome + "/Desktop/FlotteMarieteam.pdf";

                try {
                    // Création du PDF via la classe Pdf
                    Pdf.createPdf(filePath, bateaux);

                    // Demander à l'utilisateur s'il veut ouvrir le PDF
                    int option = JOptionPane.showOptionDialog(Interface.this,
                            "<html><div style='font-family:Segoe UI;font-size:12pt;padding:10px'>" +
                                    "PDF créé avec succès!<br>Souhaitez-vous ouvrir le document?</div></html>",
                            "Export réussi",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new Object[]{"Ouvrir", "Fermer"},
                            "Ouvrir");

                    // Ouvrir le PDF si l'utilisateur le souhaite
                    if (option == JOptionPane.YES_OPTION) {
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(new File(filePath));
                        }
                    }

                } catch (Exception exception) {
                    // Affichage d'erreur en cas de problème
                    JOptionPane.showMessageDialog(Interface.this,
                            "<html><div style='font-family:Segoe UI;font-size:12pt;padding:10px'>" +
                                    "Erreur lors de la création du PDF:<br>" + exception.getMessage() + "</div></html>",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
        });

        // --- GESTIONNAIRE DE SAUVEGARDE ---
        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseQuery databaseQuery = new DatabaseQuery();
                BateauVoyageur b = (BateauVoyageur) comboBox.getSelectedItem();

                // Vérifier qu'un bateau est sélectionné
                if (b == null) return;

                // Récupération des valeurs du formulaire
                String nomBateau = nameField.getText().trim();

                try {
                    // Conversion et validation des valeurs numériques
                    Double longueurBateau = Double.parseDouble(longueurField.getText().trim());
                    Double largeurBateau = Double.parseDouble(largeurField.getText().trim());
                    Double vitesseBatVoy = Double.parseDouble(vitesseField.getText().trim());
                    String image = urlField.getText().trim();

                    // Validation des données
                    if (nomBateau.isEmpty() || longueurBateau < 0 || largeurBateau < 0 || vitesseBatVoy < 0) {
                        JOptionPane.showMessageDialog(Interface.this,
                                "<html><div style='font-family:Segoe UI;font-size:12pt;padding:10px'>" +
                                        "Veuillez remplir tous les champs.</div></html>",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // MISE À JOUR EN BASE DE DONNÉES
                    Boolean update = databaseQuery.updateBateau(b.getIdBateau(), nomBateau, largeurBateau, longueurBateau,
                            vitesseBatVoy, image);

                    if (update) {
                        // Mise à jour de l'objet en mémoire
                        b.setNomBateau(nomBateau);
                        b.setLongueurBateau(longueurBateau);
                        b.setLargeurBateau(largeurBateau);
                        b.setVitesseBatVoy(vitesseBatVoy);
                        b.setImage(image);

                        // GESTION DES ÉQUIPEMENTS
                        // Supprimer tous les équipements existants pour ce bateau
                        databaseQuery.deleteAllEquipements(b.getIdBateau());

                        // Ajouter les nouveaux équipements
                        boolean allEquipmentsSaved = true;
                        for (Equipement equip : currentEquipements) {
                            boolean saved = databaseQuery.addEquipementToBateau(
                                    b.getIdBateau(),
                                    equip.getIdEquip()
                            );
                            if (!saved) {
                                allEquipmentsSaved = false;
                            }
                        }

                        // Mettre à jour la liste des équipements dans l'objet
                        b.setEquipements(new ArrayList<>(currentEquipements));

                        // Mettre à jour l'objet dans la HashMap principale
                        bateaux.put(b.getIdBateau(), b);

                        // Rafraîchir la ComboBox pour afficher le nouveau nom
                        int selectedIndex = comboBox.getSelectedIndex();
                        comboBox.removeItemAt(selectedIndex);
                        comboBox.insertItemAt(b, selectedIndex);
                        comboBox.setSelectedIndex(selectedIndex);

                        // Message de confirmation
                        String message = "<html><div style='font-family:Segoe UI;font-size:12pt;padding:10px'>" +
                                "Les modifications ont été sauvegardées.";

                        if (!allEquipmentsSaved) {
                            message += "<br><br><span style='color:orange'>Attention: Certains équipements n'ont pas pu être sauvegardés.</span>";
                        }

                        message += "</div></html>";

                        JOptionPane.showMessageDialog(Interface.this,
                                message,
                                "Sauvegarde",
                                JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        // Erreur de sauvegarde
                        JOptionPane.showMessageDialog(Interface.this,
                                "<html><div style='font-family:Segoe UI;font-size:12pt;padding:10px'>" +
                                        "Une erreur est survenue lors de la sauvegarde des modifications.</div></html>",
                                "Erreur",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    // Erreur de format des nombres
                    JOptionPane.showMessageDialog(Interface.this,
                            "<html><div style='font-family:Segoe UI;font-size:12pt;padding:10px'>" +
                                    "Veuillez entrer des valeurs numériques valides pour la longueur, largeur et vitesse.</div></html>",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // --- GESTIONNAIRE D'AJOUT D'ÉQUIPEMENT ---
        addEquipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Equipement selectedEquip = (Equipement) equipementComboBox.getSelectedItem();
                if (selectedEquip != null) {
                    // Ajouter l'équipement à la liste courante
                    currentEquipements.add(selectedEquip);

                    // Ajouter à l'affichage visuel
                    addEquipmentToList(equipListPanel, selectedEquip);

                    // Retirer l'élément de la ComboBox (plus disponible)
                    equipementComboBox.removeItem(selectedEquip);

                    // Désactiver la ComboBox s'il ne reste plus d'équipements
                    if (equipementComboBox.getItemCount() == 1) { // Seulement l'élément null
                        equipementComboBox.setEnabled(false);
                    }
                }
            }
        });

        // Affichage de la fenêtre
        setVisible(true);
    }

    // ========================================
    // MÉTHODES UTILITAIRES
    // ========================================

    /**
     * Met à jour la ComboBox des équipements disponibles
     * Exclut les équipements déjà assignés au bateau courant
     * @param allEquipements Liste de tous les équipements disponibles
     */
    private void updateEquipementComboBox(List<Equipement> allEquipements) {
        equipementComboBox.removeAllItems();

        // Ajouter l'élément null pour le placeholder
        equipementComboBox.addItem(null);

        // Ajouter seulement les équipements non encore assignés
        for (Equipement equip : allEquipements) {
            boolean alreadyPresent = false;
            for (Equipement current : currentEquipements) {
                if (current.getIdEquip() == equip.getIdEquip()) {
                    alreadyPresent = true;
                    break;
                }
            }
            if (!alreadyPresent) {
                equipementComboBox.addItem(equip);
            }
        }

        // Activer/désactiver selon la disponibilité d'équipements
        boolean hasItems = equipementComboBox.getItemCount() > 1;
        equipementComboBox.setEnabled(hasItems);
    }

    /**
     * Ajoute un équipement à la liste visuelle avec bouton de suppression
     * @param container Panel contenant la liste
     * @param equipment Équipement à ajouter
     */
    private void addEquipmentToList(JPanel container, Equipement equipment) {
        // Panel pour un item d'équipement (nom + bouton supprimer)
        JPanel itemPanel = new JPanel(new BorderLayout(5, 0));
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setMaximumSize(new Dimension(260, 25));

        // Label avec le nom de l'équipement
        JLabel itemLabel = new JLabel(equipment.toString());
        itemLabel.setFont(REGULAR_FONT);

        // Bouton de suppression avec icône
        JButton deleteButton = createStyledButton("", Color.white, "/icons/trash.png");
        deleteButton.setPreferredSize(new Dimension(30, 30));

        // Gestionnaire de suppression
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Retirer de la liste courante
                currentEquipements.remove(equipment);

                // Retirer visuellement
                container.remove(itemPanel);
                container.revalidate();
                container.repaint();
                // Rajouter dans la ComboBox des équipements disponibles
                equipementComboBox.addItem(equipment);

                // Réactiver les contrôles après suppression
                equipementComboBox.setEnabled(true);
            }
        });

        // Configuration du layout du panneau d'élément
        itemPanel.add(itemLabel, BorderLayout.CENTER);     // Label au centre
        itemPanel.add(deleteButton, BorderLayout.EAST);    // Bouton de suppression à droite

        // Ajout du panneau à son conteneur parent
        container.add(itemPanel);
        container.add(Box.createVerticalStrut(2)); // Espacement vertical de 2px entre chaque élément

        // Actualisation de l'affichage du conteneur
        container.revalidate(); // Recalcule la mise en page
        container.repaint();    // Redessine les composants
    }

    /**
     * ============================================================================
     * MÉTHODES UTILITAIRES POUR LA CRÉATION DE COMPOSANTS STYLISÉS
     * ============================================================================
     */

    /**
     * Crée un label stylisé pour les champs de formulaire
     * @param text Le texte à afficher dans le label
     * @return Un JLabel avec le style défini (police, couleur, bordures)
     */
    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);

        // Configuration du style du texte
        label.setFont(REGULAR_FONT);                           // Police standard
        label.setForeground(new Color(70, 70, 70));           // Couleur gris foncé

        // Ajout d'une marge en bas du label (3px)
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 0));

        // Alignement à gauche dans son conteneur
        label.setAlignmentX(Component.LEFT_ALIGNMENT);

        return label;
    }

    /**
     * Crée un champ de texte avec un style personnalisé
     * @return Un JTextField stylisé avec bordures et padding
     */
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();

        // Police du texte saisi
        field.setFont(REGULAR_FONT);

        // Création d'une bordure composée :
        // - Bordure externe : ligne grise fine
        // - Bordure interne : padding de 6px vertical et 8px horizontal
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)), // Bordure grise
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));            // Espacement interne

        // Alignement à gauche dans le conteneur
        field.setAlignmentX(Component.LEFT_ALIGNMENT);

        return field;
    }

    /**
     * Crée un bouton stylisé avec couleur personnalisée et icône optionnelle
     * @param text Le texte affiché sur le bouton
     * @param color La couleur de fond du bouton
     * @param iconPath Le chemin vers l'icône (peut être null)
     * @return Un JButton entièrement stylisé avec effets de survol
     */
    private JButton createStyledButton(String text, Color color, String iconPath) {
        JButton button = new JButton(text);

        // === CONFIGURATION DU STYLE DE BASE ===
        button.setFont(HEADER_FONT);           // Police en gras
        button.setForeground(Color.WHITE);     // Texte blanc
        button.setBackground(color);           // Couleur de fond personnalisée
        button.setFocusPainted(false);         // Supprime le contour de focus
        button.setBorderPainted(false);        // Supprime la bordure par défaut
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); // Curseur en forme de main

        // === GESTION DE L'ICÔNE ===
        if (iconPath != null) {
            try {
                // Chargement de l'icône depuis les ressources
                ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));

                // Redimensionnement de l'icône à 16x16 pixels avec lissage
                Image scaledImage = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                // Gestion d'erreur si l'icône n'est pas trouvée
                System.err.println("Erreur lors du chargement de l'icône : " + iconPath);
            }
        }

        // === EFFETS VISUELS DE SURVOL ===
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Survol : assombrit légèrement la couleur (10%)
                button.setBackground(darken(color, 0.1f));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Sortie du survol : retour à la couleur originale
                button.setBackground(color);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Clic enfoncé : assombrit davantage (20%)
                button.setBackground(darken(color, 0.2f));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Relâchement : retour à l'état de survol
                button.setBackground(darken(color, 0.1f));
            }
        });

        return button;
    }

    /**
     * Utilitaire pour assombrir une couleur d'un pourcentage donné
     * @param color La couleur originale
     * @param fraction Le pourcentage d'assombrissement (0.1 = 10%)
     * @return Une nouvelle couleur plus sombre
     */
    private Color darken(Color color, float fraction) {
        // Calcul des nouvelles valeurs RGB en réduisant chaque composante
        int red = Math.max(0, Math.round(color.getRed() * (1 - fraction)));
        int green = Math.max(0, Math.round(color.getGreen() * (1 - fraction)));
        int blue = Math.max(0, Math.round(color.getBlue() * (1 - fraction)));

        return new Color(red, green, blue);
    }

    /**
     * ============================================================================
     * RENDERER PERSONNALISÉ POUR LES COMBOBOX DE BATEAUX
     * ============================================================================
     */

    /**
     * Classe interne pour personnaliser l'affichage des éléments dans la ComboBox des bateaux
     * Hérite de DefaultListCellRenderer pour modifier uniquement l'apparence
     */
    class BateauRenderer extends DefaultListCellRenderer {

        /**
         * Méthode appelée pour chaque élément affiché dans la liste déroulante
         * @param list La JList qui affiche les éléments
         * @param value L'objet à afficher (BateauVoyageur ou null)
         * @param index L'index de l'élément dans la liste
         * @param isSelected Si l'élément est actuellement sélectionné
         * @param cellHasFocus Si la cellule a le focus
         * @return Le composant qui sera affiché (JLabel stylisé)
         */
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {

            // Récupération du composant de base (JLabel)
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            // Ajout d'un padding autour du texte (3px vertical, 5px horizontal)
            label.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));

            // === GESTION DU CONTENU AFFICHÉ ===
            if (value == null) {
                // Cas où aucun bateau n'est sélectionné : afficher un texte d'aide
                label.setText("Sélectionner un bateau");
                label.setFont(REGULAR_FONT);
            } else {
                // Cas normal : afficher le nom du bateau
                label.setText(((BateauVoyageur) value).getNomBateau());
                label.setFont(REGULAR_FONT);
            }

            // === GESTION DES COULEURS SELON L'ÉTAT ===
            if (isSelected) {
                // Élément sélectionné : fond coloré, texte blanc
                label.setBackground(PRIMARY_COLOR);
                label.setForeground(Color.WHITE);
            } else {
                // Élément normal : fond blanc, texte noir
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }

            return label;
        }
    }
}