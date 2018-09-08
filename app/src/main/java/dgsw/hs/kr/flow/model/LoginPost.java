package dgsw.hs.kr.flow.model;

public class LoginPost {
    private int status;
    private String message;
    private Data data;

//    @Override
//    public String toString() {
//        return "token= " + data.token;
//    }

    public String toTokenString() {
        return data.token;
    }

    public int getStatus() { return status; }
    public void setStatus(int value) { this.status = value; }

    public String getMessage() { return message; }
    public void setMessage(String value) { this.message = value; }

    public Data getData() { return data; }
    public void setData(Data value) { this.data = value; }

    public class Data {
        private String token;
        private User user;

        public String getToken() { return token; }
        public void setToken(String value) { this.token = value; }

        public User getUser() { return user; }
        public void setUser(User value) { this.user = value; }
    }

    public class User {
        private String email;
        private String name;
        private String gender;
        private String mobile;
        private MyClass myClass;

        public String getEmail() { return email; }
        public void setEmail(String value) { this.email = value; }

        public String getName() { return name; }
        public void setName(String value) { this.name = value; }

        public String getGender() { return gender; }
        public void setGender(String value) { this.gender = value; }

        public String getMobile() { return mobile; }
        public void setMobile(String value) { this.mobile = value; }

        public MyClass getMyClass() { return myClass; }
        public void setMyClass(MyClass value) { this.myClass = value; }
    }

    public class MyClass {
        private int grade;
        private int classRoom;
        private int classNumber;

        public int getGrade() { return grade; }
        public void setGrade(int value) { this.grade = value; }

        public int getClassRoom() { return classRoom; }
        public void setClassRoom(int value) { this.classRoom = value; }

        public int getClassNumber() { return classNumber; }
        public void setClassNumber(int value) { this.classNumber = value; }
    }

}



//package dgsw.hs.kr.flow.model;
//
///**
// * Created by Administrator on 2018-04-23.
// */
//
//import com.google.gson.annotations.Expose;
//import com.google.gson.annotations.SerializedName;
//
//public class LoginPost {
//    @SerializedName("status")
//    @Expose
//    private Integer status;
//
//    @SerializedName("message")
//    @Expose
//    private String message;
//
//    @SerializedName("data")
//    @Expose
//    private Data data;
//
//    public Integer getStatus() {return status;}
//
//    public void setStatus(Integer status) {this.status = status;}
//
//    public String getMessage() {return message;}
//
//    public void setMessage(String message) {this.message = message;}
//
//    public Data getData() {return data;}
//
//    public void setData(Data data) {this.data = data;}
//
//    public class Data {
//        @SerializedName("token")
//        @Expose
//        private String token;
//
//        @SerializedName("user")
//        @Expose
//        private User user;
//
//        public String getToken() {return token;}
//
//        public void setToken(String token) {this.token = token;}
//
//        public User getUser() {return user;}
//
//        public void setUser(User user) {this.user = user;}
//    }
//
//    public class User {
//        @SerializedName("email")
//        @Expose
//        private String email;
//
//        @SerializedName("name")
//        @Expose
//        private String name;
//
//        @SerializedName("gender")
//        @Expose
//        private String gender;
//
//        @SerializedName("mobile")
//        @Expose
//        private String mobile;
//
//        @SerializedName("my_class")
//        @Expose
//        private MyClass myClass;
//
//        public String getEmail() {return email;}
//
//        public void setEmail(String email) {this.email = email;}
//
//        public String getName() {return name;}
//
//        public void setName(String name) {this.name = name;}
//
//        public String getGender() {return gender;}
//
//        public void setGender(String gender) {this.gender = gender;}
//
//        public String getMobile() {return mobile;}
//
//        public void setMobile(String mobile) {this.mobile = mobile;}
//
//        public MyClass getMyClass() {return myClass;}
//
//        public void setMyClass(MyClass myClass) {this.myClass = myClass;}
//    }
//
//    public class MyClass {
//        @SerializedName("grade")
//        @Expose
//        private Integer grade;
//
//        @SerializedName("class_room")
//        @Expose
//        private Integer classRoom;
//
//        @SerializedName("class_number")
//        @Expose
//        private Integer classNumber;
//
//        public Integer getGrade() {return grade;}
//
//        public void setGrade(Integer grade) {this.grade = grade;}
//
//        public Integer getClassRoom() {return classRoom;}
//
//        public void setClassRoom(Integer classRoom) {this.classRoom = classRoom;}
//
//        public Integer getClassNumber() {return classNumber;}
//
//        public void setClassNumber(Integer classNumber) {this.classNumber = classNumber;}
//    }
//
//    @Override
//    public String toString() {
//        Data data = new Data();
//        User user = new User();
//
//        return "LoginPost{" +
//                "status='" + status + '\'' +
//                ", message='" + message + '\'' +
//                ", data: {'" +
//                ", token='" + data.token + '\'' +
//                    ", user: {'" +
//                         ", email='" + user.email + '\'' +
//                        '}' +
//                    '}' +
//                '}';
//    }
//
//}
