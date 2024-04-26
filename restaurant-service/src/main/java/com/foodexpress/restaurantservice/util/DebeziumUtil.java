package com.foodexpress.restaurantservice.util;

import com.foodexpress.restaurantservice.service.ProductService;
import io.debezium.config.Configuration;
import io.debezium.embedded.EmbeddedEngine;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.connect.data.Field;
import org.apache.kafka.connect.data.Struct;
import org.apache.kafka.connect.source.SourceRecord;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import static io.debezium.data.Envelope.FieldName.AFTER;
import static io.debezium.data.Envelope.FieldName.OPERATION;


@Component
@Slf4j
public class DebeziumUtil {

    private final Executor executor = Executors.newSingleThreadExecutor();
    private final EmbeddedEngine engine;
    private final ProductService productService;

    public void startDebeziumEngine() {
        executor.execute(engine);
        log.info("Debezium engine started");
    }

    public void stopDebeziumEngine() {
        engine.stop();
        log.info("Debezium engine stopped");
    }

    public DebeziumUtil(Configuration configuration, ProductService productService) {
        this.engine=EmbeddedEngine.create()
                .using(configuration)
                .notifying(this::handleEvent)
                .build();
      this.productService = productService;
    }

    private void handleEvent(SourceRecord sourceRecord) {
        Struct sourceRecordValue = (Struct) sourceRecord.value();

        var crudOperation = (String) sourceRecordValue.get(OPERATION);


        if (sourceRecordValue != null && (crudOperation == "c" || crudOperation == "u")) {
            Struct struct = (Struct) sourceRecordValue.get(AFTER);
            Map<String, Object> payload = struct.schema().fields().stream()
                    .filter(field -> struct.get(field) != null)
                    .collect(Collectors.toMap(Field::name, field -> struct.get(field))); // Veri degisikliginin islenmesi ve uygun bir sekilde kaydedilmesi saglandi.

        }
    }
}
