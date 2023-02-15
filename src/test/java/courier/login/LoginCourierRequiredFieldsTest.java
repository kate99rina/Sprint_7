package courier.login;

import http.CourierClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;
import static util.FakerData.*;

@RunWith(Parameterized.class)
@DisplayName("Login courier")
public class LoginCourierRequiredFieldsTest {
    private static String login;
    private static String password;
    private final CourierClient courierClient = new CourierClient();
    private String authLogin;
    private String authPassword;
    private Long idCourier;
    public LoginCourierRequiredFieldsTest(String authLogin, String authPassword) {
        this.authLogin = authLogin;
        this.authPassword = authPassword;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {login, null},
                {null, password}
        };
    }

    @Before
    public void beforeTest() {
        login = getLogin();
        password = getPassword();
        courierClient.createCourier(login, password, getFirstName());
        idCourier = courierClient.getLoginCourierId(login, password);
    }

    @After
    public void afterEachTest() {
        courierClient.deleteCourier(idCourier);
    }

    @Test
    @DisplayName("Проверка необязательности всех полей для авторизации")
    public void checkRequiredFieldsForAuth() {
        Response response = courierClient.loginCourier(authLogin, authPassword);
        Allure.step("Проверка ответа на содержание соответствующей ошибки");
        response.then()
                .assertThat().statusCode(equalTo(400))
                .assertThat().body("message", equalTo("Недостаточно данных для входа"));
    }
}
