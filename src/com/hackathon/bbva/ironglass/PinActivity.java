package com.hackathon.bbva.ironglass;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.speech.tts.TextToSpeech;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Locale;

import com.google.android.glass.app.Card;
import com.google.android.glass.timeline.TimelineManager;
import com.google.android.glass.touchpad.Gesture;
import com.google.android.glass.touchpad.GestureDetector;


public class PinActivity extends Activity implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private int pos = 0;
    private TableLayout tableLayout;
	private Integer quantity;
	private String target;
	private String divise;
    private GestureDetector mGestureDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        Intent intent = getIntent();
        target = (String)intent.getExtras().get("target");
        divise = (String)intent.getExtras().get("divise");
        quantity = (Integer)intent.getExtras().get("quantity");

        
        setContentView(R.layout.pin);
        findViewById(R.id.textView1).setBackgroundColor(Color.RED);

        tableLayout = (TableLayout) findViewById(R.id.table_layout);
        mGestureDetector = createGestureDetector(this);
        
        
   String tx= "quantity:" + quantity + " " +  divise  + "  to " + target;
   ((TextView) findViewById(R.id.transaction)).setText(tx);
      }

      private GestureDetector createGestureDetector(Context context) {
      GestureDetector gestureDetector = new GestureDetector(context);
          //Create a base listener for generic gestures
          gestureDetector.setBaseListener( new GestureDetector.BaseListener() {
              @Override
              public boolean onGesture(Gesture gesture) {
                  if (gesture == Gesture.TAP) {
                	  nextNumber();
                      // do something on tap
                      return true;
                  } else if (gesture == Gesture.TWO_TAP) {
                	  prevNumber();
                      // do something on two finger tap
                      return true;
                  } else if (gesture == Gesture.SWIPE_RIGHT) {
                	  decreasePinNumber();
                      // do something on right (forward) swipe
                      return true;
                  } else if (gesture == Gesture.SWIPE_LEFT) {
                	  increasePinNumber();
                      // do something on left (backwards) swipe
                      return true;
                  }
                  return false;
              }
          });
          gestureDetector.setFingerListener(new GestureDetector.FingerListener() {
              @Override
              public void onFingerCountChanged(int previousCount, int currentCount) {
                // do something on finger count changes
              }
          });
          gestureDetector.setScrollListener(new GestureDetector.ScrollListener() {
              @Override
              public boolean onScroll(float displacement, float delta, float velocity) {
                  // do something on scrolling
            	  return true;
            	  
              }
          });
          return gestureDetector;
      }

      /*
       * Send generic motion events to the gesture detector
       */
      @Override
      public boolean onGenericMotionEvent(MotionEvent event) {
          if (mGestureDetector != null) {
              return mGestureDetector.onMotionEvent(event);
          }
          return false;
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

    public void doSomething(View view) {
        tts = new TextToSpeech(this, this);
        int hello = tts.speak("hello", TextToSpeech.QUEUE_FLUSH, null);
        System.out.println("hello = " + hello);
    }

    public void increasePinNumber() {
        View textView = getView();
        sumDataToView(textView);
    }

    private View getView() {
        TableRow viewById = (TableRow) tableLayout.getChildAt(0);
        return viewById.getChildAt(pos);
    }

    public void decreasePinNumber() {
        View textView = getView();
        decreaseDataToView(textView);
    }

    private void sumDataToView(View viewById) {
        CharSequence text = ((TextView) viewById).getText();
        int i = Integer.parseInt(text.toString());
        if (i < 9) {
            ((TextView) viewById).setText(i + 1 + "");
        }
    }


    private void decreaseDataToView(View viewById) {
        CharSequence text = ((TextView) viewById).getText();
        int i = Integer.parseInt(text.toString());
        if (i > 0) {
            ((TextView) viewById).setText(i - 1 + "");
        }
    }

    public void nextNumber() {
    	if (pos != 3) {
    		 pos++;

    	        moveColors();
		} else{
				displayOk();
		}
    	
       
    }
    
    public void prevNumber() {
    	if (pos != 0) {
    		  pos--;

    	        moveColors();
		}
    	
      
    }

	private void moveColors() {
		TableRow tableRow = (TableRow) tableLayout.getChildAt(0);
		for (int i = 0; i < tableRow.getChildCount(); i++) {
		    View view = tableRow.getChildAt(i);
		    view.setBackgroundColor(Color.WHITE);

		}

		getView().setBackgroundColor(Color.RED);
	}
	
	
    private void displayOk() {
  	   Card showCardWithText = showCardWithText("Trasaction DONE!!");
        setContentView(showCardWithText.toView());
 	}
    
    private Card showCardWithText(String text) {
        Card card = new Card(this);
        card.setText(text);
        TimelineManager.from(this).insert(card);
        return card;
    }
}
