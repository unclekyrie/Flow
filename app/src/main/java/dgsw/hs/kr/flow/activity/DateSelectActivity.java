package dgsw.hs.kr.flow.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.hyunjun.school.SchoolMenu;

import java.util.List;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.network.MealParsing;



/**
 * Created by Administrator on 2018-04-04.
 */

public class DateSelectActivity extends AppCompatActivity implements MealParsing.MealFinish {

    DatePicker mealDatePicker;
    TextView txtDate;

    int SelectedYear;
    int SelectedMounthOfYear;
    int SelectedDayOfMonth;
    int hour;
    int minute;

    boolean dateFlag = false;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.date_select_activity);

        mealDatePicker = (DatePicker)findViewById(R.id.mealDatepicker);
        txtDate = (TextView)findViewById(R.id.txtdate);

        RadioGroup group=(RadioGroup)findViewById(R.id.radioGroup);
        RadioButton breakfastBtn=(RadioButton)findViewById(R.id.breakfastRadioBtn);
        RadioButton lunchBtn=(RadioButton)findViewById(R.id.lunchRadioBtn);
        RadioButton dinnerBtn=(RadioButton)findViewById(R.id.dinnerRadioBtn);

        mealDatePicker.init(mealDatePicker.getYear(), mealDatePicker.getMonth(), mealDatePicker.getDayOfMonth(),
                new DatePicker.OnDateChangedListener() {
                    //값이 바뀔때마다 텍스트뷰의 값을 바꿔준다.
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // TODO Auto-generated method stub
                        //monthOfYear는 0값이 1월을 뜻하므로 1을 더해줌 나머지는 같다.
                        txtDate.setText(String.format("%d/%d/%d", year, monthOfYear + 1, dayOfMonth));

                        SelectedYear = year;
                        SelectedMounthOfYear = monthOfYear+1;
                        SelectedDayOfMonth = dayOfMonth;

                        //예외
                        dateFlag = true;
                    }
                });

        findViewById(R.id.mealDatepickerBtn).setOnClickListener(
                new Button.OnClickListener() {
                    public void onClick(View v) {
                        if (dateFlag != true){
                            Toast.makeText(getApplicationContext(), "날짜를 선택해주세요.", Toast.LENGTH_SHORT).show();
                        }else {
                            new MealParsing(DateSelectActivity.this).execute(SelectedYear, SelectedMounthOfYear, SelectedDayOfMonth - 1, hour, minute);
                        }
                    }
                }
        );

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.breakfastRadioBtn:
                        hour = 7;
                        minute = 20;
                        break;
                    case R.id.lunchRadioBtn:
                        hour = 12;
                        minute = 40;
                        break;
                    case R.id.dinnerRadioBtn:
                        hour = 18;
                        minute = 40;
                        break;
                    default:


                }
            }
        });

    }

    @Override
    public void mealFinish(String menu) {
//        Intent i = new Intent(DateSelectActivity.this, SelectedMealView.class);
//        i.putExtra("menuResult", menu);
//        startActivity(i);

//        AlertDialog.Builder alertdialog = new AlertDialog.Builder(DateSelectActivity.this);
//        //다이얼로그의 내용을 설정합니다.
//        alertdialog.setTitle(SelectedYear + "/" + SelectedMounthOfYear + "/" +  SelectedDayOfMonth);
//        alertdialog.setMessage(menu);



        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DateSelectActivity.this);

        // 제목셋팅
        alertDialogBuilder.setTitle(SelectedYear + "/" + SelectedMounthOfYear + "/" +  SelectedDayOfMonth);

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage(menu)
                .setNegativeButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();

    }

}
