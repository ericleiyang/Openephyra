/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package info.ephyra.search.searchers;

import edu.stanford.nlp.parser.lexparser.Test;
import info.ephyra.search.Result;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;

import org.json.JSONObject;

/**
 *
 * @author l33yang
 */
public class BingSearch extends KnowledgeMiner {

    public static void main(String[] args) {
        BingSearch se = new BingSearch();
        se.doSearch();
    }
    /**
     * Bing Application ID.
     */
    private static final String BING_APP_ID = "k3t9WPWHEmlYy4+3R9EJ0s0zoDOUkR+sxMHo1lB36cY";
    /**
     * Maximum total number of search results.
     */
    private static final int MAX_RESULTS_TOTAL = 100;
    /**
     * Maximum number of search results per query.
     */
    private static final int MAX_RESULTS_PERQUERY = 50;

    @Override
    protected int getMaxResultsTotal() {
        return MAX_RESULTS_TOTAL;
    }

    @Override
    protected int getMaxResultsPerQuery() {
        return MAX_RESULTS_PERQUERY;
    }

    /**
     * Returns a new instance of
     * <code>BingSearch</code>. A new instance is created for each query.
     *
     * @return new instance of <code>BingKM</code>
     */
    @Override
    public KnowledgeMiner getCopy() {
        return new BingSearch();
    }

    @Override
    protected Result[] doSearch() {
        String accountKey = ":" + BING_APP_ID;
        byte[] accountKeyBytes = Base64.encodeBase64(accountKey.getBytes());
        String accountKeyEnc = new String(accountKeyBytes);
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            HttpGet httpget = new HttpGet("https://api.datamarket.azure.com/Data.ashx/Bing/Search/Web?Query=%27MAC%27&$top=3&$format=Json");
            httpget.setHeader("Authorization", "Basic " + accountKeyEnc);
            CloseableHttpResponse response = httpclient.execute(httpget);
            StatusLine statusLine = response.getStatusLine();
            System.out.println("statusLine: " + statusLine.toString());
            try {
                HttpEntity entity = response.getEntity();
                //System.out.println(EntityUtils.toString(entity, "utf-8"));
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    //System.out.println("result: " + result);
                    JSONObject _object = new JSONObject(result);

                    ObjectMapper mapper = new ObjectMapper();
                    JsonParser parser = mapper.getJsonFactory().createJsonParser(result);
                    System.out.println(parser.getBinaryValue().toString());
                    EntityObjectMapper.d _d = mapper.readValue(result, EntityObjectMapper.d.class);
                    System.out.println("d: " + _d.getResults());

                    EntityUtils.consume(entity);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
            }
        }

        return null;
    }
}
