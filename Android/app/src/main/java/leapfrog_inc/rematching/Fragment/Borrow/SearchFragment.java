package leapfrog_inc.rematching.Fragment.Borrow;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import leapfrog_inc.rematching.Fragment.BaseFragment;
import leapfrog_inc.rematching.Http.Requester.RoomRequester;
import leapfrog_inc.rematching.R;
import leapfrog_inc.rematching.System.Constants;
import leapfrog_inc.rematching.System.DeviceUtility;
import leapfrog_inc.rematching.System.PicassoUtility;

public class SearchFragment extends BaseFragment {

    public static String[] Prefs = {
        "指定なし", "北海道", "青森県", "岩手県", "宮城県", "秋田県", "山形県", "福島県",
            "茨城県", "栃木県", "群馬県", "埼玉県", "千葉県", "東京都", "神奈川県", "新潟県",
            "富山県", "石川県", "福井県", "山梨県", "長野県", "岐阜県", "静岡県", "愛知県",
            "三重県", "滋賀県", "京都府", "大阪府", "兵庫県", "奈良県", "和歌山県", "鳥取県",
            "島根県", "岡山県", "広島県", "山口県", "徳島県", "香川県", "愛媛県", "高知県",
            "福岡県", "佐賀県", "長崎県", "熊本県", "大分県", "宮崎県", "鹿児島県", "沖縄県"
    };

    private int mSelectedIndex = 0;
    private SearchFragmentCallback mCallback = null;

    public void set(int selectedIndex, SearchFragmentCallback callback) {
        mSelectedIndex = selectedIndex;
        mCallback = callback;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.fragment_search, null);

        initAction(view);
        resetListView(view);

        return view;
    }

    private void initAction(View view) {

        ((Button)view.findViewById(R.id.closeButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popFragment(AnimationType.vertical);
            }
        });
    }

    public void resetListView(View view) {

        SearchAdapater adapter = new SearchAdapater(getActivity());

        for (int i = 0; i < Prefs.length; i++) {
            SearchAdapterData data = new SearchAdapterData();
            data.pref = Prefs[i];
            data.selected = (i == mSelectedIndex);
            adapter.add(data);
        }

        ListView listView = (ListView)view.findViewById(R.id.listView);

        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                mSelectedIndex = i;
                resetListView(getView());
                mCallback.didSelect(i);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        popFragment(AnimationType.vertical);
                    }
                }, 200);
            }
        });
    }

    public interface SearchFragmentCallback {
        void didSelect(int index);
    }

    private class SearchAdapterData {
        String pref;
        boolean selected;
    }

    private class SearchAdapater extends ArrayAdapter<SearchAdapterData> {

        LayoutInflater mInflater;
        Context mContext;

        public SearchAdapater(Context context){
            super(context, 0);
            mInflater = LayoutInflater.from(context);
            mContext = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = mInflater.inflate(R.layout.adapter_search, parent, false);

            SearchAdapterData data = getItem(position);

            ((TextView)convertView.findViewById(R.id.prefTextView)).setText(data.pref);
            ((ImageView)convertView.findViewById(R.id.selectedImageView)).setVisibility(data.selected ? View.VISIBLE : View.INVISIBLE);

            return convertView;
        }
    }
}
