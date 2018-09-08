package dgsw.hs.kr.flow.activity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.helper.DatabaseOpenHelper;
import dgsw.hs.kr.flow.model.RegistPost;
import dgsw.hs.kr.flow.remote.APIService;
import dgsw.hs.kr.flow.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import dgsw.hs.kr.flow.helper.Validater;
/**
 * Created by Administrator on 2018-04-09.
 */

public class RegistActivity extends AppCompatActivity{

    private APIService mAPIService;
    private TextView mResponseTv;

    int version = 1;
    DatabaseOpenHelper helper;
    SQLiteDatabase database;

    EditText emailEditText;
    EditText pwEditText;
    EditText pwConfirmEditText;
    EditText nameEditText;

    RadioButton maleRadioButton;
    RadioButton femaleRadioButton;

    EditText mobileEditText;
    EditText class_idx_EditText;
    EditText class_number_EditText;
    Button btnJoin;

    String sql;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_activity);
        ///////////////////////** xml mapping *///////////////////////
        emailEditText = (EditText) findViewById(R.id.etEmail);
        pwEditText = (EditText) findViewById(R.id.etPassword);
        pwConfirmEditText = (EditText) findViewById(R.id.etPasswordConfirm);
        nameEditText = (EditText) findViewById(R.id.etName);
        mobileEditText = (EditText) findViewById(R.id.etMobile);
        maleRadioButton = (RadioButton) findViewById(R.id.maleRadioBtn);
        femaleRadioButton = (RadioButton) findViewById(R.id.femaleRadioBtn);
        class_idx_EditText = (EditText) findViewById(R.id.etClass_Idx);
        class_number_EditText = (EditText) findViewById(R.id.etClass_Number);
        btnJoin = (Button) findViewById(R.id.btnJoin);

        mAPIService = ApiUtils.getAPIService();

        mResponseTv = (TextView) findViewById(R.id.tv_response);

        helper = new DatabaseOpenHelper(RegistActivity.this, DatabaseOpenHelper.userTableName, null, version);
        database = helper.getWritableDatabase();

        btnJoin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                setRegistInfo();
            }
        });
    }

    public void setRegistInfo(){
        String email = emailEditText.getText().toString();
        String pw = pwEditText.getText().toString();
        String pwConfirm = pwConfirmEditText.getText().toString();
        String name = nameEditText.getText().toString();
        String mobile = mobileEditText.getText().toString();
        String gender;
        if(femaleRadioButton.isChecked()){
            gender = "여성";
        }else{
            gender = "남성";
        }
        String Sclass_idx = class_idx_EditText.getText().toString();
        int class_idx = Integer.parseInt(Sclass_idx);

        String Sclass_number = class_number_EditText.getText().toString();
        int class_number = Integer.parseInt(Sclass_number);

        if(email.length() == 0 || pw.length() == 0) {
            //아이디와 비밀번호는 필수 입력사항입니다.
            Toast toast = Toast.makeText(RegistActivity.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        System.out.println(pw + pwConfirm);

        //비밀번호와 비밀번호 확인이 일치한지 획인
        if (!pw.equals(pwConfirm)){
            Toast toast = Toast.makeText(RegistActivity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT);
            toast.show();
        }

        Validater validater = new Validater();
        boolean IsValidatedEmail = validater.emailValidation(email);
        boolean IsValidatedPassword = validater.passwordVaildation(pwConfirm);

        if (IsValidatedEmail == false){
            Toast toast = Toast.makeText(RegistActivity.this, "이메일 양식을 맞춰주세요. ex)___@dgsw.hs.kr", Toast.LENGTH_SHORT);
            toast.show();
        }

        if (IsValidatedPassword == false){
            Toast toast = Toast.makeText(RegistActivity.this, "비밀번호 양식을 맞춰주세요." , Toast.LENGTH_SHORT);
            toast.show();
        }


/** SQLite 로컬 DB 회원가입 */
        sql = "SELECT email " +
                "FROM "+ helper.userTableName +
                " WHERE email = '" + email + "'";
        cursor = database.rawQuery(sql, null);

        if(cursor.getCount() != 0){
            //존재하는 아이디입니다.
            Toast toast = Toast.makeText(RegistActivity.this, "존재하는 아이디입니다.", Toast.LENGTH_SHORT);
            toast.show();
        }else{
            helper.insertUser(database, email, pw, name, gender, mobile, class_idx, class_number);
            Toast toast = Toast.makeText(RegistActivity.this, "가입이 완료되었습니다. 로그인을 해주세요.", Toast.LENGTH_SHORT);
            toast.show();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }
/** API 서버와 통신 (Retrofit) */
            if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pw) &&
                !TextUtils.isEmpty(name) && !TextUtils.isEmpty(mobile) &&
                !TextUtils.isEmpty(gender) && !TextUtils.isEmpty(Sclass_idx) &&
                !TextUtils.isEmpty(Sclass_number)) {
            sendRegistPost(email, pw, name, mobile, gender, class_idx, class_number);
        }else{
                Toast toast = Toast.makeText(RegistActivity.this, "비어있는 항목이 있는지 확인해주십시오." , Toast.LENGTH_SHORT);
                toast.show();
            }
    }

    public void sendRegistPost(String email, String pw, String name, String mobile, String gender, int class_idx, int class_number) {

        mAPIService.registSavePost(email, pw, name, mobile, gender, class_idx, class_number).enqueue(new Callback<RegistPost>() {
            @Override
            public void onResponse(Call<RegistPost> call, Response<RegistPost> response) {

                if(response.isSuccessful()) {
                    showResponse(response.body().toString());
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<RegistPost> call, Throwable t) {
                showResponse("Unable to submit post to API.");
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void showResponse(String response) {
        if(mResponseTv.getVisibility() == View.GONE) {
            mResponseTv.setVisibility(View.VISIBLE);
        }
        mResponseTv.setText(response);
    }

}
