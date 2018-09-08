package dgsw.hs.kr.flow.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.adapter.OutgoListAdapter;
import dgsw.hs.kr.flow.adapter.OutsleepListAdapter;
import dgsw.hs.kr.flow.helper.DatabaseOpenHelper;

/**
 * Created by Administrator on 2018-06-25.
 */

public class OutsleepDocListActivity extends AppCompatActivity{

    private ListView lv_outDoc;
    private OutsleepListAdapter adapter;

    DatabaseOpenHelper helper;
    SQLiteDatabase database;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_sleep_doc_list_activity);

        helper = new DatabaseOpenHelper(OutsleepDocListActivity.this, DatabaseOpenHelper.outSleepTableName, null, 1);
        String sql = "SELECT * FROM " + helper.outSleepTableName;
        database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        adapter = new OutsleepListAdapter();
        lv_outDoc = (ListView) findViewById(R.id.lv_outsleepDoc);

        lv_outDoc.setAdapter(adapter);

        while (cursor.moveToNext()){
            int accept = cursor.getInt(0);
            String startTime = cursor.getString(2);
            String endTime = cursor.getString(3);
            String reason = cursor.getString(4);
            String studentEmail = cursor.getString(6);

            adapter.addOutSleepVO(accept, startTime, endTime, reason, studentEmail);
        }

    }

}
