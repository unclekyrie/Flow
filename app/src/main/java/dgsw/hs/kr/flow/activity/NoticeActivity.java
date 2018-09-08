package dgsw.hs.kr.flow.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.adapter.NoticeListAdapter;
import dgsw.hs.kr.flow.helper.DatabaseOpenHelper;
import dgsw.hs.kr.flow.model.LoginPost;
import dgsw.hs.kr.flow.model.NoticePost;
import dgsw.hs.kr.flow.remote.APIService;
import dgsw.hs.kr.flow.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-06-23.
 */

public class NoticeActivity extends AppCompatActivity{
    private APIService mAPIService;
    private NoticeListAdapter adapter;
    private ListView lv_notice;
    private int selectIdx;

    DatabaseOpenHelper helper;
    SQLiteDatabase database;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notice_activity);

        mAPIService = ApiUtils.getAPIService();
        adapter = new NoticeListAdapter();
        lv_notice = (ListView)findViewById(R.id.lv_noticeDoc);



//        adapter.notifyDataSetChanged();

        sendNoticePost(getToken());

//        lv_notice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getApplicationContext(), NoticeDetailActivity.class);
//                intent.putExtra("selectIdx", adapter.getItem(position).getIdx());
//                startActivity(intent);
//            }
//        });

    }

    public void sendNoticePost(String token) {
        mAPIService.noticeSavePost(token).enqueue(new Callback<NoticePost>() {
            @Override
            public void onResponse(Call<NoticePost> call, Response<NoticePost> response) {

                if(response.isSuccessful()) {
                  //response.body().getData().getList()[0].getContent();

                  for(int i = 0; i < response.body().getData().getList().length; i++){
                      String content = response.body().getData().getList()[i].getContent();
                      String writer = response.body().getData().getList()[i].getWriter();
                      String writeDate = response.body().getData().getList()[i].getWriteDate();
                      String modifyDate = response.body().getData().getList()[i].getModifyDate();
                      int idx = response.body().getData().getList()[i].getIdx();
                      adapter.addNoticeVO(content, writer, writeDate, modifyDate, idx);
                  }
                    lv_notice.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<NoticePost> call, Throwable t) {
                Toast toast = Toast.makeText(NoticeActivity.this, "Unable to submit post to API.", Toast.LENGTH_SHORT);
                toast.show();
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public String getToken(){
        helper = new DatabaseOpenHelper(NoticeActivity.this, DatabaseOpenHelper.tokenTableName, null, 1);
        database = helper.getWritableDatabase();

        String sql = "SELECT *" +
                "FROM "+ helper.tokenTableName;
        Cursor cursor = database.rawQuery(sql, null);
        cursor.moveToLast();
        String token = cursor.getString(0);

        return token;
    }

}
