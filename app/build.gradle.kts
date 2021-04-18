import org.jetbrains.kotlin.config.KotlinCompilerVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.openjfx.javafxplugin") version "0.0.9"
    id("org.beryx.jlink") version "2.23.6"
}

group = "com.treefrogapps.desktop.linux"
project.extra["name"] = "sensor.monitor"
version = "1.0.0"

val javaVersion = "11"
val javaFxVersion = "16"
val compileKotlin: KotlinCompile by tasks
val compileJava: JavaCompile by tasks

compileKotlin.kotlinOptions.jvmTarget = javaVersion
compileJava.sourceCompatibility = javaVersion
compileJava.targetCompatibility = javaVersion

application {
    mainModule.set("com.treefrogapps.desktop.linux.sensor.monitor")
    mainClass.set("com.treefrogapps.desktop.linux.sensor.monitor.SensorMonitorApp")
}

javafx {
    version = javaFxVersion
    modules = listOf("javafx.controls", "javafx.fxml")
}

kapt {
    includeCompileClasspath = false
    javacOptions { option("--module-path", compileJava.classpath.asPath) }
}

java {
    modularity.inferModulePath.set(true)
}

jlink {
    launcher { name = "sensor-monitor" }
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    with(project) { imageZip.set(file("${buildDir}/image-zip/${extra["name"]}.zip")) }
}

// NOTE : Ubuntu 20.04 bug with libjvm.so being bloated (430mb) use :  strip -p --strip-unneeded image/lib/server/libjvm.so
tasks {
    register<Task>("jlinkStripped").configure {
        dependsOn("jlink")
        doLast { project.exec { commandLine(listOf("strip", "-p", "--strip-unneeded", "${project.buildDir}/image/lib/server/libjvm.so")) } }
    }
}

dependencies {
    //Kotlin 
    implementation(kotlin("stdlib", KotlinCompilerVersion.VERSION))
    implementation(kotlin("stdlib-jdk8", KotlinCompilerVersion.VERSION))

    // TreeFrogApps Libs
    implementation("com.treefrogapps.kotlin.core:core:1.6.0")
    implementation("com.treefrogapps.rxjava3:rxjava3:1.1.0")
    implementation("com.treefrogapps.javafx:javafx:2.5.1")

    // Dagger
    val daggerVersion = "2.34"
    implementation("com.google.dagger:dagger:$daggerVersion")
    kapt("com.google.dagger:dagger-compiler:$daggerVersion")

    // RxJava
    implementation("io.reactivex.rxjava3:rxjava:3.0.0")

    // Logging
    implementation("org.apache.logging.log4j:log4j-core:2.8.2")

    testImplementation(group = "junit", name = "junit", version = "4.12")
}
