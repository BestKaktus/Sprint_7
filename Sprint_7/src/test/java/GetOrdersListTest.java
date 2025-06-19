import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetOrdersListTest {

    private String firstName = "Морда";
    private String lastName = "Серая";
    private String address = "Далеко";
    private String metroStation = "Царицыно";
    private String phone = "+7 800 111 22 33";
    private String rentTime = "2";
    private String deliveryDate = "2025-01-20";
    private String comment = "Скорее";
    private String[] color = new String[]{"BLACK"};

    OrderApi orderApi = new OrderApi();
    int track;

    @BeforeEach
    public void createOrder() {
        OrderCreateData orderCreationData = new OrderCreateData(firstName, lastName, address,
                metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = orderApi.createOrder(orderCreationData);
        this.track = response.then().extract().body().path("track");
    }

    @Test
    @DisplayName("Getting orders list")
    @Description("Positive test of getting all created orders")
    public void getOrdersListTest() {
        Response response = orderApi.getOrdersList();
        orderApi.checkResponseOfGettingOrdersList(response);
    }

    @AfterEach
    public void cancelOrder() {
        orderApi.cancelOrder(track);
    }

}