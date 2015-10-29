package alphadevs.insyto_android;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson Builder with application configuration.
 */
public class InsytoGsonBuilder {

    public static Gson create()
    {
        return new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").create();
    }
}
