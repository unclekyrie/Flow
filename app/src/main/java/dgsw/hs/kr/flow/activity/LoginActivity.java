package dgsw.hs.kr.flow.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.helper.DatabaseOpenHelper;
import dgsw.hs.kr.flow.model.LoginPost;
import dgsw.hs.kr.flow.remote.APIService;
import dgsw.hs.kr.flow.remote.ApiUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



/**
 * Created by Administrator on 2018-04-10.
 */

public class LoginActivity extends AppCompatActivity {
    int version = 1;
    DatabaseOpenHelper userHelper;
    DatabaseOpenHelper tokenHelper;
    SQLiteDatabase userDatabase;
    SQLiteDatabase tokenDatabase;

    EditText emailEditText;
    EditText pwEditText;
    Button btnLogin;
    Button btnJoin;
    Button btn_DelToken;

    String sql;
    Cursor cursor;

    private APIService mAPIService;
    private TextView mResponseTv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        emailEditText = (EditText) findViewById(R.id.etEmail);
        pwEditText = (EditText) findViewById(R.id.etPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnJoin = (Button) findViewById(R.id.btnRegist);
        btn_DelToken = (Button) findViewById(R.id.btn_tokenDel);

        mAPIService = ApiUtils.getAPIService();
        mResponseTv = (TextView) findViewById(R.id.tv_response);

        userHelper = new DatabaseOpenHelper(LoginActivity.this, DatabaseOpenHelper.userTableName, null, version);
        tokenHelper = new DatabaseOpenHelper(LoginActivity.this, DatabaseOpenHelper.tokenTableName, null, version);

        userDatabase = userHelper.getWritableDatabase();
        tokenDatabase = tokenHelper.getWritableDatabase();

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Login();
            }
        });

        btnJoin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //회원가입 버튼 클릭
                Toast toast = Toast.makeText(LoginActivity.this, "회원가입 화면으로 이동", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = new Intent(getApplicationContext(),RegistActivity.class);
                startActivity(intent);
                //finish();
            }
        });

        btn_DelToken.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sql = "DELETE FROM "+ tokenHelper.tokenTableName;
                //cursor = tokenDatabase.rawQuery(sql, null);
                tokenDatabase.execSQL(sql);

                Toast toast = Toast.makeText(LoginActivity.this, "토큰 삭제(로그아웃)", Toast.LENGTH_SHORT);
                toast.show();
            }
        });

    }

    public void Login(){
        String email = emailEditText.getText().toString();
        String pw = pwEditText.getText().toString();
        String registration_token = FirebaseInstanceId.getInstance().getToken();

            if(email.length() == 0 || pw.length() == 0) {
                //아이디와 비밀번호는 필수 입력사항입니다.
                Toast toast = Toast.makeText(LoginActivity.this, "아이디와 비밀번호는 필수 입력사항입니다.", Toast.LENGTH_SHORT);
                toast.show();
            return;
        }

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pw)) {
            sendLoginPost(email, pw, registration_token);
        }
    }

    public void sendLoginPost(String email, String pw, String registration_token) {
        mAPIService.loginSavePost(email, pw, registration_token).enqueue(new Callback<LoginPost>() {
            @Override
            public void onResponse(Call<LoginPost> call, Response<LoginPost> response) {

                if(response.isSuccessful()) {
                    tokenHelper.insertToken(tokenDatabase, response.body().toTokenString());
                    //tokenHelper.insertToken(tokenDatabase, response.body().toTokenString());
                    Toast toast = Toast.makeText(LoginActivity.this, "로컬DB에 토큰 저장 완료", Toast.LENGTH_SHORT);
                    toast.show();
                    //응답 json body의 token을 텍스트뷰에 띄운다
                    mResponseTv.setText(response.body().toTokenString());

                    //다이얼로그 메시지로 응답을 띄워준다.
                    showResponseDialog(response.body().getStatus(), response.body().getMessage());
//                    Log.i(TAG, "post submitted to API." + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<LoginPost> call, Throwable t) {
                Toast toast = Toast.makeText(LoginActivity.this, "Unable to submit post to API.", Toast.LENGTH_SHORT);
                toast.show();
//                Log.e(TAG, "Unable to submit post to API.");
            }
        });
    }

    public void showResponseDialog(int status, String message){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);

        // 제목셋팅
        alertDialogBuilder.setTitle("response");

        // AlertDialog 셋팅
        alertDialogBuilder
                .setMessage(status +"\n" + message)
                .setNegativeButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(
                                    DialogInterface dialog, int id) {
                                // 다이얼로그를 취소한다
                                dialog.cancel();

                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }
                        });

        // 다이얼로그 생성
        AlertDialog alertDialog = alertDialogBuilder.create();

        // 다이얼로그 보여주기
        alertDialog.show();
    }


//        sql = "SELECT email FROM "+ helper.tableName + " WHERE email = '" + email + "'";
//        cursor = database.rawQuery(sql, null);
//
//        if(cursor.getCount() != 1){
//            //아이디가 틀렸습니다.
//            Toast toast = Toast.makeText(LoginActivity.this, "존재하지 않는 아이디입니다.", Toast.LENGTH_SHORT);
//            toast.show();
//            return;
//        }
//
//        sql = "SELECT pw FROM "+ helper.tableName + " WHERE email = '" + email + "'";
//        cursor = database.rawQuery(sql, null);
//
//        cursor.moveToNext();
//        if(!pw.equals(cursor.getString(0))){
//            //비밀번호가 틀렸습니다.
//            Toast toast = Toast.makeText(LoginActivity.this, "비밀번호가 틀렸습니다.", Toast.LENGTH_SHORT);
//            toast.show();
//        }else{
//            //로그인성공
//            Toast toast = Toast.makeText(LoginActivity.this, "로그인성공", Toast.LENGTH_SHORT);
//            toast.show();
//            //인텐트 생성 및 호출
//            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//        cursor.close();
    }





