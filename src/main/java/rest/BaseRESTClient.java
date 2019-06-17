package rest;

import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class BaseRESTClient {

    private String url = "http://localhost:9027/simongame/";
    private Gson gson = new Gson();

    public <T> T executeQueryPost(Object requestDTO, String queryPost , Class<T> resultClass) {
        String query = url + queryPost;
        HttpPost httpPost = new HttpPost(query);
        httpPost.addHeader("content-type", "application/json");

        StringEntity params;
        try {
            gson = new Gson();
            String json = gson.toJson(requestDTO);
            params = new StringEntity(json);
            httpPost.setEntity(params);
        } catch (Exception ex) {
           System.out.println(ex.getMessage());
        }
        return executeRequest(httpPost, resultClass);
    }

    public  <T> T executeQueryGet(String queryGet, Class<T> resultClass) {
        String query = url + queryGet;
        HttpGet httpGet = new HttpGet(query);
        return executeRequest(httpGet, resultClass);
    }

    private <T> T executeRequest(HttpUriRequest request, Class<T> resultClass)
    {
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(request)) {
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            System.out.println(gson.fromJson(entityString, resultClass));
            return gson.fromJson(entityString, resultClass);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
