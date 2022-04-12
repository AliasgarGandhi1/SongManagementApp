package com.example.project_2122332;

import java.util.Comparator;
import java.util.Objects;

public class Song implements Comparable<Song>
{
    private Integer id, duration;
    private String title, artist;
    private static int count=1;

    public Song() {
        this.id = count;
        count++;
    }

    public Song(Integer id, String title, String artist, Integer duration) {
        this.id = id;
        this.duration = duration;
        this.title = title;
        this.artist = artist;
        count++;
    }

    //Change by Chintan
    public Song(String title, String artist, Integer duration) {

        this.duration = duration;
        this.title = title;
        this.artist = artist;
    }
    public void nextInt(int _id)
    {
        this.id=_id;
    }
    //^^Change by Chintan^^

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public Integer getId() {
        return id;
    }

    public Integer getDuration() {
        return duration;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    @Override
    public int compareTo(Song o) {
        return Integer.compare(this.id, o.id);
    }

    public static class ComparatorId implements Comparator<Song> {

        @Override
        public int compare(Song oS1, Song oS2) {
            return oS1.compareTo(oS2);
        }
    }

    public static class ComparatorReverseId implements Comparator<Song> {

        @Override
        public int compare(Song oS1, Song oS2) {
            return - oS1.compareTo(oS2);
        }
    }

    public static class ComparatorTitle implements Comparator<Song>
    {
        public int compare(Song oS1, Song oS2)
        {
            return oS1.title.compareTo(oS2.title);
        }
    }

    public static class ComparatorReverseTitle implements Comparator<Song>
    {
        public int compare(Song oS1, Song oS2)
        {
            return -oS1.title.compareTo(oS2.title);
        }
    }

    public static class ComparatorArtist implements Comparator<Song>
    {
        public int compare(Song oS1, Song oS2)
        {
            return oS1.artist.compareTo(oS2.artist);
        }
    }

    public static class ComparatorReverseArtist implements Comparator<Song>
    {
        public int compare(Song oS1, Song oS2)
        {
            return -oS1.artist.compareTo(oS2.artist);
        }
    }

    public static class ComparatorDuration implements Comparator<Song>
    {
        public int compare(Song oS1, Song oS2)
        {
            return oS1.duration.compareTo(oS2.duration);
        }
    }

    public static class ComparatorReverseDuration implements Comparator<Song>
    {
        public int compare(Song oS1, Song oS2)
        {
            return -oS1.duration.compareTo(oS2.duration);
        }
    }

    @Override
    public String toString() {
        return "Song[" +
                "Id=" + id +
                ", Title='" + title + '\'' +
                ", Artist='" + artist + '\'' +
                ", Duration='" + duration + '\''+
                ']'+'\n';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Song oSong = (Song) o;
        return (id == oSong.id && title.equals(oSong.title) && artist.equals(oSong.artist) && duration == oSong.duration);
    }
}