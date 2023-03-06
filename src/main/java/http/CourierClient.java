package http;

import http.model.CourierCreateRequest;
import http.model.LoginCourierRequest;
import http.model.LoginCourierResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.*;

public class CourierClient extends RestApiClient {
    private final String COURIER_PATH = "/api/v1/courier";
    private final String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    private final String COURIER_DELETE_PATH = "/api/v1/courier/";

    @Step("Создание курьера с данными: login={login}, password={password}, firstName={firstName}")
    public Response createCourier(String login, String password, String firstName) {
        System.out.println("Creating new courier with info: " + login + ", " + password + ", " + firstName);
        Response response = given()
                .spec(getBaseSpec())
                .body(new CourierCreateRequest(login, password, firstName))
                .when()
                .post(COURIER_PATH);
        System.out.println("Received response: " + response.asString());
        return response;
    }

    public Long getLoginCourierId(String login, String password) {
        return loginCourier(login, password)
                .body()
                .as(LoginCourierResponse.class)
                .getId();
    }

    @Step("Авторизация курьера с login={login}")
    public Response loginCourier(String login, String password) {
        System.out.println("Login courier with info: " + login + ", " + password);
        Response response = given()
                .spec(getBaseSpec())
                .body(new LoginCourierRequest(login, password))
                .post(COURIER_LOGIN_PATH);
        System.out.println("Received response: " + response.asString());
        return response;
    }

    @Step("Удаление курьера с id={id}")
    public void deleteCourier(Long id) {
        System.out.println("Deleting courier with id = " + id);
        if (id != null) {
            Response response = given()
                    .spec(getBaseSpec())
                    .when()
                    .delete(COURIER_DELETE_PATH + id);
            System.out.println("Received response: " + response.asString());
            response.then()
                    .assertThat()
                    .statusCode(SC_CREATED);
        } else {
            throw new RuntimeException("Id of courier is null, so nothing to delete!");
        }
    }
}
