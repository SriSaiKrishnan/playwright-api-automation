package com.qa.api;

import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.HttpHeader;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;

public class APIResponseHeadersTest {

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
        //using map
       Map<String,String> headersMap = apiResponse.headers();
       headersMap.forEach((k,v) -> System.out.println(k + " : " + v));
       Assert.assertEquals(headersMap.get("content-type"), "application/json; charset=utf-8");
        System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++++");
       //using list
        List<HttpHeader> headerList = apiResponse.headersArray();
        for (HttpHeader header : headerList){
            System.out.println(header.name + " : " + header.value);
        }

    }

        @AfterTest
    public void tearDown(){
        playwright.close();
    }

}
