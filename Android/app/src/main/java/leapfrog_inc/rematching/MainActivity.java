package leapfrog_inc.rematching;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import leapfrog_inc.rematching.Fragment.Lend.LendCreateFragment;
import leapfrog_inc.rematching.Fragment.Splash.SplashFragment;
import leapfrog_inc.rematching.Fragment.Tabbar.TabbarFragment;
import leapfrog_inc.rematching.System.SaveData;

public class MainActivity extends AppCompatActivity {

    public TabbarFragment mTabbarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SaveData.getInstance().initialize(this);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.tabContainer, new SplashFragment());
        transaction.commitAllowingStateLoss();
    }

    public int getSubContainerId() {
        return R.id.subContainer;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode != LendCreateFragment.requestCodePermission) {
            return;
        }

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) instanceof LendCreateFragment) {
                ((LendCreateFragment)fragments.get(i)).didGrantPermission();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != LendCreateFragment.requestCodeGallery) {
            return;
        }
        if(resultCode != RESULT_OK) {
            return ;
        }

        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int i = 0; i < fragments.size(); i++) {
            if (fragments.get(i) instanceof LendCreateFragment) {
                ((LendCreateFragment)fragments.get(i)).didSelectImage(data);
            }
        }
    }
}
