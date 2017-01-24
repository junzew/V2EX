package com.example.junze.myapplication.parser;

import com.example.junze.myapplication.model.HotTopic;
import com.example.junze.myapplication.model.Member;
import com.example.junze.myapplication.model.Node;
import com.example.junze.myapplication.model.Post;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by junze on 16-05-06.
 */
public class PostParser {

    public List<Post> parse(String jsonString) throws JSONException {
        List<Post> posts = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject o = jsonArray.getJSONObject(i);
            posts.add(parseHotTopic(o));
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            System.out.println("HOT TOPICS " + i + " : ");
            System.out.println(posts.get(i));
        }
        return posts;
    }
    private Post parseHotTopic(JSONObject jsonObject) throws JSONException {
        Post post = new Post();
        post.setId(jsonObject.getInt("id"));
        post.setTitle(jsonObject.getString("title"));
        post.setUrl(jsonObject.getString("url"));
        post.setContent(jsonObject.getString("content"));
        post.setContent_rendered(jsonObject.getString("content_rendered"));
        post.setReplies(jsonObject.getInt("replies"));
        post.setCreated(jsonObject.getInt("created"));
        post.setLast_modified(jsonObject.getInt("last_modified"));
        post.setLast_touched(jsonObject.getInt("last_touched"));
        Member m = parseMember(jsonObject.getJSONObject("member"));
        Node n = parseNode(jsonObject.getJSONObject("node"));
        post.setMember(m);
        post.setNode(n);
        return post;
    }

    private Node parseNode(JSONObject jsonObject) throws JSONException {
        Node n = new Node();
        n.setId(jsonObject.getInt("id"));
        n.setTopics(jsonObject.getInt("topics"));
        n.setName(jsonObject.getString("name"));
        n.setTitle(jsonObject.getString("title"));
        n.setTitle_alternative(jsonObject.getString("title_alternative"));
        n.setUrl(jsonObject.getString("url"));
        return n;
    }

    private Member parseMember(JSONObject jsonObject) throws JSONException {
        Member m = new Member();
        m.setId(jsonObject.getInt("id"));
        m.setUsername(jsonObject.getString("username"));
        m.setTagline(jsonObject.getString("username"));
        m.setAvatar_mini(jsonObject.getString("avatar_mini"));
        m.setAvatar_normal(jsonObject.getString("avatar_normal"));
        m.setAvatar_large(jsonObject.getString("avatar_large"));
        return m;
    }

}
