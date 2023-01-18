package s.m.learning.footballapp.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PaginatedResponse<T> {
    private Integer page;
    @JsonAlias("per_page")
    private Integer perPage;
    private Integer total;
    private Integer totalPages;
    private List<T> data = new ArrayList<>();
}
