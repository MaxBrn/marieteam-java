import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Interface extends JFrame {

    private JLabel imageLabel;
    private static final Color PRIMARY_COLOR = new Color(24, 100, 171);
    private static final Color SECONDARY_COLOR = new Color(245, 250, 255);
    private static final Color ACCENT_COLOR = new Color(0, 150, 136);
    private static final Font HEADER_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    public Interface(HashMap<Integer, BateauVoyageur> bateaux) {
        setTitle("Flotte Marieteam");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false); // Fenêtre non redimensionnable comme demandé

        // Définition du look and feel moderne
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            // Personnalisation des composants UI
            UIManager.put("ComboBox.selectionBackground", PRIMARY_COLOR);
            UIManager.put("ComboBox.selectionForeground", Color.WHITE);
            UIManager.put("Button.background", PRIMARY_COLOR);
            UIManager.put("Button.foreground", Color.WHITE);
            UIManager.put("TextField.caretForeground", PRIMARY_COLOR);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // --- PANNEAU GAUCHE ---
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setPreferredSize(new Dimension(320, 600));
        leftPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)));
        leftPanel.setBackground(Color.WHITE);

        // Titre du panneau en BLEU
        JLabel titleLabel = new JLabel("DÉTAILS DU BATEAU");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(titleLabel);
        leftPanel.add(Box.createVerticalStrut(15));

        // ComboBox stylisée
        JComboBox<BateauVoyageur> comboBox = new JComboBox<>();
        comboBox.setFont(REGULAR_FONT);
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        comboBox.setPreferredSize(new Dimension(280, 30));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        comboBox.addItem(null);
        for (BateauVoyageur b : bateaux.values()) comboBox.addItem(b);
        comboBox.setRenderer(new BateauRenderer());
        leftPanel.add(comboBox);
        leftPanel.add(Box.createVerticalStrut(15));

        // Container pour les champs - garantit un alignement parfait
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        formPanel.setBackground(Color.WHITE);
        formPanel.setMaximumSize(new Dimension(280, Integer.MAX_VALUE));

        // Champ Nom
        JLabel nameLabel = createFieldLabel("Nom du bateau");
        formPanel.add(nameLabel);

        JTextField nameField = createStyledTextField();
        nameField.setMaximumSize(new Dimension(280, 30));
        formPanel.add(nameField);
        formPanel.add(Box.createVerticalStrut(10));

        // Champ URL
        JLabel urlLabel = createFieldLabel("URL de l'image");
        formPanel.add(urlLabel);

        JTextField urlField = createStyledTextField();
        urlField.setMaximumSize(new Dimension(280, 30));
        formPanel.add(urlField);
        formPanel.add(Box.createVerticalStrut(10));

        // Labels pour les mesures (tous dans un panneau)
        JPanel measureLabelPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        measureLabelPanel.setBackground(Color.WHITE);
        measureLabelPanel.setMaximumSize(new Dimension(280, 20));
        measureLabelPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        measureLabelPanel.add(createFieldLabel("Longueur"));
        measureLabelPanel.add(createFieldLabel("Largeur"));
        measureLabelPanel.add(createFieldLabel("Vitesse"));

        formPanel.add(measureLabelPanel);
        formPanel.add(Box.createVerticalStrut(3));

        // Champs pour les mesures
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

        // Équipements - interface avec boutons de suppression intégrés
        JLabel equipLabel = createFieldLabel("Équipements");
        formPanel.add(equipLabel);

// Panel pour l'ajout d'équipements
        JPanel addEquipPanel = new JPanel(new BorderLayout(5, 0));
        addEquipPanel.setBackground(Color.WHITE);
        addEquipPanel.setMaximumSize(new Dimension(280, 30));
        addEquipPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField newEquipField = createStyledTextField();
        JButton addEquipButton = createStyledButton("", Color.WHITE, "/icons/addBlue.png");
        addEquipButton.setPreferredSize(new Dimension(30, 30));

        addEquipPanel.add(newEquipField, BorderLayout.CENTER);
        addEquipPanel.add(addEquipButton, BorderLayout.EAST);

        formPanel.add(addEquipPanel);
        formPanel.add(Box.createVerticalStrut(5));

// Panel pour contenir la liste d'équipements
        JPanel equipListPanel = new JPanel();
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

        formPanel.add(equipScroll);
        formPanel.add(Box.createVerticalStrut(20));

        leftPanel.add(formPanel);
        leftPanel.add(Box.createVerticalGlue());

        // Panel des boutons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setMaximumSize(new Dimension(280, 40));
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton buttonPdf = createStyledButton("Exporter", ACCENT_COLOR, "/icons/pdf.png");

        JButton buttonSave = createStyledButton("Sauvegarder", PRIMARY_COLOR, "/icons/diskette.png");

        buttonPanel.add(buttonPdf);
        buttonPanel.add(buttonSave);

        leftPanel.add(buttonPanel);

        // --- PANNEAU DROIT ---
        JPanel rightPanel = new JPanel(new BorderLayout(20, 20));
        rightPanel.setBackground(SECONDARY_COLOR);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre du panneau droit en BLEU
        JLabel rightTitleLabel = new JLabel("VISUALISATION");
        rightTitleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        rightTitleLabel.setForeground(PRIMARY_COLOR);

        // Panel pour l'image avec cadre
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setBackground(Color.WHITE);
        imagePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        // Label pour l'image
        imageLabel = new JLabel("Sélectionnez un bateau", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        imageLabel.setForeground(new Color(120, 120, 120));
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        rightPanel.add(rightTitleLabel, BorderLayout.NORTH);
        rightPanel.add(imagePanel, BorderLayout.CENTER);

        // --- ASSEMBLAGE FINAL ---
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(leftPanel, BorderLayout.WEST);
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        add(mainPanel);

        // --- LOGIQUE ---
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BateauVoyageur b = (BateauVoyageur) comboBox.getSelectedItem();
                if (b == null) {
                    nameField.setText("");
                    urlField.setText("");
                    longueurField.setText("");
                    largeurField.setText("");
                    vitesseField.setText("");
                    equipListPanel.removeAll(); // Effacer tous les équipements
                    equipListPanel.revalidate();
                    equipListPanel.repaint();
                    imageLabel.setText("Sélectionnez un bateau");
                    imageLabel.setIcon(null);
                } else {
                    nameField.setText(b.getNomBateau());
                    urlField.setText(b.getImage());
                    longueurField.setText(String.valueOf(b.getLongueurBateau()));
                    largeurField.setText(String.valueOf(b.getLargeurBateau()));
                    vitesseField.setText(String.valueOf(b.getVitesseBatVoy()));
                    equipListPanel.removeAll();
                    if( b.getEquipements() != null && !b.getEquipements().isEmpty()) {
                        for (Equipement equip : b.getEquipements()) {
                            addEquipmentToList(equipListPanel, equip.toString());
                        }
                    }


                    try {
                        URL resource = (Objects.requireNonNull(getClass().getResource(b.getImage())));
                        ImageIcon originalIcon = new ImageIcon(resource);
                        Image img = originalIcon.getImage();

                        // Calculer les dimensions pour conserver le ratio
                        int panelWidth = imagePanel.getWidth() - 20; // Tenir compte des marges
                        int panelHeight = imagePanel.getHeight() - 20;

                        double imgRatio = (double) originalIcon.getIconWidth() / originalIcon.getIconHeight();
                        double panelRatio = (double) panelWidth / panelHeight;

                        int newWidth, newHeight;

                        if (panelRatio > imgRatio) {
                            // L'image est plus haute que large par rapport au panel
                            newHeight = panelHeight;
                            newWidth = (int) (newHeight * imgRatio);
                        } else {
                            // L'image est plus large que haute par rapport au panel
                            newWidth = panelWidth;
                            newHeight = (int) (newWidth / imgRatio);
                        }

                        // Redimensionner l'image
                        Image scaledImg = img.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
                        ImageIcon scaledIcon = new ImageIcon(scaledImg);

                        imageLabel.setText("");
                        imageLabel.setIcon(scaledIcon);
                    } catch (Exception ex) {
                        imageLabel.setText("Image non disponible");
                        imageLabel.setIcon(null);
                    }
                }
                imagePanel.revalidate();
                imagePanel.repaint();
            }
        });

        buttonPdf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String userHome = System.getProperty("user.home");
                String filePath = userHome + "/Desktop/FlotteMarieteam.pdf";
                try {
                    Pdf.createPdf(filePath, bateaux);

                    int option = JOptionPane.showOptionDialog(Interface.this,
                            "<html><div style='font-family:Segoe UI;font-size:12pt;padding:10px'>" +
                                    "PDF créé avec succès!<br>Souhaitez-vous ouvrir le document?</div></html>",
                            "Export réussi",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            new Object[]{"Ouvrir", "Fermer"},
                            "Ouvrir");

                    if (option == JOptionPane.YES_OPTION) {
                        if (Desktop.isDesktopSupported()) {
                            Desktop.getDesktop().open(new File(filePath));
                        }
                    }

                } catch (Exception exception) {
                    JOptionPane.showMessageDialog(Interface.this,
                            "<html><div style='font-family:Segoe UI;font-size:12pt;padding:10px'>" +
                                    "Erreur lors de la création du PDF:<br>" + exception.getMessage() + "</div></html>",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                DatabaseQuery databaseQuery = new DatabaseQuery();
                BateauVoyageur b = (BateauVoyageur) comboBox.getSelectedItem();
                String nomBateau = nameField.getText().trim();
                Double longueurBateau = Double.parseDouble(longueurField.getText().trim());
                Double largeurBateau = Double.parseDouble(largeurField.getText().trim());
                Double vitesseBatVoy = Double.parseDouble(vitesseField.getText().trim());
                if (nomBateau.isEmpty() || longueurBateau < 0 || largeurBateau < 0 || vitesseBatVoy < 0) {
                    JOptionPane.showMessageDialog(Interface.this,
                            "<html><div style='font-family:Segoe UI;font-size:12pt;padding:10px'>" +
                                    "Veuillez remplir tous les champs.</div></html>",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                Boolean update= databaseQuery.updateBateau(b.getIdBateau(), nomBateau, longueurBateau, largeurBateau,vitesseBatVoy);
                if(update) {
                    BateauVoyageur bateau = new BateauVoyageur(b.getIdBateau(), nomBateau,largeurBateau,longueurBateau,vitesseBatVoy,null,"/imageAccueil.jpg");
                    bateaux.put(b.getIdBateau(), bateau);
                    ((BateauVoyageur) comboBox.getSelectedItem()).setNomBateau(nomBateau);
                    JOptionPane.showMessageDialog(Interface.this,
                            "<html><div style='font-family:Segoe UI;font-size:12pt;padding:10px'>" +
                                    "Les modifications ont été sauvegardées.</div></html>",
                            "Sauvegarde",
                            JOptionPane.INFORMATION_MESSAGE);

                }
                else {
                    JOptionPane.showMessageDialog(Interface.this,
                            "<html><div style='font-family:Segoe UI;font-size:12pt;padding:10px'>" +
                                    "Une erreur est survenue lors de la sauvegarde des modifications.</div></html>",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                }

            }
        });
        // Logique pour l'ajout d'équipement
        addEquipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newEquip = newEquipField.getText().trim();
                if (!newEquip.isEmpty()) {
                    addEquipmentToList(equipListPanel, newEquip);
                    newEquipField.setText("");
                }
            }
        });

        setVisible(true);
    }


    private void addEquipmentToList(JPanel container, String equipment) {
        JPanel itemPanel = new JPanel(new BorderLayout(5, 0));
        itemPanel.setBackground(Color.WHITE);
        itemPanel.setMaximumSize(new Dimension(260, 25));

        JLabel itemLabel = new JLabel(equipment);
        itemLabel.setFont(REGULAR_FONT);

        JButton deleteButton = createStyledButton("", Color.white, "/icons/trash.png");
        deleteButton.setPreferredSize(new Dimension(30, 30));
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                container.remove(itemPanel);
                container.revalidate();
                container.repaint();
            }
        });

        itemPanel.add(itemLabel, BorderLayout.CENTER);
        itemPanel.add(deleteButton, BorderLayout.EAST);

        container.add(itemPanel);
        container.add(Box.createVerticalStrut(2)); // Espace entre les éléments
        container.revalidate();
        container.repaint();
    }



    // Méthodes d'assistance pour la création de composants stylisés
    private JLabel createFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(REGULAR_FONT);
        label.setForeground(new Color(70, 70, 70));
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 0));
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(REGULAR_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private JButton createStyledButton(String text, Color color, String iconPath) {
        JButton button = new JButton(text);
        button.setFont(HEADER_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Ajout de l'icône si un chemin est fourni
        if (iconPath != null) {
            try {
                ImageIcon icon = new ImageIcon(Objects.requireNonNull(getClass().getResource(iconPath)));
                Image scaledImage = icon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
                button.setIcon(new ImageIcon(scaledImage));
            } catch (Exception e) {
                System.err.println("Erreur lors du chargement de l'icône : " + iconPath);
            }
        }

        // Effets de survol
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(darken(color, 0.1f));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(darken(color, 0.2f));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(darken(color, 0.1f));
            }
        });

        return button;
    }


    // Fonction pour assombrir une couleur
    private Color darken(Color color, float fraction) {
        int red = Math.max(0, Math.round(color.getRed() * (1 - fraction)));
        int green = Math.max(0, Math.round(color.getGreen() * (1 - fraction)));
        int blue = Math.max(0, Math.round(color.getBlue() * (1 - fraction)));
        return new Color(red, green, blue);
    }

    // Renderer combo box amélioré
    class BateauRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setBorder(BorderFactory.createEmptyBorder(3, 5, 3, 5));

            if (value == null) {
                label.setText("Sélectionner un bateau");
                label.setFont(REGULAR_FONT);
            } else {
                label.setText(((BateauVoyageur) value).getNomBateau());
                label.setFont(REGULAR_FONT);
            }

            if (isSelected) {
                label.setBackground(PRIMARY_COLOR);
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }

            return label;
        }
    }
}