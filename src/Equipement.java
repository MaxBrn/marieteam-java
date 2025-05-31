/**
 * Classe représentant un équipement dans le système.
 * Cette classe suit le pattern JavaBean avec des propriétés privées
 * et leurs accesseurs/mutateurs publics correspondants.
 */
public class Equipement {

    // Identifiant unique de l'équipement
    private Integer idEquip;

    // Libellé/nom descriptif de l'équipement
    private String libEquip;

    /**
     * Constructeur pour créer un nouvel équipement avec tous ses attributs.
     *
     * @param idEquip Identifiant unique de l'équipement
     * @param libEquip Libellé descriptif de l'équipement
     */
    public Equipement(Integer idEquip, String libEquip) {
        this.idEquip = idEquip;
        this.libEquip = libEquip;
    }

    /**
     * Méthode toString() redéfinie pour afficher le libellé de l'équipement.
     * Utile pour l'affichage dans les listes déroulantes ou les logs.
     *
     * @return Le libellé de l'équipement
     */
    public String toString() {
        return getLibEquip();
    }

    /**
     * Getter pour récupérer l'identifiant de l'équipement.
     *
     * @return L'identifiant unique de l'équipement
     */
    public Integer getIdEquip() {
        return idEquip;
    }

    /**
     * Setter pour modifier l'identifiant de l'équipement.
     *
     * @param idEquip Le nouvel identifiant à attribuer
     */
    public void setIdEquip(Integer idEquip) {
        this.idEquip = idEquip;
    }

    /**
     * Getter pour récupérer le libellé de l'équipement.
     *
     * @return Le libellé descriptif de l'équipement
     */
    public String getLibEquip() {
        return libEquip;
    }

    /**
     * Setter pour modifier le libellé de l'équipement.
     *
     * @param libEquip Le nouveau libellé à attribuer
     */
    public void setLibEquip(String libEquip) {
        this.libEquip = libEquip;
    }
}