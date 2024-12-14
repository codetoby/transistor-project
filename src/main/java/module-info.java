module org.group14 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    requires java.sql;
    requires java.desktop;

    requires com.zaxxer.hikari;
    requires graphhopper.core;
    requires graphhopper.reader.osm;
    requires com.gluonhq.maps;
    requires org.slf4j;
    requires static lombok;
    requires osmosis.core;
    requires org.yaml.snakeyaml;
    requires spring.context;
    requires spring.beans;
	requires spring.core;

    exports org.group14;
    exports org.group14.config;

    opens org.group14.presentation;
    opens org.group14.services.calculator;
    opens org.group14.presentation.controller to javafx.fxml;
	exports org.group14.config.spring to spring.beans;

}
