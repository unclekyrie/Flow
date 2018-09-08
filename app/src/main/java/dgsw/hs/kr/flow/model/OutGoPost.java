package dgsw.hs.kr.flow.model;

/**
 * Created by Administrator on 2018-04-25.
 */

public class OutGoPost {
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
        private Go_Out go_out;

        public Go_Out getGoOut() { return go_out; }
        public void setGoOut(Go_Out value) { this.go_out = value; }
    }

    public class Go_Out {
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
