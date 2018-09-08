package dgsw.hs.kr.flow.model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
/**
 * Created by Administrator on 2018-04-18.
 */
public class RegistPost {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getStatus() {return status;}

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "RegistPost{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                '}';
    }

}
