package alphadevs.insyto_android.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by taig1501 on 15-11-26.
 */
public class MainPrefs {
    public static final String      PREFS_MAIN = "InsytoPrefsMain";
    public static final String      LAST_KNOWN_LONGITUDE = "lastKnownLongitude";
    public static final int     DEF_LAST_KNOWN_LONGITUDE = 0;
    public static final String      LAST_KNOWN_LATITUDE = "lastKnownLatitude";
    public static final int     DEF_LAST_KNOWN_LATITUDE = 0;
    public static final String      NEARBY_ACTIVE = "nearbyActive";
    public static final boolean DEF_NEARBY_ACTIVE = false;
    public static final String      NEARBY_RADIUS = "insyte_nearby_radius";
    public static final int     DEF_NEARBY_RADIUS = 5;
    protected SharedPreferences settings;
    protected SharedPreferences.Editor editor;

    public MainPrefs(Context context) {
        settings = PreferenceManager.getDefaultSharedPreferences(context);
        editor = settings.edit();
    }

    public double getLastKnownLongitude() {
        return Double.longBitsToDouble(settings.getLong(LAST_KNOWN_LONGITUDE, DEF_LAST_KNOWN_LONGITUDE));
        //return Double.parseDouble(settings.getString(LAST_KNOWN_LONGITUDE, Double.toString(DEF_LAST_KNOWN_LONGITUDE)));
    }

    public boolean setLastKnownLongitude(double longitude) {
        //editor.putString(LAST_KNOWN_LONGITUDE, Double.toString(longitude));
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

    public int getNearbyRadius() {
        return settings.getInt(NEARBY_RADIUS, DEF_NEARBY_RADIUS);
    }

    public boolean setNearbyRadius(int radius) {
        editor.putInt(NEARBY_RADIUS, radius);
        return editor.commit();
    }

}
