package com.example.junze.myapplication.parser;

import com.example.junze.myapplication.model.Node;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junze on 16-05-27.
 */
public class NodeParser {
    public List<Node> parse(String jsonString) throws JSONException {
        List<Node> nodes = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            nodes.add(parseNode(o.toString()));
        }
        return nodes;

    }
    public Node parseNode(String jsonString) throws JSONException {
        Node n = new Node();
        JSONObject o = new JSONObject(jsonString);
        n.setId(o.optInt("id"));
        n.setName(o.optString("name"));
        n.setTitle(o.optString("title"));
        n.setAvatar_large(o.optString("avatar_large"));
        n.setAvatar_normal(o.optString("avatar_normal"));
        n.setUrl(o.optString("url"));
        return n;
    }
}
