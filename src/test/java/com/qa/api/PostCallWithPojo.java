package com.qa.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.data.User;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class PostCallWithPojo {

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

        User user = new User("Sai","male","SaiiKk@gmail.com", "active");

        APIResponse apiResponse = apiRequestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer a97ebeda8f119ecde8caa9194375c4e40a800d5a207ae0a15fafcc4c795e2d72")
                        .setData(user));

        Assert.assertEquals(apiResponse.status(),201);
        Assert.assertEquals(apiResponse.statusText(),"Created");

        String response = apiResponse.text();
        System.out.println(response);

        //deserialize
        ObjectMapper objectMapper = new ObjectMapper();
        User actualResponse = objectMapper.readValue(response,User.class);

        System.out.println(actualResponse.getId());
        System.out.println(actualResponse.getName());
        System.out.println(actualResponse.getGender());
        System.out.println(actualResponse.getEmail());
        System.out.println(actualResponse.getStatus());

        Assert.assertEquals(user.getName(),actualResponse.getName());
        Assert.assertEquals(user.getGender(),actualResponse.getGender());
        Assert.assertEquals(user.getEmail(),actualResponse.getEmail());
        Assert.assertEquals(user.getStatus(),actualResponse.getStatus());
        Assert.assertNotNull(actualResponse.getId());

    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }

}
