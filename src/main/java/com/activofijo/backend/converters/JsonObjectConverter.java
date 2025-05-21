package com.activofijo.backend.converters;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.StringReader;
import java.io.StringWriter;

@Converter(autoApply = false)
public class JsonObjectConverter implements AttributeConverter<JsonObject, String> {
    @Override
    public String convertToDatabaseColumn(JsonObject attribute) {
        if (attribute == null) return null;
        StringWriter sw = new StringWriter();
        try (JsonWriter writer = Json.createWriter(sw)) {
            writer.writeObject(attribute);
        }
        return sw.toString();
    }

    @Override
    public JsonObject convertToEntityAttribute(String dbData) {
        if (dbData == null) return null;
        try (JsonReader reader = Json.createReader(new StringReader(dbData))) {
            return reader.readObject();
        }
    }
}
