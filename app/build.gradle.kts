import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    application
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.openjfx.javafxplugin") version "0.0.9"
}

group = "com.treefrogapps.desktop.linux"
project.extra["name"] = "sensor.monitor"
version = "1.0.0"

val javaVersion = "11"
val javaFxVersion = "16"

application {
    mainClass.set("com.treefrogapps.desktop.linux.sensor.monitor.SensorMonitorApp")
}

javafx {
    version = javaFxVersion
    modules = listOf("javafx.controls", "javafx.fxml")
}

val compileKotlin: KotlinCompile by tasks
val compileJava: JavaCompile by tasks

compileKotlin.kotlinOptions.jvmTarget = javaVersion

compileJava.run {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

kapt.includeCompileClasspath = false

dependencies {
    //Kotlin 
    implementation(kotlin("stdlib", org.jetbrains.kotlin.config.KotlinCompilerVersion.VERSION))

    // TreeFrogApps Libs
    implementation("com.treefrogapps.kotlin.core:core:1.6.0")
    implementation("com.treefrogapps.rxjava3:rxjava3:1.1.0")
    implementation("com.treefrogapps.javafx:javafx:2.3.1")

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
