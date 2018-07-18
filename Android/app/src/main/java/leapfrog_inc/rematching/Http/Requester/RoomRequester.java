package leapfrog_inc.rematching.Http.Requester;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import leapfrog_inc.rematching.Http.HttpManager;
import leapfrog_inc.rematching.System.Base64Utility;
import leapfrog_inc.rematching.System.Constants;

public class RoomRequester {

    public static class RoomData {

        public String id;
        public boolean approval;
        public int score;
        public int review;
        public String name;
        public String place;
        public int rent;
        public String phone;
        public String email;

        static public RoomData create(JSONObject json) {

            try {
                RoomData roomData = new RoomData();
                roomData.id = json.getString("id");
                roomData.approval = json.getString("approval").equals("1");
                roomData.score = Integer.parseInt(json.getString("score"));
                roomData.review = Integer.parseInt(json.getString("review"));
                roomData.name = Base64Utility.decode(json.getString("name"));
                roomData.place = Base64Utility.decode(json.getString("place"));
                roomData.rent = Integer.parseInt(json.getString("rent"));
                roomData.phone = Base64Utility.decode(json.getString("phone"));
                roomData.email = Base64Utility.decode(json.getString("email"));
                return roomData;

            } catch (Exception e) { }

            return null;
        }
    }

    private static RoomRequester mInstance = null;
    public ArrayList<RoomData> dataList = new ArrayList<RoomData>();

    private RoomRequester(){}

    public static RoomRequester getInstance(){
        if(mInstance == null){
            mInstance = new RoomRequester();
        }
        return mInstance;
    }

    public void fetch(final RoomRequesterCallback callback){

        HttpManager httpManager = new HttpManager(new HttpManager.HttpCallback() {
            @Override
            public void didReceiveData(boolean result, String data) {
                if (result) {
                    try {
                        JSONObject json = new JSONObject(data);
                        if (json.getString("result").equals("0")) {
                            ArrayList<RoomData> datas = new ArrayList<RoomData>();
                            JSONArray dataArray = json.getJSONArray("data");
                            for (int i = 0; i < dataArray.length(); i++) {
                                RoomData roomData = RoomData.create(dataArray.getJSONObject(i));
                                if (roomData != null) {
                                    datas.add(roomData);
                                }
                            }
                            dataList = datas;
                            callback.didReceiveData(true);
                            return;
                        }
                    } catch (Exception e) {}
                }
                callback.didReceiveData(false);
            }
        });

        StringBuffer param = new StringBuffer();
        param.append("command=getRoom");
        httpManager.execute(Constants.ServerApiUrl, "POST", param.toString());
    }

    public interface RoomRequesterCallback {
        void didReceiveData(boolean result);
    }

    public RoomData query(String roomId) {
        for (int i = 0; i < dataList.size(); i++) {
            RoomData roomData = dataList.get(i);
            if (roomData.id.equals(roomId)) {
                return roomData;
            }
        }
        return null;
    }
}
