import android.app.Application;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class TreasureApplication extends Application {

    public static final String PLACES_API_KEY= "AIzaSyCipChfe99gpZgvqYV4XPG1elkgCg9oE8s";

    @Override
    public void onCreate() {
        super.onCreate();


        // Initialize the SDK
        Places.initialize(getApplicationContext(), PLACES_API_KEY );


    }
}
