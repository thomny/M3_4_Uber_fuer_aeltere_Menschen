package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

public class Escort implements Serializable { //Begleitungs/Fahrt-Klasse
    static int count;
    int id;
    Address start;
    Address destination;
    LocalDateTime time;
    boolean now = false;
    Accompaniment accompaniment;
    Service service;
    EscortStatus status = EscortStatus.PENDING;
    boolean userReady = false;
    boolean accompReady = false;
    boolean escortReady = false;

    public void setStart(Address start){
        this.start = start;
    }
    public void setDestination(Address destination){
        this.destination = destination;
    }
    public void setTime(LocalDateTime time){
        this.time = time;
    }
    public void setTime(Boolean now){
        this.now = now;
    }
    public void setAccompaniment(Accompaniment accompaniment){
        this.accompaniment = accompaniment;
    }
    public void setService(Service service){
        this.service = service;
    }
    public void setStatus(EscortStatus status){
        this.status = status;
    }
    public void setUserReady() {
        if(!this.userReady)
            this.userReady = true;
        else this.userReady =false;
    }
    public void setAccompReady() {
        if(!this.accompReady)
            this.accompReady = true;
        else this.accompReady =false;
    }
    public void setEscortReady() {
        escortReady = true;
    }
    public void setId() {
        id=count++;
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
    public boolean getNow() {return now;}
    public Accompaniment getAccompaniment() {
        return accompaniment;
    }

    public Service getService() {
        return service;
    }
    public EscortStatus getStatus() {
        return status;
    }
    public boolean getUserReady() {
        return userReady;
    }
    public boolean getAccompReady() {
        return accompReady;
    }
    public boolean getEscortReady() {return escortReady;}
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Escort escort = (Escort) o;
        return id == escort.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
