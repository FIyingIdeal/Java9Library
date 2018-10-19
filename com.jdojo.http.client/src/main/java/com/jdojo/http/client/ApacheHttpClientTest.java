package com.jdojo.http.client;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author yanchao
 * @date 2018/10/10 16:25
 */
public class ApacheHttpClientTest {

    public static void main(String[] args) {
        // normalRequest();
        // System.out.println(numTrees(4));
        // badReverse(123);
        System.out.println(goodReverse(-120));
    }



    public static int goodReverse(int x) {
        int result = 0;
        for (int pop = x % 10; x != 0; x /= 10, pop = x % 10) {
            result = result * 10 + pop;
            if (result > Integer.MAX_VALUE / 10 || (result == Integer.MAX_VALUE / 10 && pop > 7)) {
                return 0;
            }
            if (result < Integer.MIN_VALUE / 10 || (result == Integer.MIN_VALUE / 10 && pop < -8)) {
                return 0;
            }
        }
        return result;
    }

    public static int badReverse(int x) {
        String str = String.valueOf(x);
        int substringIndex = 0;
        boolean isNegative = '-' == str.charAt(0);
        if (isNegative) {
            substringIndex = 1;
        }
        List<String> characters = Arrays.asList(str.substring(substringIndex).split(""));
        Collections.reverse(characters);
        List<String> result = new ArrayList<>(characters);
        if (isNegative) {
            result.add(0, "-");
        }
        try {
            int num = Integer.parseInt(String.join("", result));
            return num;
        } catch (Exception e) {
            return 0;
        }
    }

    private static void normalRequest() {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet("https://www.google.com");
            httpGet.setConfig(getRequestConfig());
            try (CloseableHttpResponse response = client.execute(httpGet)) {
                HttpEntity entity = response.getEntity();
                try (
                        InputStreamReader reader = new InputStreamReader(entity.getContent());
                        BufferedReader br = new BufferedReader(reader)) {
                    String str;
                    while ((str = br.readLine()) != null) {
                        System.out.println(str);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                .setSocketTimeout(5000)
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .build();
    }

    public static int numTrees(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        int[] nums = new int[n + 1];
        nums[0] = 1;
        nums[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 0; j < i; j++) {
                nums[i] = nums[i] + nums[j] * nums[i - 1 - j];
            }
        }
        Arrays.stream(nums).forEach(System.out::println);
        return nums[n];
    }
}
