package biswanath.com.myapplication;

import biswanath.com.myapplication.model.BlogPostList;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Url;

public class BloggerApi {
    public static final String key = "AIzaSyAjAojS13KdPumBgeCTS7p6YsVqMBrwrkQ";
    public static final String url = "https://www.googleapis.com/blogger/v3/blogs/8184061417717822153/posts/";


    public static BloggerService bloggerService = null;

    public static BloggerService getBlogPosts() {
        if (bloggerService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            bloggerService =retrofit.create(BloggerService.class);
        }
        return bloggerService;
    }


    public interface BloggerService {
        @GET("?key="+key)
        Call<BlogPostList> getPostList();
    }
}
