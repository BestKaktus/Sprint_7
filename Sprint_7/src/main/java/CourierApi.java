import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CourierApi extends BaseHttpClient {

    private final String API_V1_COURIER = "/api/v1/courier";
    private final String LOGIN_COURIER = "/login";

    @Step("Create a courier")
    public Response createCourier(CourierCreateData courierCreateData) {
        return given()
                .spec(baseRequestSpec)
                .body(courierCreateData)
                .when()
                .post(API_V1_COURIER);
    }

    @Step("Check status code and response text after creating a courier")
    public void checkCourierCreate(Response response) {
        response
                .then()
                .assertThat()
                .statusCode(201).and()
                .body("ok", equalTo(true));
    }

    @Step("Check status code and message after creating two identical couriers")
    public void checkIdenticalCourierCreate(Response response) {
        response
                .then()
                .assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Step("Check status code and message after creating a courier without login or password")
    public void checkNoLoginOrPasswordCourierCreate(Response response) {
        response
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Step("Login the courier")
    public Response loginCourier(CourierLoginData courierLoginData) {
        return given()
                .spec(baseRequestSpec)
                .body(courierLoginData)
                .when()
                .post(API_V1_COURIER + LOGIN_COURIER);
    }

    @Step("Check status code and response after successful login")
    public void checkCourierLogin(Response response) {
        response
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());
    }

    @Step("Check status code and message after login without login or password")
    public void checkLoginWithNoLoginOrPassword(Response response) {
        response
                .then()
                .assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Step("Check status code and message after login with wrong login or password")
    public void checkLoginWithWrongLoginOrPassword(Response response) {
        response
                .then()
                .assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));
    }

    public void deleteCourier(int courierId) {
        given()
                .spec(baseRequestSpec)
                .when()
                .delete(API_V1_COURIER + "/" + courierId);
    }

}