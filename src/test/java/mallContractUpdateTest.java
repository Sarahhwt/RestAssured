import io.restassured.http.ContentType;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class mallContractUpdateTest {
    @BeforeClass
    public static void testBefore(){
        useRelaxedHTTPSValidation();
    }
    //合同状态（0:取消1:待审核2:审核通过3:已生效4:审核不通过5:异常关闭6:已完成）
    @Test
    public void testUpdate0(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("contract_id","1950632");
        map.put("earnest_money","1.01");
        map.put("status","2");

        given()
                .contentType(ContentType.JSON)
                .body(map)
        .when()
                .post("http://10.2.1.12:51000/contract/update").prettyPeek()
        .then()
                .statusCode(200);
    }

    @Test
    public void testUpdate1(){
        given().param("contract_id","1950632").param("earnest_money","1.01").param("status","2")
        .post("http://10.2.1.12:51000/contract/update").prettyPeek()
        .then().statusCode(200);
    }
    @Test
    public void testUpdate2(){
        given().queryParam("contract_id","1950632")
                .queryParam("earnest_money","1.01")
                .queryParam("status","2")
        .when().post("http://10.2.1.12:51000/contract/update").prettyPeek()
        .then().statusCode(200);
    }
    @Test
    public void testUpdate3(){
        //成功
        given().body("{\"contract_id\":\"1950632\",\"earnest_money\":\"1.01\",\"status\":\"2\"}")
                .when().post("http://10.2.1.12:51000/contract/update").prettyPeek();

    }

    @Test
    public void testUpdate4(){
        given().param("contract_id","1")
                .param("ssu_id","286")
                .param("earnest_money","4155239")
                .param("status","测试")
                .param("approval_desc","[278934]")
                .when().post("")
                .then().body("1",equalTo(1));
    }

}
