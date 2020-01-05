package tdx;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpComponentsDemo {
    static final CloseableHttpClient client = HttpClients.createDefault();

    public static void main(String[] args) throws Exception {
        HttpComponentsDemo httpUrlConnectionDemo = new HttpComponentsDemo();
        String url = "http://event.csdn.net/logstores/csdn-pc-tracking-page-exposure/track";
        Map<String, String> params = new HashMap<String, String>();
        params.put("foo", "bar中文");
        String rsp = httpUrlConnectionDemo.sendPostForm(url, params);
        System.out.println("http post rsp:" + rsp);

        //        url = "https://httpbin.org/get";
        //        System.out.println("http get rsp:" + httpUrlConnectionDemo.sendGet(url));
    }

    //    // fluent 链式调用
    //    private String sendGet(String url) throws Exception {
    //        return Request.Get(url)
    //                .connectTimeout(1000)
    //                .socketTimeout(1000)
    //                .execute().returnContent().asString();
    //    }

    // 常规调用
    private String sendPostForm(String url, Map<String, String> params) throws Exception {
        HttpPost request = new HttpPost(url);

        // set header
        request.setHeader("X-Http-Demo", HttpComponentsDemo.class.getSimpleName());

        // set params
        if (params != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : params.entrySet()) {
                nameValuePairList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            UrlEncodedFormEntity bodyEntity = new UrlEncodedFormEntity(nameValuePairList, "UTF-8");
            // System.out.println("body:" + IOUtils.toString(bodyEntity.getContent()));
            request.setEntity(new UrlEncodedFormEntity(nameValuePairList));
        }

        // send request
        CloseableHttpResponse response = client.execute(request);
        // read rsp code
        System.out.println("rsp code:" + response.getStatusLine().getStatusCode());
        // return content
        String ret = readResponseContent(response.getEntity().getContent());
        response.close();
        return ret;
    }

    private String readResponseContent(InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return "";
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[512];
        int len;
        while (inputStream.available() > 0) {
            len = inputStream.read(buf);
            out.write(buf, 0, len);
        }

        return out.toString();
    }
}
