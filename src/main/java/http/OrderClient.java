package http;

import http.model.OrderListRequest;
import http.model.OrderRequest;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestApiClient {
    private final String CREATE_ORDER_PATH = "/api/v1/orders";

    @Step("Создание заказа с цветами {colors}")
    public Response createOrder(String[] colors) {
        System.out.println("Creating new order with info: " + Arrays.toString(colors));
        Response response = given()
                .spec(getBaseSpec())
                .body(new OrderRequest(colors))
                .when()
                .post(CREATE_ORDER_PATH);
        System.out.println("Received response: " + response.asString());
        return response;
    }

    @Step("Получение списка заказов")
    public Response getListOrder() {
        System.out.println("Receiving order list");
        Response response = given()
                .spec(getBaseSpec())
                .body(new OrderListRequest())
                .when()
                .get(CREATE_ORDER_PATH);
        System.out.println("Received response with statusCode: " + response.statusCode());
        return response;
    }
}
