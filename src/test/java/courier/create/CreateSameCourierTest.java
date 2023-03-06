package courier.create;

import http.CourierClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CONFLICT;
import static org.hamcrest.Matchers.equalTo;
import static util.FakerData.*;

@DisplayName("Create courier")
public class CreateSameCourierTest {
    private final CourierClient COURIER_CLIENT = new CourierClient();
    private String login;
    private String password;
    private String firstName;

    @Before
    public void beforeClass() {
        login = getLogin();
        password = getPassword();
        firstName = getFirstName();
        COURIER_CLIENT.createCourier(login, password, firstName);
    }

    @After
    public void afterClass() {
        Long idCourier = COURIER_CLIENT
                .getLoginCourierId(login, password);
        COURIER_CLIENT.deleteCourier(idCourier);
    }

    @Test
    @DisplayName("Проверка создание двух одинаковых курьеров")
    public void checkCreateSameCouriers() {
        var response = COURIER_CLIENT.createCourier(login, password, firstName);
        Allure.step("Проверка ответа на содержание соответствующей ошибки");
        response.then().assertThat().statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Проверка возвращения ошибки при создании курьера с существующим логином")
    public void checkConflictResponse() {
        COURIER_CLIENT.createCourier(login, password, firstName);
        var response = COURIER_CLIENT.createCourier(login, password, firstName);
        Allure.step("Проверка ответа на содержание соответствующей ошибки");
        response.then().assertThat().body("message", equalTo("Этот логин уже используется"));
    }
}
