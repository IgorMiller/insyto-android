package alphadevs.insyto_android.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by taig1501 on 15-11-26.
 */
public class MainPrefs {
    public static final String PREFS_MAIN = "InsytoPrefsMain";
    public static final String LAST_KNOWN_LONGITUDE = "lastKnownLongitude";
    public static final String LAST_KNOWN_LATITUDE = "lastKnownLatitude";
    public static final String NEARBY_ACTIVE = "nearbyActive";
    public static final int DEF_LAST_KNOWN_LONGITUDE = 0;
    public static final int DEF_LAST_KNOWN_LATITUDE = 0;
    public static final boolean DEF_NEARBY_ACTIVE = false;
    protected SharedPreferences settings;
    protected SharedPreferences.Editor editor;

    public MainPrefs(Context context) {
        settings = context.getSharedPreferences(PREFS_MAIN, Context.MODE_PRIVATE);
        editor = settings.edit();
    }

    public double getLastKnownLongitude() {
        return Double.longBitsToDouble(settings.getLong(LAST_KNOWN_LONGITUDE, DEF_LAST_KNOWN_LONGITUDE));
    }

    public boolean setLastKnownLongitude(double longitude) {
        editor.putLong(LAST_KNOWN_LONGITUDE, Double.doubleToRawLongBits(longitude));
        return editor.commit();
    }

    public double getLastKnownLatitude() {
        return Double.longBitsToDouble(settings.getLong(LAST_KNOWN_LATITUDE, DEF_LAST_KNOWN_LATITUDE));
    }

    public boolean setLastKnownLatitude(double latitude) {
        editor.putLong(LAST_KNOWN_LATITUDE, Double.doubleToRawLongBits(latitude));
        return editor.commit();
    }

    public boolean setNearbyActive(boolean active) {
        editor.putBoolean(NEARBY_ACTIVE, active);
        return editor.commit();
    }

    public boolean getNearbyActive() {
        return settings.getBoolean(NEARBY_ACTIVE, DEF_NEARBY_ACTIVE);
    }

}
