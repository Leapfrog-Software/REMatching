package leapfrog_inc.rematching.Fragment.Lend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.List;

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

        ((Button)view.findViewById(R.id.backButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Fragment> fragments = getActivity().getSupportFragmentManager().getFragments();
                for (int i = 0; i < fragments.size(); i++) {
                    if (fragments.get(i) instanceof LendFragment) {
                        ((LendFragment) fragments.get(i)).resetListView(null);
                    }
                }
                for (int i = 0; i < fragments.size(); i++) {
                    Fragment fragment = fragments.get(i);
                    if ((fragment instanceof LendCreateFragment) || (fragment instanceof LendCreateCompleteFragment)) {
                        ((BaseFragment)fragment).popFragment(AnimationType.horizontal);
                    }
                }
            }
        });
    }
}
