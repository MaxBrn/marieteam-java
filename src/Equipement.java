public class Equipement {
    private Integer idEquip;
    private String libEquip;

    public Equipement(Integer idEquip, String libEquip) {
        this.idEquip = idEquip;
        this.libEquip = libEquip;
    }

    public String toString() {
        return getLibEquip();
    }

    public Integer getIdEquip() {
        return idEquip;
    }

    public void setIdEquip(Integer idEquip) {
        this.idEquip = idEquip;
    }

    public String getLibEquip() {
        return libEquip;
    }

    public void setLibEquip(String libEquip) {
        this.libEquip = libEquip;
    }
}
