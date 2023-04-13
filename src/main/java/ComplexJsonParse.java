import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {

        JsonPath jsonPath = new JsonPath(Payload.coursePrice());

        //Print number of courses returned by API
        int count = jsonPath.getInt("courses.size()");
        System.out.println(count);

        //Print purchase amount
        int totalAmount = jsonPath.getInt("dashboard.purchaseAmount");
        System.out.println(totalAmount);

        //Print Title of the first course
        String firstCourseTitle = jsonPath.getString("courses[0].title");
        System.out.println(firstCourseTitle);

    }
}
