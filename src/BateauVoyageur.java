import java.util.List;

/**
 * Classe BateauVoyageur qui hérite de la classe Bateau
 *
 * Cette classe représente un type spécialisé de bateau destiné au transport de voyageurs.
 * Elle étend la classe Bateau en ajoutant des caractéristiques spécifiques :
 * - La vitesse de navigation
 * - Une liste d'équipements disponibles à bord
 * - Une image représentative du bateau
 *
 * HÉRITAGE : BateauVoyageur EST-UN Bateau (relation "is-a")
 */
public class BateauVoyageur extends Bateau {

    // === ATTRIBUTS SPÉCIFIQUES AU BATEAU VOYAGEUR ===
    // Ces attributs s'ajoutent à ceux hérités de la classe parent Bateau

    /**
     * Vitesse du bateau voyageur (probablement en nœuds ou km/h)
     * Type double pour permettre des valeurs décimales précises
     */
    private double vitesseBatVoy;

    /**
     * Liste des équipements disponibles à bord du bateau
     * Utilisation d'une List<Equipement> pour stocker une collection d'objets Equipement
     * La liste peut être vide (pas d'équipements) ou null (non initialisée)
     */
    private List<Equipement> equipements;

    /**
     * Chemin ou nom du fichier image représentant le bateau
     * Stocké sous forme de String
     */
    private String image;

    // === CONSTRUCTEUR ===

    /**
     * Constructeur de BateauVoyageur
     *
     * Utilise le constructeur de la classe parent (super) pour initialiser
     * les attributs hérités, puis initialise les attributs spécifiques.
     *
     * @param idBateau Identifiant unique du bateau (hérité)
     * @param nomBateau Nom du bateau (hérité)
     * @param largeurBateau Largeur du bateau en mètres (hérité)
     * @param longueurBateau Longueur du bateau en mètres (hérité)
     * @param vitesseBatVoy Vitesse du bateau voyageur
     * @param equipements Liste des équipements disponibles à bord
     * @param image Chemin vers l'image du bateau
     */
    public BateauVoyageur(Integer idBateau, String nomBateau, double largeurBateau, double longueurBateau,
                          double vitesseBatVoy, List<Equipement> equipements, String image) {

        // Appel au constructeur de la classe parent (Bateau)
        // OBLIGATOIRE et doit être la première instruction du constructeur
        super(idBateau, nomBateau, largeurBateau, longueurBateau);

        // Initialisation des attributs spécifiques à BateauVoyageur
        this.vitesseBatVoy = vitesseBatVoy;
        this.equipements = equipements;
        this.image = image;
    }

    // === GETTERS (ACCESSEURS) SPÉCIFIQUES ===
    // Ces getters donnent accès aux nouveaux attributs de BateauVoyageur
    // Les getters de Bateau restent disponibles par héritage

    /**
     * Getter pour la vitesse du bateau voyageur
     *
     * @return double La vitesse du bateau voyageur
     */
    public double getVitesseBatVoy() {
        return vitesseBatVoy;
    }

    /**
     * Getter pour la liste des équipements
     *
     * @return List<Equipement> La liste des équipements du bateau
     *         Peut retourner null si aucune liste n'a été assignée
     */
    public List<Equipement> getEquipements() {
        return equipements;
    }

    /**
     * Getter pour l'image du bateau
     *
     * @return String Le chemin ou nom de l'image du bateau
     */
    public String getImage() {
        return image;
    }

    // === SETTERS (MUTATEURS) SPÉCIFIQUES ===

    /**
     * Setter pour la vitesse du bateau voyageur
     *
     * @param vitesseBatVoy Nouvelle vitesse du bateau
     */
    public void setVitesseBatVoy(double vitesseBatVoy) {
        this.vitesseBatVoy = vitesseBatVoy;
    }

    /**
     * Setter pour la liste des équipements
     *
     * @param equipements Nouvelle liste d'équipements
     */
    public void setEquipements(List<Equipement> equipements) {
        this.equipements = equipements;
    }

    /**
     * Setter pour l'image du bateau
     *
     * @param image Nouveau chemin vers l'image
     */
    public void setImage(String image) {
        this.image = image;
    }

    // === REDÉFINITION DE MÉTHODE (OVERRIDE) ===

    /**
     * Redéfinition de la méthode toString() héritée de Bateau
     *
     * Cette méthode SURCHARGE (override) celle de la classe parent pour
     * inclure les informations spécifiques au bateau voyageur.
     *
     * @return String Représentation textuelle complète du bateau voyageur
     *         incluant les informations de base (via super.toString())
     *         plus les informations spécifiques (vitesse et équipements)
     */
    public String toString() {
        // Utilisation de StringBuilder pour une construction efficace de chaînes
        // StringBuilder est plus performant que la concaténation simple pour
        // des constructions complexes de chaînes
        StringBuilder equipementsString = new StringBuilder("Liste des équipements:");

        // Vérification de nullité pour éviter NullPointerException
        if (equipements != null) {
            // Parcours de la liste avec une boucle for-each (enhanced for loop)
            // Plus lisible et moins sujette aux erreurs que les indices
            for (Equipement equipement : equipements) {
                // Ajout de chaque équipement à la StringBuilder
                // Utilisation du toString() de la classe Equipement
                equipementsString.append("\n").append(equipement.toString());
            }
        } else {
            // Gestion du cas où la liste est null
            equipementsString.append("\n Aucun");
        }

        // Appel à la méthode toString() de la classe parent avec super.toString()
        // puis ajout des informations spécifiques au bateau voyageur
        return super.toString() + "\nVitesse: " + getVitesseBatVoy() + "\n" + equipementsString;
    }
}