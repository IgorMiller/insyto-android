package alphadevs.insyto_android;


public enum InsyteMediaType {
    TEXT("Text"),
    VIDEO("Video");

    private String type;

    InsyteMediaType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return this.type;
    }
}
