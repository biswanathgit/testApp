package biswanath.com.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List;

import biswanath.com.myapplication.model.Item;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>{
    private Context context;
    private List<Item> items;

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.post_item, parent, false);
        return new PostViewHolder(view);
    }

    public PostAdapter(Context context, List<Item> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        final Item item = items.get(position);
        holder.txtTitle.setText(item.getTitle());


        Document document = Jsoup.parse(item.getContent());
        holder.txtDescription.setText(document.text());

        Elements elements = document.select("img");
        Glide.with(context).load(elements.get(0).attr("src")).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView ;
        TextView txtTitle;
        TextView txtDescription;
        public PostViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.postImage);
            txtTitle = itemView.findViewById(R.id.postTitle);
            txtDescription = itemView.findViewById(R.id.postDescription);
        }
    }
}
