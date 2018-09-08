package dgsw.hs.kr.flow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.model.OutgoDocVO;
import dgsw.hs.kr.flow.model.OutsleepDocVO;

/**
 * Created by Administrator on 2018-06-25.
 */

public class OutsleepListAdapter extends BaseAdapter{
    private ArrayList<OutsleepDocVO> listVO = new ArrayList<OutsleepDocVO>() ;
    public OutsleepListAdapter() {

    }

    @Override
    public int getCount() {
        return listVO.size() ;
    }

    // ** 이 부분에서 리스트뷰에 데이터를 넣어줌 **
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //postion = ListView의 위치      /   첫번째면 position = 0
        final int pos = position;
        final Context context = parent.getContext();


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_outsleep_listview, parent, false);
        }

        TextView tv_accept = (TextView) convertView.findViewById(R.id.cus_out_sleep_accept) ;
        TextView tv_startTime = (TextView) convertView.findViewById(R.id.cus_out_sleep_startTime);
        TextView tv_endTime = (TextView) convertView.findViewById(R.id.cus_out_sleep_endTime);
        TextView tv_reason = (TextView) convertView.findViewById(R.id.cus_out_sleep_reason);
        TextView tv_student_email = (TextView) convertView.findViewById(R.id.cus_out_sleep_student_email);

        OutsleepDocVO listViewItem = listVO.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tv_accept.setText(Integer.toString(listViewItem.getAccept()));
        tv_startTime.setText(listViewItem.getStartTime());
        tv_endTime.setText(listViewItem.getEndTime());
        tv_reason.setText(listViewItem.getReason());
        tv_student_email.setText(listViewItem.getStudent_email());


        //리스트뷰 클릭 이벤트
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, (pos+1)+"번째 리스트가 클릭되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });


        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position ;
    }


    @Override
    public Object getItem(int position) {
        return listVO.get(position) ;
    }

    // 데이터값 넣어줌
    public void addOutSleepVO(int accept , String startTime, String endTime, String reason, String studentEmail) {
        OutsleepDocVO item = new OutsleepDocVO();

        item.setAccept(accept);
        item.setStartTime(startTime);
        item.setEndTime(endTime);
        item.setReason(reason);
        item.setStudent_email(studentEmail);

        listVO.add(item);
    }
}

