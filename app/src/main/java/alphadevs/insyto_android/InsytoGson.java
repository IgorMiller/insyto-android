package alphadevs.insyto_android;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import alphadevs.insyto_android.models.InsyteItemData;
import alphadevs.insyto_android.models.InsyteMedia;
import alphadevs.insyto_android.models.InsyteMediaAudio;
import alphadevs.insyto_android.models.InsyteMediaText;
import alphadevs.insyto_android.models.InsyteMediaVideo;

/**
 * Gson Builder with application configuration.
 */
public class InsytoGson {

    private final static Gson gson;

    static {
        InsyteDeserializer deserializer = new InsyteDeserializer("media_type");
        deserializer.registerMediaType(InsyteMediaType.TEXT.toString(), InsyteMediaText.class);
        deserializer.registerMediaType(InsyteMediaType.VIDEO.toString(), InsyteMediaVideo.class);
        deserializer.registerMediaType(InsyteMediaType.AUDIO.toString(), InsyteMediaAudio.class);

        gson = new GsonBuilder().registerTypeAdapter(InsyteItemData.class, deserializer).create();
    }

    public static Gson getInstance()
    {
        return gson;
    }

    static class InsyteDeserializer implements JsonDeserializer<InsyteItemData>
    {
        String mediaTypeElementName;
        Gson gson;
        Map<String, Class<? extends InsyteMedia>> mediaTypeRegistry;

        InsyteDeserializer(String mediaTypeElementName)
        {
            this.mediaTypeElementName = mediaTypeElementName;
            gson = new Gson();
            mediaTypeRegistry = new HashMap<>();
        }

        void registerMediaType(String mediaTypeName, Class<? extends InsyteMedia> mediaType)
        {
            mediaTypeRegistry.put(mediaTypeName, mediaType);
        }

        @Override
        public InsyteItemData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException
        {
            JsonObject insyteObject = json.getAsJsonObject();
            JsonElement mediaTypeElement = insyteObject.get(mediaTypeElementName);
            InsyteItemData insyte = gson.fromJson(insyteObject, InsyteItemData.class);
            insyte.setMedia_type(mediaTypeElement.getAsString());
            Class<? extends InsyteMedia> mediaType = mediaTypeRegistry.get(insyte.getMedia_type());
            insyte.setMedia(gson.fromJson(insyteObject.get("media"), mediaType));
            return insyte;
        }
    }
}
