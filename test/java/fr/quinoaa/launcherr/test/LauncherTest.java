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

package fr.quinoaa.launcherr.test;

import fr.quinoaa.launcherr.Launcher;
import fr.quinoaa.launcherr.LauncherParameters;
import fr.quinoaa.launcherr.auth.User;
import fr.quinoaa.launcherr.data.VersionListData;
import fr.quinoaa.launcherr.data.version.VersionData;
import fr.quinoaa.launcherr.launch.JavaSettings;
import fr.quinoaa.launcherr.launch.LaunchWrapper;
import fr.quinoaa.launcherr.resource.download.version.VersionResource;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

public class LauncherTest {
    Path files = Paths.get("artifacts");
    Path games = Paths.get("game");

    //@Test
    public void testLauncher() throws IOException {
        try{
            Launcher launcher = new Launcher(files, games);

            VersionListData versions = launcher.getVersionList();
            VersionResource version = versions.getVersion("1.12.2");
            // It doesn't need jre 16/17 and has new arguments

            VersionData data = launcher.queryVersionData(version);

            launcher.downloadDatas(data);

            User user = new User("caca", "a", "a", UUID.nameUUIDFromBytes
                    ("abcd".getBytes()), 1, "mojang");
            LauncherParameters params = launcher.createLaunchParameters(user, data, new JavaSettings("java"));
            LaunchWrapper wrap = launcher.prepareLaunch(data, params);
            wrap.launch();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
