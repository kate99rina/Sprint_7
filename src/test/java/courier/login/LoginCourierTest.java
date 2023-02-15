package courier.login;

import http.CourierClient;
import http.model.LoginCourierResponse;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertNotNull;
import static util.FakerData.*;

@DisplayName("Login courier")
public class LoginCourierTest {

    private final CourierClient courierClient = new CourierClient();
    private String login;
    private String password;
    private Long idCourier;

    @Before
    public void beforeEachTest() {
        login = getLogin();
        password = getPassword();
        courierClient.createCourier(login, password, getFirstName());
    }

    @After
    public void afterEachTest() {
        courierClient.deleteCourier(idCourier);
    }

    @Test
    @DisplayName("Проверка авторизации курьера")
    public void checkCourierAuth() {
        Response response = courierClient.loginCourier(login, password);
        idCourier = response
                .body()
                .as(LoginCourierResponse.class)
                .getId();
        Allure.step("Проверка status code ответа");
        response.then().assertThat().statusCode(equalTo(200));
    }

    @Test
    @DisplayName("Проверка обязательности всех полей для авторизации")
    public void checkRequiredFieldsForAuth() {
        idCourier = courierClient.getLoginCourierId(login, password);
        Allure.step("Проверка тела ответа на содержание значения id, не равное null ");
        assertNotNull("Id = null", idCourier);
    }

    @Test
    @DisplayName("Проверка на наличие id в теле ответа")
    public void checkBodyResponse() {
        Response response = courierClient.loginCourier(login, password);
        idCourier = response.body()
                .as(LoginCourierResponse.class)
                .getId();
        Allure.step("Проверка тела ответа на содержание значения id, равное числу>=0");
        response.then()
                .assertThat()
                .body("id", greaterThanOrEqualTo(0));
    }
}
