package com.example.testingbtvoice;

import android.content.Intent;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private Intent recognizerIntent;
    private SpeechRecognizer speechRecognizer;
    ArrayList <String> resultsList = new ArrayList<>();
    RecyclerView recyclerView;
    private SpeechResultAdapter speechResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initSpeachIntent();
        Button getDeviceButton = findViewById(R.id.getDeviceBtn);
        Button startListenBtn = findViewById(R.id.startListenBtn);
        Button stopListenBtn = findViewById(R.id.stopListenBtn);
        recyclerView  = findViewById(R.id.recycler);
        initRecycler();
        stopListenBtn.setOnClickListener(v->stopListen());
        startListenBtn.setOnClickListener(v->{
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
            speechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    Timber.v(params.toString());
                }

                @Override
                public void onBeginningOfSpeech() {
                    Toast.makeText(MainActivity.this, "onBeginningOfSpeech", Toast.LENGTH_SHORT).show();
                    Timber.v("onBeginningOfSpeech");
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                    Timber.v("onRmsChanged");
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                    Timber.v("onBufferReceived");
                }

                @Override
                public void onEndOfSpeech() {
                    Toast.makeText(MainActivity.this, "onEndOfSpeech", Toast.LENGTH_SHORT).show();
                    Timber.v("onEndOfSpeech");
                }

                @Override
                public void onError(int error) {
                    switch (error){
                         case SpeechRecognizer.ERROR_NO_MATCH:
                         case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                             startListen();
                    }

                    Toast.makeText(MainActivity.this, String.valueOf(error), Toast.LENGTH_SHORT).show();
                    Timber.v("onError");
                    //startListen();
                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> stringArrayList = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    StringBuilder sb = new StringBuilder();
                    for (String result :
                            stringArrayList) {
                        sb.append(result);
                        sb.append(" ");
                        Toast.makeText(MainActivity.this, sb.toString(), Toast.LENGTH_SHORT).show();
                        Timber.v(result);
                        resultsList.add(sb.toString());
                        speechResultAdapter.notifyDataSetChanged();

                    }


                    startListen();
                    Timber.v("onResults");
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                    Timber.v("onPartialResults");
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                    Timber.v("onEvent" + String.valueOf(eventType));
                }
            });

            startListen();

        });
    }

    private void stopListen() {
        speechRecognizer.stopListening();
        speechRecognizer.cancel();
    }

    private void initRecycler() {
        resultsList.add("dfdsfds");
        resultsList.add("2");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        speechResultAdapter = new SpeechResultAdapter(resultsList);
        recyclerView.setAdapter(speechResultAdapter);
    }

    private void initSpeachIntent() {
       recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,
                getPackageName());
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "ru");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,1);
    }
    private void startListen(){
        stopListen();
        speechRecognizer.startListening(recognizerIntent);
    }
}
