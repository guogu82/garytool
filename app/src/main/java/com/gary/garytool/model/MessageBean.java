package com.gary.garytool.model;

/**
 * Created by Administrator on 2015/9/7.
 */
public class MessageBean {
    private String title;
    private String content;
    private int comment;
    private int read;

    public MessageBean(String title, String content, int comment, int read) {

        this.title=title;
        this.content=content;
        this.comment=comment;
        this.read=read;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public int getComment() {
        return comment;
    }

    public void setComment(int comment) {
        this.comment = comment;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }
}
