package http.model;

public class LoginCourierResponse {
    private Long id;

    public LoginCourierResponse(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
