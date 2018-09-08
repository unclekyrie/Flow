package dgsw.hs.kr.flow.remote;

/**
 * Created by Administrator on 2018-04-18.
 */

import dgsw.hs.kr.flow.activity.OutGoActivity;
import dgsw.hs.kr.flow.helper.DatabaseOpenHelper;
import dgsw.hs.kr.flow.model.LoginPost;
import dgsw.hs.kr.flow.model.NoticeDetailPost;
import dgsw.hs.kr.flow.model.NoticePost;
import dgsw.hs.kr.flow.model.OutGoPost;
import dgsw.hs.kr.flow.model.OutSleepPost;
import dgsw.hs.kr.flow.model.RegistPost;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIService {

    @POST("/auth/signup")
    @FormUrlEncoded
    Call<RegistPost> registSavePost(@Field("email") String email,
                              @Field("pw") String pw,
                              @Field("name") String name,
                              @Field("mobile") String mobile,
                              @Field("gender") String gender,
                              @Field("class_idx") int class_idx,
                              @Field("class_number") int class_number);

    @POST("/auth/signin")
    @FormUrlEncoded
    Call<LoginPost> loginSavePost(@Field("email") String email,
                                  @Field("pw") String pw,
                                  @Field("registration_token") String registration_token);

    @POST("/out/go")
    @FormUrlEncoded
    Call<OutGoPost> outgoSavePost(@Header("x-access-token") String token,
                                  @Field("start_time") String start_time,
                                  @Field("end_time") String end_time,
                                  @Field("reason") String reason);

    @POST("/out/sleep")
    @FormUrlEncoded
    Call<OutSleepPost> outsleepSavePost(@Header("x-access-token") String token,
                                        @Field("start_time") String start_time,
                                        @Field("end_time") String end_time,
                                        @Field("reason") String reason);

    @GET("/notice")
    Call<NoticePost> noticeSavePost(@Header("x-access-token") String token);

    @GET("/notice/{notice_idx}")
    Call<NoticeDetailPost> noticeDetailSavePost(@Header("x-access-token") String token, @Path("notice_idx")int notice_idx);
}
