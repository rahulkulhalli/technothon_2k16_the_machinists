package com.example.user.watsonapi;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.ibm.watson.developer_cloud.http.ServiceCall;
import com.ibm.watson.developer_cloud.tone_analyzer.v3.ToneAnalyzer;

public class ToneAnalysis extends AppCompatActivity {

    private final String TONE_ANALYZER_URL = "https://gateway.watsonplatform.net/tone-analyzer/api";
    private final String TONE_ANALYZER_USERNAME = "24c89c93-9ac3-4d8f-85a6-d66083563d99";
    private final String TONE_ANALYZER_PASSWORD = "nOLVX78MsN0z";

    private TextView results;

    public ToneAnalyzer _init_ToneAnalyzer(){
        ToneAnalyzer analyzer = new ToneAnalyzer(ToneAnalyzer.VERSION_DATE_2016_05_19);
        analyzer.setUsernameAndPassword(TONE_ANALYZER_USERNAME, TONE_ANALYZER_PASSWORD);
        return analyzer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tone_analysis);

        results = (TextView) findViewById(R.id.results);

        //get String from intent
        Intent i = getIntent();

        String text = i.getStringExtra("text");

        //initialize Tone Analyzer
        ToneAnalyzer toneAnalyzer = _init_ToneAnalyzer();

        ServiceCall<com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis> res = toneAnalyzer.getTone(text, null);

        com.ibm.watson.developer_cloud.tone_analyzer.v3.model.ToneAnalysis tone = res.execute();

        Log.d("INCOMING JSON :: ",String.valueOf(tone));

        results.setText(String.valueOf(tone));
    }
}
