package s.m.learning.footballapp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

public class JsonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private JsonUtil(){}

    @SneakyThrows
    public static <T> String toJson(T t){
        return MAPPER.writeValueAsString(t);
    }

    @SneakyThrows
    public static <U> U fromJson(String json, TypeReference<U> typeReference) {
        return MAPPER.readValue(json, typeReference);
    }
}
