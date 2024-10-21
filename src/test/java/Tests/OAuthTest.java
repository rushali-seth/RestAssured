package Tests;

import files.Payload;
import files.ReuseableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;

public class OAuthTest {
    public static void main ( String[] args){

      RestAssured.baseURI = "https://rahulshettyacademy.com";
      String response =  given()
                .formParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type", "client_credentials")
                .formParams("scope", "trust")
                .when().log().all()
                .post("oauthapi/oauth2/resourceOwner/token").asString();
        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String accessToken = js.getString("access_token");

        String response1 = given().queryParams("access_token", accessToken)
                .log().all().when().get("oauthapi/getCourseDetails").asString();
        System.out.println(response1);


    }


}
