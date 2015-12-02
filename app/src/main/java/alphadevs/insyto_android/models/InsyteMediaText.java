package alphadevs.insyto_android.models;

/**
 * Created by gorik on 12/2/15.
 */
public class InsyteMediaText extends InsyteMedia {

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public InsyteMediaText withContent(String content)
    {
        setContent(content);
        return this;
    }
}
