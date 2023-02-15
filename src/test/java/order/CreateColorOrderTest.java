package order;

import http.OrderClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.Matchers.greaterThan;

@RunWith(Parameterized.class)
@DisplayName("Create order")
public class CreateColorOrderTest {

    private final OrderClient ORDER_CLIENT = new OrderClient();
    private String[] colors;

    public CreateColorOrderTest(String[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {new String[]{"BLACK"}},
                {new String[]{"GREY"}},
                {new String[]{"BLACK", "GREY"}},
                {new String[]{null}}
        };
    }

    @Test
    public void checkOrderColors() {
        Response response = ORDER_CLIENT.createOrder(colors);
        Allure.step("Проверка тела ответа на наличие track");
        response.then().body("track", greaterThan(0));
    }
}