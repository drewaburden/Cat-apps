package com.hogg.catapps.ui.inventory;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.hogg.catapps.Init;
import com.hogg.catapps.R;
import com.hogg.catapps.items.InventoryFragmentContent;
import com.hogg.catapps.items.Item;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link InventoryActivity} in two-pane mode (on tablets) or a
 * {@link InventoryDetailActivity} on handsets.
 */
public class InventoryDetailFragment extends Fragment {
	
	InventoryFragmentContent content;
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
	public InventoryDetailFragment() {
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
		public void onUseButtonSelected();
	}

	/**
	 * A dummy implementation of the {@link Callbacks} interface that does
	 * nothing. Used only when this fragment is not attached to an activity.
	 */
	private static Callbacks sDummyCallbacks = new Callbacks() {

		public void onUseButtonSelected() {
			
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
		
		content = new InventoryFragmentContent();

		if (getArguments().containsKey(ARG_ITEM_ID)) {
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = content.ITEM_MAP.get(getArguments().getString(
					ARG_ITEM_ID));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.activity_inventory_detail,
				container, false);		

		// Show the dummy content as text in a TextView.
		if (mItem != null) {
			((TextView) rootView.findViewById(R.id.textName))
					.setText(mItem.getName());
			
			((TextView) rootView.findViewById(R.id.textQuantity))
			.setText(Integer.toString(Init.player.getInv().itemCount(mItem)));
			
			((TextView) rootView.findViewById(R.id.textDescription))
			.setText(mItem.getDescription());
			
			// Enable the scrollbars on the description
			TextView textView = (TextView) rootView.findViewById(R.id.textDescription);
			textView.setMovementMethod(ScrollingMovementMethod.getInstance());
			
			ImageButton useButton = (ImageButton) rootView.findViewById(R.id.buttonUse);
			useButton.setImageResource(R.drawable.ic_use);
			useButton.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					mItem.useItem();
					mCallbacks.onUseButtonSelected();
				}
				
			});
		}
		else {
			((TextView) rootView.findViewById(R.id.textName))
			.setText("Please select an item");
	
			((TextView) rootView.findViewById(R.id.textQuantity))
			.setText("0");
		}

		return rootView;
	}
}
