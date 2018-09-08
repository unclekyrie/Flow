package dgsw.hs.kr.flow.remote;

/**
 * Created by Administrator on 2018-04-18.
 */

public class ApiUtils {

    private ApiUtils() {}

    public static final String BASE_URL = "http://flow.cafe24app.com/";

    public static APIService getAPIService() {

        return RetrofitClient.getClient(BASE_URL).create(APIService.class);

    }
}