import com.itextpdf.text.Image;

import java.util.List;

public class BateauVoyageur extends Bateau {
    private double vitesseBatVoy;
    private List<Equipement> equipements;
    private String image;

    public BateauVoyageur(Integer idBateau, String nomBateau, double largeurBateau, double longueurBateaut
                          ,double vitesseBatVoy, List<Equipement> equipements, String image) {
        super(idBateau, nomBateau, largeurBateau, longueurBateaut);
        this.vitesseBatVoy = vitesseBatVoy;
        this.equipements = equipements;
        this.image = image;
    }

    public double getVitesseBatVoy() {
        return vitesseBatVoy;
    }

    public void setVitesseBatVoy(double vitesseBatVoy) {
        this.vitesseBatVoy = vitesseBatVoy;
    }

    public List<Equipement> getEquipements() {
        return equipements;
    }

    public void setEquipements(List<Equipement> equipements) {
        this.equipements = equipements;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String toString() {
        StringBuilder equipementsString = new StringBuilder("Liste des équipements:");
        if (equipements != null) {
            for (Equipement equipement : equipements) {
                equipementsString.append("\n").append(equipement.toString());
            }
        }
        else {
            equipementsString.append("\n Aucun");
        }

        return super.toString() + "\nVitesse: "+getVitesseBatVoy()+"\n"+equipementsString;
    }
}
