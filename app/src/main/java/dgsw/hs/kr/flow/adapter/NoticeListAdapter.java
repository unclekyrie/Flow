package dgsw.hs.kr.flow.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import dgsw.hs.kr.flow.R;
import dgsw.hs.kr.flow.activity.MainActivity;
import dgsw.hs.kr.flow.activity.NoticeActivity;
import dgsw.hs.kr.flow.activity.NoticeDetailActivity;
import dgsw.hs.kr.flow.model.NoticePost;
import dgsw.hs.kr.flow.model.NoticeVO;
import dgsw.hs.kr.flow.model.OutgoDocVO;

/**
 * Created by Administrator on 2018-06-23.
 */

public class NoticeListAdapter extends BaseAdapter {
    private ArrayList<NoticeVO> listVO = new ArrayList<NoticeVO>() ;
    public NoticeListAdapter() {

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

        final int selPos;


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.custom_notice_listview, parent, false);
        }

        TextView tv_content = (TextView) convertView.findViewById(R.id.cus_content);
        TextView tv_writer = (TextView) convertView.findViewById(R.id.cus_writer);
        TextView tv_writeDate = (TextView) convertView.findViewById(R.id.cus_writeDate);
        TextView tv_modifyDate = (TextView) convertView.findViewById(R.id.cus_modifyDate);

        final NoticeVO listViewItem = listVO.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        tv_content.setText(listViewItem.getContent());
        tv_writer.setText(listViewItem.getWriter());
        tv_writeDate.setText(listViewItem.getWriteDate());
        tv_modifyDate.setText(listViewItem.getModifyDate());


        //리스트뷰 클릭 이벤트
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, (pos+1)+"번째 리스트가 클릭되었습니다.", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, NoticeDetailActivity.class);
                intent.putExtra("selectIdx", listViewItem.getIdx());
                context.startActivity(intent);
            }
        });


        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return position ;
    }


    @Override
    public NoticeVO getItem(int position) {
        return listVO.get(position) ;
    }

    // 데이터값 넣어줌
    public void addNoticeVO(String content, String writer, String writeDate, String modifyDate, int idx) {
        NoticeVO item = new NoticeVO();
        item.setContent(content);
        item.setWriter(writer);
        item.setWriteDate(writeDate);
        item.setModifyDate(modifyDate);
        item.setIdx(idx);

        listVO.add(item);
    }
}
