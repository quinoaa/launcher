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

import fr.quinoaa.launcherr.launch.data.LaunchData;
import fr.quinoaa.launcherr.resource.extract.NativeResource;
import fr.quinoaa.launcherr.util.ZipUtil;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class LaunchWrapper {
    LaunchData data;
    ParameterProvider gameargs;
    ParameterProvider jvmargs;
    JavaSettings settings;
    Path rootdir;
    Path gamedir;

    Map<String, Boolean> features = new HashMap<>();

    Process process;

    public LaunchWrapper(LaunchData data, ParameterProvider gameargs, ParameterProvider jvmargs,
                         JavaSettings settings, Path root, Path game){
        this.data = data;
        this.gameargs = gameargs;
        this.jvmargs = jvmargs;
        this.settings = settings;
        this.rootdir = root;
        this.gamedir = game;
    }

    public void setFeatures(Map<String, Boolean> features){
        this.features = features;
    }

    public void setFeature(String key, boolean value){
        features.put(key, value);
    }

    public void prepareNatives() throws IOException {
        for(NativeResource res : data.version.libraries.natives){
            ZipUtil.unzip(res.zip.getPath(rootdir), res.getPath(gamedir), res.exclude);
        }
    }

    public void launch() throws IOException {
        List<String> jvm = data.parseJVMArgs(features, jvmargs);
        List<String> game = data.parseGameArgs(features, gameargs);

        List<String> command = new ArrayList<>();

        command.add(settings.java);
        command.addAll(jvm);
        command.addAll(Arrays.asList(settings.extraarguments));
        command.add(data.mainclass);
        command.addAll(game);

        command.replaceAll(str->{
            if(str.contains(" ") || str.contains(".")) return '"' + str + '"';
            return str;
        });

        ProcessBuilder pb = new ProcessBuilder().command(command);
        pb.directory(this.gamedir.toFile());
        this.process = pb.start();
    }

    public Process getProcess(){
        return process;
    }


    public interface ParameterProvider {
        String replaceArgument(String key);
    }
}
