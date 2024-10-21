package Tests;

import files.Payload;
import files.ReuseableMethods;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args){
        JsonPath js = ReuseableMethods.rawToJson(Payload.coursePrice());
        System.out.println(  js.getInt("courses.size()"));
        int count = js.getInt("courses.size()");
        System.out.println( js.getInt("dashboard.purchaseAmount"));
        System.out.println( js.getString("courses[0].title"));
        System.out.println( js.getString("courses[2].title"));

        for(int i=0;i<js.getInt("courses.size()"); i++){
           String titles =  js.get("courses["+ i +"].title");
            System.out.println(titles);
            System.out.println( js.get("courses["+i+"].price").toString());
            if(titles.equalsIgnoreCase("RPA")){
               int copies =  js.get("courses["+i+"].copies");
               System.out.println(copies);
               break;
            }

        }




    }
}
