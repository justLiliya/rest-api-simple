import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

public class ReqresInTest {

    @Test
    public void getuserListTest(){
        when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .statusCode(200)
                .body("data.id", hasItems(7,8, 9, 10, 11, 12),
                        "support.url", equalTo("https://reqres.in/#support-heading"));
    }

    @Test
    public void getSingleUserTest(){
        when()
                .get("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .body("data.id", is(2),
                        "data.first_name", equalTo("Janet"),
                        "data.last_name", equalTo("Weaver"));
    }

    @Test
    public void postCreateUserTest(){
        Faker faker = new Faker();
        String job = faker.gameOfThrones().dragon();
        String name = faker.funnyName().name();
        given().log().all().
                body("{\"name\": \"" + name + "\",\"job\": \"" + job + "\"}")
                .contentType("application/json").
                when().
                post("https://reqres.in/api/users")
                .then()
                .statusCode(201)
                .assertThat().body("name", equalTo(name))
                .assertThat().body("job", equalTo(job));
    }

    @Test
    public void postUpdateUserTest(){
        Faker faker = new Faker();
        String job = faker.harryPotter().character();
        String name = faker.name().firstName();
        given().log().all().
                body("{\"name\": \"" + name + "\",\"job\": \"" + job + "\"}")
                .contentType("application/json").
                when().
                put("https://reqres.in/api/users/2")
                .then()
                .statusCode(200)
                .assertThat().body("name", equalTo(name))
                .assertThat().body("job", equalTo(job));
    }

    @Test
    public void deleteUserTest(){
        given().log().all().
                when().
                delete("https://reqres.in/api/users/5")
                .then()
                .statusCode(204);
    }
}