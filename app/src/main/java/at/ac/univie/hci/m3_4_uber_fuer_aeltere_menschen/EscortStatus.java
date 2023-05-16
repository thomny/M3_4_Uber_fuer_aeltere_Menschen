package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

public enum EscortStatus {
    ACCEPTED("Akzeptiert"), PENDING("Ausstehend"), DECLINED("Abgelehnt"), ACTIVE("Aktiv");

    private String mappedName ;

    private EscortStatus (String name) {
        this.mappedName = name;
    }
    public String getMappedName() {
        return mappedName;
    }
    @Override
    public String toString () {return getMappedName();}
}