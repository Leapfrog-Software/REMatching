package leapfrog_inc.rematching.Fragment.Tabbar;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import leapfrog_inc.rematching.Fragment.BaseFragment;
import leapfrog_inc.rematching.Fragment.Borrow.BorrowFragment;
import leapfrog_inc.rematching.Fragment.Lend.LendFragment;
import leapfrog_inc.rematching.Http.Requester.RoomRequester;
import leapfrog_inc.rematching.MainActivity;
import leapfrog_inc.rematching.R;

public class TabbarFragment extends BaseFragment {

    private BorrowFragment mBorrowFragment;
    private LendFragment mLendFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.fragment_tabbar, null);

        initFragmentController();
        changeTab(0);
        initAction(view);

        ((MainActivity)getActivity()).mTabbarFragment = this;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                timerProc();
                new Handler().postDelayed(this, 60000);
            }
        };
        new Handler().post(runnable);

        return view;
    }

    private void initFragmentController() {

        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        int contentsLayoutId = R.id.contentsLayout;

        mBorrowFragment = new BorrowFragment();
        transaction.add(contentsLayoutId, mBorrowFragment);

        mLendFragment = new LendFragment();
        transaction.add(contentsLayoutId, mLendFragment);

        transaction.commitAllowingStateLoss();
    }

    private void initAction(View view) {

        ((Button)view.findViewById(R.id.tab1Button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTab(0);
            }
        });

        ((Button)view.findViewById(R.id.tab2Button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeTab(1);
            }
        });
    }

    public void changeTab(int index) {

        View view = getView();
        if (view == null) return;

        if (index == 0) {
            mBorrowFragment.getView().setVisibility(View.VISIBLE);
            ((ImageView)view.findViewById(R.id.tab1OnImageView)).setVisibility(View.VISIBLE);
            ((ImageView)view.findViewById(R.id.tab1OffImageView)).setVisibility(View.GONE);
            ((TextView)view.findViewById(R.id.tab1TextView)).setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.tabSelected));
        } else {
            mBorrowFragment.getView().setVisibility(View.GONE);
            ((ImageView)view.findViewById(R.id.tab1OnImageView)).setVisibility(View.GONE);
            ((ImageView)view.findViewById(R.id.tab1OffImageView)).setVisibility(View.VISIBLE);
            ((TextView)view.findViewById(R.id.tab1TextView)).setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.tabUnselected));
        }

        if (index == 1) {
            mLendFragment.getView().setVisibility(View.VISIBLE);
            ((ImageView)view.findViewById(R.id.tab2OnImageView)).setVisibility(View.VISIBLE);
            ((ImageView)view.findViewById(R.id.tab2OffImageView)).setVisibility(View.GONE);
            ((TextView)view.findViewById(R.id.tab2TextView)).setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.tabSelected));
        } else {
            mLendFragment.getView().setVisibility(View.GONE);
            ((ImageView)view.findViewById(R.id.tab2OnImageView)).setVisibility(View.GONE);
            ((ImageView)view.findViewById(R.id.tab2OffImageView)).setVisibility(View.VISIBLE);
            ((TextView)view.findViewById(R.id.tab2TextView)).setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.tabUnselected));
        }
    }

    private void timerProc() {
        RoomRequester.getInstance().fetch(new RoomRequester.RoomRequesterCallback() {
            @Override
            public void didReceiveData(boolean result) {
                if (result) {
                    mBorrowFragment.resetListView(null);
                    mLendFragment.resetListView(null);
                }
            }
        });
    }
}
