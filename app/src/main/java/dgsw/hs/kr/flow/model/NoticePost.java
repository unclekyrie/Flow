package dgsw.hs.kr.flow.model;

import java.util.List;

/**
 * Created by Administrator on 2018-06-23.
 */

public class NoticePost {
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

    public class Data {
        private NoticeList[] list;

        public NoticeList[] getList() {
            return list;
        }

        public void setList(NoticeList[] list) {
            this.list = list;
        }
    }

    public class NoticeList {
        private int idx;
        private String content;
        private String writer;
        private String write_date;
        private String modify_date;
        private NoticeFiles[] noticeFiles;

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

        public String getWriteDate() {
            return write_date;
        }

        public void setWriteDate(String writeDate) {
            this.write_date = writeDate;
        }

        public String getModifyDate() {
            return modify_date;
        }

        public void setModifyDate(String modifyDate) {
            this.modify_date = modifyDate;
        }

        public NoticeFiles[] getNoticeFiles() {
            return noticeFiles;
        }

        public void setNoticeFiles(NoticeFiles[] noticeFiles) {
            this.noticeFiles = noticeFiles;
        }
    }

    public class NoticeFiles {
        private int idx;
        private String uploadName;
        private String uploadDir;
        private int noticeIdx;

        public int getIdx() {
            return idx;
        }

        public void setIdx(int idx) {
            this.idx = idx;
        }

        public String getUploadName() {
            return uploadName;
        }

        public void setUploadName(String uploadName) {
            this.uploadName = uploadName;
        }

        public String getUploadDir() {
            return uploadDir;
        }

        public void setUploadDir(String uploadDir) {
            this.uploadDir = uploadDir;
        }

        public int getNoticeIdx() {
            return noticeIdx;
        }

        public void setNoticeIdx(int noticeIdx) {
            this.noticeIdx = noticeIdx;
        }

    }

}
