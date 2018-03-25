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
     * ��������������ȴ��ͻ������� 
     */  
    private static void startTcpService() {  
        try {  
            // ����ServerSocket  
            ServerSocket serverSocket = new ServerSocket(9999);  
            System.out.println("--�����������������˿� 9999--");  
  
            // �����˿ڣ��ȴ��ͻ�������  
            while (true) {
                System.out.println("--�ȴ��ͻ�������--");  
                Socket socket = serverSocket.accept(); //�ȴ��ͻ�������  
                System.out.println("�õ��ͻ������ӣ�" + socket);  
                  
                startReader(socket);  
            }  
  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
  
    /**  
     * �Ӳ�����Socket���ȡ���µ���Ϣ 
     */  
    private static void startReader(final Socket socket) {  
  
//        new Thread(){  
//            @Override  
//            public void run() {  
                DataInputStream reader;  
                try {  
                    // ��ȡ��ȡ��  
                    reader = new DataInputStream( socket.getInputStream());  
//                    while (true) {  
                        System.out.println("*�ȴ��ͻ�������*");  
                        // ��ȡ����  
                        String msg = reader.readUTF();  
                        System.out.println("��ȡ���ͻ��˵���Ϣ��" + msg);  
//                    }  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
//            }  
//        }.start();  
    } 
    
    
    
    // ����һЩ����
    private static final int MAX_LENGTH = 1024; // �������ֽڳ���
    private static final int PORT_NUM   = 5066;   // port��
    // ���Դ�Ž������ݵ��ֽ�����
    private static byte[] receMsgs = new byte[MAX_LENGTH];
    // ���ݱ��׽���
    private static DatagramSocket datagramSocket;
    // ���Խ������ݱ�
    private static DatagramPacket datagramPacket;
   
    public static void startUdpServer(){
        try {
            /******* ������������**/
            // ����һ�����ݱ��׽��֣�������󶨵�ָ��port��
            datagramSocket = new DatagramSocket(PORT_NUM);
            // DatagramPacket(byte buf[], int length),����һ���ֽ�����������UDP��
            datagramPacket = new DatagramPacket(receMsgs, receMsgs.length);
            System.out.println("ready to receive data");
            // receive()���ȴ�����UDP���ݱ�
            datagramSocket.receive(datagramPacket);
           
            /****** �������ݱ�****/
            String receStr = new String(datagramPacket.getData(), 0 , datagramPacket.getLength());
            System.out.println("Server Rece:" + receStr);
            System.out.println("Server Port:" + datagramPacket.getPort());
           
            /***** ����ACK��Ϣ���ݱ�*/
            // ��װ���ݱ�
            byte[] buf = " upd server has receive the message".getBytes();
            DatagramPacket sendPacket = new DatagramPacket(buf, buf.length, datagramPacket.getAddress(), datagramPacket.getPort());
            // ������Ϣ
            datagramSocket.send(sendPacket);
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // �ر�socket
            if (datagramSocket != null) {
                datagramSocket.close();
            }
        }
    }
    


}
