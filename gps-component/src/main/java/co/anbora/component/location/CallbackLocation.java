package co.anbora.component.location;

import android.location.Location;

/**
 * Created by dalgarins on 03/04/18.
 */

public interface CallbackLocation {

    void onLocationResult(Location location);

    void onLocationError();

}
