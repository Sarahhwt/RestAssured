import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class tesDay01 {
    @BeforeClass
    public static void setSSL() {
        //设置默认跳过ssl验证
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    public void gettest(){
        //1
        get("https://testerhome.com/api/v3/topics.json?limit=2&offset=0&type=last_actived");
/*
        //2
        Map<String,Object> map1 = new HashMap<String,Object>();
        map1.put("limit",2);
        map1.put("offset","0");
        map1.put("type","last_actived");

        //https://testerhome.com/api/v3/topics.json?limit=2&offset=0&type=last_actived
        given().params(map1).get("https://testerhome.com/api/v3/topics.json").prettyPeek();

        //3
        //https://testerhome.com/topics/12192
        Map<String,Object> map2 = new HashMap<String,Object>();
        map2.put("topics","topics");
        map2.put("topicid",12192);
        get("https://testerhome.com/{topics}/{topicid}",map2).prettyPeek();

        //4
        get("https://testerhome.com/{topics}/{topicid}","topics",12192).prettyPeek();*/
    }

    @Test
	public void posttest() {
        //1
//        given().param("user[login]", "test@qq.com").param("user[password]", "111111").param("user[remember_me]", 0)
//                .post("https://testerhome.com/account/sign_in").prettyPeek();

        //2
//		Map<String, String> mapPost = new HashMap<String, String>();
//            mapPost.put("user[login]", "test@qq.com");
//            mapPost.put("user[password]", "111111");
//            mapPost.put("user[remember_me]", "0");
//		 given().params(mapPost).post("https://testerhome.com/account/sign_in").prettyPeek();
//
        //3
        given().body("{ \"message\" : \"hello world\"}").post("https://testerhome.com/api/v3/topics.json").prettyPeek();

        //4
        File file = new File("/User/haowenting/1.txt");
        given().body(file).post("https://testerhome.com/api/v3/topics.json").prettyPeek();
//
//        //cookie
//        given().cookie("username","xxxx").get("https://yunshanmeicai.com/getbyrout");
//
//        given().header("username","xxxx").get("https://yunshanmeicai.com/getbyrout");
//
//        //编码
//        given().urlEncodingEnabled(false).params("user","社区").params("pass","111").get("https://yunshanmeicai.com");
//
//        //上传文件
//        given().multiPart(file).post("https://yunshanmeicai.com/111");

	}

    @Test
    public void jsonPathtest(){
        Response response = get("https://testerhome.com/api/v3/topics.json?limit=2&offset=0&type=last_actived");
        List<Object> list = response.jsonPath().getList("topics");
        System.out.println(list.size());
        System.out.println(response.jsonPath().getString("topics[0].id"));
    }

}
