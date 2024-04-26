package com.foodexpress.restaurantservice.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DebeziumConnectorConfig {

    @Bean
    public io.debezium.config.Configuration customerConnector() {
        return io.debezium.config.Configuration.create()
                .with("name", "product-connector")
                .with("connector.class", "io.debezium.connector.postresql.PostgresConnector")
                .with("tasks.max", "1")
                .with("database.hostname", "localhost")
                .with("database.dbname", "restaurant-express")
                .with("database.port", "5434")
                .with("database.user", "root")
                .with("database.password", "root")
                .with("database.server.name", "restaurant-express")
                .with("offset.storage", "org.apache.kafka.connect.storage.FileOffsetBackingStore")
                .with("schema.history.internal.kafka.bootstrap.servers", "localhost:9092")
                .with("schma.history.internal.kafka.topic", "schema-changes.restaurant-express")
                .with("offset.storage.file.filename", "offset.dat")
                .with("offset.flush.interval.ms", 60000)
                .with("plugin.name", "pgoutput")
                .with("database.whitelist", "foodexpress")
                .with("table.whitelist", "foodexpress.restaurant")
                .with("database.history", "io.debezium.relational.history.FileDatabaseHistory")
                .with("database.history.file.filename", "/debezium/dbhistory.dat")
                .build();
    }
}
