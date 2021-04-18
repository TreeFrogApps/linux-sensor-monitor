module com.treefrogapps.desktop.linux.sensor.monitor {

    // Required by this module (its dependencies)
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

    // Add "opens" with "opens xxx.xxx.xxx (optional : to javafx.fxml)  if "@FXML" is used in this module
    // For some reason I need to specify each sub package location where controllers are ... ¯\_(ツ)_/¯
    opens com.treefrogapps.desktop.linux.sensor.monitor.ui.controller;
    opens com.treefrogapps.desktop.linux.sensor.monitor.ui.adapter;
    // Export package (needed by JavaFX to start the Application)
    exports com.treefrogapps.desktop.linux.sensor.monitor;
    // needs this explicitly for some reason ... ¯\_(ツ)_/¯
    exports com.treefrogapps.desktop.linux.sensor.monitor.repository;
}