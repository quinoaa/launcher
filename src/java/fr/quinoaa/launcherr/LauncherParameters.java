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

package fr.quinoaa.launcherr;

import fr.quinoaa.launcherr.auth.User;
import fr.quinoaa.launcherr.data.version.VersionData;
import fr.quinoaa.launcherr.launch.GameParameters;
import fr.quinoaa.launcherr.launch.JVMParameters;
import fr.quinoaa.launcherr.launch.JavaSettings;
import fr.quinoaa.launcherr.resource.extract.NativeResource;

import java.nio.file.Path;
import java.util.logging.Logger;

public class LauncherParameters {
    User user;
    public JavaSettings java;
    public JVMParameters jvmparams;
    public GameParameters gameparams;

    public LauncherParameters(User user, VersionData version, JavaSettings java, Launcher launcher){
        this.user = user;
        this.java = java;

        jvmparams = new JVMParameters(version, launcher.gamefiles, NativeResource.getRelativePath(launcher.savefiles));
        jvmparams.setLauncherVersion("1");
        jvmparams.setLauncherName("launcher");
        gameparams = new GameParameters(version, launcher.gamefiles, launcher.savefiles);
        gameparams.setClientId(user.clientid);
        gameparams.setUserType(user.usertype);
        gameparams.setXuid(user.xuid);
        gameparams.setUuid(user.uuid);
        gameparams.setAccessToken(user.accesstoken);
        gameparams.setUserName(user.username);
    }

}
