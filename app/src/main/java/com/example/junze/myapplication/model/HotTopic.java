package com.example.junze.myapplication.model;

import com.example.junze.myapplication.model.Member;
import com.example.junze.myapplication.model.Node;

/**
 * Created by junze on 16-05-06.
 */
public class HotTopic {
    Member member;
    Node node;
    private int id;
    private String title;
    private String url;
    private String content;
    private String content_rendered;
    private int replies;
    private int created;
    private int last_modified;
    private int last_touched;

    public HotTopic() {}

    public HotTopic(Member member, Node node, int id, String title, String url, String content, String content_rendered, int replies, int created, int last_modified, int last_touched) {
        this.member = member;
        this.node = node;
        this.id = id;
        this.title = title;
        this.url = url;
        this.content = content;
        this.content_rendered = content_rendered;
        this.replies = replies;
        this.created = created;
        this.last_modified = last_modified;
        this.last_touched = last_touched;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent_rendered() {
        return content_rendered;
    }

    public void setContent_rendered(String content_rendered) {
        this.content_rendered = content_rendered;
    }

    public int getReplies() {
        return replies;
    }

    public void setReplies(int replies) {
        this.replies = replies;
    }

    public int getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public int getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(int last_modified) {
        this.last_modified = last_modified;
    }

    public int getLast_touched() {
        return last_touched;
    }

    public void setLast_touched(int last_touched) {
        this.last_touched = last_touched;
    }
}
