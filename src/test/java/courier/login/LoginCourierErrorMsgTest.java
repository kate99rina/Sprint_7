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

import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.Matchers.equalTo;
import static util.FakerData.*;

@RunWith(Parameterized.class)
@DisplayName("Login courier")
public class LoginCourierErrorMsgTest {

    private static final String login = getLogin();
    private static final String password = getPassword();
    private final String firstName = getFirstName();
    private String authLogin;
    private String authPassword;
    private Long idCourier;
    private final CourierClient COURIER_CLIENT = new CourierClient();

    public LoginCourierErrorMsgTest(String authLogin, String authPassword) {
        this.authLogin = authLogin;
        this.authPassword = authPassword;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {login + "test", password},
                {login, password + "test"},
                {login + "test", password + "test"}
        };
    }

    @Before
    public void beforeTest() {
        COURIER_CLIENT.createCourier(login, password, firstName);
        idCourier = COURIER_CLIENT.getLoginCourierId(login, password);
    }

    @After
    public void afterEachTest() {
        COURIER_CLIENT.deleteCourier(idCourier);
    }

    @Test
    @DisplayName("Проверка на возникновение ошибки при некорректном/несуществующем логине/пароле/логине и пароле")
    public void checkIncorrectFields() {
        Response response = COURIER_CLIENT.loginCourier(authLogin, authPassword);
        Allure.step("Проверка ответа на содержание соответствующей ошибки");
        response.then()
                .assertThat().statusCode(equalTo(SC_NOT_FOUND))
                .assertThat().body("message", equalTo("Учетная запись не найдена"));
    }
}
