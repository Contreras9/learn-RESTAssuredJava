package oauth;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class OAuthTest {

    public static void main(String[] args) throws InterruptedException {

        /*
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable notifications");
        options.setCapability(ChromeOptions.CAPABILITY, options);

        System.setProperty("webDriver.chrome.driver", "/Users/hamzahcontreras/Development/Java/SeleniumFundamentals/chromedriver_mac_arm64");
        WebDriver driver = new ChromeDriver(options);
        driver.get("https://accounts.google.com/v3/signin/identifier?opparams=%253Fauth_url%253Dhttps%25253A%25252F%25252Faccounts.google.com%25252Fo%25252Foauth2%25252Fv2%25252Fauth&dsh=S1761732763%3A1683782648873135&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&o2v=2&redirect_uri=https%3A%2F%2Frahulshettyacademy.com%2FgetCourse.php&response_type=code&scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&service=lso&state=verifyfjdss&flowName=GeneralOAuthFlow&continue=https%3A%2F%2Faccounts.google.com%2Fsignin%2Foauth%2Fconsent%3Fauthuser%3Dunknown%26part%3DAJi8hANtIol0ax0YuARU50Zvn7jnt29DD2l5lW4NCiBLS2aHBV-eIWp2o3IPYye8jUGdMLcEVptaNLQf6b6OeVxrW55Vxy5ZQEA0M4Mp5RNuw2iF7FLo90hNknUf2OHQSwgS5OTTi_uRc5NOcxb8Ptp0C-Jbi-xV8bKe-WC9jXdtAEAI873ZVd2diaUwpelkrbeyDlXPfpOpUILReYlErE9HIDUcnU9qq9iCjCnkq89_djZLpjl4PjSjgJcIMH4V2tfTaN6tBPBn1TcfJrpJP-QAGfvqRL55k6f7UYzqAtEHXFPPDIiSNUKdu-Iw83gCg2l2xY_OfVHkgdsWCGjkiG4OLteS2Lbf2khyyllpUqosSBJNLyhEhdyd34pjIOv50ucnUam0qgNIaVnWZIqDda2PV7hnqIIjQ3gUWmOj2EuAMSoG9C29o6V2ZLOTzuX7SSkukMxeHl8jSmvFaIOwfCWLRK5dXbdCEw%26as%3DS1761732763%253A1683782648873135%26client_id%3D692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com%23&app_domain=https%3A%2F%2Frahulshettyacademy.com&rart=ANgoxcfLgkSk67W9ncWwZ7NYHJUe6f6Cy9YUjEtu8JAb-x0TNvHO8oMzkYU73LQx1mu9xRjPbt21xoEX3--AzF-QHhOCGDejew");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("email");
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys("password");
        driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
        Thread.sleep(4000);
        String url = driver.getCurrentUrl();
         */

        String url = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AbUR2VO42qsgAKOU23H3TEukRoHnS6Xt_WUgPfUu0DzmtSdnxq5sf1ZS0_u03zwCIo2yDA&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=none";


        String[] courseTitles = {"Selenium Webdriver Java", "Cypress", "Protractor"};
        String partialCode = url.split("code=")[1];
        String code = partialCode.split("&scope")[0];
        System.out.println(code);

        String accessTokenResponse = given().urlEncodingEnabled(false)
                .queryParams("code", code)
                .queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParams("grant_type", "authorization_code")
                .when().log().all().post("https://www.googleapis.com/oauth2/v4/token").asString();

        JsonPath jsonPath = new JsonPath(accessTokenResponse);
        String accessToken = jsonPath.getString("access_token");

        GetCourse gc = given().queryParam("access_token", accessToken).expect().defaultParser(Parser.JSON)
                .when().get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);

        System.out.println(gc.getLinkedIn());

        System.out.println(gc.getInstructor());

        System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());

        List<Api> apiCourses = gc.getCourses().getApi();

        for (int i = 0; i < apiCourses.size(); i++) {
            if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
                System.out.println(apiCourses.get(i).getPrice());
            }
        }


        ArrayList<String> arrList = new ArrayList<>();
        List<WebAutomation> webAuto = gc.getCourses().getWebAutomation();

        for (int j = 0; j < webAuto.size(); j++) {
            arrList.add(webAuto.get(j).getCourseTitle());
        }

        List<String> expectedList = Arrays.asList(courseTitles);

        Assert.assertTrue(arrList.equals(expectedList));


//        System.out.println(response);
    }
}
