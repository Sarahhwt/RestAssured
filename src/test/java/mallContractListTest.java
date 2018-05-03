import org.junit.Test;

        import static io.restassured.RestAssured.*;
        import static io.restassured.matcher.RestAssuredMatchers.*;
        import static org.hamcrest.Matchers.*;
public class mallContractListTest {
    @Test
    public void testCreate(){
        given().param("contract_id","1")
                .param("ssu_id","286")
                .param("earnest_money","4155239")
                .param("status","测试")
                .param("approval_desc","[278934]")
                .when().post("")
                .then().body("",equalTo(1));
    }
}