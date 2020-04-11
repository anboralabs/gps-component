# GPS Component

GPS Component: It is a library to avoid boilerplate code with Android GPS. This project uses Android architecture components to managing life cycle states.

## Installation

Add dependency with gradle.

```gradle
pip install foobar
```

## Usage

```Kotlin
        val locationUpdate = LocationUpdate(
            10, 10,
            LocationUpdate.PRIORITY_HIGH_ACCURACY
        );

        val locationComponent = LocationComponent.Builder()
            .build(this, locationUpdate)

        locationComponent
            .onLastLocation(LastLocationCallback())
            .whenLocationChange()
            .onLocationChanged(object : CallbackLocation {
                override fun onLocationResult(location: Location?) {
                    location?.run {
                        current_location.text = getString(R.string.location, this.latitude, this.longitude)
                    }
                }

                override fun onLocationError() {
                }
            }).attachState().observe(lifecycle)
```

## Contributing
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Please make sure to update tests as appropriate.

## License
[MIT](https://choosealicense.com/licenses/mit/)
