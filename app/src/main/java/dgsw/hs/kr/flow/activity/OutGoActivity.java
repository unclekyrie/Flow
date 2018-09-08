package dgsw.hs.kr.flow.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.helper.DatabaseOpenHelper;
import dgsw.hs.kr.flow.helper.CurrentTimeStorage;
import dgsw.hs.kr.flow.model.LoginPost;
import dgsw.hs.kr.flow.model.OutGoPost;
import dgsw.hs.kr.flow.remote.APIService;
import dgsw.hs.kr.flow.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-04-25.
 */

public class OutGoActivity extends AppCompatActivity {
    int version = 1;
    DatabaseOpenHelper helper;
    SQLiteDatabase database;
    Cursor cursor;
    String sql;

    Button btn_GOoutgoDocList;
    Button btn_Date;
    Button btn_startTime;
    Button btn_endTime;
    Button btn_OutRequest;
    TextView tv_startTime;
    TextView tv_endTime;
    EditText et_Reason;

    CurrentTimeStorage currentTimeStorage = new CurrentTimeStorage();

    private String outgoYear = String.format("%04d", currentTimeStorage.getCurYear());
    private String outgoMounth = String.format("%02d", currentTimeStorage.getCurMounth()+1);
    private String outgoDay = String.format("%02d", currentTimeStorage.getCurDate());

    private int curHour = currentTimeStorage.getHour();
    private int curMinute = currentTimeStorage.getMinute();

    private String hourOfStartTime;
    private String minuteOfStartTime;

    private String hourOfEndTime;
    private String minuteOfEndTime;

    private String StartTime;
    private String EndTime;
    private String OutReason;

    private String token;
    private APIService mAPIService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_go_activity);

        btn_GOoutgoDocList = (Button) findViewById(R.id.btn_GOoutgoDocList);
        btn_Date = (Button) findViewById(R.id.outgoDateBtn);
        btn_startTime = (Button) findViewById(R.id.outgoStartTimeBtn);
        btn_endTime = (Button) findViewById(R.id.outgoEndTimeBtn);
        btn_OutRequest = (Button) findViewById(R.id.btn_OutRequest);
        tv_startTime = (TextView) findViewById(R.id.tv_outgoStartTime);
        tv_endTime = (TextView) findViewById(R.id.tv_outgoEndTime);
        et_Reason = (EditText) findViewById(R.id.etOutGoReason);

        mAPIService = ApiUtils.getAPIService();

        helper = new DatabaseOpenHelper(OutGoActivity.this, DatabaseOpenHelper.tokenTableName, null, version);
        database = helper.getWritableDatabase();

        // 로그인 상태인지 체크
        checkLogin();

        final DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, 2018, 1, 1);

        final TimePickerDialog startTimeDialog = new TimePickerDialog(this, startTimeSetListener, curHour, curMinute, true);

        final TimePickerDialog endTimeDialogdialog = new TimePickerDialog(this, endTimeSetListener, curHour, curMinute, true);

        // Show the date popup.
        btn_GOoutgoDocList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OutGoActivity.this, OutgoDocListActivity.class);
                startActivity(i);
            }
        });

        // Show the date popup.
        btn_Date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });

        // Show the start time.
        btn_startTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startTimeDialog.show();
            }
        });

        // Show the end time.
        btn_endTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                endTimeDialogdialog.show();
            }
        });


        // 외출 신청
        btn_OutRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                OutReason = et_Reason.getText().toString();
                getToken();
                requestTheOutGo(token, StartTime, EndTime, OutReason);

            }
        });

//        FirebaseMessaging.getInstance().notifyAll();
//        FirebaseInstanceId.getInstance().getToken();

    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
           outgoYear = String.format("%04d", year);
           outgoMounth = String.format("%02d", month+1);
           outgoDay = String.format("%02d", dayOfMonth);
        }
    };

    // When you clicked the setting button, Set the [start time].
    private TimePickerDialog.OnTimeSetListener startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourOfStartTime = String.format("%02d", hourOfDay);
            minuteOfStartTime = String.format("%02d", minute);

            tv_startTime.setText(hourOfStartTime + ":" + minuteOfStartTime);

            StartTime = outgoYear + "-" + outgoMounth + "-" + outgoDay + " " + hourOfStartTime + ":" + minuteOfStartTime;

            System.out.println(StartTime);
        }
    };

    // When you clicked the setting button, Set the [end time].
    private TimePickerDialog.OnTimeSetListener endTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourOfEndTime = String.format("%02d", hourOfDay);
            minuteOfEndTime= String.format("%02d", minute);

            tv_endTime.setText(hourOfEndTime + ":" + minuteOfEndTime);

            EndTime = outgoYear + "-" + outgoMounth + "-" + outgoDay + " " + hourOfEndTime + ":" + minuteOfEndTime;
        }
    };

    public void checkLogin(){


        sql = "SELECT *" +
                "FROM "+ helper.tokenTableName;
        cursor = database.rawQuery(sql, null);

        if(cursor.getCount() == 0){
            Toast toast = Toast.makeText(OutGoActivity.this, "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public String getToken(){
        helper = new DatabaseOpenHelper(OutGoActivity.this, DatabaseOpenHelper.tokenTableName, null, version);
        database = helper.getWritableDatabase();

        sql = "SELECT *" +
                "FROM "+ helper.tokenTableName;
        cursor = database.rawQuery(sql, null);
        cursor.moveToLast();
        token = cursor.getString(0);

        return token;
    }

    public void requestTheOutGo(String Token, String StartTime, String EndTime, String reason){
//        sql = "SELECT *" +
//                "FROM "+ helper.tokenTableName;
//        cursor = database.rawQuery(sql, null);
//
//        token = cursor.getString(1);


        mAPIService.outgoSavePost(Token, StartTime, EndTime, reason).enqueue(new Callback<OutGoPost>() {
            @Override
            public void onResponse(Call<OutGoPost> call, Response<OutGoPost> response) {

                if(response.isSuccessful()) {
                    showResponseDialog(response.body().getStatus(), response.body().getMessage());

                    int accept = response.body().getData().getGoOut().getAccept();
                    int idx = response.body().getData().getGoOut().getIdx();
                    String start_time = response.body().getData().getGoOut().getStartTime();
                    String end_time = response.body().getData().getGoOut().getEndTime();
                    String reason = response.body().getData().getGoOut().getReason();
                    int class_idx = response.body().getData().getGoOut().getIdx();
                    String student_email = response.body().getData().getGoOut().getStudentEmail();

                    helper = new DatabaseOpenHelper(OutGoActivity.this, DatabaseOpenHelper.outGoTableName, null, version);
                    database = helper.getWritableDatabase();

                    helper.insertOutDocument(database, accept, idx, start_time, end_time, reason, class_idx, student_email);

                    Toast toast = Toast.makeText(OutGoActivity.this, "db저장완료", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<OutGoPost> call, Throwable t) {
                Toast toast = Toast.makeText(OutGoActivity.this, "Unable to submit post to API.", Toast.LENGTH_SHORT);
                toast.show();
//                Log.e(TAG, "Unable to submit post to API.");
            }

        });
    }

    public void showResponseDialog(int status, String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OutGoActivity.this);

        // 제목셋팅
        alertDialogBuilder.setTitle("response");

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage(status + "\n" + message)
                .setNegativeButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();

//                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                                startActivity(intent);
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }

}
