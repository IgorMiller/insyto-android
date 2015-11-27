package alphadevs.insyto_android.preferences;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by taig1501 on 15-11-26.
 */
public class MainPrefs {
    public static final String PREFS_MAIN = "InsytoPrefsMain";
    protected SharedPreferences settings;

    public MainPrefs(Context context) {
        settings = context.getSharedPreferences(PREFS_MAIN, Context.MODE_PRIVATE);
    }


}
