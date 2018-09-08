package dgsw.hs.kr.flow.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.hyunjun.school.SchoolMenu;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.helper.CurrentTimeStorage;
import dgsw.hs.kr.flow.network.MealParsing;

/**
 * Created by Administrator on 2018-04-03.
 */

public class SchoolMealView extends AppCompatActivity implements MealParsing.MealFinish {
    CurrentTimeStorage currentTimeStorage = new CurrentTimeStorage();

    private int curYear = currentTimeStorage.getCurYear();
    private int curMounth = currentTimeStorage.getCurMounth() +1;
    private int curDate = currentTimeStorage.getCurDate() -1;
    private int hour = currentTimeStorage.getHour();
    private int minute = currentTimeStorage.getMinute();
    TextView mealResult;


//    Calendar cal = Calendar.getInstance();
//    int curYear = cal.get(Calendar.YEAR);
//    int curMounth = cal.get(Calendar.MONTH) + 1; //  0번부터 시작, 1월은 0
//    int curDate = cal.get(Calendar.DATE) - 1; // 급식 리스트, 0번 부터 시작
//    int hour = cal.get(Calendar.HOUR_OF_DAY);
//    int minute = cal.get(Calendar.MINUTE);


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.schoolmealview_activity);

        mealResult = (TextView) findViewById(R.id.mealResult);

        getMealData();

    }

    public void getMealData() {
        MealParsing mealParsing = new MealParsing(this);
        mealParsing.execute(curYear, curMounth, curDate, hour, minute);
    }

    @Override
    public void mealFinish(String menu) {
        mealResult.setText(menu);
    }
}
