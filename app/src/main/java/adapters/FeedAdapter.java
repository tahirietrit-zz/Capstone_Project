package adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tahirietrit.ireport.R;
import com.tahirietrit.ireport.ReportDetailActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import objects.Item;

/**
 * Created by macb on 04/05/16.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {
    private Context ctx;
    public FeedAdapter(Context ctx){
        this.ctx = ctx;
    }
    public List<Item> articles = new ArrayList<Item>();
    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.report_thumb)
        ImageView reportThumb;
        @Bind(R.id.report_author)
        TextView reportAuthor;
        @Bind(R.id.report_title)
        TextView reportTitle;
        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.feed_recycle_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(ctx).load(articles.get(position).getImage()).into(holder.reportThumb);
        holder.reportAuthor.setText(articles.get(position).getCreator().getName());
        holder.reportTitle.setText(articles.get(position).getDescription());
        holder.reportThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ctx, ReportDetailActivity.class);
                intent.putExtra("reportId", articles.get(position).getId());
                ctx.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void setArticles(List<Item> articles){
        this.articles = articles;
        notifyDataSetChanged();
    }


}