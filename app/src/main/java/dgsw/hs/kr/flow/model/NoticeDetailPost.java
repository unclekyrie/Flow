package dgsw.hs.kr.flow.model;

/**
 * Created by Administrator on 2018-06-24.
 */

public class NoticeDetailPost {
    private int status;
    private String message;
    private Data data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public  class Data {
        private int idx;
        private String content;
        private String writer;
        private String write_date;
        private String modify_date;
        private NoticeFiles[] notice_files;

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getWriter() {
            return writer;
        }

        public void setWriter(String writer) {
            this.writer = writer;
        }

        public String getWrite_date() {
            return write_date;
        }

        public void setWrite_date(String write_date) {
            this.write_date = write_date;
        }

        public String getModify_date() {
            return modify_date;
        }

        public void setModify_date(String modify_date) {
            this.modify_date = modify_date;
        }

        public NoticeFiles[] getNoticeFiles() {
            return notice_files;
        }

        public void setNoticeFiles(NoticeFiles[] noticeFiles) {
            this.notice_files = noticeFiles;
        }

    }

    public class NoticeFiles {
        private int idx;
        private String upload_name;
        private String upload_dir;
        private int notice_idx;

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public String getUpload_name() {
            return upload_name;
        }

        public void setUpload_name(String upload_name) {
            this.upload_name = upload_name;
        }

        public String getUpload_dir() {
            return upload_dir;
        }

        public void setUpload_dir(String upload_dir) {
            this.upload_dir = upload_dir;
        }

        public int getNotice_idx() {
            return notice_idx;
        }

        public void setNotice_idx(int notice_idx) {
            this.notice_idx = notice_idx;
        }
    }

}
