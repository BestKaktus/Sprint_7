import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderApi extends BaseHttpClient{

    private final String API_V1_ORDERS = "/api/v1/orders";
    private final String CANCEL_ORDER = "/cancel";

    @Step("Create an order")
    public Response createOrder(OrderCreateData orderCreationData) {
        return given()
                .spec(baseRequestSpec)
                .body(orderCreationData)
                .when()
                .post(API_V1_ORDERS);
    }

    @Step("Check status code and message after order creation")
    public void checkResponseAfterOrderCreate(Response response) {
        response
                .then()
                .assertThat()
                .statusCode(201)
                .and()
                .body("track", notNullValue());
    }

    @Step("Get the orders list")
    public Response getOrdersList() {
        return given()
                .spec(baseRequestSpec)
                .when()
                .get(API_V1_ORDERS);
    }

    @Step("Check status code and response after getting orders list")
    public void checkResponseOfGettingOrdersList(Response response) {
        response.then().assertThat().statusCode(200).and().body("orders", notNullValue());
    }

    public void cancelOrder(int track) {
        given()
                .spec(baseRequestSpec)
                .when()
                .body(track)
                .put(API_V1_ORDERS + CANCEL_ORDER);
    }

}