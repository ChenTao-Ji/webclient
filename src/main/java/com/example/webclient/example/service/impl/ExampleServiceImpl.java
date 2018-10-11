package com.example.webclient.example.service.impl;

import com.example.webclient.example.model.Book;
import com.example.webclient.example.service.ExampleService;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class ExampleServiceImpl implements ExampleService {

    private final WebClient webClient;

    public ExampleServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://newwxoc.unp2p.com/").build();
    }

    @Override
    public String getHotCityList() {
        return webClient.get().uri("/wxoc/consult/getHotCityList").retrieve().bodyToMono(String.class).block();
    }

    @Override
    public String testWithCookie() {
        return WebClient.create()
                .method(HttpMethod.GET)
                .uri("http://baidu.com")
                .cookie("token","xxxx")
                .cookie("JSESSIONID","XXXX")
                .retrieve()
                .bodyToMono(String.class).block();
    }

    @Override
    public String testWithBasicAuth() {
        String basicAuth = "Basic "+ Base64.getEncoder().encodeToString("user:pwd".getBytes(StandardCharsets.UTF_8));
        return WebClient.create()
                .get()
                .uri("http://baidu.com")
                .header(HttpHeaders.AUTHORIZATION,basicAuth)
                .retrieve()
                .bodyToMono(String.class).block();
    }

    @Override
    public String testWithHeaderFilter() {
        WebClient webClient = WebClient.builder()
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36")
                .filter(ExchangeFilterFunctions
                        .basicAuthentication("user","password"))
                .filter((clientRequest, next) -> {
                    clientRequest.headers()
                            .forEach((name, values) -> values.forEach(value -> System.out.println("name:" + name + "    value:" + value)));
                    return next.exchange(clientRequest);
                })
                .build();
        return webClient.get()
                .uri("https://baidu.com")
                .retrieve()
                .bodyToMono(String.class).block();
    }

    @Override
    public String testUrlPlaceholder() {
        return WebClient.create()
                .get()
                //多个参数也可以直接放到map中,参数名与placeholder对应上即可
                .uri("http://www.baidu.com/s?wd={key}&other={another}","北京天气","test") //使用占位符
                .retrieve()
                .bodyToMono(String.class).block();
    }

    @Override
    public String testUrlBiulder() {
        return WebClient.create()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("http")
                        .host("www.baidu.com")
                        .path("/s")
                        .queryParam("wd", "北京天气")
                        .queryParam("other", "test")
                        .build())
                .retrieve()
                .bodyToMono(String.class).block();
    }

    @Override
    public String testFormParam() {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("name1","value1");
        formData.add("name2","value2");
        return  WebClient.create().post()
                .uri("http://www.w3school.com.cn/test/demo_form.asp")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(formData))
                .retrieve().bodyToMono(String.class).block();
    }

    @Override
    public String testPostJson() {
        Book book = new Book();
        book.setName("name");
        book.setTitle("this is title");
        return WebClient.create().post()
                .uri("http://localhost:8080/demo/json")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(Mono.just(book), Book.class)
                .retrieve().bodyToMono(String.class).block();
    }

    @Override
    public String testPostRawJson() {
        return WebClient.create().post()
                .uri("http://localhost:8080/demo/json")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(BodyInserters.fromObject("{\n" +
                        "  \"title\" : \"this is title\",\n" +
                        "  \"author\" : \"this is author\"\n" +
                        "}"))
                .retrieve().bodyToMono(String.class).block();
    }

    @Override
    public String testUploadFile() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        HttpEntity<ClassPathResource> entity = new HttpEntity<>(new ClassPathResource("parallel.png"), headers);
        MultiValueMap<String, Object> parts = new LinkedMultiValueMap<>();
        parts.add("file", entity);
        return WebClient.create().post()
                .uri("http://localhost:8080/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(parts))
                .retrieve().bodyToMono(String.class).block();
    }

    @Override
    public String testDownloadImage() {
        Mono<Resource> resp = WebClient.create().get()
                .uri("http://www.toolip.gr/captcha?complexity=99&size=60&length=9")
                .accept(MediaType.IMAGE_PNG)
                .retrieve().bodyToMono(Resource.class);
        Resource resource = resp.block();

        return resource.getDescription();
//        BufferedImage bufferedImage = ImageIO.read(resource.getInputStream());
//        ImageIO.write(bufferedImage, "png", new File("captcha.png"));
    }

    @Override
    public String testDownloadFile() {
        Mono<ClientResponse> resp = WebClient.create().get()
                .uri("http://localhost:8080/file/download")
                .accept(MediaType.APPLICATION_OCTET_STREAM)
                .exchange();
        ClientResponse response = resp.block();
        return response.toString();
//        String disposition = response.headers().asHttpHeaders().getFirst(HttpHeaders.CONTENT_DISPOSITION);
//        String fileName = disposition.substring(disposition.indexOf("=")+1);
//        Resource resource = response.bodyToMono(Resource.class).block();
//        File out = new File(fileName);
//        FileUtils.copyInputStreamToFile(resource.getInputStream(),out);
    }

    @Override
    public String testRetrieve4xx() {
        WebClient webClient = WebClient.builder()
                .baseUrl("https://api.github.com")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/vnd.github.v3+json")
                .defaultHeader(HttpHeaders.USER_AGENT, "Spring 5 WebClient")
                .build();
        WebClient.ResponseSpec responseSpec = webClient.method(HttpMethod.GET)
                .uri("/user/repos?sort={sortField}&direction={sortDirection}",
                        "updated", "desc")
                .retrieve();
        Mono<String> mono = responseSpec
                .onStatus(e -> e.is4xxClientError(),resp -> {
                    return Mono.error(new RuntimeException(resp.statusCode().value() + " : " + resp.statusCode().getReasonPhrase()));
                })
                .bodyToMono(String.class)
                .doOnError(WebClientResponseException.class, err -> {
                    throw new RuntimeException(err.getMessage());
                })
                .onErrorReturn("fallback");
        String result = mono.block();

        return result;
    }
}
