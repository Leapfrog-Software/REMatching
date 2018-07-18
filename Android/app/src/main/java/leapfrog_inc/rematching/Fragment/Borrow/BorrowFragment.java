package leapfrog_inc.rematching.Fragment.Borrow;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import leapfrog_inc.rematching.Fragment.BaseFragment;
import leapfrog_inc.rematching.Http.Requester.RoomRequester;
import leapfrog_inc.rematching.R;
import leapfrog_inc.rematching.System.Constants;
import leapfrog_inc.rematching.System.DeviceUtility;
import leapfrog_inc.rematching.System.PicassoUtility;

public class BorrowFragment extends BaseFragment {

    private int mSearchIndex = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.fragment_borrow, null);

        initAction(view);
        resetListView(view);

        return view;
    }

    private void initAction(View view) {

        ((ImageButton)view.findViewById(R.id.searchButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchFragment fragment = new SearchFragment();
                fragment.set(mSearchIndex, new SearchFragment.SearchFragmentCallback() {
                    @Override
                    public void didSelect(int index) {
                        mSearchIndex = index;
                        resetListView(getView());
                    }
                });
                getTabbar().stackFragment(fragment, AnimationType.vertical);
            }
        });
    }

    public void resetListView(View view) {

        int count = 0;

        BorrowAdapater adapter = new BorrowAdapater(getActivity());
        ArrayList<RoomRequester.RoomData> dataList = RoomRequester.getInstance().dataList;
        for (int i = 0; i < dataList.size(); i++) {
            if (mSearchIndex != 0) {
                String pref = SearchFragment.Prefs[mSearchIndex];
                RoomRequester.RoomData roomData = dataList.get(i);
                if (roomData.place.contains(pref)) {
                    adapter.add(roomData);
                    count++;
                }
            } else {
                adapter.add(dataList.get(i));
                count++;
            }
        }

        ListView listView = (ListView)view.findViewById(R.id.listView);
        TextView noDataTextView = (TextView)view.findViewById(R.id.noDataTextView);

        if (count > 0) {
            listView.setVisibility(View.VISIBLE);
            noDataTextView.setVisibility(View.GONE);
        } else {
            listView.setVisibility(View.GONE);
            noDataTextView.setVisibility(View.VISIBLE);
        }

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                RoomRequester.RoomData roomData = (RoomRequester.RoomData) adapterView.getItemAtPosition(i);
                BorrowDetailFragment fragment = new BorrowDetailFragment();
                fragment.set(roomData);
                stackFragment(fragment, AnimationType.horizontal);
            }
        });
    }

    private class BorrowAdapater extends ArrayAdapter<RoomRequester.RoomData> {

        LayoutInflater mInflater;
        Context mContext;

        public BorrowAdapater(Context context){
            super(context, 0);
            mInflater = LayoutInflater.from(context);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = mInflater.inflate(R.layout.adapter_borrow, parent, false);

            RoomRequester.RoomData data = getItem(position);

            PicassoUtility.getRoomImage(mContext, Constants.RoomImageDirectory + data.id, (ImageView)convertView.findViewById(R.id.roomImageView));

            FrameLayout imageRootLayout = (FrameLayout)convertView.findViewById(R.id.imageRootLayout);
            ViewGroup.LayoutParams imageRootLayoutParams = imageRootLayout.getLayoutParams();
            imageRootLayoutParams.height = (DeviceUtility.getWindowSize((Activity) mContext).x - (int)(24 * DeviceUtility.getDeviceDensity((Activity)mContext))) / 2;
            imageRootLayout.setLayoutParams(imageRootLayoutParams);

            ((TextView)convertView.findViewById(R.id.nameTextView)).setText(data.name);

            StringBuffer review = new StringBuffer();
            review.append((data.score <= 0) ? "☆" : "★");
            review.append((data.score <= 1) ? "☆" : "★");
            review.append((data.score <= 2) ? "☆" : "★");
            review.append((data.score <= 3) ? "☆" : "★");
            review.append((data.score <= 4) ? "☆" : "★");
            review.append(" " + String.valueOf(data.review) + "件のレビュー");
            ((TextView)convertView.findViewById(R.id.reviewTextView)).setText(review);

            ((TextView)convertView.findViewById(R.id.placeTextView)).setText(data.place);

            return convertView;
        }
    }
}
