package info.ephyra.search.searchers;

import info.ephyra.search.Result;

import java.util.ArrayList;
import java.util.List;

import com.aliasi.util.Collections;
import com.google.code.bing.search.client.BingSearchClient;
import com.google.code.bing.search.client.BingSearchClient.SearchRequestBuilder;
import com.google.code.bing.search.client.BingSearchServiceClientFactory;
import com.google.code.bing.search.schema.AdultOption;
import com.google.code.bing.search.schema.SearchOption;
import com.google.code.bing.search.schema.SearchResponse;
import com.google.code.bing.search.schema.SourceType;
import com.google.code.bing.search.schema.web.WebResult;
import com.google.code.bing.search.schema.web.WebSearchOption;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * <p>
 * A <code>KnowledgeMiner</code> that deploys the Bing search engine to search
 * the Web.</p>
 *
 * <p>
 * It runs as a separate thread, so several queries can be performed in
 * parallel.</p>
 *
 * <p>
 * This class extends the class <code>KnowledgeMiner</code>.</p>
 *
 * @author James Zhang, Jeff Chen
 * @version 2011-10-05
 */
public class BingKM extends KnowledgeMiner {

    /**
     * Bing Application ID.
     */
    private static final String BING_APP_ID = "Input your Bing app ID here";
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
     * Returns a new instance of <code>BingKM</code>. A new instance is created
     * for each query.
     *
     * @return new instance of <code>BingKM</code>
     */
    @Override
    public KnowledgeMiner getCopy() {
        return new BingKM();
    }

    private String cleanQueryString(String query) {
        String result = "";
        String[] _split = query.split("\"");
        query = "";
        for (int m = 0; m < _split.length; m++) {
            query += _split[m];
        }

        byte[] _byte = query.getBytes();
        System.out.println("=======query: " + query);
        int i = 0;
        for (i = 0; i < _byte.length; i++) {
            if ((_byte[i] > 'A' && _byte[i] < 'Z') || (_byte[i] > 'a' && _byte[i] < 'z')) {
                break;
            }
        }
        result = query.substring(i);
        System.out.println("======result: " + result);

        _byte = result.getBytes();
        int j = _byte.length - 1;
        for (j = _byte.length - 1; j >= 0; j--) {
            if ((_byte[j] > 'A' && _byte[j] < 'Z') || (_byte[j] > 'a' && _byte[j] < 'z')) {
                break;
            }
        }
        result = result.substring(0, j + 1);
        System.out.println("=====result: " + result);
        return result;
    }

    @Override
    protected Result[] doSearch() {
        String accountKey = ":" + BING_APP_ID;
        byte[] accountKeyBytes = Base64.encodeBase64(accountKey.getBytes());
        String accountKeyEnc = new String(accountKeyBytes);
        CloseableHttpClient httpclient = HttpClients.createDefault();

        try {
            String _query = java.net.URLEncoder.encode(query.getQueryString(), "UTF-8");
            HttpGet httpget = new HttpGet("https://api.datamarket.azure.com/Data.ashx/Bing/Search/Web?Query=%27" + _query + "%27&$top=3&$format=Json");
            httpget.setHeader("Authorization", "Basic " + accountKeyEnc);
            CloseableHttpResponse response = httpclient.execute(httpget);
            StatusLine statusLine = response.getStatusLine();
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String result = EntityUtils.toString(entity);
                    ArrayList<String> snippets = new ArrayList<String>();
                    ArrayList<String> urls = new ArrayList<String>();
                    snippets = getString(result, "Description");
                    urls = getString(result, "Url");
                    EntityUtils.consume(entity);
                    Result[] results = getResults(Collections.toStringArray(snippets),
                            Collections.toStringArray(urls), true);
                    return results;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                httpclient.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    private ArrayList<String> getString(String result, String key) {
        System.out.println(key);
        String head = "\":\"";
        int startIndex = head.length();
        String end = "\",\"";

        ArrayList<String> descriptionList = new ArrayList();
        String[] get = result.split(key);

        for (int i = 1; i < get.length;) {
            //System.out.println("get: " + get[i]);
            get[i] = get[i].substring(startIndex);
            //System.out.println("get[i] after start: " + get[i]);
            get[i] = get[i].substring(0, get[i].indexOf(end));
            System.out.println("result: " + get[i]);
            if (!get[i].isEmpty()) {
                descriptionList.add(get[i]);
            }
            i += 2;
        }
        return descriptionList;

    }
}
