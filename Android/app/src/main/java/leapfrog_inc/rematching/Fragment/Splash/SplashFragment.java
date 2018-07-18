package leapfrog_inc.rematching.Fragment.Splash;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import leapfrog_inc.rematching.Fragment.BaseFragment;
import leapfrog_inc.rematching.Fragment.Common.Dialog.Dialog;
import leapfrog_inc.rematching.Fragment.Tabbar.TabbarFragment;
import leapfrog_inc.rematching.Http.Requester.RoomRequester;
import leapfrog_inc.rematching.R;

public class SplashFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.fragment_splash, null);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                fetch();
            }
        }, 2000);

        return view;
    }

    private void fetch() {

        RoomRequester.getInstance().fetch(new RoomRequester.RoomRequesterCallback() {
            @Override
            public void didReceiveData(boolean result) {
                if (result) {
                    stackFragment(new TabbarFragment(), AnimationType.none);
                } else {
                    Dialog.show(getActivity(), Dialog.Style.error, "エラー", "通信に失敗しました", new Dialog.DialogCallback() {
                        @Override
                        public void didClose() {
                            fetch();
                        }
                    });
                }
            }
        });
    }
}
