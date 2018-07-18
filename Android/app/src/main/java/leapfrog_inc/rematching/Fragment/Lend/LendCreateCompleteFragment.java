package leapfrog_inc.rematching.Fragment.Lend;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import leapfrog_inc.rematching.Fragment.BaseFragment;
import leapfrog_inc.rematching.R;

public class LendCreateCompleteFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.fragment_lend_create_complete, null);

        initAction(view);

        return view;
    }

    private void initAction(View view) {

    }
}
