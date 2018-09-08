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
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.helper.CurrentTimeStorage;
import dgsw.hs.kr.flow.helper.DatabaseOpenHelper;
import dgsw.hs.kr.flow.model.OutGoPost;
import dgsw.hs.kr.flow.model.OutSleepPost;
import dgsw.hs.kr.flow.remote.APIService;
import dgsw.hs.kr.flow.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2018-04-25.
 */

public class OutSleepActivity extends AppCompatActivity {
    int version = 1;
    DatabaseOpenHelper helper;
    SQLiteDatabase database;
    Cursor cursor;
    String sql;

    Button btn_GOoutsleepDocList;
    Button btn_StartDate;
    Button btn_End_Date;
    Button btn_startTime;
    Button btn_endTime;
    Button btn_OutSleepRequest;
    TextView tv_startTime;
    TextView tv_endTime;
    EditText et_Reason;

    CurrentTimeStorage currentTimeStorage = new CurrentTimeStorage();

    private String outsleepYear = String.format("%04d", currentTimeStorage.getCurYear());
    private String outsleepMounth = String.format("%02d", currentTimeStorage.getCurMounth()+1);
    private String outsleepDay = String.format("%02d", currentTimeStorage.getCurDate());

    private String outsleepEndYear = String.format("%04d", currentTimeStorage.getCurYear());
    private String outsleepEndMounth = String.format("%02d", currentTimeStorage.getCurMounth()+1);
    private String outsleepEndDay = String.format("%02d", currentTimeStorage.getCurDate());

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
        setContentView(R.layout.out_sleep_activity);

        btn_GOoutsleepDocList = (Button) findViewById(R.id.btn_GOoutsleepDocList);
        btn_StartDate = (Button) findViewById(R.id.outsleepStartDateBtn);
        btn_startTime = (Button) findViewById(R.id.outsleepStartTimeBtn);
        btn_End_Date = (Button) findViewById(R.id.outsleepEndDateBtn);
        btn_endTime = (Button) findViewById(R.id.outsleepEndTimeBtn);
        btn_OutSleepRequest = (Button) findViewById(R.id.btn_OutSleepRequest);
        tv_startTime = (TextView) findViewById(R.id.tv_outsleepStartTime);
        tv_endTime = (TextView) findViewById(R.id.tv_outsleepEndTime);
        et_Reason = (EditText) findViewById(R.id.etOutSleepReason);

        mAPIService = ApiUtils.getAPIService();



        // 로그인 상태인지 체크
        checkLogin();

        final DatePickerDialog startDatePickerDialog = new DatePickerDialog(this, startDateSetListener, 2018, 1, 1);

        final DatePickerDialog endDatePickerDialog = new DatePickerDialog(this, endDateSetListener, 2018, 1, 1);

        final TimePickerDialog startTimeDialog = new TimePickerDialog(this, startTimeSetListener, curHour, curMinute, true);

        final TimePickerDialog endTimeDialog = new TimePickerDialog(this, endTimeSetListener, curHour, curMinute, true);

//         Show the date popup.
        btn_GOoutsleepDocList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OutSleepActivity.this, OutsleepDocListActivity.class);
                startActivity(i);
            }
        });

        // Show the date popup.
        btn_StartDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startDatePickerDialog.show();
            }
        });

        // Show the start time.
        btn_startTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                startTimeDialog.show();
            }
        });

        btn_End_Date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                endDatePickerDialog.show();
            }
        });

        // Show the end time.
        btn_endTime.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                endTimeDialog.show();
            }
        });


        // 외출 신청
        btn_OutSleepRequest.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                OutReason = et_Reason.getText().toString();
                getToken();
                requestOutSleep(getToken(), StartTime, EndTime, OutReason);

            }
        });

//        FirebaseMessaging.getInstance().notifyAll();
//        FirebaseInstanceId.getInstance().getToken();

    }

    private DatePickerDialog.OnDateSetListener startDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            outsleepYear = String.format("%04d", year);
            outsleepMounth = String.format("%02d", month+1);
            outsleepDay = String.format("%02d", dayOfMonth);
        }
    };

    private DatePickerDialog.OnDateSetListener endDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            outsleepEndYear = String.format("%04d", year);
            outsleepEndMounth = String.format("%02d", month+1);
            outsleepEndDay = String.format("%02d", dayOfMonth);
        }
    };

    // When you clicked the setting button, Set the [start time].
    private TimePickerDialog.OnTimeSetListener startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hourOfStartTime = String.format("%02d", hourOfDay);
            minuteOfStartTime = String.format("%02d", minute);

//            tv_startTime.setText(hourOfStartTime + ":" + minuteOfStartTime);

            StartTime = outsleepYear + "-" + outsleepMounth + "-" + outsleepDay + " " + hourOfStartTime + ":" + minuteOfStartTime;
            tv_startTime.setText(StartTime);

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

            EndTime = outsleepEndYear + "-" + outsleepEndMounth + "-" + outsleepEndDay + " " + hourOfEndTime + ":" + minuteOfEndTime;
            tv_endTime.setText(EndTime);
        }
    };

    public void checkLogin(){
        helper = new DatabaseOpenHelper(OutSleepActivity.this, DatabaseOpenHelper.tokenTableName, null, version);
        database = helper.getWritableDatabase();

        sql = "SELECT *" +
                "FROM "+ helper.tokenTableName;
        cursor = database.rawQuery(sql, null);

        if(cursor.getCount() == 0){
            Toast toast = Toast.makeText(OutSleepActivity.this, "로그인 후 이용 가능합니다.", Toast.LENGTH_SHORT);
            toast.show();
        }
    }


    public String getToken(){
        helper = new DatabaseOpenHelper(OutSleepActivity.this, DatabaseOpenHelper.tokenTableName, null, version);
        database = helper.getWritableDatabase();

        sql = "SELECT *" +
                "FROM "+ helper.tokenTableName;
        cursor = database.rawQuery(sql, null);
        cursor.moveToLast();
        token = cursor.getString(0);

        return token;
    }

    public void requestOutSleep(String Token, String StartTime, String EndTime, String reason){
//        sql = "SELECT *" +
//                "FROM "+ helper.tokenTableName;
//        cursor = database.rawQuery(sql, null);
//
//        token = cursor.getString(1);


        mAPIService.outsleepSavePost(Token, StartTime, EndTime, reason).enqueue(new Callback<OutSleepPost>() {
            @Override
            public void onResponse(Call<OutSleepPost> call, Response<OutSleepPost> response) {

                if(response.isSuccessful()) {
                    showResponseDialog(response.body().getStatus(), response.body().getMessage());

                    int accept = response.body().getData().getSleep_out().getAccept();
                    int idx = response.body().getData().getSleep_out().getIdx();
                    String start_time = response.body().getData().getSleep_out().getStartTime();
                    String end_time = response.body().getData().getSleep_out().getEndTime();
                    String reason = response.body().getData().getSleep_out().getReason();
                    int class_idx = response.body().getData().getSleep_out().getIdx();
                    String student_email = response.body().getData().getSleep_out().getStudentEmail();

                    helper = new DatabaseOpenHelper(OutSleepActivity.this, DatabaseOpenHelper.outSleepTableName, null, version);
                    database = helper.getWritableDatabase();

                    helper.insertOutSleepDocument(database, accept, idx, start_time, end_time, reason, class_idx, student_email);

                    Toast toast = Toast.makeText(OutSleepActivity.this, "db저장완료", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<OutSleepPost> call, Throwable t) {
                Toast toast = Toast.makeText(OutSleepActivity.this, "Unable to submit post to API.", Toast.LENGTH_SHORT);
                toast.show();
//                Log.e(TAG, "Unable to submit post to API.");
            }

        });
    }

    public void showResponseDialog(int status, String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(OutSleepActivity.this);

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
