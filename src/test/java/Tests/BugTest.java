package Tests;

import files.ReuseableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.*;

class BugTest {
     public static void main(String[] args){
         RestAssured.baseURI = "https://sethrushali12.atlassian.net";
        String response =  given().log().all().header("Content-Type", "application/json")
                 .header("Authorization", "Basic c2V0aC5ydXNoYWxpMTJAZ21haWwuY29tOkFUQVRUM3hGZkdGMEFWR3JtRUFYaXV1VlhfLXp2dFVlNWZnU3FKdVhnM3FfU1YxaGU3N091bXlqeExOZGo2STNfSTExN3FTc085T1VJNklWRmNZcDRjQjNDc083VjFBWnFtcTgwM0YtQ2NmVXE0dXhfSm9IRFFOeUw1bHphT1lmWXNXbkRES3FKWENIbVBCT1lZd3RSOF9aZTlUNVJKNjV5NGk3UkdSVk5feHUxbHlybUlURXVXdz1BOTg2NTA1Mw==")
                 .body("{\n" +
                         "    \"fields\": {\n" +
                         "       \"project\":\n" +
                         "       {\n" +
                         "          \"key\": \"RUS\"\n" +
                         "       },\n" +
                         "       \"summary\": \"Website not working coming from RestAssured\",\n" +
                         "       \"issuetype\": {\n" +
                         "          \"name\": \"Bug\"\n" +
                         "       }\n" +
                         "   }\n" +
                         "}")
                 .when().post("/rest/api/3/issue")
                 .then().log().all().assertThat().statusCode(201)
                 .extract().response().asString();

        JsonPath js = ReuseableMethods.rawToJson(response);
        String bugID = js.getString("id");

        //add attachment
         given().log().all()
                 .header("Authorization", "Basic c2V0aC5ydXNoYWxpMTJAZ21haWwuY29tOkFUQVRUM3hGZkdGMEFWR3JtRUFYaXV1VlhfLXp2dFVlNWZnU3FKdVhnM3FfU1YxaGU3N091bXlqeExOZGo2STNfSTExN3FTc085T1VJNklWRmNZcDRjQjNDc083VjFBWnFtcTgwM0YtQ2NmVXE0dXhfSm9IRFFOeUw1bHphT1lmWXNXbkRES3FKWENIbVBCT1lZd3RSOF9aZTlUNVJKNjV5NGk3UkdSVk5feHUxbHlybUlURXVXdz1BOTg2NTA1Mw==")
                 .header("X-Atlassian-Token", "no-check").pathParams("key", bugID)
                 .multiPart("file", new File("C:\\Users\\garvt\\RestAssured\\response.json"))
                 .when().post("/rest/api/3/issue/{key}/attachments")
                 .then().assertThat().log().all().statusCode(200);




     }
}
