package com.micro.android316.housekeeping.utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

	Socket socket;

	Handler handler;
	public Client(Handler handler){
		this.handler=handler;
		Log.e("hhh","com on");
		new Thread(){
			public void run(){
				try {
					socket = new Socket("139.199.196.199",1992);
					new get(socket).start();
					Log.e("hhh",socket+"nmmmb");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}.start();


	}

public void send(String content){
	OutputStreamWriter osw=null;
	try {
		//Log.e("hhh",socket+"sadsssss");
		osw=new OutputStreamWriter(socket.getOutputStream());
		osw.write(content+"\n");
		osw.flush();

	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{

	}
}



class get extends Thread {
	Socket s = null;

	get(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		try {
			Log.e("hhh","zzzzzzzzzzz");
			while (true) {
				Log.e("hhh","22222222222222"+socket);
				BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
				String str = br.readLine();
				if (s.isClosed()||str==null) {
					break;
				}
				//System.out.println(str);
				//	br.close();
				Log.e("hhh",str+"zzzzzzzzzzz");

				Message message=Message.obtain();
				message.obj=str;
				handler.sendMessage(message);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

	public void destory(){
		try {
			send("null@null");
			socket.close();
			if(!socket.isClosed()){
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
