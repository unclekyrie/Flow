package dgsw.hs.kr.flow.helper;

import java.util.Calendar;

/**
 * Created by Administrator on 2018-05-01.
 */

public class CurrentTimeStorage {
    Calendar cal = Calendar.getInstance();
    int curYear = cal.get(Calendar.YEAR);
    int curMounth = cal.get(Calendar.MONTH); // +1  0번부터 시작, 1월은 0
    int curDate = cal.get(Calendar.DATE); // -1 급식 리스트, 0번 부터 시작
    int hour = cal.get(Calendar.HOUR_OF_DAY);
    int minute = cal.get(Calendar.MINUTE);

    public Calendar getCal() {
        return cal;
    }

    public void setCal(Calendar cal) {
        this.cal = cal;
    }

    public int getCurYear() {
        return curYear;
    }

    public void setCurYear(int curYear) {
        this.curYear = curYear;
    }

    public int getCurMounth() {
        return curMounth;
    }

    public void setCurMounth(int curMounth) {
        this.curMounth = curMounth;
    }

    public int getCurDate() {
        return curDate;
    }

    public void setCurDate(int curDate) {
        this.curDate = curDate;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }


}
