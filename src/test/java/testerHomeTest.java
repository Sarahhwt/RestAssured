import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class testerHomeTest {
    @BeforeClass
    public static void setup(){
        useRelaxedHTTPSValidation();
        RestAssured.baseURI = "https://testerhome.com";
        RestAssured.proxy("127.0.0.1",8080);

    }
    @Test
    public void testHtml(){

        given().queryParam("q", "appium")
        .when().get("https://testerhome.com/search").prettyPeek()
        .then().statusCode(200)
                .body("html.head.title", equalTo("appium · 搜索结果 · TesterHome"));

    }

    @Test
    public void testRestful(){
        given().when().get("https://testerhome.com/api/v3/topics.json").prettyPeek()
        .then().statusCode(200)
                .body("topics.title",hasItem("号外号外，MTSC2018 开发大会议题继续征集！"))
                .body("topics.title[0]",equalTo("号外号外，MTSC2018 开发大会议题继续征集！"))
                .body("topics.id[0]",equalTo(11402))
                .body("topics.findAll{topic->topic.id ==11402}.title",hasItem("号外号外，MTSC2018 开发大会议题继续征集！"))
        .body("topics.title.size()",equalTo(23))
        ;
    }

    @Test
    public void testTestHomeJsonSingle(){
        given().when().get("https://testerhome.com/api/v3/topics/10254.json").prettyPeek()
                .then().statusCode(200)
                .body("topic.title",equalTo("优质招聘汇总"));
    }

    @Test
    public void testXml(){
//        given().when().get("http://127.0.0.1:8000/hwttest.xml").prettyPeek()
//        .then().statusCode(200).body("shopping.category.item.name[2]",equalTo("Paper"));

//        given().when().get("http://127.0.0.1:8000/hwttest.xml").prettyPeek()
//                .then().statusCode(200).body("shopping.category[1].item.name[0]",equalTo("Paper"));

//        given().when().get("http://127.0.0.1:8000/hwttest.xml").prettyPeek()
//                .then().statusCode(200).body("shopping.category.size()",equalTo(3));

//        String name = given().when().get("http://127.0.0.1:8000/hwttest.xml").prettyPeek()
//                .then().statusCode(200)
//                .body("shopping.category.find{it.@type=='present'}.item.name",equalTo("Kathryn's Birthday"))
//                .body("**.find{it.price==200}.name",equalTo("Kathryn's Birthday"))
//        .extract().path("shopping.category.item.name[2]");
//        System.out.print(name);

        Response response = given().when().get("http://127.0.0.1:8000/hwttest.xml").prettyPeek()
                .then().statusCode(200)
                .body("shopping.category.find{it.@type=='present'}.item.name",equalTo("Kathryn's Birthday"))
                .body("**.find{it.price==200}.name",equalTo("Kathryn's Birthday"))
                .extract().response();
        System.out.print(response.statusLine());
    }

    @Test
    public void testRestfulGlobal() {
        RestAssured.
        given().when().get("/api/v3/topics.json").prettyPeek()
                .then().statusCode(200)
                .body("topics.title", hasItem("号外号外，MTSC2018 开发大会议题继续征集！"));
    }

    @Test
    public void testProxy() {
        given()
//                .proxy("127.0.0.1",8080)
        .when()
                .get("/api/v3/topics.json").prettyPeek()
        .then()
                .statusCode(200)
                .body("topics.title", hasItem("号外号外，MTSC2018 开发大会议题继续征集！"));
    }

    @Test
    public void testPostJson(){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("id",6040);
        data.put("title","通过代理安装 appium");
        data.put("name","hwt");

        given()
                .contentType(ContentType.JSON)
                .body(data)
        .when()
                .post("http://www.baidu.com")
        .then()
                .statusCode(200);
    }

    @Test
    public void testPostJson2(){
        HashMap<String, Object> data = new HashMap<String, Object>();
        data.put("id",6040);
        data.put("title","通过代理安装 appium");
        data.put("name","hwt");

        HashMap<String, Object> root = new HashMap<String, Object>();
        root.put("topic",data);

        given()
                .contentType(ContentType.JSON)
                .body(root)
                .when()
                .post("http://www.baidu.com").prettyPeek()
        .then().time(lessThan(1000L));
    }

    @Test
    public void multiApi(){
        String name = given().get("https://testerhome.com/api/v3/topics/6040.json").prettyPeek()
                .then().statusCode(200)
                .extract().path("topic.user.name");

        System.out.println(name);

        given().queryParam("q",name)
                .cookie("uid",name)
                .when().get("http://testerhome.com/search")
                .then().statusCode(200)
                .body(containsString(name));
    }

    @Test
    public void multiApi1(){
        Response response = given().get("https://testerhome.com/api/v3/topics/6040.json").prettyPeek()
                .then().statusCode(200)
                .extract().response();
        String name = response.path("topic.user.name");
        Integer uid = response.path("topic.user.id");

        System.out.println(name);
        System.out.println(uid);

        given().queryParam("q",name)
                .cookie("name",name)
                .cookie("uid",uid)
        .when().get("/search")
        .then().statusCode(200)
                .body(containsString(name));
    }

    @Test
    public void testSpec(){
        //通用的请求封装，通用的结果断言
//        RestAssured.requestSpecification
        ResponseSpecification rs = new ResponseSpecBuilder(){}.build();
        rs.statusCode(200);
        rs.body(not(containsString("error")));
        rs.time(lessThan(1500L));

        given().get("/api/v3/topics/6040.json")
                .then().spec(rs);
    }

    @Test
    public void testFilter(){
        //filter自定义认证、session管理
        // 记录所有的网络请求
        // 可以实现自动解密
        //filter可以应用于所有全局请求
        //
//        RestAssured.filters()
    }

    @Test
    public void testJsonSchema(){
        //
        //
    }
}