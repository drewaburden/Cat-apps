package com.hogg.catapps.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hogg.catapps.R;
import com.hogg.catapps.R.id;
import com.hogg.catapps.R.layout;
import com.hogg.catapps.items.StoreFragmentContent;
import com.hogg.catapps.items.Item;

/**
 * A fragment representing a single Item detail screen. This fragment is either
 * contained in a {@link ItemListActivity} in two-pane mode (on tablets) or a
 * {@link ItemDetailActivity} on handsets.
 */
public class ItemDetailFragment extends Fragment {
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
	public ItemDetailFragment() {
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
		View rootView = inflater.inflate(R.layout.activity_item_detail,
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
		}

		return rootView;
	}
}
