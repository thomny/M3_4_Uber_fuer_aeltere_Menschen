package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Accompaniment implements Parcelable,Serializable {
    String name;
    int age;
    String occupation;
    String description;
    Drawable profilepicture;

    public Accompaniment(String name, int age, String occupation, String description) {
        this.name = name;
        this.occupation = occupation;
        this.age = age;
        this.description = description;
        //this.profilepicture = context.getResources().getDrawable(R.drawable.baseline_person_24);
    }

    protected Accompaniment(Parcel in) {
        name = in.readString();
        age = in.readInt();
        occupation = in.readString();
        description = in.readString();
    }

    public static final Creator<Accompaniment> CREATOR = new Creator<Accompaniment>() {
        @Override
        public Accompaniment createFromParcel(Parcel in) {
            return new Accompaniment(in);
        }

        @Override
        public Accompaniment[] newArray(int size) {
            return new Accompaniment[size];
        }
    };

    public String getName(){
        return name;
    }
    public int getAge(){
        return age;
    }
    public String getOccupation(){
        return occupation;
    }
    public String getDescription(){
        return description;
    }
    public Drawable getProfilepicture() {
        return profilepicture;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeInt(age);
        parcel.writeString(occupation);
        parcel.writeString(description);
    }

    @Override
    public String toString() {
        return name + ", " + age;
    }
}
