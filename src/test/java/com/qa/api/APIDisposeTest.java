package com.qa.api;

import com.microsoft.playwright.*;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;

public class APIDisposeTest {

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
    public void getUserApiTest() throws IOException {
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = apiResponse.status();
        Assert.assertEquals(statusCode, 200);
        Assert.assertEquals(apiResponse.ok(), true);

        String statusText = apiResponse.statusText();
        System.out.println(statusText);

        System.out.println();

        System.out.println("---------Print api response with plain text----------");
        System.out.println(apiResponse.text());

        //Calling the dispose method will dispose the api response body
        try{
            apiResponse.dispose();
        }catch (PlaywrightException e){
            System.out.println("Disposed the API Response Body");
        }

        APIResponse apiResponse1 = apiRequestContext.get("https://reqres.in/api/users/2");
        System.out.println(apiResponse1.status());
        System.out.println(apiResponse1.statusText());
        System.out.println(apiResponse1.text());

        //Also, we can dispose the response body in context level as well which will dispose the response body for all the request

        apiRequestContext.dispose();

        System.out.println(apiResponse.text());
        System.out.println(apiResponse1.text());

    }

    @AfterTest
    public void tearDown(){
        playwright.close();
    }


}
