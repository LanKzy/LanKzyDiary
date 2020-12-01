package com.example.soul_searching;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soul_searching.Tools.DiaryData;
import com.example.soul_searching.Tools.TimerService;

import java.util.LinkedList;
import java.util.List;


public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {
    private int position;
    public static List<ViewHolder> viewHolders;
    public DiaryAdapter(List<DiaryData> diaryItemList){
        System.err.println(diaryItemList.size());
        this.diaryItemList = diaryItemList;
    }
    private List<DiaryData> diaryItemList;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private List<DiaryData> diaryItemList;

        public ViewHolder(View v,List<DiaryData> diaryItemList) {
            super(v);
            this.diaryItemList = diaryItemList;
            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //修改
                    //getAdapterPosition()
                    System.err.println(getAdapterPosition());
                }
            });
            textView = (TextView) v.findViewById(R.id.diary_item_preview_text);

        }

        public void SetService(){
            if(diaryItemList != null){
                DiaryData data = diaryItemList.get(getAdapterPosition());
                long target = data.TargetTime;
                long current = System.currentTimeMillis();

                if(target >= current){
                    System.err.println("SetService:" + getAdapterPosition());
                    System.err.println("SetService target:" + target + "current:" + current);
                    //TimerService ts = new TimerService(target - current,"测试",data.Content);
                    Intent intent = new Intent(MainActivity.Ins,TimerService.class);
                    MainActivity.Ins.startService(intent);
                }else{

                    System.err.println("无需计时 target:" + target + "current:" + current);
                }
            }else{
                System.err.println("DiaryItemList null");
            }
        }



        public TextView getTextView() {
            return textView;
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.parent = parent;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.diary_gird_item, parent, false);

        System.err.println("CreateView");
        ViewHolder viewHolder = new ViewHolder(v,diaryItemList);
        return viewHolder;
    }
    private ViewGroup parent;
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        this.position = position;
        viewHolders = new LinkedList<ViewHolder>();
        String content = diaryItemList.get(position).Content;
        System.err.println(content);
        System.err.println(position);
        holder.SetService();
        //holder.getTextView().setText(diaryItemList.get(position).Content);
        holder.getTextView().setText("" + position);
        viewHolders.add(holder);
    }



    @Override
    public int getItemCount() {
        return diaryItemList == null ? 0 : diaryItemList.size();
    }
}
