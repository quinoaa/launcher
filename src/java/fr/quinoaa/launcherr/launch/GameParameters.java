/*
 * MIT License
 *
 * Copyright (c) 2022 quinoaa
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package fr.quinoaa.launcherr.launch;

import fr.quinoaa.launcherr.data.version.VersionData;

import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

public class GameParameters extends LaunchParameters {

    public GameParameters(VersionData version, Path resources, Path gamedir){
        setVersionName(version.id);
        params.put("game_directory", gamedir.toAbsolutePath().toString());
        params.put("assets_root", version.assets.assetFolder.getPath(resources).toAbsolutePath().toString());
        params.put("assets_index_name", version.assets.id);

        setVersionType(version.type);
    }


    public void setVersionName(String name){
        params.put("version_name", name);
    }

    public void setUserName(String name){
        params.put("auth_player_name", name);
    }

    public void setUuid(UUID uuid){
        setUuid(uuid.toString());
    }
    public void setUuid(String uuid){
        params.put("auth_uuid", new String(Base64.getEncoder().encode(uuid.getBytes(StandardCharsets.UTF_8))));
    }

    public void setAccessToken(String token){
        params.put("auth_access_token", token);
    }

    public void setClientId(String id){
        params.put("clientid", id);
    }

    public void setXuid(long xuid){
        params.put("auth_xuid", Long.toString(xuid));
    }

    public void setUserType(String type){
        params.put("user_type", type);
    }

    public void setVersionType(String type){
        params.put("version_type", type);
    }

    public void setResolution(int w, int h, Map<String, Boolean> features){
        features.put("has_custom_resolution", true);
        params.put("resolution_width", Integer.toString(w));
        params.put("resolution_height", Integer.toString(h));
    }
}
