import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
    public static void main(String[] args) {

        JsonPath jsonPath = new JsonPath(Payload.coursePrice());
    }
}
