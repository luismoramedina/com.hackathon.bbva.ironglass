package com.hackathon.bbva.ironglass;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;

public class MyCardAdapter extends CardScrollAdapter {

	private ArrayList<Card> mCards;
	
	public MyCardAdapter( ArrayList<Card> cards, Activity act ){
		this.mCards = cards;
	}
	

	@Override
	public int findIdPosition(Object arg0) {
		return 0;
	}

	@Override
	public int findItemPosition(Object arg0) {
		return mCards.indexOf(arg0);
	}

	@Override
	public int getCount() {
		return mCards.size();
	}

	@Override
	public Object getItem(int arg0) {
		return mCards.get( arg0 );
	}

	@Override
	public View getView( int position, View convertView, ViewGroup parent ) {

		// Cards are able to render themselves
        return mCards.get(position).toView();
    }

}
