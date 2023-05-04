package at.ac.univie.hci.m3_4_uber_fuer_aeltere_menschen;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Accompaniment implements Parcelable {
    String name;
    int age;
    String occupation;
    String description;
    Drawable profilepicture;

    public Accompaniment(String name, Context context) {
        this.name = name;
        this.occupation = "Student";
        this.age = 21;
        this.description = "Hier steht ein Beispiel fuer eine Beschreibung";
        this.profilepicture = context.getResources().getDrawable(R.drawable.baseline_person_24);
    }
    public String getName(){
        return name;
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

    }

    @Override
    public String toString() {
        return name + ", " + age;
    }
}
