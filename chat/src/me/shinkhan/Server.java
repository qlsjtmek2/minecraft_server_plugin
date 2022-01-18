package me.shinkhan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
 
public class Server {
    /*
     * Worker class(nested design pattern ����)�� client�� �������� ����� �ϴ� class�̴�. �� worker�� �� client�� ����Ѵ�.
     * ex) ������ ����(���� ���� Ŭ���̾�Ʈ ���� �°� �׻� �����Ѵٰ� ����)�� �����غ��� �ȴ�.
     * ����(Worker)�� ���� �� ��(client)���� ��ȭ�� �Ѵ�.
     */
    private ArrayList<Worker> clientList = new ArrayList<Worker>();        // worker�� ������ list
 
    public void go() throws IOException {
        ServerSocket serverSocket = new ServerSocket(5555);        //5555 ��Ʈ�� server ����
                                                                                                    //�� ������ �����ϱ� ���ؼ��� client���� ��Ʈ�� 5555�� ���߰�
                                                                                                    //������ �����ؾ� �Ѵ�.
        System.out.println("ä�� ����");
        try {
            while (true) {
                Socket socket = serverSocket.accept();        //client�� server�� ������
                Worker worker = new Worker(socket);        //client�� ����� worker�� �����ϰ� client socket�� �Ű������� �Ѱ���
                clientList.add(worker);                                //list�� worker �߰�
                new Thread(worker).start();                        //������ �غ�
                                                                                //ä���� ����� ����̱� ������ �����尡 �ʿ��ϴ�. 
                                                                                //�����带 ������� ������ �� client�� �޼����� �����µ��ȿ�
                                                                                //�ٸ� client�� �޼����� ���� �� ����.
            }
        } finally {
            serverSocket.close();
        }
 
    }
 
    /*
     * broadcast(String message)
     * � client�� �޼����� ������ ���� server�� �������� ���
     * client�鿡�� �� �޼����� ���۵Ǿ�� �ϱ� ������ ����ϴ� �޼����̴�.
     * 
     * �޼����� ���۵Ǹ� ���� list�� �ִ� ��� worker����
     *  ���� ���� client�鿡�� �޼����� �����Ѵ�.
     */
    public void broadcast(String message) {
        for( Worker w : clientList ){
            w.sendMessage(message);            //�ش� worker�� ���� client���� ���ڰ����� �޾ƿ� message�� 
                                                            //�������ش�.
        }
    }
 
    public class Worker implements Runnable {        // client�� ���������� ��� ���� ��ü
        private Socket socket;
        private BufferedReader br;    //client�� ���� �Էµ� ������ �о�� �� ����� ��ü
        private PrintWriter pw;        //client���� �޼����� �����ϱ� ���� ����� ��ü
        private String user;
 
        public Worker(Socket socket) {
            this.socket = socket;
            user = "[" + socket.getInetAddress() + "]�� : ";
            System.out.println(user + "�����ϼ̽��ϴ�.");
        }
 
        public void chatting() throws IOException {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //�ش� soket�� inputstream�� �޾ƿ´�.
            pw = new PrintWriter(socket.getOutputStream(),true);        //�ش� socket�� outputstream�� �޾ƿ´�.
                
            while(true){
                String message = br.readLine();        //client�� message�� �о�� �Ҵ��Ѵ�.
                if(message.equals("null") || message == null || message.equals("����")){        //client�� server�� ���� ���� ����
                    socket.close();                //�ش� client�� socket�� �����ش�.
                    System.out.println(user + "�������ϴ�.");
                    broadcast(user + "�������ϴ�.");
                    clientList.remove(this);    // client���� ����� �������Ƿ� �ش� worker�� �����Ѵ�.
                    break;    // while���� ���
                }
                broadcast(socket.getInetAddress() + "�� message : " +message);    //��� client�鿡�� �޼����� �����ϱ� ����
                                                                                                                        // broadcast�� ȣ���Ѵ�.
            }
 
        }
        
        public void sendMessage(String message){        // worker�� client���� message�� ������ �� ����ϴ� �޼���
            pw.println(message);                                        // message ����
        }
 
        @Override
        public void run() {            //������ ����
            try {
                chatting();                //
            } catch (IOException e) {    //client������ �ǵ�ġ���� ���ܰ� �߻����� ��� ������ �����ش�.
                System.out.println(user + "�������ϴ�.");
                broadcast(user + "�������ϴ�.");
                clientList.remove(this);
            } finally {
                closeAll();
            }
        }    //run ��
        
        public void closeAll(){
            if( pw != null )
                pw.close();
            if( br != null )
                try{ br.close(); } catch(IOException e){ e.printStackTrace(); }
            if( socket != null )
                try{ socket.close(); } catch(IOException e){ e.printStackTrace(); }
        }
    }        //Worker class ��
    
    
 
    public static void main(String[] args) {
        try {
            new Server().go();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}