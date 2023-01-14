package s.m.learning.footballapp.model;
import java.util.ArrayList;
import java.util.List;
public class PaginatedResponse<T> {
    private Integer page;
    private Integer perPage;
    private Integer total;
    private Integer totalPages;
    private List<T> data = new ArrayList<>();
    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getPerPage() {
        return perPage;
    }
    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }
    public Integer getTotal() {
        return total;
    }
    public void setTotal(Integer total) {
        this.total = total;
    }
    public Integer getTotalPages() {
        return totalPages;
    }
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }
    public List<T> getData() {
        return data;
    }
    public void setData(List<T> data) {
        this.data = data;
    }
}
