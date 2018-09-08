package dgsw.hs.kr.flow.model;

/**
 * Created by Administrator on 2018-06-25.
 */

public class OutSleepPost {
    private int status;
    private String message;
    private Data data;

    public int getStatus() { return status; }
    public void setStatus(int value) { this.status = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public Data getData() { return data; }
    public void setData(Data value) { this.data = value; }

    public class Data {
        private Sleep_Out sleep_out;

        public Sleep_Out getSleep_out() {
            return sleep_out;
        }

        public void setSleep_out(Sleep_Out sleep_out) {
            this.sleep_out = sleep_out;
        }
    }

    public class Sleep_Out {
        private int accept;
        private int idx;
        private String start_time;
        private String end_time;
        private String reason;
        private int class_idx;
        private String student_email;

        public int getAccept() { return accept; }
        public void setAccept(int value) { this.accept = value; }

        public int getIdx() { return idx; }
        public void setIdx(int value) { this.idx = value; }

        public String getStartTime() { return start_time; }
        public void setStartTime(String value) { this.start_time = value; }

        public String getEndTime() { return end_time; }
        public void setEndTime(String value) { this.end_time = value; }

        public String getReason() { return reason; }
        public void setReason(String value) { this.reason = value; }

        public int getClassIdx() { return class_idx; }
        public void setClassIdx(int value) { this.class_idx = value; }

        public String getStudentEmail() { return student_email; }
        public void setStudentEmail(String value) { this.student_email = value; }
    }
}
