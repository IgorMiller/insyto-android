package alphadevs.insyto_android.data;


import com.google.gson.annotations.SerializedName;

public class InsyteItemData {
    private String id;
    private String title;
    private String description;
    private int thumbnail;

    private String type;
    @SerializedName("value")
    private ChuckNorrisItemData chuck; // TODO

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ChuckNorrisItemData getChuck() {
        return chuck;
    }

    public void setChuck(ChuckNorrisItemData chuck) {
        this.chuck = chuck;
    }
}
