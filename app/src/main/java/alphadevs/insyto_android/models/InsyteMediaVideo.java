package alphadevs.insyto_android.models;

/**
 * Created by gorik on 12/2/15.
 */
public class InsyteMediaVideo extends InsyteMedia {

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public InsyteMediaVideo withUrl(String url)
    {
        setUrl(url);
        return this;
    }
}
