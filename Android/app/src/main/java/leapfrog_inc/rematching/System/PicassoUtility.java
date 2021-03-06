package leapfrog_inc.rematching.System;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import leapfrog_inc.rematching.R;

public class PicassoUtility {

    public static void getRoomImage(Context context, String url, ImageView imageView) {
        if (url.length() == 0) {
            imageView.setImageResource(R.drawable.no_image);
            return;
        }
        Picasso.with(context)
                .load(url)
                .networkPolicy(NetworkPolicy.NO_STORE)
                .noFade()
                .error(R.drawable.no_image)
                .into(imageView);
    }
}
