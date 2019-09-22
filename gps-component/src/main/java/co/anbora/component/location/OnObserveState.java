package co.anbora.component.location;


import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

/**
 * Created by dalgarins on 03/09/18.
 */

public interface OnObserveState {

    void observe(@NonNull Lifecycle lifecycle);

}
