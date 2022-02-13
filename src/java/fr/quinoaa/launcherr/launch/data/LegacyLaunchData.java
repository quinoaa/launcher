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

package fr.quinoaa.launcherr.launch.data;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.quinoaa.launcherr.data.version.VersionData;

public class LegacyLaunchData extends LaunchData {
    public static final String JVMARGS = "[{\"rules\": [{\"action\": \"allow\", \"os\": {\"name\": \"osx\"}}], " +
            "\"value\": [\"-XstartOnFirstThread\"]}, {\"rules\": [{\"action\": \"allow\", \"os\": {\"name\": " +
            "\"windows\"}}], \"value\": \"-XX:HeapDumpPath=MojangTricksIntelDriversForPerformance_javaw." +
            "exe_minecraft.exe.heapdump\"}, {\"rules\": [{\"action\": \"allow\", \"os\": {\"name\": " +
            "\"windows\", \"version\": \"^10\\\\.\"}}], \"value\": [\"-Dos.name=Windows 10\", \"-Dos.version=10.0\"]}" +
            ", {\"rules\": [{\"action\": \"allow\", \"os\": {\"arch\": \"x86\"}}], \"value\": \"-Xss1M\"}, " +
            "\"-Djava.library.path=${natives_directory}\", \"-Dminecraft.launcher.brand=${launcher_name}\", " +
            "\"-Dminecraft.launcher.version=${launcher_version}\", \"-cp\", \"${classpath}\"]";

    public LegacyLaunchData(JsonObject root, VersionData version) {
        super(version);

        jvmargs = new Gson().fromJson(JVMARGS, JsonArray.class);
        gameargs = new JsonArray();
        for(String s : root.get("minecraftArguments").getAsString().split(" ")){
            gameargs.add(s);
        }

        mainclass = root.get("mainClass").getAsString();
    }
}
