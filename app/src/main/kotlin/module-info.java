module com.treefrogapps.desktop.linux.sensor.monitor {

    // Require JavaFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx;
    requires rxjava3;
    requires kotlin.stdlib;
    requires kotlin.stdlib.jdk8;
    requires javax.inject;
    requires dagger;
    requires io.reactivex.rxjava3;
    requires log4j.api;
    requires java.prefs;
    requires core;

    // Export package (needed by JavaFX to start the Application)
    // Replace "exports" with "opens" if "@FXML" is used in this module
    exports com.treefrogapps.desktop.linux.sensor.monitor;
    exports com.treefrogapps.desktop.linux.sensor.monitor.repository;
}