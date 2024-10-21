package Tests;

import files.Payload;
import files.ReuseableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class DynamicJson {

    @Test(dataProvider ="BookData")
    public void addBook(String isbn, String aisle) throws IOException {
        // payload coming from response.json/external file

        baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("C:\\Users\\garvt\\RestAssured\\response.json"))))
                .when().post("Library/Addbook.php")
                .then().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReuseableMethods.rawToJson(response);
        String ID = js.get("ID");
        System.out.println(ID);

    }

    @Test(dataProvider="deleteData")
    public void deleteBook(String id){ // id values hardcoded in dataprovider annotation
        baseURI = "http://216.10.245.166";
        given().header("Content-Type", "application/json")
                .body(Payload.deleteBook(id))
                .when().post("Library/DeleteBook.php")
                .then().assertThat().statusCode(200).body("msg", equalTo("book is successfully deleted"));


    }


    @DataProvider(name="BookData")
    public Object[][] getData(){
        return new Object[][]{{"vvf", "654"},{"gfd", "432"},{"ddssa", "987"}};
    }

    @DataProvider(name="deleteData")
    public Object[] deleteData(){
        return new Object[]{"vvf654","gfd432" , "ddssa987"};
    }








}
