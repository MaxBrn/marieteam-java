public class Equipement {
    private String idEquip;
    private String libEquip;

    public Equipement(String idEquip, String libEquip) {
        this.idEquip = idEquip;
        this.libEquip = libEquip;
    }

    public String toString() {
        return getLibEquip();
    }

    public String getIdEquip() {
        return idEquip;
    }

    public void setIdEquip(String idEquip) {
        this.idEquip = idEquip;
    }

    public String getLibEquip() {
        return libEquip;
    }

    public void setLibEquip(String libEquip) {
        this.libEquip = libEquip;
    }
}
