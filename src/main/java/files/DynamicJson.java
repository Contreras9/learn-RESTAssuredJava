package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

public class DynamicJson {

    @Test(dataProvider = "BooksData")
    public void addBook() {
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().log().all().header("Content-Type", "application/json")
                .body(Payload.addBook("adsfs", "6464565"))
                .when().post("/Library/Addbook.php")
                .then().log().all().assertThat().statusCode(200)
                .extract().response().asString();
        JsonPath jsonPath = ReusableMethods.rawToJson(response);
        String id = jsonPath.getString("ID");
        System.out.println(id);
    }

    @DataProvider(name = "BookData")
    public Object[][] getData() {
        return new Object[][] {{"ojfwty", "9363"},{"cwetee", "4253"},{"okmfet", "533"}};
    }
}
