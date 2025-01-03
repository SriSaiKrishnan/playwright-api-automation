package com.qa.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import com.qa.data.User;
import com.qa.data.Users;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class PutCallWithLombok {

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

        Users users = Users.builder()
                .name("Sai")
                .gender("male")
                .email("saiik10kr@gmail.com")
                .status("active").build();

        // 1.Post  Call
        APIResponse apiResponse = apiRequestContext.post("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer a97ebeda8f119ecde8caa9194375c4e40a800d5a207ae0a15fafcc4c795e2d72")
                        .setData(users));

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

        Assert.assertEquals(users.getName(),actualResponse.getName());
        Assert.assertEquals(users.getGender(),actualResponse.getGender());
        Assert.assertEquals(users.getEmail(),actualResponse.getEmail());
        Assert.assertEquals(users.getStatus(),actualResponse.getStatus());
        Assert.assertNotNull(actualResponse.getId());

        // 2. Put Call
        users.setEmail("saiik11kr@gmail.com");
        users.setStatus("inactive");

        String userID = actualResponse.getId();

        APIResponse apiPutResponse = apiRequestContext.put("https://gorest.co.in/public/v2/users/"+userID,
                RequestOptions.create()
                        .setHeader("Content-Type","application/json")
                        .setHeader("Authorization","Bearer a97ebeda8f119ecde8caa9194375c4e40a800d5a207ae0a15fafcc4c795e2d72")
                        .setData(users));

        Assert.assertEquals(apiPutResponse.status(),200);
        Assert.assertEquals(apiPutResponse.statusText(),"OK");

        String putResponse = apiPutResponse.text();
        System.out.println(putResponse);

        //deserialize
        ObjectMapper putObjectMapper = new ObjectMapper();
        User putActualResponse = putObjectMapper.readValue(putResponse,User.class);

        System.out.println(putActualResponse.getId());
        System.out.println(putActualResponse.getName());
        System.out.println(putActualResponse.getGender());
        System.out.println(putActualResponse.getEmail());
        System.out.println(putActualResponse.getStatus());

        Assert.assertEquals(users.getName(),putActualResponse.getName());
        Assert.assertEquals(users.getGender(),putActualResponse.getGender());
        Assert.assertEquals(users.getEmail(),putActualResponse.getEmail());
        Assert.assertEquals(users.getStatus(),putActualResponse.getStatus());
        Assert.assertNotNull(putActualResponse.getId());

    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }


}
