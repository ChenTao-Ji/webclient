package com.example.webclient.example.service;

import reactor.core.publisher.Mono;

public interface ExampleService {

    String getHotCityList();

    /**
     * 携带cookie
     *
     * @return
     */
    String testWithCookie();

    /**
     * 携带basic auth
     *
     * @return
     */
    String testWithBasicAuth();

    /**
     *设置全局user-agent
     *
     * @return
     */
    String testWithHeaderFilter();

    /**
     *使用placeholder传递参数
     *
     * @return
     */
    String testUrlPlaceholder();

    /**
     * 使用uriBuilder传递参数
     *
     * @return
     */
    String testUrlBiulder();

    /**
     *post表单
     *
     * @return
     */
    String testFormParam();

    /**
     * 使用bean来post
     *
     * @return
     */
    String testPostJson();

    /**
     * 直接post raw json
     *
     * @return
     */
    String testPostRawJson();

    /**
     * post二进制--上传文件
     *
     * @return
     */
    String testUploadFile();

    /**
     *下载图片
     *
     * @return
     */
    String testDownloadImage();

    /**
     *下载文件
     *
     * @return
     */
    String testDownloadFile();

    /**
     * 错误处理
     *
     * @return
     */
    String testRetrieve4xx();

}
