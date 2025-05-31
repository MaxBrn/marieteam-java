/**
 * Classe représentant un bateau avec ses caractéristiques principales.
 * Cette classe permet de modéliser un bateau avec un identifiant unique,
 * un nom et ses dimensions physiques (largeur et longueur).
 */
public class Bateau {

    // === ATTRIBUTS PRIVÉS ===
    // L'encapsulation est respectée : tous les attributs sont privés
    // et accessibles uniquement via les getters/setters

    /**
     * Identifiant unique du bateau
     * Utilisation d'Integer (classe wrapper) plutôt que int primitif
     * pour permettre les valeurs null si nécessaire
     */
    private Integer idBateau;

    /**
     * Nom du bateau (ex: "Titanic", "Queen Mary", etc.)
     */
    private String nomBateau;

    /**
     * Largeur du bateau en mètres
     * Type double pour permettre des valeurs décimales précises
     */
    private double largeurBateau;

    /**
     * Longueur du bateau en mètres
     * Type double pour permettre des valeurs décimales précises
     */
    private double longueurBateau;

    // === CONSTRUCTEUR ===

    /**
     * Constructeur principal de la classe Bateau
     * Initialise tous les attributs de l'objet lors de sa création
     *
     * @param idBateau Identifiant unique du bateau
     * @param nomBateau Nom du bateau
     * @param largeurBateau Largeur du bateau en mètres
     * @param longueurBateau Longueur du bateau en mètres
     */
    public Bateau(Integer idBateau, String nomBateau, double largeurBateau, double longueurBateau) {
        // Utilisation de 'this' pour différencier les paramètres des attributs de classe
        // quand ils ont le même nom
        this.idBateau = idBateau;
        this.nomBateau = nomBateau;
        this.largeurBateau = largeurBateau;
        this.longueurBateau = longueurBateau;
    }

    // === MÉTHODES D'AFFICHAGE ===

    /**
     * Redéfinition de la méthode toString() héritée de Object
     * Permet d'obtenir une représentation textuelle lisible de l'objet Bateau
     *
     * @return String contenant les informations principales du bateau
     *         formatées de manière lisible
     */
    public String toString() {
        // Concaténation de chaînes pour créer un affichage structuré
        // Utilisation des getters plutôt que l'accès direct aux attributs
        // (bonne pratique même dans la classe elle-même)
        return "Nom du bateau: " + getNomBateau()
                + "\nLargeur du bateau: " + getLargeurBateau() + "m"
                + "\nLongueur du bateau: " + getLongueurBateau() + "m";
    }

    // === GETTERS (ACCESSEURS) ===
    // Les getters permettent de lire les valeurs des attributs privés
    // depuis l'extérieur de la classe tout en respectant l'encapsulation

    /**
     * Getter pour la largeur du bateau
     *
     * @return double La largeur du bateau en mètres
     */
    public double getLargeurBateau() {
        return largeurBateau;
    }

    /**
     * Getter pour l'identifiant du bateau
     *
     * @return Integer L'identifiant unique du bateau
     */
    public Integer getIdBateau() {
        return idBateau;
    }

    /**
     * Getter pour le nom du bateau
     *
     * @return String Le nom du bateau
     */
    public String getNomBateau() {
        return nomBateau;
    }

    /**
     * Getter pour la longueur du bateau
     *
     * @return double La longueur du bateau en mètres
     */
    public double getLongueurBateau() {
        return longueurBateau;
    }

    // === SETTERS (MUTATEURS) ===
    // Les setters permettent de modifier les valeurs des attributs privés
    // depuis l'extérieur de la classe tout en respectant l'encapsulation

    /**
     * Setter pour la largeur du bateau
     *
     * @param largeurBateau Nouvelle largeur du bateau en mètres
     */
    public void setLargeurBateau(double largeurBateau) {
        // Utilisation de 'this' pour différencier le paramètre de l'attribut
        this.largeurBateau = largeurBateau;
    }

    /**
     * Setter pour l'identifiant du bateau
     *
     * @param idBateau Nouvel identifiant du bateau
     */
    public void setIdBateau(Integer idBateau) {
        this.idBateau = idBateau;
    }

    /**
     * Setter pour le nom du bateau
     *
     * @param nomBateau Nouveau nom du bateau
     */
    public void setNomBateau(String nomBateau) {
        this.nomBateau = nomBateau;
    }

    /**
     * Setter pour la longueur du bateau
     *
     * @param longueurBateau Nouvelle longueur du bateau en mètres
     */
    public void setLongueurBateau(double longueurBateau) {
        this.longueurBateau = longueurBateau;
    }
}