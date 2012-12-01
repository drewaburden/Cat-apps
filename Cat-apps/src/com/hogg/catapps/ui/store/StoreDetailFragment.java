package com.hogg.catapps.ui.store;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;
import com.hogg.catapps.items.StoreFragmentContent;
import com.hogg.catapps.items.Item;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link StoreActivity} in two-pane mode (on tablets) or a
 * {@link StoreDetailActivity} on handsets.
 */
public class StoreDetailFragment extends Fragment {
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";

	/**
	 * The content this fragment is presenting.
	 */
	private Item mItem;

	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public StoreDetailFragment() {
	}
	
	private Callbacks mCallbacks = sDummyCallbacks;

	/**
	 * A callback interface that all activities containing this fragment must
	 * implement. This mechanism allows activities to be notified of item
	 * selections.
	 */
	public interface Callbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onBuyButtonSelected();
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {

		public void onBuyButtonSelected() {
			
		}

	};
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// Activities containing this fragment must implement its callbacks.
		if (!(activity instanceof Callbacks)) {
			throw new IllegalStateException(
					"Activity must implement fragment's callbacks.");
		}

		mCallbacks = (Callbacks) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = StoreFragmentContent.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_store_detail,
				container, false);		

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.textName))
					.setText(mItem.getName());
			
			((TextView) rootView.findViewById(R.id.textPrice))
			.setText(Integer.toString(mItem.getPrice()));
			
			((TextView) rootView.findViewById(R.id.textDescription))
			.setText(mItem.getDescription());
			
			// Enable the scrollbars on the description
			TextView textView = (TextView) rootView.findViewById(R.id.textDescription);
			textView.setMovementMethod(ScrollingMovementMethod.getInstance());
			
			if (Init.player.getMoney() < mItem.getPrice()) {
				((ImageButton) rootView.findViewById(R.id.buttonBuy)).setImageResource(R.drawable.ic_buy_disabled);
			}
			else {
				((ImageButton) rootView.findViewById(R.id.buttonBuy)).setImageResource(R.drawable.ic_buy);
				ImageButton buyButton = (ImageButton) rootView.findViewById(R.id.buttonBuy);
				buyButton.setOnClickListener(new View.OnClickListener() {
					public void onClick(View v) {
						onBuyButtonPress(v);
					}
					
				});
			}
		}
		else {
			((TextView) rootView.findViewById(R.id.textName))
			.setText("Please select an item");
	
			((TextView) rootView.findViewById(R.id.textPrice))
			.setText("0");
		}

		return rootView;
	}
	
	public void onBuyButtonPress(final View v) {
		
		final SeekBar seek = new SeekBar(Init.getAppContext());
		seek.setMax(Math.min((int) Math.floor(Init.player.getMoney()/mItem.getPrice()),
				99-Init.player.getInv().itemCount(mItem)));
		seek.setProgress(1);
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {		
			public void onProgressChanged(SeekBar s, int i, boolean b) {
				s.setProgress(i);
			}
			public void onStartTrackingTouch(SeekBar s) { }
			public void onStopTrackingTouch(SeekBar s) { }				
		});
		
		AlertDialog.Builder builder = new AlertDialog.Builder(((ViewGroup)getView()).getContext());
		builder.setTitle(R.string.diag_buy_head);
		builder.setCancelable(true);
		builder.setView(seek);
		builder.setPositiveButton(Init.getAppContext().getString(R.string.diag_ok), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				Init.player.decrementMoney( (mItem.getPrice() * seek.getProgress()) );
				Init.player.getInv().addItem(mItem, seek.getProgress());
				if(Init.player.getMoney() < mItem.getPrice()) {
					((ImageButton) v).setImageResource(R.drawable.ic_buy_disabled);
					v.setEnabled(false);
				}
				mCallbacks.onBuyButtonSelected();
			}
		});
		
		AlertDialog dialog = builder.create();
		dialog.show();
	}
}
