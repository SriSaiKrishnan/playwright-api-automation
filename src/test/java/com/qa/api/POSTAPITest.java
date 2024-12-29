package com.qa.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class POSTAPITest {

    Playwright playwright;
    APIRequest apiRequest;
    APIRequestContext apiRequestContext;

    @BeforeTest
    public void setUp(){
        playwright = Playwright.create();
        apiRequest = playwright.request();
        apiRequestContext = apiRequest.newContext();
    }

    @Test
    public void createUser() throws IOException {

        Map<String,Object> data = new HashMap<>();
        data.put("name","sai");
        data.put("email","s1248ai@test.com");
        data.put("gender","male");
        data.put("status","active");

        APIResponse apiResponse = apiRequestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer a97ebeda8f119ecde8caa9194375c4e40a800d5a207ae0a15fafcc4c795e2d72")
                        .setData(data));

        System.out.println("-------Print api json response--------------");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = jsonResponse.toPrettyString();
        System.out.println(jsonPrettyResponse);

        Assert.assertEquals(apiResponse.status(),201);
        Assert.assertEquals(apiResponse.statusText(),"Created");

        String userID = jsonResponse.get("id").asText();
        System.out.println("User ID " + userID);

        //Validate with Get Call

        APIResponse apiResponse1 = apiRequestContext.get("https://gorest.co.in/public/v2/users/"+userID,
                RequestOptions.create()
                        .setHeader("Authorization","Bearer a97ebeda8f119ecde8caa9194375c4e40a800d5a207ae0a15fafcc4c795e2d72"));
        Assert.assertEquals(apiResponse1.status(),200);
        Assert.assertEquals(apiResponse1.statusText(),"OK");
    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }


}
