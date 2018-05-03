import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
public class mallContractCreateTest {
    @Test
    public void testCreate(){
        given().param("city_id","1")
                .param("sale_area_id","97")
                .param("company_id","4620736")
                .param("warehouse_id","测试")
                .param("ssu_ids","[278934]")
        .when().post("")
        .then();
    }
}
