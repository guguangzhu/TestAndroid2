package com.example.testandroid2.adapter;

import java.util.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.testandroid2.R;

public class HomeAdapter extends Adapter<HomeAdapter.MyViewHolder> {
	private List<String> mDatas;
	private Context context;
	private OnItemClickLitener mOnItemClickLitener;
	
	
	
	public HomeAdapter(List<String> mDatas, Context context) {
		this.mDatas = mDatas;
		this.context = context;
	}

	public interface OnItemClickLitener {
		void onItemClick(View view, int position);

		void onItemLongClick(View view, int position);
	}

	public HomeAdapter setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
		this.mOnItemClickLitener = mOnItemClickLitener;
		return this;
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
				.inflate(R.layout.item_text, parent, false));
		
		return holder;
	}

	@Override
	public void onBindViewHolder(final MyViewHolder holder, int position) {
		holder.tv.setText(mDatas.get(position));
	      // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
	}

	@Override
	public int getItemCount() {
		return mDatas.size();
	}

	class MyViewHolder extends ViewHolder {

		TextView tv;

		public MyViewHolder(View view) {
			super(view);
			tv = (TextView) view.findViewById(R.id.textView1);
		}
	}
}
