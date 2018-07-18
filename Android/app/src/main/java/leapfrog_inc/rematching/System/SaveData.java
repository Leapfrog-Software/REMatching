package leapfrog_inc.rematching.System;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;

public class SaveData {

    private static SaveData container = null;

    public Context mContext;
    public ArrayList<String> createdRoomIds = new ArrayList<String>();

    private SaveData(){}

    public static SaveData getInstance(){
        if(container == null){
            container = new SaveData();
        }
        return container;
    }

    public void initialize(Context context) {

        mContext = context;

        SharedPreferences data = context.getSharedPreferences(Constants.SharedPreferenceKey.Key, Context.MODE_PRIVATE);

        createdRoomIds = new ArrayList<String>();
        String rawCreatedRoomIds = data.getString(Constants.SharedPreferenceKey.createdRoomIds, "");
        String[] splitedCreatedRoomIds = rawCreatedRoomIds.split(",");
        for (int i = 0; i < splitedCreatedRoomIds.length; i++) {
            createdRoomIds.add(splitedCreatedRoomIds[i]);
        }
    }

    public void save() {

        SharedPreferences data = mContext.getSharedPreferences(Constants.SharedPreferenceKey.Key, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = data.edit();

        StringBuffer rawCreatedRoomIds = new StringBuffer();
        for (int i = 0; i < createdRoomIds.size(); i++) {
            if (i > 0) rawCreatedRoomIds.append(",");
            rawCreatedRoomIds.append(createdRoomIds.get(i));
        }
        editor.putString(Constants.SharedPreferenceKey.createdRoomIds, rawCreatedRoomIds.toString());

        editor.apply();
    }
}
