import files.Payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {
    public static void main(String[] args) {
        //Validate if AddPlace API is working as expected
        //Add place -> Update Place with New Address -> Get Place to validate if New Address is present in response


        //given - All input details
        //when - Submit the api/resource, http method
        //Then - Validate the response
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(Payload.addPlace())
                .when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
                .header("Server", "Apache/2.4.41 (Ubuntu)").extract().response().asString();

        System.out.println(response);
        JsonPath js = new JsonPath(response); //For parsing Json
        String placeId = js.getString("place_id");

        System.out.println(placeId);
    }

}
