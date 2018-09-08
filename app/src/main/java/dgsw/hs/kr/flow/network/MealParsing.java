package dgsw.hs.kr.flow.network;

import android.os.AsyncTask;
import android.widget.TextView;

import org.hyunjun.school.School;
import org.hyunjun.school.SchoolException;
import org.hyunjun.school.SchoolMenu;

import java.util.List;

/**
 * Created by Administrator on 2018-04-03.
 */

public class MealParsing extends AsyncTask<Integer, Void, String> {

    private MealFinish delegate;

    public interface MealFinish {
        public void mealFinish(String menu);
    }

    public MealParsing(MealFinish delegate) {
        this.delegate = delegate;
    }

    School api = new School(School.Type.HIGH, School.Region.DAEGU, "D100000282");
    int year;
    int month;
    int day;
    int hour;
    int minute;
    /*TextView mealTextResult;
    TextView breakfastResult;
    TextView lunchResult;
    TextView dinnerResult;*/
    List<SchoolMenu> menu = null;

    @Override
    protected String doInBackground(Integer... date) {
        //List<SchoolMenu> menu = null;

        try {
            year = date[0];
            month = date[1];
            day = date[2];
            hour = date[3];
            minute = date[4];

            int curTime;

            curTime = hour * 100 + minute;

            String result;

//            curTime = objects[3];/*
//            mealTextResult = (TextView) objects[4];
//            breakfastResult = (TextView) objects[5];
//            lunchResult = (TextView) objects[6];
//            dinnerResult = (TextView) objects[7];*/

            menu = api.getMonthlyMenu(year, month);

                if(curTime < 720) {
                    result = (menu.get(day).breakfast);
                } else if(curTime < 1300) {
                    result = (menu.get(day).lunch);
                } else if(curTime < 1900) {
                    result = (menu.get(day).dinner);
                } else {
                    result = (menu.get(day + 1).breakfast);
                }

                return result;

        }catch(SchoolException e){
            e.printStackTrace();

            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {

        delegate.mealFinish(result);

        /*        if(curTime != null) {
            if ((int) curTime >= 1 && (int) curTime <= 7) {
                mealTextResult.setText(menu.get(date).breakfast);
            } else if ((int) curTime > 8 && (int) curTime < 13) {
                mealTextResult.setText(menu.get(date).lunch);
            } else if((int) curTime > 13 && (int) curTime < 7){
                mealTextResult.setText(menu.get(date).dinner);
            }else{
                mealTextResult.setText(menu.get(date+1).breakfast);
            }
        }else{
            //mealTextResult.setText("아침\n"+ menu.get(date).breakfast + "점심\n" + menu.get(date).lunch + "저녁\n"+ menu.get(date).dinner);

            breakfastResult.setText("아침\n"+ menu.get(date).breakfast);
            lunchResult.setText("점심\n" + menu.get(date).lunch);
            dinnerResult.setText("저녁\n"+ menu.get(date).dinner);



        }*/

    }
}
