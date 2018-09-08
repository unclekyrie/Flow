package dgsw.hs.kr.flow.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.helper.DatabaseOpenHelper;
import dgsw.hs.kr.flow.model.NoticeDetailPost;
import dgsw.hs.kr.flow.remote.APIService;
import dgsw.hs.kr.flow.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-06-24.
 */

public class NoticeDetailActivity extends AppCompatActivity{

    private TextView tv_noticeDetail;
    private APIService mAPIService;
    private int selectIdx;

    DatabaseOpenHelper helper;
    SQLiteDatabase database;


    DownloadManager dm;
    String fileLink;
    boolean isDownFile = false;
    String baseURL = "http://flow.cafe24app.com/";
    TextView tv_fileName;
    Button btn_filesSave;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_detail_activity);
        dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        tv_noticeDetail = (TextView) findViewById(R.id.tv_noticeDetail);
        mAPIService = ApiUtils.getAPIService();
        tv_fileName = (TextView)findViewById(R.id.fileNameView);
        btn_filesSave = (Button)findViewById(R.id.filesaveBtn);
        Intent intent = getIntent();
        selectIdx  = intent.getExtras().getInt("selectIdx");

        sendNoticeDetailPost(getToken(), selectIdx);
        Log.e("TAGTOKEN",getToken());
        btn_filesSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDownFile) {
                    Toast.makeText(NoticeDetailActivity.this, "파일다운", Toast.LENGTH_SHORT).show();
                    DownloadManager.Request request = new DownloadManager.Request(Uri.parse(baseURL + fileLink));
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    dm.enqueue(request);
                } else {
                    Toast.makeText(NoticeDetailActivity.this, "파일없음", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void sendNoticeDetailPost(String token, int idx) {
        mAPIService.noticeDetailSavePost(token,idx).enqueue(new Callback<NoticeDetailPost>() {
            @Override
            public void onResponse(Call<NoticeDetailPost> call, Response<NoticeDetailPost> response) {

                if(response.isSuccessful()) {
                        tv_noticeDetail.setText(response.body().getData().getContent() + "\n" +
                                response.body().getData().getWriter() + "\n" +
                                response.body().getData().getWrite_date() + "\n" +
                                response.body().getData().getModify_date() + "\n");
                        if (response.body().getData().getNoticeFiles()!=null) {
                            if (response.body().getData().getNoticeFiles().length > 0) {
                                fileLink = response.body().getData().getNoticeFiles()[0].getUpload_dir();
                                tv_fileName.setText(response.body().getData().getNoticeFiles()[0].getUpload_name());
                                isDownFile = true;
                            }
                        }


                }
            }

            @Override
            public void onFailure(Call<NoticeDetailPost> call, Throwable t) {
                Toast toast = Toast.makeText(NoticeDetailActivity.this, "Unable to submit post to API.", Toast.LENGTH_SHORT);
                toast.show();
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public String getToken(){
        helper = new DatabaseOpenHelper(NoticeDetailActivity.this, DatabaseOpenHelper.tokenTableName, null, 1);
        database = helper.getWritableDatabase();

        String sql = "SELECT *" +
                "FROM "+ helper.tokenTableName;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToLast();
        String token = cursor.getString(0);

        return token;
    }
}
