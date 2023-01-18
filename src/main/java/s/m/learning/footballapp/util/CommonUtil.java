package s.m.learning.footballapp.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import s.m.learning.footballapp.model.external.FootballMatchResultDTO;
import s.m.learning.footballapp.model.external.PaginatedResponse;

import java.util.concurrent.Future;

public class CommonUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private CommonUtil(){}

    @SneakyThrows
    public static <T> String toJson(T t){
        return MAPPER.writeValueAsString(t);
    }

    @SneakyThrows
    public static <U> U fromJson(String json, TypeReference<U> typeReference) {
        return MAPPER.readValue(json, typeReference);
    }

    @SneakyThrows
    public static PaginatedResponse<FootballMatchResultDTO> resolve(Future<PaginatedResponse<FootballMatchResultDTO>> futureRes){
        return futureRes.get();
    }
}
