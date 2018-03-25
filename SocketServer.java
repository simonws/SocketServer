package com.demo.socketdemo;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

public class SocketServer {

	public SocketServer() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		startTcpService();
		
		startUdpServer();
	}
	
    /**  
     * 启动服务监听，等待客户端连接 
     */  
    private static void startTcpService() {  
        try {  
            // 创建ServerSocket  
            ServerSocket serverSocket = new ServerSocket(9999);  
            System.out.println("--开启服务器，监听端口 9999--");  
  
            // 监听端口，等待客户端连接  
            while (true) {
                System.out.println("--等待客户端连接--");  
                Socket socket = serverSocket.accept(); //等待客户端连接  
                System.out.println("得到客户端连接：" + socket);  
                  
                startReader(socket);  
            }  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /**  
     * 从参数的Socket里获取最新的消息 
     */  
    private static void startReader(final Socket socket) {  
  
//        new Thread(){  
//            @Override  
//            public void run() {  
                DataInputStream reader;  
                try {  
                    // 获取读取流  
                    reader = new DataInputStream( socket.getInputStream());  
//                    while (true) {  
                        System.out.println("*等待客户端输入*");  
                        // 读取数据  
                        String msg = reader.readUTF();  
                        System.out.println("获取到客户端的信息：" + msg);  
//                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
//            }  
//        }.start();  
    } 
    
    
    
    // 定义一些常量
    private static final int MAX_LENGTH = 1024; // 最大接收字节长度
    private static final int PORT_NUM   = 5066;   // port号
    // 用以存放接收数据的字节数组
    private static byte[] receMsgs = new byte[MAX_LENGTH];
    // 数据报套接字
    private static DatagramSocket datagramSocket;
    // 用以接收数据报
    private static DatagramPacket datagramPacket;
   
    public static void startUdpServer(){
        try {
            /******* 接收数据流程**/
            // 创建一个数据报套接字，并将其绑定到指定port上
            datagramSocket = new DatagramSocket(PORT_NUM);
            // DatagramPacket(byte buf[], int length),建立一个字节数组来接收UDP包
            datagramPacket = new DatagramPacket(receMsgs, receMsgs.length);
            System.out.println("ready to receive data");
            // receive()来等待接收UDP数据报
            datagramSocket.receive(datagramPacket);
           
            /****** 解析数据报****/
            String receStr = new String(datagramPacket.getData(), 0 , datagramPacket.getLength());
            System.out.println("Server Rece:" + receStr);
            System.out.println("Server Port:" + datagramPacket.getPort());
           
            /***** 返回ACK消息数据报*/
            // 组装数据报
            byte[] buf = " upd server has receive the message".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, datagramPacket.getAddress(), datagramPacket.getPort());
            // 发送消息
            datagramSocket.send(sendPacket);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭socket
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
    }
    


}
