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