package co.anbora.component.location;


import androidx.annotation.NonNull;

/**
 * Created by dalgarins on 03/05/18.
 */

public interface OnLocationChangeListener {

    OnLocationChangeListener onLocationChanged(@NonNull CallbackLocation callback);

    OnLocationRequest whenRequestLocation();

    OnObserveState attachState();

}
