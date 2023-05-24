package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

public enum Service { //Arten der Beförderung einer Begleitung/Fahrt
    AUTO("Auto"), OEFFENTLICH("Öffentlich");

    private String mappedName ;

    private Service (String name) {
        this.mappedName = name;
    }
    public String getMappedName() {
        return mappedName;
    }
    @Override
    public String toString () {return getMappedName();}
}
