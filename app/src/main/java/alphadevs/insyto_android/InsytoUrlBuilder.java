package alphadevs.insyto_android;

import android.net.Uri;

/**
 * Use it to create required URLs for insyto.
 */
public class InsytoUrlBuilder {
    private final static String BASE_URL = "http://80ea1dcb.ngrok.io"; // TODO change per config
    private final static String API_V1 = "v1";
    private final static String INSYTES = "insytes";
    private final static String PAGE_PARAM = "page";

    public static String getInsytesUrl(int pageNb)
    {
        return getInsytesBuilder().appendQueryParameter(PAGE_PARAM, Integer.toString(pageNb)).build().toString();
    }

    public static String getInsytesUrl()
    {
        return getInsytesBuilder().build().toString();
    }

    public static String getInsyteUrl(String id)
    {
        return getInsytesBuilder().appendEncodedPath(id).build().toString();
    }

    private static Uri.Builder getV1Builder()
    {
        return new Uri.Builder().encodedPath(BASE_URL).appendEncodedPath(API_V1);
    }

    private static Uri.Builder getInsytesBuilder()
    {
        return getV1Builder().appendEncodedPath(INSYTES);
    }
}
