package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

public enum Payment { //Arten der Beförderung einer Begleitung/Fahrt
    CASH("Barzahlung"), CREDIT("Guthaben"), CARD("Kartenzahlung");

    private String mappedName ;

    private Payment(String name) {
        this.mappedName = name;
    }
    public String getMappedName() {
        return mappedName;
    }
    @Override
    public String toString () {return getMappedName();}
}
