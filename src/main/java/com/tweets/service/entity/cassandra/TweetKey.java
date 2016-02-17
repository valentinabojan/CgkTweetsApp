package com.tweets.service.entity.cassandra;

import org.springframework.cassandra.core.PrimaryKeyType;
import org.springframework.data.cassandra.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.mapping.PrimaryKeyColumn;

import java.io.Serializable;
import java.util.Date;

@PrimaryKeyClass
public class TweetKey implements Serializable {

    @PrimaryKeyColumn(name = "temp_key", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Integer tempKey;

    @PrimaryKeyColumn(name = "date", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    private Date date;

    public Integer getTempKey() {
        return tempKey;
    }

    public void setTempKey(Integer tempKey) {
        this.tempKey = tempKey;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TweetKey tweetKey = (TweetKey) o;

        if (date != null ? !date.equals(tweetKey.date) : tweetKey.date != null) return false;
        if (tempKey != null ? !tempKey.equals(tweetKey.tempKey) : tweetKey.tempKey != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = tempKey != null ? tempKey.hashCode() : 0;
        result = 31 * result + (date != null ? date.hashCode() : 0);
        return result;
    }
}