package jetty;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.api.Response;
import org.eclipse.jetty.client.api.Result;
import org.eclipse.jetty.http.HttpMethod;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BlockingAPI {
    public static void main(String[] args) {

        Future<String> futureList = new Future<String>() {
            @Override
            public boolean cancel(boolean mayInterruptIfRunning) {
                return false;
            }

            @Override
            public boolean isCancelled() {
                return false;
            }

            @Override
            public boolean isDone() {
                return false;
            }

            @Override
            public String get() throws InterruptedException, ExecutionException {
                return "rock demo";
            }

            @Override
            public String get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
                return null;
            }
        };
        try {
            System.out.println(futureList.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


        HttpClient httpClient = new HttpClient();
        try {
            ContentResponse response1 = httpClient.GET("http://www.baidu.com/search?query=123");

        ContentResponse response2 = httpClient.newRequest("http://www.baidu.com/search?query=123")
                .method(HttpMethod.HEAD)
                .agent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:17.0) Gecko/20100101 Firefox/17.0")
                .send();

        ContentResponse response3 = httpClient.POST("http://www.baidu.com/entity/1")
                .param("p", "123")
                .send();

        ContentResponse response4 = httpClient.newRequest("http://www.baidu.com/upload")
                .method(HttpMethod.POST)
                .file(Paths.get("file_to_upload.txt"), "text/plain")
                .send();

        ContentResponse response5 = httpClient.newRequest("http://www.baidu.com/path?query")
                .timeout(5, TimeUnit.SECONDS)
                .send();

        httpClient.newRequest("http://www.baidu.com/path")
                .send(new Response.CompleteListener() {
                    @Override
                    public void onComplete(Result result) {

                    }
                });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
