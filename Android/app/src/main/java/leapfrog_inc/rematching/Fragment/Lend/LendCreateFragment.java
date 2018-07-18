package leapfrog_inc.rematching.Fragment.Lend;

import android.Manifest;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;

import leapfrog_inc.rematching.Fragment.BaseFragment;
import leapfrog_inc.rematching.Fragment.Common.Dialog.Dialog;
import leapfrog_inc.rematching.Fragment.Common.Loading.Loading;
import leapfrog_inc.rematching.Http.ImageUploader;
import leapfrog_inc.rematching.Http.Requester.RoomRequester;
import leapfrog_inc.rematching.R;
import leapfrog_inc.rematching.System.DeviceUtility;
import leapfrog_inc.rematching.System.SaveData;

public class LendCreateFragment extends BaseFragment {

    public static int requestCodeGallery = 1000;
    public static int requestCodePermission = 1001;
    private Uri mImageUri;
    private Bitmap mBitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance) {

        View view = inflater.inflate(R.layout.fragment_lend_create, null);

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

        ((Button)view.findViewById(R.id.imageButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickImage();
            }
        });

        ((Button)view.findViewById(R.id.addButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickAdd();
            }
        });

        ((ScrollView)view.findViewById(R.id.scrollView)).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    hideKeyboard();
                }
                return false;
            }
        });
    }

    private void initContents(View view) {

        FrameLayout imageRootLayout = (FrameLayout)view.findViewById(R.id.imageRootLayout);
        ViewGroup.LayoutParams imageRootLayoutParams = imageRootLayout.getLayoutParams();
        imageRootLayoutParams.height = (DeviceUtility.getWindowSize(getActivity()).x - (int)(40 * DeviceUtility.getDeviceDensity(getActivity()))) / 2;
        imageRootLayout.setLayoutParams(imageRootLayoutParams);
    }

    private void onClickImage() {

        if (Build.VERSION.SDK_INT >= 23) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
            else{
                ActivityCompat.requestPermissions(getActivity(), new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE }, requestCodePermission);
            }
        } else {
            openGallery();
        }
    }

    private void openGallery() {

        Intent intentGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intentGallery.addCategory(Intent.CATEGORY_OPENABLE);
        intentGallery.setType("image/jpeg");

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "tmp.jpg");
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        mImageUri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);

        Intent intent = Intent.createChooser(intentCamera, "画像の選択");
        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {intentGallery});
        getActivity().startActivityForResult(intent, requestCodeGallery);
    }

    public void didGrantPermission() {
        openGallery();
    }

    public void didSelectImage(Intent data) {

        Uri uri = null;
        if (data != null) {
            Uri dataUri = data.getData();
            if (dataUri != null) {
                uri = dataUri;
            }
        }
        if (uri == null) {
            uri = mImageUri;
        }
        if(uri == null) {
            return;
        }
        MediaScannerConnection.scanFile(getActivity(), new String[]{ uri.getPath() }, new String[]{"image/jpeg"}, null);

        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
        } catch (Exception e) {
            return;
        }
        int bmpWidth = bitmap.getWidth();
        Matrix scale = new Matrix();
        scale.postScale((400.0f / (float)bmpWidth), (200.0f / (float)bmpWidth));
        mBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), scale, false);
        bitmap.recycle();
        bitmap = null;

        View view = getView();
        if (view != null) {
            ((ImageView)view.findViewById(R.id.roomImageView)).setImageBitmap(mBitmap);
        }
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void onClickAdd() {

        hideKeyboard();

        View view = getView();
        if (view == null) return;

        String name = ((EditText)view.findViewById(R.id.nameEditText)).getText().toString();
        String place = ((EditText)view.findViewById(R.id.placeEditText)).getText().toString();
        String rent = ((EditText)view.findViewById(R.id.rentEditText)).getText().toString();
        String phone = ((EditText)view.findViewById(R.id.phoneEditText)).getText().toString();
        String email = ((EditText)view.findViewById(R.id.emailEditText)).getText().toString();

        if (name.length() == 0) {
            showError("物件名を入力してください");
            return;
        }
        if (place.length() == 0) {
            showError("住所を入力してください");
            return;
        }
        if (rent.length() == 0) {
            showError("賃料を入力してください");
            return;
        }

        int rentInt = 0;
        try {
            rentInt = Integer.parseInt(rent);
        } catch (Exception e) {
            showError("賃料が正しくありません");
            return;
        }

        if ((phone.length() == 0) && (email.length() == 0)) {
            showError("電話番号・Emailのいずれかを入力してください");
            return;
        }

        Loading.start(getActivity());

        RoomRequester.post(name, place, rentInt, phone, email, new RoomRequester.RoomRequesterPostCallback() {
            @Override
            public void didReceiveData(boolean result, String roomId) {

                Loading.stop(getActivity());

                if (result) {
                    uploadImage(roomId);

                    SaveData saveData = SaveData.getInstance();
                    saveData.createdRoomIds.add(roomId);
                    saveData.save();
                } else {
                    leapfrog_inc.rematching.Fragment.Common.Dialog.Dialog.show(getActivity(), leapfrog_inc.rematching.Fragment.Common.Dialog.Dialog.Style.error, "エラー", "通信に失敗しました", new leapfrog_inc.rematching.Fragment.Common.Dialog.Dialog.DialogCallback() {
                        @Override
                        public void didClose() {

                        }
                    });
                }
            }
        });

    }

    private void uploadImage(String roomId) {

        if (mBitmap == null) {
            fetchRoom();
        } else {
            ImageUploader.ImageUploaderParameter param = new ImageUploader.ImageUploaderParameter();
            param.roomId = roomId;
            param.bitmap = mBitmap;
            ImageUploader uploader = new ImageUploader(new ImageUploader.ImageUploaderCallback() {
                @Override
                public void didReceive(boolean result) {
                    if (result) {
                        fetchRoom();
                    } else {
                        Dialog.show(getActivity(), Dialog.Style.error, "エラー", "通信に失敗しました", new Dialog.DialogCallback() {
                            @Override
                            public void didClose() {
                            }
                        });
                    }
                }
            });
            uploader.execute(param);
        }
    }

    private void fetchRoom() {

        RoomRequester.getInstance().fetch(new RoomRequester.RoomRequesterCallback() {
            @Override
            public void didReceiveData(boolean result) {
                stackFragment(new LendCreateCompleteFragment(), AnimationType.horizontal);
            }
        });
    }

    private void showError(String message) {
        Dialog.show(getActivity(), Dialog.Style.error, "エラー", message, new Dialog.DialogCallback() {
            @Override
            public void didClose() {

            }
        });
    }
}
