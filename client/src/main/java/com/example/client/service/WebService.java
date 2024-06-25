package com.example.client.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class WebService {

    private static final String SERVER_URL = "http://localhost:9090/api/v1/regulator/";

    public Float setTemperature(Double temperature) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(SERVER_URL);
            String stringResponse = client.execute(httpPost, r -> EntityUtils.toString(r.getEntity()));
            String[] strings = stringResponse.replaceAll("[\\[|\\]]", " ").split(",");
            String current = strings[strings.length - 1];
            return Float.valueOf(current);
        }
    }

    public List<Float> getData() throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(SERVER_URL);
            httpGet.setHeader("test", new Random().nextInt());
            String stringResponse = client.execute(httpGet, r -> EntityUtils.toString(r.getEntity()));
            String[] strings = stringResponse.replaceAll("[\\[|\\]]", " ").split(",");
            return Arrays.stream(strings)
                    .map(Float::valueOf)
                    .collect(Collectors.toList());
        }
    }
}