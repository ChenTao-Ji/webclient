package com.example.webclient.example.controller;

import com.example.webclient.example.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/example")
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @GetMapping(value = "/getHotCityList")
    public String getHotCityList() {
        return exampleService.getHotCityList();
    }

    @GetMapping(value = "/testWithCookie")
    public String testWithCookie() {
        return exampleService.testWithCookie();
    }

    @GetMapping(value = "/testWithBasicAuth")
    public String testWithBasicAuth() {
        return exampleService.testWithBasicAuth();
    }

    @GetMapping(value = "/testWithHeaderFilter")
    public String testWithHeaderFilter() {
        return exampleService.testWithHeaderFilter();
    }

    @GetMapping(value = "/testUrlPlaceholder")
    public String testUrlPlaceholder() {
        return exampleService.testUrlPlaceholder();
    }

    @GetMapping(value = "/testUrlBiulder")
    public String testUrlBiulder() {
        return exampleService.testUrlBiulder();
    }

    @PostMapping(value = "/testFormParam")
    public String testFormParam() {
        return exampleService.testFormParam();
    }

    @PostMapping(value = "/testPostJson")
    public String testPostJson() {
        return exampleService.testPostJson();
    }

    @PostMapping(value = "/testPostRawJson")
    public String testPostRawJson() {
        return exampleService.testPostRawJson();
    }

    @PostMapping(value = "/testUploadFile")
    public String testUploadFile() {
        return exampleService.testUploadFile();
    }

    @GetMapping(value = "/testDownloadImage")
    public String testDownloadImage() {
        return exampleService.testDownloadImage();
    }

    @GetMapping(value = "/testDownloadFile")
    public String testDownloadFile() {
        return exampleService.testDownloadFile();
    }

    @GetMapping(value = "/testRetrieve4xx")
    public String testRetrieve4xx() {
        return exampleService.testRetrieve4xx();
    }

}
