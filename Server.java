/*2nd Assignment of Integrative Programming */
/* 18213019-18213021*/
/* File transfer server */
/*Source: http://www.rgagnon.com/javadetails/java-0542.html*/

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.BufferedInputStream;
import java.io.OutputStream;

public class Server implements Runnable {

ServerSocket serversocket;
BufferedReader br1, br2;
PrintWriter pr1, pr2;
Socket socket;
Thread t1, t2;
String in="",out="";
String in2="";
FileInputStream fis = null;
BufferedInputStream bis = null;
OutputStream os = null;
File myFile = null;
public final static String FILE_A = "A.txt";
public final static String FILE_B = "B.txt";
public final static String FILE_C = "C.txt";

public Server(int port) {
    try {
        t1 = new Thread(this);
        t2 = new Thread(this);
        serversocket = new ServerSocket(port);
        System.out.println("Server is waiting. . . . ");
        socket = serversocket.accept();
        System.out.println("Client connected with Ip " +        socket.getInetAddress().getHostAddress());
        t1.start();;
        t2.start();

    } 
    catch (Exception e) {
    }
 }

 public void run() {
    try {
        if (Thread.currentThread() == t1) {
            do {
                br1 = new BufferedReader(new InputStreamReader(System.in));
                pr1 = new PrintWriter(socket.getOutputStream(), true);
                in = br1.readLine();
                pr1.println(in);
            } while (!in.equals("END"));
        } else {
            do {
				pr2 = new PrintWriter(socket.getOutputStream(), true);
                br2 = new BufferedReader(new   InputStreamReader(socket.getInputStream()));
                out = br2.readLine();
				if (out.compareTo("1") == 0) {
					System.out.println ("akan mengirim file A");
					//pr2.println ("Server"+ in2);
					// send file
					myFile = new File (FILE_A);
					byte [] mybytearray  = new byte [(int)myFile.length()];
					fis = new FileInputStream(myFile);
					bis = new BufferedInputStream(fis);
					bis.read(mybytearray,0,mybytearray.length);
					os = socket.getOutputStream();
					System.out.println("Sending " + FILE_A + "(" + mybytearray.length + " bytes)");
					os.write(mybytearray,0,mybytearray.length);
					os.flush();
					pr2.println (".");
				}
				else {
					if (out.compareTo("2") == 0) {
						System.out.println ("akan mengirim file B");
						//in2 = " akan mengirim file B";
						// send file
						myFile = new File (FILE_B);
						byte [] mybytearray  = new byte [(int)myFile.length()];
						fis = new FileInputStream(myFile);
						bis = new BufferedInputStream(fis);
						bis.read(mybytearray,0,mybytearray.length);
						os = socket.getOutputStream();
						System.out.println("Sending " + FILE_B + "(" + mybytearray.length + " bytes)");
						os.write(mybytearray,0,mybytearray.length);
						os.flush();
						pr2.println (".");
					}
					else {
						if (out.compareTo("3") == 0) {
							System.out.println ("akan mengirim file C");
							//in2 = " akan mengirim file C";
							// send file
							myFile = new File (FILE_C);
							byte [] mybytearray  = new byte [(int)myFile.length()];
							fis = new FileInputStream(myFile);
							bis = new BufferedInputStream(fis);
							bis.read(mybytearray,0,mybytearray.length);
							os = socket.getOutputStream();
							System.out.println("Sending " + FILE_C + "(" + mybytearray.length + " bytes)");
							os.write(mybytearray,0,mybytearray.length);
							os.flush();
							pr2.println (".");
						}
						else {
							if (out.compareTo("END") != 0) {
								System.out.println ("Masukan salah");
								in2 = " tidak dapat memproses permintaan, harap ulang";
								pr2.println ("Server"+in2);
							}
						}
					}
				}
                //System.out.println("Client says : : : " + out);
            } while (!out.equals("END"));
        }
    } 
    catch (Exception e) {
    }
  }

public static void main(String[] args) {
    Server server1 = null;
    if (args.length != 1)
        System.out.println("Usage: java ChatServer port");
    else
        server1 = new Server(Integer.parseInt(args[0]));
}
}
