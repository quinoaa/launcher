# Modded version note
To run custom versions, you'll have to create a CustomVersionResource with the path to the file.
The version can be downloaded when the URL is specified.

Then use it like a normal VersionResource.

Some modded versions like forge requires you to use their installer (see [Forge section](#forge))

## forge
    Please do not automate the download and installation of Forge.
    Our efforts are supported by ads from the download page.
    If you MUST automate this, please consider supporting the project through https://www.patreon.com/LexManos/
For the forge installer to work, you'll have to create `launcher_profiles.json` inside your game files folder with at least `{}` inside or any valid json.

You also have to install the version once.
For example, for forge-1.12.2, you'll have to install the 1.12.2's VersionResource once via `launcher.downloadDatas()`
