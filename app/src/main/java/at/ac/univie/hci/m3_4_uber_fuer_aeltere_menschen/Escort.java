package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import java.time.LocalDateTime;

public class Escort {
    Address start;
    Address destination;
    LocalDateTime time;
    Accompaniment accompaniment;
    Service service;

    public void setStart(Address start){
        this.start = start;
    }
    public void setDestination(Address destination){
        this.destination = destination;
    }
    public void setTime(LocalDateTime time){
        this.time = time;
    }
    public void setAccompaniment(Accompaniment accompaniment){
        this.accompaniment = accompaniment;
    }
    public void setService(Service service){
        this.service = service;
    }

    public Address getStart() {
        return start;
    }

    public Address getDestination() {
        return destination;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public Accompaniment getAccompaniment() {
        return accompaniment;
    }

    public Service getService() {
        return service;
    }
}
