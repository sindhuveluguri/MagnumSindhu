package com.example.magnumsindhu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class ArticlesAdaptor extends RecyclerView.Adapter<ArticlesAdaptor.ArticleViewHolder> implements Filterable {
    private final int VIEW_TYPE_ITEM = 1;
    private final int VIEW_TYPE_LOADING = 0;
    private Context mContext;
    private ArrayList<DataList> mdataList;
    private ArrayList<DataList> dataListFull;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ArticlesAdaptor(Context context, ArrayList<DataList> exampleList) {
        mContext = context;
        mdataList = exampleList;
    }

    public void setData(ArrayList<DataList> articles){
        this.mdataList = articles;
        this.dataListFull = new ArrayList<>(articles);
    }
    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View articleView = null;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        articleView = inflater.inflate(R.layout.recycler_row, parent, false);
        return new ArticleViewHolder(articleView);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        DataList dataList = mdataList.get(position);

        String imageUrl = dataList.getImage();
        String loginName = dataList.getLoginName();
        String loginId =  dataList.getLoginId();

        holder.mtvloginName.setText(loginName);
        holder.mtvloginId.setText(loginId);
        Picasso.get().load(imageUrl).into(holder.mImageview);
    }

    @Override
    public int getItemCount() {
        return mdataList.size();
    }

    public class ArticleViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageview;
        public TextView mtvloginName;
        public TextView mtvloginId;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageview = itemView.findViewById(R.id.iv_RR_logo);
            mtvloginName = itemView.findViewById(R.id.tv_RR_loginName);
            mtvloginId = itemView.findViewById(R.id.tv_RR_loginId);

            //Redirect From recyclerview to another page
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    //Search bar code
    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<DataList> filteredList = new ArrayList<>();

            if(charSequence == null || charSequence.length() == 0) {
                filteredList.addAll(dataListFull);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (DataList list : dataListFull) {
                    if(list.getLoginName().toLowerCase().contains(filterPattern)){
                        filteredList.add(list);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mdataList.clear();
            mdataList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };
}