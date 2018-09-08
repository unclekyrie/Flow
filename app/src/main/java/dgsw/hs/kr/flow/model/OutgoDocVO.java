package dgsw.hs.kr.flow.model;

/**
 * Created by Administrator on 2018-06-22.
 */

public class OutgoDocVO {
    private int accept;
    private String startTime;
    private String endTime;
    private String reason;
    private String student_email;

    public int getAccept() {
        return accept;
    }

    public void setAccept(int accept) {
        this.accept = accept;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStudent_email() {
        return student_email;
    }

    public void setStudent_email(String student_email) {
        this.student_email = student_email;
    }
}
