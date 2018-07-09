package biswanath.com.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import biswanath.com.myapplication.model.BlogPostList;
import biswanath.com.myapplication.model.Item;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    List<Item> items = new ArrayList<>();
    Set<Item> itemsSet = new HashSet<>(items);
    PostAdapter postAdapter;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    boolean isScrolling = false;
    int totalItems, currentItems, scrollItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rv_post);
        linearLayoutManager = new LinearLayoutManager(this);
        postAdapter = new PostAdapter(this, items);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(postAdapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentItems = linearLayoutManager.getChildCount();
                totalItems = linearLayoutManager.getItemCount();
                scrollItems = linearLayoutManager.findFirstVisibleItemPosition();
                Log.d(TAG, "currentItems : " + currentItems + " scrolitem : " + scrollItems + " total : " + totalItems);
                if (isScrolling && (scrollItems + currentItems == totalItems)) {
                    Log.d(TAG, "fetch data: ");
                    getPostData();
                }
            }
        });
        getPostData();
    }

    private void getPostData() {
        //String baseUrl = BloggerApi.url + "?key=" + BloggerApi.key;
        String baseUrl = "?key=" + BloggerApi.key;

        final Call<BlogPostList> blogPostListCall = BloggerApi.getBlogPosts().getPostList();

        blogPostListCall.enqueue(new Callback<BlogPostList>() {
            @Override
            public void onResponse(Call<BlogPostList> call, Response<BlogPostList> response) {
                BlogPostList blogPostList = response.body();
                items.addAll(blogPostList.getItems());
                postAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<BlogPostList> call, Throwable t) {

            }
        });
    }

}
