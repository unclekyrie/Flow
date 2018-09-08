package dgsw.hs.kr.flow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import dgsw.hs.kr.flow.R;

/**
 * Created by Administrator on 2018-05-01.
 */

public class OutOfGoOrSleep extends AppCompatActivity {

    private Button btn_OutGo;
    private Button btn_OutSleep;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.out_of_go_or_sleep_activity);

        btn_OutGo = (Button) findViewById(R.id.btn_OutGo);
        btn_OutSleep = (Button) findViewById(R.id.btn_OutSleep);

        //외출 activity로 이동
        btn_OutGo.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OutOfGoOrSleep.this, OutGoActivity.class);
                startActivity(i);
            }
        });

        //외박 activity로 이동
        btn_OutSleep.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(OutOfGoOrSleep.this, OutSleepActivity.class);
                startActivity(i);
            }
        });
    }

}

