import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

public class BateauVoyageurTest {

    @Test
    public void testConstructor() {
        // Arrange
        Integer id = 1;
        String nom = "Test BateauVoyageur";
        double largeur = 5.5;
        double longueur = 15.0;
        double vitesse = 25.0;
        List<Equipement> equipements = new ArrayList<>();
        equipements.add(new Equipement(1, "GPS"));
        String image = "/test.jpg";
        
        // Act
        BateauVoyageur bateauVoyageur = new BateauVoyageur(id, nom, largeur, longueur, vitesse, equipements, image);
        
        // Assert
        assertEquals(id, bateauVoyageur.getIdBateau());
        assertEquals(nom, bateauVoyageur.getNomBateau());
        assertEquals(largeur, bateauVoyageur.getLargeurBateau());
        assertEquals(longueur, bateauVoyageur.getLongueurBateau());
        assertEquals(vitesse, bateauVoyageur.getVitesseBatVoy());
        assertEquals(equipements, bateauVoyageur.getEquipements());
        assertEquals(image, bateauVoyageur.getImage());
    }
    
    @Test
    public void testSetters() {
        // Arrange
        BateauVoyageur bateauVoyageur = new BateauVoyageur(1, "Initial", 4.0, 10.0, 20.0, null, "/initial.jpg");
        
        // Act
        double newVitesse = 30.0;
        List<Equipement> newEquipements = new ArrayList<>();
        newEquipements.add(new Equipement(2, "Radar"));
        String newImage = "/updated.jpg";
        
        bateauVoyageur.setVitesseBatVoy(newVitesse);
        bateauVoyageur.setEquipements(newEquipements);
        bateauVoyageur.setImage(newImage);
        
        // Assert
        assertEquals(newVitesse, bateauVoyageur.getVitesseBatVoy());
        assertEquals(newEquipements, bateauVoyageur.getEquipements());
        assertEquals(newImage, bateauVoyageur.getImage());
    }
    
    @Test
    public void testToStringWithEquipements() {
        // Arrange
        Integer id = 1;
        String nom = "Test BateauVoyageur";
        double largeur = 5.5;
        double longueur = 15.0;
        double vitesse = 25.0;
        List<Equipement> equipements = new ArrayList<>();
        equipements.add(new Equipement(1, "GPS"));
        equipements.add(new Equipement(2, "Radar"));
        String image = "/test.jpg";
        
        BateauVoyageur bateauVoyageur = new BateauVoyageur(id, nom, largeur, longueur, vitesse, equipements, image);
        
        // Act
        String result = bateauVoyageur.toString();
        
        // Assert
        assertTrue(result.contains("Nom du bateau: " + nom));
        assertTrue(result.contains("Largeur du bateau: " + largeur + "m"));
        assertTrue(result.contains("Longueur du bateau: " + longueur + "m"));
        assertTrue(result.contains("Vitesse: " + vitesse));
        assertTrue(result.contains("Liste des équipements:"));
        assertTrue(result.contains("GPS"));
        assertTrue(result.contains("Radar"));
    }
    
    @Test
    public void testToStringWithoutEquipements() {
        // Arrange
        Integer id = 1;
        String nom = "Test BateauVoyageur";
        double largeur = 5.5;
        double longueur = 15.0;
        double vitesse = 25.0;
        String image = "/test.jpg";
        
        BateauVoyageur bateauVoyageur = new BateauVoyageur(id, nom, largeur, longueur, vitesse, null, image);
        
        // Act
        String result = bateauVoyageur.toString();
        
        // Assert
        assertTrue(result.contains("Nom du bateau: " + nom));
        assertTrue(result.contains("Largeur du bateau: " + largeur + "m"));
        assertTrue(result.contains("Longueur du bateau: " + longueur + "m"));
        assertTrue(result.contains("Vitesse: " + vitesse));
        assertTrue(result.contains("Liste des équipements:"));
        assertTrue(result.contains("Aucun"));
    }
}