import java.util.Scanner;

public class Bateau {
    private Integer idBateau;
    private String nomBateau;
    private double largeurBateau;
    private double longueurBateau;

    public Bateau(Integer idBateau, String nomBateau, double largeurBateau, double longueurBateau) {
        this.idBateau = idBateau;
        this.nomBateau = nomBateau;
        this.largeurBateau = largeurBateau;
        this.longueurBateau = longueurBateau;
    }

    public String toString() {
        return "Nom du bateau: "+getNomBateau()
                +"\nLargeur du bateau: "+getLargeurBateau()+"m"
                +"\nLongueur du bateau: "+getLongueurBateau()+"m";
    }

    public double getLargeurBateau() {
        return largeurBateau;
    }

    public void setLargeurBateau(double largeurBateau) {
        this.largeurBateau = largeurBateau;
    }

    public Integer getIdBateau() {
        return idBateau;
    }

    public void setIdBateau(Integer idBateau) {
        this.idBateau = idBateau;
    }

    public String getNomBateau() {
        return nomBateau;
    }

    public void setNomBateau(String nomBateau) {
        this.nomBateau = nomBateau;
    }

    public double getLongueurBateau() {
        return longueurBateau;
    }

    public void setLongueurBateau(double longueurBateau) {
        this.longueurBateau = longueurBateau;
    }





}
