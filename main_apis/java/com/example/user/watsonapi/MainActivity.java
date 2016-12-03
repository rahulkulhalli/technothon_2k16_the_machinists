package com.example.user.watsonapi;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.speech_to_text.v1.SpeechToText;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.RecognizeOptions;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechModel;
import com.ibm.watson.developer_cloud.speech_to_text.v1.model.SpeechResults;
//import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeDelegate;
//import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.BaseRecognizeCallback;
import com.ibm.watson.developer_cloud.speech_to_text.v1.websocket.RecognizeCallback;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    //username, password, and confidential information stored as globals

    /*CONFIDENTIAL - SHOULD NOT BE DISCLOSED!*/

    private final String SPEECH_TO_TEXT_URL = "https://stream.watsonplatform.net/speech-to-text/api";
    private final String SPEECH_TO_TEXT_USERNAME = "d8bccc94-0ee6-4df7-8afc-ede8074670b0";
    private final String SPEECH_TO_TEXT_PASSWORD = "yRlYKAiz747V";

    private Button speechButton;
    private Button textToneButton;
    private TextView incoming;
    private String incomingText;

    public SpeechToText _init_speech_to_text(){
        SpeechToText speechToText = new SpeechToText();
        speechToText.setUsernameAndPassword(SPEECH_TO_TEXT_USERNAME, SPEECH_TO_TEXT_PASSWORD);
        return speechToText;
    }

    public ServiceCall<List<SpeechModel>> getAvailableModels(SpeechToText spt){
        return spt.getModels();
    }

    public RecognizeCallback getRecognizeCallBack(){

        RecognizeCallback callback = new RecognizeCallback() {
            @Override
            public void onTranscription(SpeechResults speechResults) {

                //do stuff when results come back

                /*
                * @Soumyakant : Handling text for now
                *
                * */


                incomingText = String.valueOf(speechResults);

                Log.d("INCOMING_TEXT:: ",String.valueOf(speechResults));
            }

            @Override
            public void onConnected() {
                //throw simple connected log
                Log.d("CONNECTION_SUCCESS","Connected successfully");
            }

            @Override
            public void onError(Exception e) {
                /*
                * @Soumyakant - fallback mechanism?
                * */

                Log.d("ERROR_S2T","Some Error occured!");
                e.printStackTrace();
            }

            @Override
            public void onDisconnected() {
                Log.d("DISCONNECT","Disconnected from service...");
            }
        };

        return callback;
    }

    public RecognizeOptions _init_recognizeOptions(){
        RecognizeOptions options = new RecognizeOptions.Builder().
                contentType("audio/wav").
                continuous(true).
                interimResults(true).
                build();

        return options;
    }

    public void recognizeAudio(File mediaFile, SpeechToText s2t, RecognizeOptions options, RecognizeCallback callback){
        try {
            s2t.recognizeUsingWebSocket(new FileInputStream(mediaFile), options, callback);
        }catch(FileNotFoundException fne){
            Log.d("FILE_ERROR","Wrong file passed...");
        }
    }

    public File getFile(){

        /*pseudo method for recording, converting, and returning audio in wav format*/

        return null;
    }


    private class speechClass extends AsyncTask<File, Void, Void>{
        @Override
        protected void onPostExecute(Void aVoid) {

            //update textview AFTER doInBackground executes!!
            incoming.setText(incomingText);
        }

        @Override
        protected void onProgressUpdate(Void... values) {}

        @Override
        protected Void doInBackground(File... files) {

            File audioFile = files[0];

            //initialize speech-to-text
            SpeechToText init = _init_speech_to_text();

            //get all available models
            ServiceCall<List<SpeechModel>> models = getAvailableModels(init);

            //print to console
            Log.d("ALL_MODELS ::", models.toString());

            //Set US Broadband model as default model
            ServiceCall<SpeechModel> model = init.getModel("en-US_BroadbandModel");

            //initialize recognization options
            RecognizeOptions options = _init_recognizeOptions();

            //generate callback parameters
            RecognizeCallback callback = getRecognizeCallBack();

            //recognize audio
            recognizeAudio(audioFile, init, options, callback);

            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        speechButton = (Button) findViewById(R.id.speech_button);
        textToneButton = (Button) findViewById(R.id.textToTone);
        incoming = (TextView) findViewById(R.id.incomingText);

        speechButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*Creating pseudo method for recording and converting file*/

                File file = getFile();

                //launch an AsyncTask
                new speechClass().execute(file);
            }
        });

        textToneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis.class);
                i.putExtra("text", incomingText);
                startActivity(i);
            }
        });
    }
}
