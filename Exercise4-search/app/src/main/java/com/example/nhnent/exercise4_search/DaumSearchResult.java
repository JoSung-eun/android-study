package com.example.nhnent.exercise4_search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nhnent on 2017. 4. 13..
 */

public class DaumSearchResult {
    int responseCode;

    @SerializedName("channel")
    @Expose
    private Channel channel;

    public Channel getChannel() {
        return this.channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    class Channel {
        @SerializedName("result")
        @Expose
        private String result;
        @SerializedName("pageCount")
        @Expose
        private String pageCount;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("totalCount")
        @Expose
        private String totalCount;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("item")
        @Expose
        private List<Item> item = null;
        @SerializedName("lastBuildDate")
        @Expose
        private String lastBuildDate;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("generator")
        @Expose
        private String generator;

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getPageCount() {
            return pageCount;
        }

        public void setPageCount(String pageCount) {
            this.pageCount = pageCount;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<Item> getItem() {
            return item;
        }

        public void setItem(List<Item> item) {
            this.item = item;
        }

        public String getLastBuildDate() {
            return lastBuildDate;
        }

        public void setLastBuildDate(String lastBuildDate) {
            this.lastBuildDate = lastBuildDate;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getGenerator() {
            return generator;
        }

        public void setGenerator(String generator) {
            this.generator = generator;
        }
    }


    class Item {
        @SerializedName("pubDate")
        @Expose
        private String pubDate;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("link")
        @Expose
        private String link;
        @SerializedName("url")
        @Expose
        private String url;

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }


}
