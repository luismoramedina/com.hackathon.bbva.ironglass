package com.hackathon.bbva.ironglass;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class HomeActivity extends Activity implements OnItemClickListener {

	private CardScrollView mCardScrollView;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		int inmersion = R.layout.inmersion;
//		setContentView(inmersion);
		
		
		super.onCreate(savedInstanceState);
		// Create array of sample cards to use in the adapter 
//		createCards();
		// Create view programmatically 
		mCardScrollView = new CardScrollView(this);
		mCardScrollView.setOnItemClickListener(this);
		ArrayList<Card> cards = createCards();
		CardScrollAdapter adapter = new MyCardAdapter( cards, this );
		mCardScrollView.setAdapter(adapter);
		
		
		// Important! Views are NOT active by default
		mCardScrollView.activate();
		// Set activity view as usual
		setContentView(mCardScrollView);
	}

	private ArrayList<Card> createCards() {
		
		ArrayList<Card> result = new ArrayList<Card>();
		Card card1 = new Card( this ).setText("Saldo").setFootnote("pie1");
		result.add( card1 );
		
		
		result.add( new Card( this ).setText("Anular Tarjeta").setFootnote("0555550000") );
	
		
		result.add( new Card( this ).setText("Transferencia").setFootnote("0555550000") );

		
		result.add( new Card( this ).setText("Movimientos").setFootnote("0555550000") );
		return result;
		
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate menu as usual
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.samplemenu, menu);
		return true;
	}


	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Card card = (Card) parent.getItemAtPosition(position);
		String text = card.getText();
		Intent intent = new Intent(this, MainActivity.class);
		this.startActivity(intent);
		
	}

}
