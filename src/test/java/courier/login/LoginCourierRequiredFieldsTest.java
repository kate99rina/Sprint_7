package courier.login;

import http.CourierClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;
import static util.FakerData.*;

@RunWith(Parameterized.class)
@DisplayName("Login courier")
public class LoginCourierRequiredFieldsTest {
    private static String login = getLogin();
    private static String password = getPassword();
    private static final CourierClient courierClient = new CourierClient();
    private String authLogin;
    private String authPassword;
    private static Long idCourier;

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

    @BeforeClass
    public static void beforeClass() {
        courierClient.createCourier(login, password, getFirstName());
        idCourier = courierClient.getLoginCourierId(login, password);
    }


    @AfterClass
    public static void afterClass() {
        courierClient.deleteCourier(idCourier);
    }

    @Test
    @DisplayName("Проверка необязательности всех полей для авторизации")
    public void checkRequiredFieldsForAuth() {
        Response response = courierClient.loginCourier(authLogin, authPassword);
        Allure.step("Проверка ответа на содержание соответствующей ошибки",
                () -> response.then()
                        .assertThat().statusCode(equalTo(400))
                        .assertThat().body("message", equalTo("Недостаточно данных для входа"))
        );
    }
}
