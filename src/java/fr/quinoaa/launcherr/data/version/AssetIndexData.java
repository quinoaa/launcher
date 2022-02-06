package fr.quinoaa.launcherr.data.version;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.resource.version.AssetResource;

public class AssetIndexData {
    public AssetResource[] assets;
    private int i = 0;

    public AssetIndexData(JsonElement el){
        JsonObject base = el.getAsJsonObject().getAsJsonObject("objects");
        assets = new AssetResource[base.size()];

        base.entrySet().forEach(entry ->{
            assets[i] = new AssetResource(entry.getValue().getAsJsonObject());
            i++;
        });
    }
}
