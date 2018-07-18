package leapfrog_inc.rematching.Fragment.Borrow;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import leapfrog_inc.rematching.Fragment.BaseFragment;
import leapfrog_inc.rematching.Http.Requester.RoomRequester;
import leapfrog_inc.rematching.R;
import leapfrog_inc.rematching.System.Constants;
import leapfrog_inc.rematching.System.DeviceUtility;
import leapfrog_inc.rematching.System.PicassoUtility;

public class BorrowDetailFragment extends BaseFragment {

    private RoomRequester.RoomData mRoomData;

    public void set(RoomRequester.RoomData roomData) {
        mRoomData = roomData;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.fragment_borrow_detail, null);

        initAction(view);
        initContents(view);

        return view;
    }

    private void initAction(View view) {

        ((ImageButton)view.findViewById(R.id.backButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popFragment(AnimationType.horizontal);
            }
        });

        ((Button)view.findViewById(R.id.phoneActiveButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mRoomData.phone));
                startActivity(intent);
            }
        });

        ((Button)view.findViewById(R.id.emailActiveButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SENDTO);

                intent.setType("text/plain");
                intent.setData(Uri.parse("mailto:" + mRoomData.email));
                intent.putExtra(Intent.EXTRA_SUBJECT, "お問い合わせ");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, null));
            }
        });
    }

    private void initContents(View view) {

        PicassoUtility.getRoomImage(getActivity(), Constants.RoomImageDirectory + mRoomData.id, (ImageView)view.findViewById(R.id.roomImageView));

        FrameLayout imageRootLayout = (FrameLayout)view.findViewById(R.id.imageRootLayout);
        ViewGroup.LayoutParams imageRootLayoutParams = imageRootLayout.getLayoutParams();
        imageRootLayoutParams.height = DeviceUtility.getWindowSize(getActivity()).x / 2;
        imageRootLayout.setLayoutParams(imageRootLayoutParams);

        ((TextView)view.findViewById(R.id.nameTextView)).setText(mRoomData.name);

        StringBuffer review = new StringBuffer();
        review.append((mRoomData.score <= 0) ? "☆" : "★");
        review.append((mRoomData.score <= 1) ? "☆" : "★");
        review.append((mRoomData.score <= 2) ? "☆" : "★");
        review.append((mRoomData.score <= 3) ? "☆" : "★");
        review.append((mRoomData.score <= 4) ? "☆" : "★");
        review.append(" " + String.valueOf(mRoomData.review) + "件のレビュー");
        ((TextView)view.findViewById(R.id.reviewTextView)).setText(review.toString());

        ((TextView)view.findViewById(R.id.placeTextView)).setText(mRoomData.place);

        String rent = String.format("%,d", mRoomData.rent);
        ((TextView)view.findViewById(R.id.rentTextView)).setText(rent + "円");

        if (mRoomData.phone.length() > 0) {
            view.findViewById(R.id.phoneActiveButton).setVisibility(View.VISIBLE);
            view.findViewById(R.id.phoneInactiveButton).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.phoneActiveButton).setVisibility(View.GONE);
            view.findViewById(R.id.phoneInactiveButton).setVisibility(View.VISIBLE);
        }

        if (mRoomData.email.length() > 0) {
            view.findViewById(R.id.emailActiveButton).setVisibility(View.VISIBLE);
            view.findViewById(R.id.emailInactiveButton).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.emailActiveButton).setVisibility(View.GONE);
            view.findViewById(R.id.emailInactiveButton).setVisibility(View.VISIBLE);
        }
    }
}
