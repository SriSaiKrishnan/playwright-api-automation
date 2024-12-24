package com.qa.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.RequestOptions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.Map;

public class GetAPITest {

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
    public void getSpecificUserApiTest() throws IOException {
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users",
                RequestOptions.create()
                        .setQueryParam("gender","male")
                        .setQueryParam("status","active"));

        System.out.println("-------Print api json response--------------");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = jsonResponse.toPrettyString();
        System.out.println(jsonPrettyResponse);
    }


    @Test
    public void getUserApiTest() throws IOException {
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = apiResponse.status();
        Assert.assertEquals(statusCode,200);
        Assert.assertEquals(apiResponse.ok(),true);

        String statusText = apiResponse.statusText();
        System.out.println(statusText);

        System.out.println();

        System.out.println("---------Print api response with plain text----------");
        System.out.println(apiResponse.text());

        System.out.println("-------Print api json response--------------");
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(apiResponse.body());
        String jsonPrettyResponse = jsonResponse.toPrettyString();
        System.out.println(jsonPrettyResponse);

        System.out.println("----------Print API URL----------------------");
        System.out.println(apiResponse.url());

        System.out.println();

        System.out.println("----------Print Response Header---------------");
        Map<String,String> headersMap = apiResponse.headers();
        System.out.println(headersMap);
        Assert.assertEquals(headersMap.get("content-type"),"application/json; charset=utf-8");

    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }

}
