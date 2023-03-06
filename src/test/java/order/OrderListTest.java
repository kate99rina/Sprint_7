package order;

import http.OrderClient;
import io.qameta.allure.Allure;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Order list")
public class OrderListTest {

    @Test
    @DisplayName("Проверка на содержание в теле ответа списка заказов")
    public void checkOrderList() {
        OrderClient orderClient = new OrderClient();
        Response response = orderClient.getListOrder();
        Allure.step("Проверка тела ответа на наличие списка заказов orders");
        response.then().assertThat().body("orders", notNullValue());
    }
}
