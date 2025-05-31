import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BateauTest {

    @Test
    public void testConstructor() {
        // Arrange
        Integer id = 1;
        String nom = "Test Bateau";
        double largeur = 5.5;
        double longueur = 15.0;
        
        // Act
        Bateau bateau = new Bateau(id, nom, largeur, longueur);
        
        // Assert
        assertEquals(id, bateau.getIdBateau());
        assertEquals(nom, bateau.getNomBateau());
        assertEquals(largeur, bateau.getLargeurBateau());
        assertEquals(longueur, bateau.getLongueurBateau());
    }
    
    @Test
    public void testSetters() {
        // Arrange
        Bateau bateau = new Bateau(1, "Initial", 4.0, 10.0);
        
        // Act
        Integer newId = 2;
        String newNom = "Updated";
        double newLargeur = 6.0;
        double newLongueur = 12.0;
        
        bateau.setIdBateau(newId);
        bateau.setNomBateau(newNom);
        bateau.setLargeurBateau(newLargeur);
        bateau.setLongueurBateau(newLongueur);
        
        // Assert
        assertEquals(newId, bateau.getIdBateau());
        assertEquals(newNom, bateau.getNomBateau());
        assertEquals(newLargeur, bateau.getLargeurBateau());
        assertEquals(newLongueur, bateau.getLongueurBateau());
    }
    
    @Test
    public void testToString() {
        // Arrange
        String nom = "Test Bateau";
        double largeur = 5.5;
        double longueur = 15.0;
        Bateau bateau = new Bateau(1, nom, largeur, longueur);
        
        // Act
        String result = bateau.toString();
        
        // Assert
        assertTrue(result.contains("Nom du bateau: " + nom));
        assertTrue(result.contains("Largeur du bateau: " + largeur + "m"));
        assertTrue(result.contains("Longueur du bateau: " + longueur + "m"));
    }
}