package alphadevs.insyto_android.models;


import java.util.Date;

public class InsyteItemData {
    private Integer id;
    private String title;
    private String description;
    private Date created_at;
    private Date updated_at;
    private Integer category_id;
    private String category_name;
    private String media_type;
    private InsyteMedia media; // For GET method
    private InsyteMedia media_attributes; // For POST method
    private String author;

    private Double lat;
    private Double lng;


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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public InsyteMedia getMedia() {
        return media;
    }

    public void setMedia(InsyteMedia media) {
        this.media = media;
    }

    public InsyteMedia getMedia_attributes() {
        return media_attributes;
    }

    public void setMedia_attributes(InsyteMedia media_attributes) {
        this.media_attributes = media_attributes;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }
}
