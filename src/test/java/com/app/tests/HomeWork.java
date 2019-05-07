package com.app.tests;

import com.app.utlities.BookITRestUtility;
import com.google.gson.Gson;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import com.app.pojos.Room;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static org.hamcrest.Matchers.is;

public class HomeWork {
        Response response;

        @BeforeClass
        public static void setup() {
            RestAssured.baseURI = "https://cybertek-reservation-api-qa.herokuapp.com";
        }

        @Test
        public void verifyWrongRespondCode() {

            response = given().log().all().
                    queryParam("email", "teacherva55@gmail.com").
                    queryParam("password", "maxpayne!").
                    get("/sign");
            response.then().log().all().assertThat().statusCode(422);
            response.then().assertThat().body(is("requested email resource was not found."));

        }

        @Test
        public void compareTokenFromTwoUsers() {

            response = given().log().all().
                    queryParam("email", "teacherva5@gmail.com").
                    queryParam("password", "maxpayne").
                    when().get("/sign");

            response.then().assertThat().statusCode(200);
            String token1 = response.jsonPath().get("accessToken");

            response = given().log().all().
                    queryParam("email", "soslan15@gmail.com").
                    queryParam("password", "soslan1992").
                    when().get("/sign");

            response.then().assertThat().statusCode(200);
            String token2 = response.jsonPath().get("accessToken");

            assertThat(token1, Matchers.is(not(token2)));
            Assert.assertNotEquals(token1, token2);
        }

        @Test
        public void noDuplicatedInAllRooms() {

            String token = BookITRestUtility.getTeacherToken();

            response = given().log().all().header("Authorization", token).
                    when().get("/api/rooms");

            JsonPath jsonPath = response.jsonPath();

            List<String> result = jsonPath.getList("id");

            Set<String> result1 = new HashSet<>(result);

            assertThat(result.size(), equalTo(result1.size()));

        }

        @Test
        public void getAllRoomsVerifyBody() {
            Room r = new Room();
            String token = BookITRestUtility.getTeacherToken();

            given().log().all().header("Authorization", token).
                    pathParam("room-name", "yale").
                    when().get("/api/rooms/{room-name}");

            String myJsonString = "{\"id\":\"113\"," +
                    "\"name\":\"yale\"," +
                    "\"description\":\"lux et veritas\"," +
                    "\"capacity\":\"6\"," +
                    "\"withTV\":\"true\"," +
                    "\"withWhiteBoard\":\"false\"}";


            Gson gson = new Gson();
            Room resultRoom = gson.fromJson(myJsonString, Room.class);
            System.out.println("Printing the new room object: " + resultRoom.toString());

            String idExpected = "113";
            String nameExpected = "yale";
            String descriptionExpected = "lux et veritas";
            String capacityExpected = "6";
            String withTVExpected = "true";
            String withWhiteBoardExpected = "false";

            assertThat(idExpected, is(resultRoom.getId()));
            assertThat(nameExpected, is(resultRoom.getName()));
            assertThat(descriptionExpected, is(resultRoom.getDescription()));
            assertThat(capacityExpected, is(resultRoom.getCapavity()));
            assertThat(withTVExpected, is(resultRoom.getWithTV()));
            assertThat(withWhiteBoardExpected, is(resultRoom.getWithWhiteBoard()));
        }

        @Test
        public void verifyIfNewTeamCreated() {

            String token = BookITRestUtility.getTeacherToken();

            response = given().log().all().header("Authorization", token).
                    when().get("/api/teams");

            JsonPath jsonPath = response.jsonPath();


            //checking how many teams was created before
            List<String> allTeamsBefore = jsonPath.getList("id");
            int allTeamsCountBefore = allTeamsBefore.size();
            System.out.println(allTeamsCountBefore + " teams was created Before");

            //creating new team
            String teamName = "Lola1";
            String batch = "8";

            given().log().all().header("Authorization", token).
                    queryParam("campus-location", "VA").
                    queryParam("batch-number", batch).
                    queryParam("team-name", teamName).
                    when().post("/api/teams/team").
                    then().statusCode(201).
                    body(is("team " + teamName + " has been added to the batch " + batch + "."));

            //verifying how many teams were created

            List<String> allTeamsAfter = jsonPath.getList("id");
            int allTeamsCountAfter = allTeamsBefore.size();
            System.out.println(allTeamsCountAfter + " teams was created After");

        }

        @Test
        public void verifyCamousesIllinois(){

            String token = BookITRestUtility.getTeacherToken();

            given().log().all().header("Authorization", token).
                    pathParam("campus-location", "IL").
                    when().get("/api/campuses/{campus-location}").
                    then().assertThat().statusCode(200);
        }
    }

