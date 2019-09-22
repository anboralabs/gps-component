package co.anbora.component.location;


import androidx.annotation.NonNull;

import com.google.android.gms.location.LocationSettingsResponse;

/**
 * Created by dalgarins on 03/03/18.
 */

public interface LocationSettings {

    void addOnSuccessListener(LocationSettingsResponse locationSettingsResponse);

    void addOnFailureListener(@NonNull Exception e);
}
