package alphadevs.insyto_android;

import com.google.gson.Gson;

import alphadevs.insyto_android.models.InsyteItemData;

/**
 * Created by gorik on 11/18/15.
 */
public class Test {
    public static void main(String [ ] args)
    {
        String insyto_json = "{\n" +
                "  \"id\": 72,\n" +
                "  \"title\": \"I luv you\",\n" +
                "  \"description\": \"My rock is rolling bby!\",\n" +
                "  \"created_at\": \"2015-11-18T20:21:09.888Z\",\n" +
                "  \"updated_at\": \"2015-11-18T20:21:09.888Z\",\n" +
                "  \"media_id\": null,\n" +
                "  \"media_type\": null\n" +
                "}";
        Gson gson = InsytoGsonBuilder.create();
        InsyteItemData item = gson.fromJson(insyto_json, InsyteItemData.class);
        System.out.println(item.getCreatedAt());
        System.out.println(item.getCreatedAt().getTime());
    }
}
