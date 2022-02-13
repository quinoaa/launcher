# Launcherr
A simple minecraft launcher library, for vanilla or modded game.

Currently, it supports

- [x] Downloading files
- [ ] Downloading files with multiple threads
- [x] Launching game
- [ ] Legacy versions (1.12.2 and lower)
- [ ] Authentication system
    - [ ] Microsoft
    - [ ] ~~Mojang~~ (It will be disabled soon anyway)
- [x] Custom version manifest for modded version [please read this](MODDED.md)
## How to use
First, create an instance of the launcher
```java
Launcher launcher = new Launcher(Paths.get("game files"));
```

### 1 - Gettings the Version Manifest
```java
// First, we have to fetch the version manifest
VersionListData versionlist = launcher.getVersionList();
// You can search for a specific version
VersionResource version = versionlist.getVersion("1.18.1");
// Or you can get the whole list
List<VersionResource> list = versions.getAllVersions();
```
Note: For modded versions, you have to use `CustomVersionResource` [and also please read this](MODDED.md)
```java
// The path should be relative to the game files
// Downloads the manifest from the url
CustomVersionResource custom = new CustomVersionResource(Paths.get("version.json"), "https://example.org/version.manifest");
// And if the manifest is already present
CustomVersionResource custom = new CustomVersionResource(Paths.get("version.json"));
```

### 2 - Downloading the version's manifest and game files
```java
// Queries the version's manifest
VersionData versiondata = launcher.queryVersionData(version);
// Download the game files
launcher.downloadDatas(data);
```

### 3 - Authentication
TODO

### 4 - Launching game
First, indicate the java's command:
`new JavaSettings("java")`

Then create the launch parameters:
```java
LauncherParameters launchparams = launcher.createLaunchParameters(user, versiondata, new JavaSettings("java"));
// You probably also want to set your launcher's name and version
launchparams.jvmparams.setLauncherName("name");
launchparams.jvmparams.setLauncherVersion("version");
// There are also other parameters
launchparams.gameparams.setVersionName("name");
```
After, create the wrapper and launch !
```java
LaunchWrapper wrap = launcher.prepareLaunch(versiondata, launchparams);
wrap.launch();
```
