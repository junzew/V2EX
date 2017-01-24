package com.example.junze.myapplication.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.junze.myapplication.R;
import com.example.junze.myapplication.activity.MainActivity;
import com.example.junze.myapplication.model.Node;
import com.example.junze.myapplication.parser.NodeParser;

import org.json.JSONException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by junze on 16-05-24.
 */
public class NodeFragment extends Fragment {

    // TODO: search Node
    // TODO: Node database

    private GridView mGridView;
    private View layout;
    private NodeAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.node_fragment, container, false);
        mGridView = (GridView) layout.findViewById(R.id.gridview);
        adapter = new NodeAdapter();
        mGridView.setAdapter(adapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getActivity(), position+"", Toast.LENGTH_SHORT).show();
                Node n = adapter.mNodeList.get(position);
                showNode(n.getName());
            }
        });
//        List<Node> nodes = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            nodes.add(new Node(Integer.toString(i)));
//        }
//        adapter.setNodeList(nodes);
        String url = "https://www.v2ex.com/api/nodes/all.json";
        new AllNodeTask().execute(url); // 显示全部节点
        return layout;
    }

    // 跳转到选中节点
    private void showNode(String nodeName) {
        String url = "http://www.v2ex.com/api/nodes/show.json?name=" + nodeName;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String responseData = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Node node = new NodeParser().parseNode(responseData);
                            if (node != null) {
                                WebFragment wf = WebFragment.newInstance(node.getUrl(), node.getTitle());
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.frame_container, wf);
                                ft.addToBackStack(null);
                                ft.commit();
                            }
                        } catch(JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }

    private static class ViewHolder {
        TextView id;
    }
    private class NodeAdapter extends BaseAdapter {
        private List<Node> mNodeList = new ArrayList<>();
        public void setNodeList (List<Node> nodes) {
            this.mNodeList = nodes;
            notifyDataSetChanged();
        }
        @Override
        public int getCount() {
            return mNodeList.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder; // view lookup cache stored in tag
            if (convertView == null) {
                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.item_node, parent, false);
                viewHolder.id = (TextView) convertView.findViewById(R.id.node_name);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            // Populate the data into the template view using the data object
            String name = mNodeList.get(position).getName();
            viewHolder.id.setText(name);
            if (name.length() > 10) {
                viewHolder.id.setTextSize(12);
            } else {
                viewHolder.id.setTextSize(14);

            }

            // Return the completed view to render on screen
            return convertView;
        }
    }
    private class AllNodeTask extends AsyncTask<String, Integer, List<Node>> {

        HttpURLConnection connection;
        StringBuilder jsonResults;

        @Override
        protected List<Node> doInBackground(String... urls) {
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
                return new NodeParser().parse(jsonResults.toString());
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
        protected void onPostExecute(List<Node> posts) {
            super.onPostExecute(posts);
            if (posts != null) {
                adapter.setNodeList(posts);
            }
        }
    }

}
