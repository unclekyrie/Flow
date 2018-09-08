package dgsw.hs.kr.flow.activity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.List;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.adapter.OutgoListAdapter;
import dgsw.hs.kr.flow.helper.DatabaseOpenHelper;

/**
 * Created by Administrator on 2018-06-22.
 */

public class OutgoDocListActivity extends AppCompatActivity{

    private ListView lv_outDoc;
    private OutgoListAdapter adapter;

    DatabaseOpenHelper helper;
    SQLiteDatabase database;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_go_doc_list_activity);

        helper = new DatabaseOpenHelper(OutgoDocListActivity.this, DatabaseOpenHelper.outGoTableName, null, 1);
        String sql = "SELECT * FROM " + helper.outGoTableName;
        database = helper.getReadableDatabase();
        Cursor cursor = database.rawQuery(sql, null);

        adapter = new OutgoListAdapter();
        lv_outDoc = (ListView) findViewById(R.id.lv_outgoDoc);

        lv_outDoc.setAdapter(adapter);

        while (cursor.moveToNext()){
            int accept = cursor.getInt(0);
            String startTime = cursor.getString(2);
            String endTime = cursor.getString(3);
            String reason = cursor.getString(4);
            String studentEmail = cursor.getString(6);

            adapter.addVO(accept, startTime, endTime, reason, studentEmail);
        }


    }

}
