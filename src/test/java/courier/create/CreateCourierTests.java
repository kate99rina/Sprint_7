package courier.create;

import http.CourierClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static util.FakerData.*;

@DisplayName("Create courier")
public class CreateCourierTests {
    private final CourierClient COURIER_CLIENT = new CourierClient();
    private String login;
    private String password;
    private String firstName;

    @Before
    public void beforeClass() {
        login = getLogin();
        password = getPassword();
        firstName = getFirstName();
    }

    @After
    public void afterEachTest() {
        Long idCourier = COURIER_CLIENT
                .getLoginCourierId(login, password);
        COURIER_CLIENT.deleteCourier(idCourier);
    }

    @Test
    @DisplayName("Проверка создания курьера")
    public void checkCreateCourier() {
        var response = COURIER_CLIENT.createCourier(login, password, firstName);
        Allure.step("Проверка status line ответа");
        response.then().assertThat().statusLine(containsString("Created"));
    }

    @Test
    @DisplayName("Проверка создания курьера со всеми обязательными полями")
    public void checkCreateCourierFields() {
        var response = COURIER_CLIENT.createCourier(login, password, firstName);
        Allure.step("Проверка status code ответа");
        response.then().assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Запрос создания курьера возвращает корректный статус запроса")
    public void checkStatusResponse() {
        var response = COURIER_CLIENT.createCourier(login, password, firstName);
        response.then().assertThat().statusCode(201);
    }

    @Test
    @DisplayName("Запрос создания курьера возвращает корректное тело ответа")
    public void checkBodyResponse() {
        var response = COURIER_CLIENT.createCourier(login, password, firstName);
        response.then().assertThat().body("ok", equalTo(true));
    }

}
