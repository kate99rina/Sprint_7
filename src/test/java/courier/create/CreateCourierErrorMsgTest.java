package courier.create;

import http.CourierClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.equalTo;
import static util.FakerData.*;

@DisplayName("Create courier")
@RunWith(Parameterized.class)
public class CreateCourierErrorMsgTest {

    private final CourierClient COURIER_CLIENT = new CourierClient();
    private String login;
    private String password;
    private String firstName;

    public CreateCourierErrorMsgTest(String login, String password, String firstName) {
        this.login = login;
        this.password = password;
        this.firstName = firstName;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {null, "password", "firstName"},
                {"login", null, "firstName"},
                {"login1324", "password", null},
                {null, null, "firstName"},
                {null, "password", null},
                {"login", null, null}
        };
    }

    @Before
    public void beforeClass() {
        login = getLogin();
        password = getPassword();
        firstName = getFirstName();
    }

    @Test
    @DisplayName("Создание курьера со всеми необходимыми полями")
    public void checkCreateCourierFields() {
        var response = COURIER_CLIENT.createCourier(login, password, firstName);
        Allure.step("Проверка наличия в теле соответствующей ошибки и status code");
        response.then()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .assertThat().statusCode(400);
    }
}
