package com.qgs.gd.gouwuche.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.qgs.gd.gouwuche.R;
import com.qgs.gd.gouwuche.bean.User;
import com.qgs.gd.gouwuche.zidy.AddSub;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * date:2018/11/21    10:53
 * author:秦广帅(Lenovo)
 * fileName:OneAdapter
 */
public class OneAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<User.DataBean> list;
    private AddSub.OnNumChangedListener mOnNumChangedListener;

    public void setOnNumChangedListener(AddSub.OnNumChangedListener onNumChangedListener) {
        mOnNumChangedListener = onNumChangedListener;
    }

    public OneAdapter(Context context, List<User.DataBean> list) {
        mContext = context;
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if(convertView==null){
            convertView = View.inflate(mContext,R.layout.group,null);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.group_box = convertView.findViewById(R.id.group_box);
            groupViewHolder.group_text = convertView.findViewById(R.id.group_text);
            convertView.setTag(groupViewHolder);
        }else{
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.group_text.setText(list.get(groupPosition).getSellerName());
        groupViewHolder.group_box.setChecked(list.get(groupPosition).isChecked());
        /*groupViewHolder.group_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){

                }
            }
        });*/
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if(convertView == null){
            convertView = View.inflate(mContext,R.layout.child,null);
            childViewHolder = new ChildViewHolder();
            childViewHolder.child_box = convertView.findViewById(R.id.child_box);
            childViewHolder.child_text = convertView.findViewById(R.id.child_text);
            childViewHolder.child_image = convertView.findViewById(R.id.child_image);
            childViewHolder.child_text1 = convertView.findViewById(R.id.child_text1);
            childViewHolder.mAddSub = convertView.findViewById(R.id.child_addsub);
            convertView.setTag(childViewHolder);
        }else{
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final User.DataBean.ListBean bean = list.get(groupPosition).getList().get(childPosition);
        childViewHolder.child_text.setText(bean.getTitle());
        //(图片)字符串截取
        String url = list.get(childPosition).getList().get(0).getImages();
        String[] split = url.split("!");
        Picasso.with(mContext).load(split[0]).into(childViewHolder.child_image);
        childViewHolder.child_text1.setText(bean.getPrice());
        childViewHolder.child_box.setChecked(bean.isChecked());
        childViewHolder.mAddSub.setOnNumChangedListener(new AddSub.OnNumChangedListener() {
            @Override
            public void onNumChange(View view, int curNum) {
                bean.setNum(curNum);
                if(mOnNumChangedListener!=null){
                    mOnNumChangedListener.onNumChange(view,curNum);
                }
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class GroupViewHolder{
        CheckBox group_box;
        TextView group_text;
    }

    class ChildViewHolder{
        CheckBox child_box;
        ImageView child_image;
        TextView child_text;
        TextView child_text1;
        AddSub mAddSub;
    }
}
