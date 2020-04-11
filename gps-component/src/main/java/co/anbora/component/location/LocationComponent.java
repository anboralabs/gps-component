package co.anbora.component.location;


import android.content.Context;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;

import static androidx.lifecycle.Lifecycle.State.STARTED;


/**
 * Component to get the GPS location
 *
 * Created by dalgarins on 03/04/18.
 */

public class LocationComponent implements OnLastLocationListener
        , OnLocationChangeListener
        , OnLocationRequest
        , OnObserveState
        , LifecycleObserver {

    private Lifecycle lifecycle;

    private Context context;
    private LocationSettings locationSettings;

    private LocationCallback mLocationCallback;

    /**
     * Contains parameters used by {@link com.google.android.gms.location.FusedLocationProviderApi}.
     */
    private LocationRequest mLocationRequest;

    /**
     * Provides access to the Fused Location Provider API.
     */
    private FusedLocationProviderClient mFusedLocationClient;

    private LocationComponent(Context context,
                              LocationSettings locationSettings,
                              LocationRequest locationRequest,
                              FusedLocationProviderClient mFusedLocationClient,
                              LocationSettingsRequest.Builder builder,
                              SettingsClient client) {

        super();
        this.context = context;
        this.locationSettings = locationSettings;
        this.mLocationRequest = locationRequest;
        this.mFusedLocationClient = mFusedLocationClient;
        this.setUpSettingsGps(builder, client);
    }

    @Override
    public OnLastLocationListener onLastLocation(@NonNull CallbackLocation callback) {

        if (UtilPermission.checkLocationPermission(context)) {
            mFusedLocationClient.getLastLocation()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            callback.onLocationResult(task.getResult());
                        } else {
                            callback.onLocationError();
                        }
                    });
        }
        return this;
    }

    @Override
    public OnLocationChangeListener whenLocationChange() {
        return this;
    }

    @Override
    public OnLocationChangeListener onLocationChanged(@NonNull CallbackLocation callback) {

        this.mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                callback.onLocationResult(locationResult.getLastLocation());
            }
        };
        return this;
    }

    @Override
    public OnLocationRequest whenRequestLocation() {
        return this;
    }

    @Override
    public OnObserveState attachState() {
        return this;
    }



    /**
     * Display dialog to enable GPS
     */
    private void setUpSettingsGps(LocationSettingsRequest.Builder builder, SettingsClient client) {

        builder.addLocationRequest(mLocationRequest);
        Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());

        if (locationSettings != null) {
            task.addOnSuccessListener(locationSettingsResponse ->
                    locationSettings.addOnSuccessListener(locationSettingsResponse));

            task.addOnFailureListener(exception ->
                    locationSettings.addOnFailureListener(exception));
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void start() {

        if (lifecycle.getCurrentState().isAtLeast(STARTED)) {
            this.requestLocationUpdates();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void stop() {

        if (mLocationCallback != null) {
            this.removeLocationUpdates();
        }
    }

    @Override
    public void requestLocationUpdates() {
        if (mLocationCallback != null
                && UtilPermission.checkLocationPermission(context)) {
            mFusedLocationClient.requestLocationUpdates(mLocationRequest,
                    mLocationCallback, Looper.myLooper());
        }
    }

    @Override
    public void removeLocationUpdates() {
        if (UtilPermission.checkLocationPermission(context)) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @Override
    public void observe(@NonNull Lifecycle lifecycle) {

        this.lifecycle = lifecycle;
        this.lifecycle.addObserver(this);
    }

    public static class Builder {

        private LocationSettings locationSettings;

        public Builder locationSettings(LocationSettings locationSettings) {

            this.locationSettings = locationSettings;
            return this;
        }

        public LocationComponent build(Context context, LocationUpdate locationUpdate) {

            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setInterval(locationUpdate.getInterval());
            locationRequest.setFastestInterval(locationUpdate.getFastInterval());
            locationRequest.setPriority(locationUpdate.getPriority());

            return new LocationComponent(context,
                    locationSettings,
                    locationRequest,
                    LocationServices.getFusedLocationProviderClient(context),
                    new LocationSettingsRequest.Builder(),
                    LocationServices.getSettingsClient(context));
        }
    }
}
