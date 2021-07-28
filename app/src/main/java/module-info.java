module com.treefrogapps.desktop.linux.sensor.monitor {

    // Required by this module (its dependencies)
    requires java.compiler;
    requires java.prefs;
    requires javax.inject;
    requires kotlin.stdlib;
    requires kotlin.stdlib.jdk8;
    requires javafx;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires rxjava3;
    requires dagger;
    requires io.reactivex.rxjava3;
    requires log4j.api;
    requires core;

    // Add "opens" with "opens xxx.xxx.xxx (optional : to javafx.fxml)  if "@FXML" is used in this module
    opens com.treefrogapps.desktop.linux.sensor.monitor.ui.controller;
    opens com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter;
    // Export package (needed by JavaFX to start the Application)
    exports com.treefrogapps.desktop.linux.sensor.monitor;
    exports com.treefrogapps.desktop.linux.sensor.monitor.repository;
}