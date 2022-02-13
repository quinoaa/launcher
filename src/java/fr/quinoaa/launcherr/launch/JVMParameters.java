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
import fr.quinoaa.launcherr.resource.download.version.ArtifactResource;
import fr.quinoaa.launcherr.resource.download.version.ClassifierResource;
import fr.quinoaa.launcherr.util.OsUtil;

import java.nio.file.Path;

public class JVMParameters extends LaunchParameters {
    public JVMParameters(VersionData data, Path gamefiles, Path natives) {
        params.put("natives_directory", natives.toAbsolutePath().toString());
        params.put("classpath", getDependencies(data, gamefiles));
        params.put("classpath_separator", OsUtil.getJarSeparator() + "");
        params.put("library_directory", gamefiles.resolve(ArtifactResource.getLibraryFolder()).
                toAbsolutePath().toString());
        params.put("version_name", data.id);
    }

    private String getDependencies(VersionData data, Path gamefiles){
        StringBuilder builder = new StringBuilder();

        builder.append(data.client.getPath(gamefiles).toAbsolutePath());
        for(ArtifactResource artifact : data.libraries.artifacts){
            builder.append(OsUtil.getJarSeparator());
            builder.append(artifact.getPath(gamefiles).toAbsolutePath());
        }
        for(ClassifierResource artifact : data.libraries.classifiers){
            builder.append(OsUtil.getJarSeparator());
            builder.append(artifact.getPath(gamefiles).toAbsolutePath());
        }
        return builder.toString();
    }

    public void setLauncherName(String name){
        params.put("launcher_name", name);
    }
    public void setLauncherVersion(String version){
        params.put("launcher_version", version);
    }
}
