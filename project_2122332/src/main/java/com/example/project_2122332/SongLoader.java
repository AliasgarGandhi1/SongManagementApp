package com.example.project_2122332;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SongLoader {
    /*
     * METHODS CREATED BY CHINTAN:
     * 1. lastRecordIndex - set last index of the Song ID
     * 2. saveCSVData - set CSV data to respective file
     * 3. createCSV - create new CSV file
     * 4. listAllCSV - list's all CSV Files from Data Folder
     * */

    //Return lenght of file.
    public static int lengthOfFile(String filename)
    {
        BufferedReader br = null;
        List<Song> songList = new LinkedList<>();
        Song oSong = new Song();

        try
        {
            int count=0;
            boolean fileExists=new File(filename).exists();
            if(fileExists) {
                br = new BufferedReader(new FileReader(filename));
                String line = br.readLine();

                while (line != null) {
                    String[] fields = line.split(",");
                    oSong = new Song(Integer.parseInt(fields[0]), fields[1], fields[2], Integer.parseInt(fields[3]));
                    songList.add(oSong);
                    line = br.readLine();
                }
                return (int) songList.stream().count()+1;//Counts total nodes in LL
            }
            else
                return 1;
        }
        catch (Exception e)
        {
            System.out.println("File Not Found.");
            return 1;
        }
    }

    // Set Latest Id of Songs Class
    public static void lastRecordIndex(Song _song,String filename)
    {
        BufferedReader br = null;
        List<Song> songList = new LinkedList<>();
        Song oSong = new Song();

        try
        {
            int count=0;
            boolean fileExists=new File(filename).exists();
            if(fileExists) {
                br = new BufferedReader(new FileReader(filename));
                String line = br.readLine();

                while (line != null) {
                    String[] fields = line.split(",");
                    oSong = new Song(Integer.parseInt(fields[0]), fields[1], fields[2], Integer.parseInt(fields[3]));
                    songList.add(oSong);
                    line = br.readLine();
                }
                _song.nextInt((int) songList.stream().count());//Counts total nodes in LL
            }
            else
                _song.nextInt(1);
        }
        catch (Exception e)
        {
            System.out.println("File Not Found.");
            _song.nextInt(1);
        }
    }

    // Add Data to CSV File
    public static boolean saveCSVData(Song song,String filename,int checkFileStatus)
    {
        lastRecordIndex(song,filename);
        BufferedWriter bw=null;
        List<Song> songList= new LinkedList<>();
        Song oSong = new Song();
        try {
            boolean fileExists = new File(filename).exists();
            if(fileExists) {
                bw = new BufferedWriter(new PrintWriter(new FileWriter(filename, true)));
                if(checkFileStatus==0){
                    bw.newLine();
                    bw.write(song.getId() + "," + song.getTitle() + "," + song.getArtist() + "," + song.getDuration()+"");

                }
                if(checkFileStatus==1){
                    bw.write(song.getId() + "," + song.getTitle() + "," + song.getArtist() + "," + song.getDuration()+"");
                }
                bw.close();
                return true;
            }
            return false;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public static boolean rearrangeCSVData(int i1,int i2,String filename){
        BufferedReader br = null;
        BufferedWriter bw=null;
        List<Song> songList1 = new LinkedList<>();
        Song song=new Song();
        Song oSongi1 = new Song();
        Song oSongi2=new Song();
        try
        {
            int count=0;
            boolean fileExists=new File(filename).exists();
            br= new BufferedReader(new FileReader(filename));
            String line= br.readLine();

            if(fileExists) {
                int s11=0,s14=0,s21=0,s24=0;
                String s12 = null,s13=null,s22=null,s23=null;
                while (line != null) {
                    String[] fields = line.split(",");
                    song = new Song(Integer.parseInt(fields[0]), fields[1], fields[2], Integer.parseInt(fields[3]));
                    if(i1==song.getId())
                    {
                        s11=Integer.parseInt(fields[0]);
                        s12=fields[1];
                        s13=fields[2];
                        s14=Integer.parseInt(fields[3]);
                    }
                    if(i2==song.getId())
                    {
                        s21=Integer.parseInt(fields[0]);
                        s22=fields[1];
                        s23=fields[2];
                        s24=Integer.parseInt(fields[3]);
                    }
                    songList1.add(song);
                    line = br.readLine();
                }

                for (Song s:songList1) {
                    if(s.getId()==i1){
                        s.setArtist(s23);
                        s.setTitle(s22);
                        s.setDuration(s24);
                    }
                    if(s.getId()==i2){
                        s.setArtist(s13);
                        s.setTitle(s12);
                        s.setDuration(s14);
                    }
                }
                File fileObj=new File(filename);
                br.close();
                boolean del=fileObj.delete();
                System.out.println(del);
                File file=new File(filename);
                if(file.createNewFile())
                {
                    bw = new BufferedWriter(new FileWriter(filename, true));
                    for (Song song1:songList1){
                        bw.write(song1.getId() + "," + song1.getTitle() + "," + song1.getArtist() + "," + song1.getDuration()+"");
                        bw.newLine();
                    }
                    bw.close();
                }
                return del;
            }
            return false;
        }
        catch (Exception e)
        {
            System.out.println("File Not Found.");
            return false;
        }

    }

    public static boolean deleteCSVData(int index,String filename){
        BufferedReader br = null;
        BufferedWriter bw=null;

        List<Song> songList= new LinkedList<>();
        Song oSong;
        try {
            File fileObj=new File(filename);
            String fname= fileObj.getName();

            boolean fileExists=new File(filename).exists();
            br= new BufferedReader(new FileReader(filename));
            String line= br.readLine();
            if(fileExists) {
                while (line != null) {
                    String[] fields = line.split(",");
                    if(Integer.parseInt(fields[0]) != (index))
                    {
                        oSong = new Song(Integer.parseInt(fields[0]), fields[1],fields[2], Integer.parseInt(fields[3]));
                        songList.add(oSong);
                    }
                    line = br.readLine();
                }
                br.close();
                boolean delete1 = fileObj.delete();
                System.out.println(delete1);
            }

             createCSV(filename);
            fileObj=new File(filename);
            fname= fileObj.getName();
            fileExists=new File(filename).exists();

            bw = new BufferedWriter(new PrintWriter(new FileWriter(filename, true)));
            for (Song s: songList) {
                bw.write(s.getId() + "," + s.getTitle() + "," + s.getArtist() + "," + s.getDuration()+"");
                bw.newLine();
            }

            bw.close();
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    //Add new CSV File
    public static boolean createCSV(String filename){
        try {
            File file=new File(filename);
            if(file.createNewFile())
            {
                return true;
            }
            else
            {
                return false;
            }

        }catch (Exception e)
        {
            return false;
        }
    }

    public static List<Song> loadCSV(String filename)
    {
        BufferedReader br = null;
        List<Song> songList = new LinkedList<>();
        Song oSong = new Song();

        try
        {
            int count=0;
            boolean fileExists=new File(filename).exists();
            br= new BufferedReader(new FileReader(filename));
            String line= br.readLine();
            if(fileExists) {
                while (line != null) {
                    String[] fields = line.split(",");
                    oSong = new Song(Integer.parseInt(fields[0]), fields[1], fields[2], Integer.parseInt(fields[3]));
                    songList.add(oSong);
                    line = br.readLine();
                }
            }
        }
        catch (Exception e)
        {
            System.out.println("File Not Found.");

        }
        return songList;
    }
}