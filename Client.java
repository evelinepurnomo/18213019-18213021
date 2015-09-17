/*2nd Assignment of Integrative Programming */
/* 18213019-18213021*/
/* File transfer client */
/*Source: http://www.rgagnon.com/javadetails/java-0542.html*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.io.File;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.*;

public class Client implements Runnable {

BufferedReader br1, br2;
PrintWriter pr1;
Socket socket;
Thread t1, t2;
String in = "", out = "";
int bytesRead;
int current = 0;
BufferedWriter bw;
File file=null;
byte [] mybytearray  = null;
String namafile="";
public final static String FILE_A_RECEIVED = "A.txt";
public final static String FILE_B_RECEIVED = "B.txt";
public final static String FILE_C_RECEIVED = "C.txt";
public final static int FILE_SIZE = 6022386;

public Client(String serverip, int port) {
    try {
        t1 = new Thread(this);
        t2 = new Thread(this);
        socket = new Socket(serverip, port);
		System.out.println ("Ketik 1/2/3 untuk memilih file yang diinginkan: 1) A.txt; 2) B.txt; 3) C.txt");
        t1.start();
        t2.start();
    } catch (Exception e) {
    }
}

public void run() {

    try {
        if (Thread.currentThread() == t2) {
            do {
                br1 = new BufferedReader(new InputStreamReader(System.in));
                pr1 = new PrintWriter(socket.getOutputStream(), true);
                in = br1.readLine();
                pr1.println(in);
            } while (!in.equals("END"));
        } else {
            do {
				br2 = new BufferedReader(new   InputStreamReader(socket.getInputStream()));
				out = br2.readLine();
				if (!((in.equals ("1") || in.equals("2")) || in.equals("3") || in.equals("."))) {
					System.out.println("Server says : : : " + out);
				}
				else {
					if (in.equals ("1")) {
						namafile = FILE_A_RECEIVED;
					}
					if (in.equals ("2")) {
						namafile = FILE_B_RECEIVED;
					}
					if (in.equals ("3")) {
						namafile = FILE_C_RECEIVED;
					}
					File file = new File(namafile);
					bw = new BufferedWriter (new FileWriter (namafile));
					if (!file.exists()) {
						file.createNewFile();
					};
					bw.write(out);
					bw.close();
					//if (socket != null) socket.close();
				}
            } while (!out.equals("END"));
        }
    } catch (Exception e) {
    }
	finally {
	}
 }

 public static void main(String[] args) {
     Client client1 = null;
	 if (args.length != 2)
         System.out.println("Usage: java ChatClient host port");
      else
         client1 = new Client(args[0], Integer.parseInt(args[1]));
 }
 }
