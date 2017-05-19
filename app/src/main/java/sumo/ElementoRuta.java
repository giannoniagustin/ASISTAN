package sumo;

import android.os.Parcel;
import android.os.Parcelable;


public interface ElementoRuta extends Parcelable {

 //   void dibujar(GoogleMap mGeneral);
 //   void borrar(GoogleMap mGeneral);

    @Override
    int describeContents();

    @Override
    void writeToParcel(Parcel dest, int flags);

}
