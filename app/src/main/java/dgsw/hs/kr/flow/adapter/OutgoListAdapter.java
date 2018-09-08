package dgsw.hs.kr.flow.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.model.OutgoDocVO;

/**
 * Created by Administrator on 2018-06-22.
 */

public class OutgoListAdapter extends BaseAdapter {
    private ArrayList<OutgoDocVO> listVO = new ArrayList<OutgoDocVO>() ;
    public OutgoListAdapter() {

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
            convertView = inflater.inflate(R.layout.custom_listview, parent, false);
        }

        TextView tv_accept = (TextView) convertView.findViewById(R.id.cus_accept) ;
        TextView tv_startTime = (TextView) convertView.findViewById(R.id.cus_startTime);
        TextView tv_endTime = (TextView) convertView.findViewById(R.id.cus_endTime);
        TextView tv_reason = (TextView) convertView.findViewById(R.id.cus_reason);
        TextView tv_student_email = (TextView) convertView.findViewById(R.id.cus_student_email);

        OutgoDocVO listViewItem = listVO.get(position);

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
    public void addVO(int accept , String startTime, String endTime, String reason, String studentEmail) {
        OutgoDocVO item = new OutgoDocVO();

        item.setAccept(accept);
        item.setStartTime(startTime);
        item.setEndTime(endTime);
        item.setReason(reason);
        item.setStudent_email(studentEmail);

        listVO.add(item);
    }
}

//    private List outgoDoc_List;
//    private Context context;
//
//    public OutgoListAdapter(List outgoDocList, Context context) {
//        this.outgoDoc_List = outgoDocList;
//        this.context = context;
//    }
//
//
//    @Override
//    public int getCount() {
//        return this.outgoDoc_List.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return this.outgoDoc_List.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        Holder holder = null;
//
//        if (convertView == null) {
//
//            convertView = new LinearLayout(context);
//            ((LinearLayout) convertView).setOrientation(LinearLayout.HORIZONTAL);
//
//            TextView tv_accept = new TextView(context);
//            tv_accept.setPadding(10, 0, 20, 0);
//            tv_accept.setTextColor(Color.rgb(0, 0, 0));
//
//            TextView tv_idx = new TextView(context);
//            tv_idx.setPadding(10, 0, 20, 0);
//            tv_idx.setTextColor(Color.rgb(0, 0, 0));
//
//            TextView tv_startTime = new TextView(context);
//            tv_startTime.setPadding(10, 0, 20, 0);
//            tv_startTime.setTextColor(Color.rgb(0, 0, 0));
//
//            TextView tv_endTime = new TextView(context);
//            tv_endTime.setPadding(10, 0, 20, 0);
//            tv_endTime.setTextColor(Color.rgb(0, 0, 0));
//
//            TextView tv_reason = new TextView(context);
//            tv_reason.setPadding(10, 0, 20, 0);
//            tv_reason.setTextColor(Color.rgb(0, 0, 0));
//
//            TextView tv_class_idx = new TextView(context);
//            tv_class_idx.setPadding(10, 0, 20, 0);
//            tv_class_idx.setTextColor(Color.rgb(0, 0, 0));
//
//            TextView tv_student_email = new TextView(context);
//            tv_student_email.setPadding(10, 0, 20, 0);
//            tv_student_email.setTextColor(Color.rgb(0, 0, 0));
//
//            ((LinearLayout) convertView).addView(tv_accept);
//            ((LinearLayout) convertView).addView(tv_idx);
//            ((LinearLayout) convertView).addView(tv_startTime);
//            ((LinearLayout) convertView).addView(tv_endTime);
//            ((LinearLayout) convertView).addView(tv_reason);
//            ((LinearLayout) convertView).addView(tv_class_idx);
//            ((LinearLayout) convertView).addView(tv_student_email);
//
//            holder = new Holder();
//            holder.tv_accept = tv_accept;
//            holder.tv_idx = tv_idx;
//            holder.tv_startTime = tv_startTime;
//            holder.tv_endTime = tv_endTime;
//            holder.tv_reason = tv_reason;
//            holder.tv_class_idx = tv_class_idx;
//            holder.tv_student_email = tv_student_email;
//
//            convertView.setTag(holder);
//        } else {
//            holder = (Holder) convertView.getTag();
//        }
//
//        OutgoDocVO outgoDocList = (OutgoDocVO) getItem(position);
//        holder.tv_accept.setText(outgoDocList.getAccept() + "");
//        holder.tv_idx.setText(outgoDocList.getIdx() + "");
//        holder.tv_startTime.setText(outgoDocList.getStartTime() + "");
//        holder.tv_endTime.setText(outgoDocList.getEndTime() + "");
//        holder.tv_reason.setText(outgoDocList.getReason() + "");
//        holder.tv_class_idx.setText(outgoDocList.getClass_idx() + "");
//        holder.tv_student_email.setText(outgoDocList.getStudent_email() + "");
//
//        return convertView;
//    }
//}
//
//class Holder {
//    public TextView tv_accept;
//    public TextView tv_idx;
//    public TextView tv_startTime;
//    public TextView tv_endTime;
//    public TextView tv_reason;
//    public TextView tv_class_idx;
//    public TextView tv_student_email;
//}