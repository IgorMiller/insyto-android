package alphadevs.insyto_android.data;


import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class InsyteItemData {
    private String id;
    private String title;
    private String description;
    private Date created_at;
    private Date update_at;

    public InsyteItemData() {
    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdateAt() {
        return update_at;
    }

    public void setUpdateAt(Date update_at) {
        this.update_at = update_at;
    }
}
