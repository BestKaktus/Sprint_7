import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CourierCreateTests {

    private String login = RandomStringUtils.random(7);
    private String password = RandomStringUtils.random(8);
    private String firstName = RandomStringUtils.random(9);
    private String noLogin = "";
    private String noPassword = "";

    CourierApi courierApi = new CourierApi();

    @Test
    @DisplayName("Create a courier with valid field data")
    @Description("Positive test of courier creating with valid data")
    public void courierCreatedWithValidDataTest() {
        CourierCreateData courierCreateData = new CourierCreateData(login, password, firstName);
        Response response = courierApi.createCourier(courierCreateData);
        courierApi.checkCourierCreate(response);

    }

    @Test
    @DisplayName("Error when creating two identical couriers")
    @Description("Negative test of no opportunity to create two identical couriers")
    public void createTwoIdenticalCouriersTest() {
        CourierCreateData courierCreateData = new CourierCreateData(login, password, firstName);
        courierApi.createCourier(courierCreateData);
        courierApi.checkIdenticalCourierCreate(courierApi.createCourier(courierCreateData));

    }

    @Test
    @DisplayName("Error when creating a courier with no login")
    @Description("Negative test of no opportunity to create a courier with no login")
    public void createCourierWithNoLoginTest() {
        CourierCreateData courierCreateData = new CourierCreateData(noLogin, password, firstName);
        courierApi.checkNoLoginOrPasswordCourierCreate(courierApi.createCourier(courierCreateData));
    }

    @Test
    @DisplayName("Error when creating a courier with no password")
    @Description("Negative test of no opportunity creating a courier with password missing")
    public void notPossibleToCreateCourierWithPasswordMissingTest() {
        CourierCreateData courierCreateData = new CourierCreateData(login, noPassword, firstName);
        courierApi.checkNoLoginOrPasswordCourierCreate(courierApi.createCourier(courierCreateData));
    }

    @Test
    @DisplayName("Error when creating a courier without login and password")
    @Description("Negative test of no opportunity to create a courier with no login and password")
    public void createCourierWithNoLoginAndPasswordTest() {
        CourierCreateData courierCreateData = new CourierCreateData(noLogin, noPassword, firstName);
        Response response = courierApi.createCourier(courierCreateData);
        courierApi.checkNoLoginOrPasswordCourierCreate(courierApi.createCourier(courierCreateData));
    }

    @AfterEach
    public void deleteCourier() {
        CourierLoginData courierLoginData = new CourierLoginData(login, password);
        Response response = courierApi.loginCourier(courierLoginData);
        if (response.then().extract().statusCode() == 200) {
            int id = response.then().extract().body().path("id");
            courierApi.deleteCourier(id);
        }

    }

}