package machinists.technothon.com.watsonexample;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.*;
import org.apache.http.impl.client.DefaultHttpClient;

public class MainActivity extends AppCompatActivity {

    private Button sendButton;
    private TextView text;

    private class sendHttpRequestToAws extends AsyncTask<File, Void, File>{

        @Override
        protected File doInBackground(File... files) {
            //only one URL, so get it
            File audioFile = files[0];

            try {
                String content = postFile(audioFile);
            }catch(Exception e){
                e.printStackTrace();
            }

            //send HTTP GET from here
            return audioFile;
        }

        @Override
        protected void onProgressUpdate(Void... values) {}

        @Override
        protected void onPostExecute(File aLong) {
            text.setText(aLong.toString()+" successfully uploaded..");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //asynctask

        sendButton = (Button) findViewById(R.id.button);
        text = (TextView) findViewById(R.id.text);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    URL url = new URL("URL goes here...");
                }catch(MalformedURLException e){
                    Log.d("URL Exception", "Wrong URL");
                    e.printStackTrace();
                }
                //first, construct HTTP request
                new sendHttpRequestToAws().execute(new File(""));
            }
        });

    }

    //-------------------------------------------------------------------------

    public String postFile(File file) throws Exception {

        HttpClient client = new DefaultHttpClient();
        HttpPost post = new HttpPost("");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

        //final File file = new File(fileName);
        FileBody fb = new FileBody(file);

        builder.addPart("file", fb);
        final HttpEntity yourEntity = builder.build();

        class ProgressiveEntity implements HttpEntity {
            @Override
            public void consumeContent() throws IOException {
                yourEntity.consumeContent();
            }
            @Override
            public InputStream getContent() throws IOException,
                    IllegalStateException {
                return yourEntity.getContent();
            }
            @Override
            public Header getContentEncoding() {
                return yourEntity.getContentEncoding();
            }
            @Override
            public long getContentLength() {
                return yourEntity.getContentLength();
            }
            @Override
            public Header getContentType() {
                return yourEntity.getContentType();
            }
            @Override
            public boolean isChunked() {
                return yourEntity.isChunked();
            }
            @Override
            public boolean isRepeatable() {
                return yourEntity.isRepeatable();
            }
            @Override
            public boolean isStreaming() {
                return yourEntity.isStreaming();
            } // CONSIDER put a _real_ delegator into here!

            @Override
            public void writeTo(OutputStream outstream) throws IOException {

                class ProxyOutputStream extends FilterOutputStream {
                    /**
                     * @author Stephen Colebourne
                     */

                    public ProxyOutputStream(OutputStream proxy) {
                        super(proxy);
                    }
                    public void write(int idx) throws IOException {
                        out.write(idx);
                    }
                    public void write(byte[] bts) throws IOException {
                        out.write(bts);
                    }
                    public void write(byte[] bts, int st, int end) throws IOException {
                        out.write(bts, st, end);
                    }
                    public void flush() throws IOException {
                        out.flush();
                    }
                    public void close() throws IOException {
                        out.close();
                    }
                } // CONSIDER import this class (and risk more Jar File Hell)

                class ProgressiveOutputStream extends ProxyOutputStream {
                    public ProgressiveOutputStream(OutputStream proxy) {
                        super(proxy);
                    }
                    public void write(byte[] bts, int st, int end) throws IOException {

                        // FIXME  Put your progress bar stuff here!

                        out.write(bts, st, end);
                    }
                }

                yourEntity.writeTo(new ProgressiveOutputStream(outstream));
            }

        };
        ProgressiveEntity myEntity = new ProgressiveEntity();

        post.setEntity(myEntity);
        HttpResponse response = client.execute(post);

        return getContent(response);

    }

    public String getContent(HttpResponse response) throws IOException {
        BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String body = "";
        String content = "";

        while ((body = rd.readLine()) != null)
        {
            content += body + "\n";
        }
        return content.trim();
    }
}
