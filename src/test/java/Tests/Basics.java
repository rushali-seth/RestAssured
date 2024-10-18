package Tests;

import files.Payload;
import files.ReuseableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args){

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        //add place
        String response = given().log().all().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(Payload.addPlace()).when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

       // System.out.println(response);
        JsonPath js = new JsonPath(response);
        String placeID = js.getString("place_id");
        System.out.println(placeID);

        //update place
        String newAddress = "70 winter walkk, CA";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\"" + placeID + "\",\n" +
                        "\"address\":\"" + newAddress +"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n")
                .when().put("maps/api/place/update/json")
                .then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //get Place
        String getPlaceAddress = given().log().all().queryParam("key", "qaclick123")
                .queryParam("place_id",placeID)
                .when().get("maps/api/place/get/json")
                .then().assertThat().log().all().statusCode(200).extract().response().asString();

        JsonPath js1 = ReuseableMethods.rawToJson(getPlaceAddress);
        String actualAdd = js.getString("address");
        System.out.println(actualAdd);
        Assert.assertEquals(actualAdd, newAddress);



    }
}
