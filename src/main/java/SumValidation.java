import files.Payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SumValidation {

    @Test
    public void sumOfCourse() {
        JsonPath jsonPath = new JsonPath(Payload.coursePrice());
        int count = jsonPath.getInt("courses.size()");
        int sum = 0;

        for (int i = 0; i < count; i++) {
            int price = jsonPath.getInt("courses["+ i +"].price");
            int copies = jsonPath.getInt("courses["+ i +"].copies");
            int amount = price * copies;
            System.out.println(amount);
            sum += amount;
        }
        System.out.println(sum);
        int purchaseAmount = jsonPath.getInt("dashboard.purchaseAmount");
        Assert.assertEquals(sum, purchaseAmount);
    }
}
