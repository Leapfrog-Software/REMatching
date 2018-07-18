package leapfrog_inc.rematching.Fragment.Lend;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import leapfrog_inc.rematching.Fragment.BaseFragment;
import leapfrog_inc.rematching.Http.Requester.RoomRequester;
import leapfrog_inc.rematching.R;
import leapfrog_inc.rematching.System.Constants;
import leapfrog_inc.rematching.System.DeviceUtility;
import leapfrog_inc.rematching.System.PicassoUtility;
import leapfrog_inc.rematching.System.SaveData;

public class LendFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.fragment_lend, null);

        initAction(view);
        resetListView(view);

        return view;
    }

    private void initAction(View view) {

        ((Button)view.findViewById(R.id.addButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stackFragment(new LendCreateFragment(), AnimationType.horizontal);
            }
        });
    }

    public void resetListView(View view) {

        int count = 0;

        LendAdapater adapter = new LendAdapater(getActivity());

        SaveData saveData = SaveData.getInstance();
        for (int i = 0; i < saveData.createdRoomIds.size(); i++) {
            RoomRequester.RoomData roomData = RoomRequester.getInstance().query(saveData.createdRoomIds.get(i));
            if (roomData != null) {
                adapter.add(roomData);
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
    }

    private class LendAdapater extends ArrayAdapter<RoomRequester.RoomData> {

        LayoutInflater mInflater;
        Context mContext;

        public LendAdapater(Context context){
            super(context, 0);
            mInflater = LayoutInflater.from(context);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = mInflater.inflate(R.layout.adapter_lend, parent, false);

            RoomRequester.RoomData data = getItem(position);

            PicassoUtility.getRoomImage(mContext, Constants.RoomImageDirectory + data.id, (ImageView)convertView.findViewById(R.id.roomImageView));

            ImageView roomImageView = (ImageView)convertView.findViewById(R.id.roomImageView);
            ViewGroup.LayoutParams imageLayoutParams = roomImageView.getLayoutParams();
            imageLayoutParams.height = (DeviceUtility.getWindowSize((Activity) mContext).x - (int)(60 * DeviceUtility.getDeviceDensity((Activity)mContext))) / 2;
            roomImageView.setLayoutParams(imageLayoutParams);

            ((TextView)convertView.findViewById(R.id.nameTextView)).setText(data.name);
            ((TextView)convertView.findViewById(R.id.placeTextView)).setText(data.place);

            String rent = String.format("%,d", data.rent);
            ((TextView)convertView.findViewById(R.id.rentTextView)).setText("賃料: " + rent + "円/月");

            TextView statusTextView = (TextView)convertView.findViewById(R.id.statusTextView);
            if (data.approval) {
                statusTextView.setText("掲載中");
                statusTextView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.approvedGreen));
            } else {
                statusTextView.setText("審査待ち");
                statusTextView.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.notApprovedRed));
            }

            return convertView;
        }
    }
}