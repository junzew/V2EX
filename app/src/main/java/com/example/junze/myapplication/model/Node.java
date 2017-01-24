package com.example.junze.myapplication.model;

/**
 * Created by junze on 16-05-06.
 */
public class Node {
    private int id = -1;
    private String name = "";
    private String title = "";
    private String title_alternative = "";
    private String url = "";
    private int topics = -1;

    @Override
    public String toString() {
        return "Node{" +
                "avatar_large='" + avatar_large + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", title_alternative='" + title_alternative + '\'' +
                ", url='" + url + '\'' +
                ", topics=" + topics +
                ", avatar_mini='" + avatar_mini + '\'' +
                ", avatar_normal='" + avatar_normal + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;

        Node node = (Node) o;

        if (id != node.id) return false;
        if (topics != node.topics) return false;
        if (!name.equals(node.name)) return false;
        if (!title.equals(node.title)) return false;
        if (!title_alternative.equals(node.title_alternative)) return false;
        if (!url.equals(node.url)) return false;
        if (!avatar_mini.equals(node.avatar_mini)) return false;
        if (!avatar_normal.equals(node.avatar_normal)) return false;
        return avatar_large.equals(node.avatar_large);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + title_alternative.hashCode();
        result = 31 * result + url.hashCode();
        result = 31 * result + topics;
        result = 31 * result + avatar_mini.hashCode();
        result = 31 * result + avatar_normal.hashCode();
        result = 31 * result + avatar_large.hashCode();
        return result;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_alternative() {
        return title_alternative;
    }

    public void setTitle_alternative(String title_alternative) {
        this.title_alternative = title_alternative;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getTopics() {
        return topics;
    }

    public void setTopics(int topics) {
        this.topics = topics;
    }

    public String getAvatar_mini() {
        return avatar_mini;
    }

    public void setAvatar_mini(String avatar_mini) {
        this.avatar_mini = avatar_mini;
    }

    public String getAvatar_normal() {
        return avatar_normal;
    }

    public void setAvatar_normal(String avatar_normal) {
        this.avatar_normal = avatar_normal;
    }

    public String getAvatar_large() {
        return avatar_large;
    }

    public void setAvatar_large(String avatar_large) {
        this.avatar_large = avatar_large;
    }

    private String avatar_mini;
    private String avatar_normal;
    private String avatar_large;

    public Node() {}
    public Node(String name) {
        this.name = name;
    }
    public Node(int id, String name, String title, String title_alternative, String url, int topics, String avatar_mini, String avatar_normal, String avatar_large) {
        this.id = id;
        this.name = name;
        this.title = title;
        this.title_alternative = title_alternative;
        this.url = url;
        this.topics = topics;
        this.avatar_mini = avatar_mini;
        this.avatar_normal = avatar_normal;
        this.avatar_large = avatar_large;
    }
}
