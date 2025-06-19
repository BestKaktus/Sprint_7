import io.qameta.allure.Description;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CourierLoginTests {

    private String login = RandomStringUtils.random(7);
    private String password = RandomStringUtils.random(8);
    private String firstName = RandomStringUtils.random(9);
    private String noLogin = "";
    private String noPassword = "";
    private String wrongLogin = "wrongLogin";
    private String wrongPassword = "wrongPassword";

    CourierApi courierApi = new CourierApi();

    @BeforeEach
    public void createCourier() {
        CourierCreateData courierCreateData = new CourierCreateData(login, password, firstName);
        courierApi.createCourier(courierCreateData);
    }

    @Test
    @DisplayName("Login courier with valid data")
    @Description("Positive test of login a courier with valid data")
    public void loginWithValidDataTest() {
        CourierLoginData courierLoginData = new CourierLoginData(login, password);
        courierApi.checkCourierLogin(courierApi.loginCourier(courierLoginData));
    }

    @Test
    @DisplayName("Error when logging a courier in with no login")
    @Description("Negative test of failing to log in with no login")
    public void loginWithNoLoginTest() {
        CourierLoginData courierLoginData = new CourierLoginData(noLogin, password);
        courierApi.checkLoginWithNoLoginOrPassword(courierApi.loginCourier(courierLoginData));
    }

    @Test
    @DisplayName("Error when logging a courier in with no password")
    @Description("Negative test of failing to log in with no password")
    public void loginWithNoPasswordTest() {
        CourierLoginData courierLoginData = new CourierLoginData(login, noPassword);
        courierApi.checkLoginWithNoLoginOrPassword(courierApi.loginCourier(courierLoginData));
    }

    @Test
    @DisplayName("Error when logging a courier in with no login nor password")
    @Description("Negative test of failing to log in with no login nor password")
    public void loginWithNoLoginNorPasswordTest() {
        CourierLoginData courierLoginData = new CourierLoginData(noLogin, noPassword);;
        courierApi.checkLoginWithNoLoginOrPassword(courierApi.loginCourier(courierLoginData));
    }

    @Test
    @DisplayName("Error when logging a courier in with wrong login")
    @Description("Negative test of failing to log in with wrong login")
    public void loginWithWrongLoginTest() {
        CourierLoginData courierLoginData = new CourierLoginData(wrongLogin, password);
        courierApi.checkLoginWithWrongLoginOrPassword(courierApi.loginCourier(courierLoginData));
    }

    @Test
    @DisplayName("Error when logging a courier in with wrong password")
    @Description("Negative test of failing to log in with wrong password")
    public void loginWithWrongPasswordTest() {
        CourierLoginData courierLoginData = new CourierLoginData(login, wrongPassword);
        courierApi.checkLoginWithWrongLoginOrPassword(courierApi.loginCourier(courierLoginData));
    }

    @Test
    @DisplayName("Error when logging a courier in with wrong login and password")
    @Description("Negative test of failing to log in with wrong login and password")
    public void loginWithWrongLoginAndPasswordTest() {
        CourierLoginData courierLoginData = new CourierLoginData(wrongLogin, wrongPassword);
        courierApi.checkLoginWithWrongLoginOrPassword(courierApi.loginCourier(courierLoginData));
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