package com.hackathon.bbva.ironglass;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.glass.app.Card;
import com.google.android.glass.timeline.TimelineManager;

import java.util.List;
import java.util.Locale;



public class SecondActivity extends Activity implements TextToSpeech.OnInitListener {

    private static final int SPEECH_REQUEST = 0;
    private String[] favUsers = {"David", "Kevin", "Aaron", "Andrew"};
    private TextToSpeech tts;
	private String target;
	int quantity = -1;
	String divise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        LiveCard liveCard = TimelineManager.from(this).createLiveCard(LIVE_CARD_STRING);
//        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.livecard1);
//
//        liveCard.setViews(remoteViews);

        Card showCardWithText = showCardWithText("Tap and say a quantity and divise");
        Intent intent = getIntent();
        target = (String)intent.getExtras().get("target");
        setContentView(showCardWithText.toView());
             
    }

    private Card showCardWithText(String text) {
        Card card = new Card(this);
        card.setText(text);
        TimelineManager.from(this).insert(card);
        return card;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
        	
				displaySpeechRecognizer();
	            	
            return true;
        }
        return false;
    }



    /*
	private void displaySpeechRecognizer() {
        quantity = 20; 
        divise = "euros";
        
        // Do something with spokenText.
    	Intent intent = new Intent(this, PinActivity.class); 
		intent.putExtra("target", target);
		intent.putExtra("quantity", quantity);
		intent.putExtra("divise", divise);
             	startActivity(intent);
    }
	*/

	private void displaySpeechRecognizer() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        startActivityForResult(intent, SPEECH_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
           
            String spokenText = results.get(0);

            System.out.println("spokenText = " + spokenText);
            

            
            try {
                String[] transfer = spokenText.split(" ");
                 quantity = Integer.parseInt(transfer[0]); 
                 divise = transfer[1];
                 
                 // Do something with spokenText.
             	Intent intent = new Intent(this, PinActivity.class); 
 				intent.putExtra("target", target);
 				intent.putExtra("quantity", quantity);
 				intent.putExtra("divise", divise);
 		             	startActivity(intent);
            	
 		             	

            	
            } catch (Exception e) {
           

            }
            		
            	

            
            
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void speak(String spokenText) {
        tts = new TextToSpeech(this, this);
        tts.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "This Language is not supported");
            }
        } else {
            Log.e("TTS", "Initilization Failed!");
        }
    }
}
