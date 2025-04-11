import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;

public class Interface extends JFrame {

    private JLabel imageLabel;

    public Interface(List<BateauVoyageur> bateaux) {
        setTitle("Sélection des Bateaux");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // --- PANNEAU GAUCHE ---
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.setPreferredSize(new Dimension(300, 600));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        // Composants
        JComboBox<BateauVoyageur> comboBox = new JComboBox<>();
        comboBox.addItem(null);
        for (BateauVoyageur b : bateaux) comboBox.addItem(b);
        comboBox.setRenderer(new BateauRenderer());

        JTextField nameField = new JTextField();
        JTextField urlField = new JTextField();
        JTextField longueurField = new JTextField();
        JTextField largeurField = new JTextField();
        JTextField vitesseField = new JTextField();
        JTextArea equipArea = new JTextArea();
        equipArea.setLineWrap(true);
        equipArea.setWrapStyleWord(true);
        equipArea.setEditable(false);
        equipArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        JScrollPane equipScroll = new JScrollPane(equipArea);

        JTextField[] fields = {nameField, urlField, longueurField, largeurField, vitesseField};
        for (JTextField f : fields) {
            f.setEditable(false);
            f.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        }

        // Organisation des composants
        int row = 0;

        // ComboBox seule sans label
        gbc.gridx = 0;
        gbc.gridy = row++;
        gbc.gridwidth = 2;
        leftPanel.add(comboBox, gbc);

        // Nom
        gbc.gridy = row++;
        leftPanel.add(new JLabel("Nom:"), gbc);

        gbc.gridy = row++;
        leftPanel.add(nameField, gbc);

        // URL
        gbc.gridy = row++;
        leftPanel.add(new JLabel("URL:"), gbc);

        gbc.gridy = row++;
        leftPanel.add(urlField, gbc);

        // Longueur
        gbc.gridy = row++;
        leftPanel.add(new JLabel("Longueur:"), gbc);

        gbc.gridy = row++;
        leftPanel.add(longueurField, gbc);

        // Largeur
        gbc.gridy = row++;
        leftPanel.add(new JLabel("Largeur:"), gbc);

        gbc.gridy = row++;
        leftPanel.add(largeurField, gbc);

        // Vitesse
        gbc.gridy = row++;
        leftPanel.add(new JLabel("Vitesse:"), gbc);

        gbc.gridy = row++;
        leftPanel.add(vitesseField, gbc);

        // Équipements
        gbc.gridy = row++;
        leftPanel.add(new JLabel("Équipements:"), gbc);

        gbc.gridy = row++;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        leftPanel.add(equipScroll, gbc);

        gbc.gridy = row++;
        JButton buttonPdf = new JButton("Créer un pdf");
        leftPanel.add(buttonPdf, gbc);

        gbc.gridy = row++;
        JButton buttonSave = new JButton("Sauvegarder");
        leftPanel.add(buttonSave, gbc);


        // --- PANNEAU DROIT ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setPreferredSize(new Dimension(700, 600));
        rightPanel.setBackground(Color.WHITE);

        imageLabel = new JLabel("Sélectionnez un bateau", SwingConstants.CENTER);
        imageLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        rightPanel.add(imageLabel, BorderLayout.CENTER);

        // --- CONTENU PRINCIPAL ---
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(leftPanel, BorderLayout.WEST);
        contentPanel.add(rightPanel, BorderLayout.CENTER);

        // Ajouter un panneau parent avec BorderLayout pour aligner les hauteurs
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // Ajouter un panneau vide en bas pour compenser si nécessaire
        mainPanel.add(new JPanel(), BorderLayout.SOUTH);

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
                    equipArea.setText("");
                    imageLabel.setText("Sélectionnez un bateau");
                    imageLabel.setIcon(null);
                } else {
                    nameField.setText(b.getNomBateau());
                    urlField.setText(b.getImage());
                    longueurField.setText(String.valueOf(b.getLongueurBateau()));
                    largeurField.setText(String.valueOf(b.getLargeurBateau()));
                    vitesseField.setText(String.valueOf(b.getVitesseBatVoy()));
                    equipArea.setText(b.getEquipements().toString());

                    try {
                        ImageIcon originalIcon = new ImageIcon(b.getImage());
                        Image img = originalIcon.getImage();

                        // Calculer les dimensions pour conserver le ratio
                        int panelWidth = rightPanel.getWidth();
                        int panelHeight = rightPanel.getHeight();

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
                rightPanel.revalidate();
                rightPanel.repaint();
            }
        });

        buttonPdf.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String filePath = "appli.pdf";
                try {
                    Pdf.createPdf(filePath, bateaux);

                    int option = JOptionPane.showOptionDialog(Interface.this,
                            "<html>PDF créé avec succès!<br>Souhaitez-vous ouvrir le document?</html>",
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
                            "Erreur lors de la création du PDF: " + exception.getMessage(),
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE);
                    exception.printStackTrace();
                }
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Save");
            }
        });


        setVisible(true);
    }

    // Renderer combo box
    class BateauRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                      boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value == null) setText("Sélectionner un bateau");
            else setText(((BateauVoyageur) value).getNomBateau());
            return this;
        }
    }
}