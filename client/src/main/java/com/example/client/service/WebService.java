package com.example.client.service;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.entity.UrlEncodedFormEntity;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.message.BasicNameValuePair;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class WebService {

    private static final String SERVER_URL = "http://localhost:9090/api/v1/regulator/";

    public Float setTemperature(Double temperature) throws IOException {
        HttpPost httpPost = new HttpPost(SERVER_URL);
        BasicNameValuePair temperatureParam = new BasicNameValuePair("temperature", temperature.toString());
        httpPost.setEntity(new UrlEncodedFormEntity(List.of(temperatureParam), StandardCharsets.UTF_8));
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String stringResponse = client.execute(httpPost, r -> EntityUtils.toString(r.getEntity()));
            List<Float> tempList = convertResponseToList(stringResponse);
            return tempList.get(tempList.size() -1);
        }
    }

    public List<Float> getData() throws IOException {
        HttpGet httpGet = new HttpGet(SERVER_URL);
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String stringResponse = client.execute(httpGet, r -> EntityUtils.toString(r.getEntity()));
            List<Float> tempList = convertResponseToList(stringResponse);
            int count = ThreadLocalRandom.current().nextInt(10) + 3;
            if (count > tempList.size()) {
                return tempList;
            }
            int size = tempList.size();
            return tempList.subList(size - count, size);
        }
    }

    private List<Float> convertResponseToList(String response) {
        String string = response.replaceAll("[\\[|\\]]", "");
        if (string.isEmpty()) {
            return List.of();
        }
        String[] array = string.split(",");
        return Arrays.stream(array)
                .map(Float::valueOf)
                .collect(Collectors.toList());
    }
}