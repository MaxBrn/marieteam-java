import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EquipementTest {

    @Test
    public void testConstructor() {
        // Arrange
        Integer id = 1;
        String libelle = "Bouée de sauvetage";
        
        // Act
        Equipement equipement = new Equipement(id, libelle);
        
        // Assert
        assertEquals(id, equipement.getIdEquip());
        assertEquals(libelle, equipement.getLibEquip());
    }
    
    @Test
    public void testSetters() {
        // Arrange
        Equipement equipement = new Equipement(1, "Initial");
        
        // Act
        Integer newId = 2;
        String newLibelle = "Updated";
        
        equipement.setIdEquip(newId);
        equipement.setLibEquip(newLibelle);
        
        // Assert
        assertEquals(newId, equipement.getIdEquip());
        assertEquals(newLibelle, equipement.getLibEquip());
    }
    
    @Test
    public void testToString() {
        // Arrange
        String libelle = "GPS";
        Equipement equipement = new Equipement(1, libelle);
        
        // Act
        String result = equipement.toString();
        
        // Assert
        assertEquals(libelle, result);
    }
}