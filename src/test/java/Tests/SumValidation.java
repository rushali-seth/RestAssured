package Tests;

import files.Payload;
import files.ReuseableMethods;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {

    @Test
    public void sumOfCourses(){
        int sum = 0;
        JsonPath js = ReuseableMethods.rawToJson(Payload.coursePrice());
        int totalAmount = js.getInt("dashboard.purchaseAmount");
        for(int i=0;i<js.getInt("courses.size()"); i++){
           int price =  js.getInt("courses["+i+"].price");
           int copies =  js.getInt("courses["+i+"].copies");
           int amount = price * copies;
           sum = sum + amount;
           System.out.println(amount);
        }
        Assert.assertEquals(sum, totalAmount );




    }
}
