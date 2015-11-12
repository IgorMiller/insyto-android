package alphadevs.insyto_android.models;

public class PostInsyteItem {
    private InsyteItemData insyte;

    public PostInsyteItem(InsyteItemData insyteData)
    {
        this.insyte = insyteData;
    }

    public InsyteItemData getInsyte() {
        return insyte;
    }

    public void setInsyte(InsyteItemData insyte) {
        this.insyte = insyte;
    }
}
