package com.example.myaivoiceassistant;

import static com.example.myaivoiceassistant.Functions.wishMe;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private SpeechRecognizer recognizer;
    private TextView tvResult;
    private TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        Dexter.withContext(this)
                .withPermission(Manifest.permission.RECORD_AUDIO)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        System.exit(0);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
        findByid();
        initializeTextToSpeech();
        initializeResult();

    }

    private void initializeTextToSpeech() {
        tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (tts.getEngines().size() == 0) {
                    Toast.makeText(MainActivity.this, "Engine is not available", Toast.LENGTH_SHORT).show();

                } else {
                    String s = wishMe();
                    speak("Hello Sir I am Nexa..." + s);
                }
            }
        });

    }

    private void speak(String msg) {
        tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null, null);
    }


    private void findByid() {
        tvResult = (TextView) findViewById(R.id.tv_result);
    }

    private void initializeResult() {
        if (SpeechRecognizer.isRecognitionAvailable(this)) {
            recognizer = SpeechRecognizer.createSpeechRecognizer(this);
            recognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {

                }

                @Override
                public void onBeginningOfSpeech() {

                }

                @Override
                public void onRmsChanged(float rmsdB) {

                }

                @Override
                public void onBufferReceived(byte[] buffer) {

                }

                @Override
                public void onEndOfSpeech() {

                }

                @Override
                public void onError(int error) {

                }

                @Override
                public void onResults(Bundle results) {
                    ArrayList<String> result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                    Toast.makeText(MainActivity.this, "" + result.get(0), Toast.LENGTH_SHORT).show();
                    tvResult.setText(result.get(0));
                    response(result.get(0));
                }

                @Override
                public void onPartialResults(Bundle partialResults) {

                }

                @Override
                public void onEvent(int eventType, Bundle params) {

                }
            });
        }
    }

    private void response(String msg) {
        String msgs = msg.toUpperCase();
        if (msgs.contains("HII") || msgs.contains("HELLO")) {
            speak("Hello Sir! How are you today?");
        }
        if (msgs.contains("WHAT YOU") || msgs.contains("CAN DO")) {
            speak("I can control the device through your voice commands");
        }
        if (msgs.contains("RUMAN") || msgs.contains("SIR")) {
            speak("Hello Ruman Sir! I am Nexa, Your personal voice assistant");
        }
        if (msgs.contains("DO YOU") || msgs.contains("KNOW MY NAME?")) {
            speak("Yes Sir! Your Name is Anup and you're my Creator");
        }
        if (msgs.contains("FINE")) {
            speak("it's good to know that you are fine Sir");
        } else if (msgs.contains("I AM NOT GOOD")) {
            speak("Please take care of yourself Sir");
        }
        if (msgs.contains("WHAT")) {
            if (msgs.contains("YOUR")) {
                if (msgs.contains("NAME")) {
                    speak("I am Nexa! Your Smart AI Assistant");
                }
            }
            if (msgs.contains("TIME")) {
                if (msgs.contains("NOW")) {
                    Date date = new Date();
                    String time = DateUtils.formatDateTime(this, date.getTime(), DateUtils.FORMAT_SHOW_TIME);
                    speak("The time now is " + time);
                }
            }
            if (msgs.contains("TODAY")) {
                if (msgs.contains("DATE")) {
                    SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy");
                    Calendar cal = Calendar.getInstance();
                    String todays_date = df.format(cal.getTime());
                    speak("Today's date is " + todays_date);
                }
            }
        }
        if (msgs.contains("OPEN")) {
            if (msgs.contains("GOOGLE")) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
                startActivity(intent);
            }
        }
        if (msgs.contains("BROWSER")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
            startActivity(intent);
        }
        if (msgs.contains("YOUTUBE")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
            startActivity(intent);
        }
        if (msgs.contains("CHROME")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"));
            startActivity(intent);
        }
        if (msgs.contains("FACEBOOK")) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com"));
            startActivity(intent);

        }

    }

    public void startRecording (View view){
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            recognizer.startListening(intent);
        }
    }
