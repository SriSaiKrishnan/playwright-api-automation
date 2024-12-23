package com.qa.api;


import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class GetAPITest {

    Playwright playwright;
    APIRequest apiRequest;
    APIRequestContext apiRequestContext;

    @BeforeSuite
    public void setUp(){
        playwright = Playwright.create();
        apiRequest = playwright.request();
        apiRequestContext = apiRequest.newContext();
    }


    @Test
    public void getAPI(){
        APIResponse apiResponse = apiRequestContext.get("https://gorest.co.in/public/v2/users");
        int statusCode = apiResponse.status();
        Assert.assertEquals(statusCode,200);
    }

    @AfterSuite
    public void tearDown(){
        playwright.close();
    }

}
