package com.jdojo.http.client;

import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author yanchao
 * @date 2018/10/9 18:23
 */
public class SimpleTest {

    public static void main(String[] args) {
        // send() 发送同步请求
        // send();
        // sendAsync() 发送异步请求
        sendAsync();
    }

    private static void send() {
        try {
            URI uri = new URI("https://www.baidu.com");
            // HttpClient 是一个 不可变 可重用 对象
            HttpClient client = HttpClient.newHttpClient();
            // 创建 HttpRequest 对象
            HttpRequest request = HttpRequest.newBuilder(uri)
                    .GET()
                    .build();
            // send() 发送同步请求
            HttpResponse<?> response = client.send(request, HttpResponse.BodyHandler.discard(null));
            System.out.println("Response Status code : " + response.statusCode());
            System.out.println("Response Headers are : ");
            response.headers().map().entrySet().forEach(System.out::println);
        } catch (URISyntaxException | InterruptedException | IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendAsync() {
        try {
            URI uri = new URI("https://www.baidu.com");
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder(uri).GET().build();
            CompletableFuture<?> future = client.sendAsync(request, HttpResponse.BodyHandler.asString())
                    .whenComplete(SimpleTest::processRequest);
            TimeUnit.SECONDS.sleep(5);
        } catch (URISyntaxException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void processRequest(HttpResponse<String> response, Throwable t) {
        if (t == null) {
            System.out.println("Response Status Code : " + response.statusCode());
            System.out.println("Response Body : " + response.body());
        } else {
            System.out.println("An exception occurred while processing the HTTP request. Error:" +  t.getMessage());
        }
    }
}
