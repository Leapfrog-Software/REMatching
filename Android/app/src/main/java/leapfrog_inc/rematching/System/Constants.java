package leapfrog_inc.rematching.System;

public class Constants {

    public static String ServerRootUrl = "http://10.0.2.2/REMatching/";
    public static String ServerApiUrl = Constants.ServerRootUrl + "srv.php";
    public static String RoomImageDirectory = Constants.ServerRootUrl + "data/image/room/";

    public static int HttpConnectTimeout = 10000;
    public static int HttpReadTimeout = 10000;

    public static class SharedPreferenceKey {
        public static String Key = "REMatching";
        public static String createdRoomIds = "CreatedRoomIds";
    }
}
