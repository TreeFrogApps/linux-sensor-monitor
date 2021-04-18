## TreeFrogApps Linux JavaFX Sensor Temp monitor

- Includes various sensors
- Includes cpu package temp
- Includes cpu core temps
- Includes cpu frequency (current/min/max)
- Ability to adjust sensor poll rate
- Ability to set audio warning when average cores temps hit over set temp
- Ability to shut down when average core temp exceeds max temp

##### Notes

This is a very simple app that uses MVC pattern to visualise information by running
sub-processes from `sensors` and `lscpu` at configurable poll rates.

At some point this might migrate to using JNI or even JNV calls directly to `lm-sensors`.

---
##Create a native application with JLink

The native application created with Jlink will work with the OS that you are currently using:

### Linux
`./gradlew jlink` - Creates a modular runtime image with jlink

`./gradlew jlinkZip` - Creates a zip of the modular runtime image

The result is in "build/image" (the content of the folder is needed to run the application)
You run the application with "build/image/bin/exe_name.bat" (Windows) or "build/image/bin/exe_name" (Linux/macOS)
You don't need a JRE to be able to run it