package pojo.serialize;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializeTest {

    public static void main(String[] args) {

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        AddPlace place = new AddPlace();

        Location l = new Location();
        l.setLat(-38.383494);
        l.setLng(33.427362);
        place.setLocation(l);
        place.setAccuracy(50);
        place.setName("Frontline house");
        place.setPhone_number("(+91) 983 893 3937");
        place.setAddress("29, side layout, cohen 09");
        List<String> myList = new ArrayList<>();
        myList.add("shoe park");
        myList.add("shop");
        place.setTypes(myList);
        place.setWebsite("https://rahulshettyacademy.com");
        place.setLanguage("French-IN");

        Response res = given().log().all().queryParam("key", "qaclick123").body(place)
                .when().post("/maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response();

        String resString = res.asString();
        System.out.println(resString);
    }

}
