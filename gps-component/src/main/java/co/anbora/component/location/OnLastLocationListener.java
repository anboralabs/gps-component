package co.anbora.component.location;


import androidx.annotation.NonNull;

/**
 * Created by dalgarins on 03/05/18.
 */

public interface OnLastLocationListener {

    OnLastLocationListener onLastLocation(@NonNull CallbackLocation callback);

    OnLocationChangeListener whenLocationChange();

}
