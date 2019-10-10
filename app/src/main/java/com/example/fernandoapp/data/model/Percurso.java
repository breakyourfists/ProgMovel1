package com.example.fernandoapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Percurso implements Parcelable {
    ArrayList<LatLng> percurso;

    protected Percurso(Parcel in) {
        percurso = in.createTypedArrayList(LatLng.CREATOR);
    }

    public static final Creator<Percurso> CREATOR = new Creator<Percurso>() {
        @Override
        public Percurso createFromParcel(Parcel in) {
            return new Percurso(in);
        }

        @Override
        public Percurso[] newArray(int size) {
            return new Percurso[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(percurso);
    }

    public Percurso(){
        percurso = new ArrayList<LatLng>();
    }
}
