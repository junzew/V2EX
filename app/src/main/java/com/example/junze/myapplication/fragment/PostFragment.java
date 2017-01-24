package com.example.junze.myapplication.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junze.myapplication.parser.PostParser;
import com.example.junze.myapplication.R;
import com.example.junze.myapplication.model.Post;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by junze on 16-05-24.
 */
public abstract class PostFragment extends android.support.v4.app.Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private ListView lv;
    private MainAdapter adapter;
    private SwipeRefreshLayout srl;
    protected String url;

    private View layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.post_fragment, container, false);
        init();
        return layout;
    }

    private void init() {
        lv = (ListView) layout.findViewById(R.id.lv_items);
        adapter = new MainAdapter();
        lv.setAdapter(adapter);
//        Button b = (Button) findViewById(R.id.button);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new LoadingTask().execute("https://www.v2ex.com/api/topics/hot.json");
//            }
//        });

//        Fresco.initialize(this);

        srl = (SwipeRefreshLayout) layout.findViewById(R.id.swipe_container);
        srl.setColorSchemeResources(R.color.colorPrimary);
        srl.setDistanceToTriggerSync(300);
        srl.setProgressBackgroundColorSchemeResource(R.color.colorAccent);
        srl.setSize(SwipeRefreshLayout.DEFAULT);
        srl.setOnRefreshListener(this);
        new LoadingTask().execute(url);
    }

    @Override
    public void onRefresh() {
        refresh();
    }
    private void refresh() {
        new LoadingTask().execute(url);
    }

    private class MainAdapter extends BaseAdapter {
        List<Post> data = new ArrayList<>();

        public MainAdapter() {}

        public void setData(List<Post> data) {
            this.data = data;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            // if null, create new view
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.item_post, viewGroup, false);
            }

            // if not null, reuse view from recycler
            final Post post = data.get(position);
            String title = post.getTitle();
            String userName = post.getMember().getUsername();
            TextView un = (TextView) convertView.findViewById(R.id.user_name);
            TextView t = (TextView) convertView.findViewById(R.id.title);
            un.setText(userName);
            t.setText(shortenTitle(title));
            // process image with Picasso
            ImageView imgView = (ImageView) convertView.findViewById(R.id.imageView);
            String url = "http:"+ post.getMember().getAvatar_normal();
//          Picasso.with(getBaseContext()).load(url).into(imgView);
            Picasso.with(getActivity().getBaseContext()).load(url).into(imgView);

            // Alternatively, process image with Fresco, uncomment Fresco.initialize(this) in init()
//          Uri uri = Uri.parse("http:" + post.getMember().getAvatar_mini());
//          SimpleDraweeView draweeView = (SimpleDraweeView) convertView.findViewById(R.id.imageView);
//          draweeView.setImageURI(uri);

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // start default browser
//                  Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(post.getUrl()));
//                  startActivity(browserIntent);

//                    Intent intent = new Intent(getActivity(), WebActivity.class);
//                    String [] extra = {post.getTitle(), post.getUrl()};
//                    intent.putExtra("url, title", extra);
//                    startActivity(intent);

                    WebFragment wf = WebFragment.newInstance(post.getUrl(), post.getTitle());
                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.frame_container, wf);
                    ft.addToBackStack(null);
                    ft.commit();
                }
            });

            return convertView;
        }
    }

    private class LoadingTask extends AsyncTask<String, Integer, List<Post>> {
        HttpURLConnection connection;
        StringBuilder jsonResults;

        @Override
        protected List<Post> doInBackground(String... urls) {
            Log.d("URL",urls[0]);
            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();
                if (connection.getResponseCode() != 200) {
                    throw new IOException();
                }
                jsonResults = readResponse();
                System.out.println(jsonResults.toString());
                return new PostParser().parse(jsonResults.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch(JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
            return null;
        }

        private StringBuilder readResponse() throws IOException {
            StringBuilder jsonResults = new StringBuilder();
            InputStreamReader in = new InputStreamReader(connection.getInputStream());
            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
            return jsonResults;
        }

        @Override
        protected void onPostExecute(List<Post> posts) {
            super.onPostExecute(posts);
            if (posts != null) {
                adapter.setData(posts);
                srl.setRefreshing(false);
            }
        }
    }

    // process title that is too long to fit
    private String shortenTitle(String title) {
        if (title.length() > 40) {
            return title.substring(0,40) + "...";
        }
        return title;
    }
}