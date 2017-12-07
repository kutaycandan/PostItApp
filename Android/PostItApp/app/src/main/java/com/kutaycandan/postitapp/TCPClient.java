package com.kutaycandan.postitapp;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.Stack;

import static java.lang.Thread.sleep;

/**
 * Created by kutay on 4.12.2017.
 */

public class TCPClient {
    public static final String IP= "192.168.4.20";
    public static final int HOST= 6789;
    private String toServerMessage;
    private String fromServerMessage;
    private PrintStream outToServer;
    private Scanner inFromServer;
    private boolean isConnected;
    //private Stack <String> messages;
    private static String message="Bos";
    private static Socket socket;
    public TCPClient(){

    }
    public void openConnection(){
        try {
            socket = new Socket(IP, HOST);
            Log.d("DEBUG","Connection kuruldu");
            outToServer= new PrintStream(socket.getOutputStream());
            inFromServer = new Scanner(socket.getInputStream());
            while(inFromServer.hasNextLine()){
                message=inFromServer.nextLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void destroyConnection(){
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void sendMessage(String s){
        Log.d("DEBUG","MesajGitti"+s);
        outToServer.println(s);
    }


    public String readMessage(){
        String tmp;
        while(message=="Bos"){
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        tmp=message;
        message ="Bos";
        return tmp;
    }




}
