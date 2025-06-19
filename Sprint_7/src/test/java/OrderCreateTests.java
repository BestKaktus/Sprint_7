import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class OrderCreateTests {

    OrderApi orderApi = new OrderApi();
    int track;

    @ParameterizedTest
    @MethodSource("orderDataProvider")
    @DisplayName("Order creation")
    @Description("Positive test of creating an order with different scooter color")
    public void createOrderTest(String firstName, String lastName,
                                String address, String metroStation, String phone, String rentTime,
                                String deliveryDate, String comment, String[] color) {
        OrderCreateData orderCreationData = new OrderCreateData(firstName, lastName,
                address, metroStation, phone, rentTime, deliveryDate, comment, color);
        Response response = orderApi.createOrder(orderCreationData);
        orderApi.checkResponseAfterOrderCreate(response);
        this.track = response.then().extract().body().path("track");
    }

    private static Stream<Arguments> orderDataProvider() {
        return Stream.of(
                Arguments.of("Морда", "Серая", "Далеко", "Царицыно", "+7 800 111 22 33", "2", "2025-01-20", "Скорее", new String[]{"BLACK"}),
                Arguments.of("Морда", "Рыжая", "Ещё дальше", "Пушкинская", "+7 800 111 22 33", "2", "2025-01-20", "Скорее", new String[]{"BLACK", "GREY"}),
                Arguments.of("Морда", "Пушистая", "Совсем всё плохо", "Маяковская", "+7 800 111 22 33", "2", "2025-01-20", "Скорее", new String[]{"GREY"}),
                Arguments.of("Морда", "Вредная", "И ещё хуже", "Измайлово", "+7 800 111 22 33", "2", "2025-01-20", "Скорее", new String[]{})
        );
    }

    @AfterEach
    public void cancelOrder() {
            orderApi.cancelOrder(track);
        }

}
