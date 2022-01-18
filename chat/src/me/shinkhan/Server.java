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
     * Worker class(nested design pattern 적용)는 client와 직접적인 통신을 하는 class이다. 한 worker당 한 client를 담당한다.
     * ex) 고객센터 상담원(상담원 수는 클라이언트 수에 맞게 항상 존재한다고 가정)을 생각해보면 된다.
     * 상담원(Worker)은 오직 한 고객(client)과의 통화를 한다.
     */
    private ArrayList<Worker> clientList = new ArrayList<Worker>();        // worker를 저장할 list
 
    public void go() throws IOException {
        ServerSocket serverSocket = new ServerSocket(5555);        //5555 포트의 server 생성
                                                                                                    //이 서버와 연결하기 위해서는 client측의 포트를 5555로 맞추고
                                                                                                    //서버에 접속해야 한다.
        System.out.println("채팅 시작");
        try {
            while (true) {
                Socket socket = serverSocket.accept();        //client가 server에 접속함
                Worker worker = new Worker(socket);        //client를 담당할 worker를 생성하고 client socket을 매개변수로 넘겨중
                clientList.add(worker);                                //list에 worker 추가
                new Thread(worker).start();                        //쓰레드 준비
                                                                                //채팅은 양방향 통신이기 때문에 쓰레드가 필요하다. 
                                                                                //쓰레드를 사용하지 않으면 한 client가 메세지를 보내는동안에
                                                                                //다른 client의 메세지를 받을 수 없다.
            }
        } finally {
            serverSocket.close();
        }
 
    }
 
    /*
     * broadcast(String message)
     * 어떤 client가 메세지를 보내면 현재 server에 접속중인 모든
     * client들에게 이 메세지가 전송되어야 하기 때문에 사용하는 메서드이다.
     * 
     * 메세지가 전송되면 현재 list에 있는 모든 worker들이
     *  각자 맡은 client들에게 메세지를 전송한다.
     */
    public void broadcast(String message) {
        for( Worker w : clientList ){
            w.sendMessage(message);            //해당 worker가 맡은 client에게 인자값으로 받아온 message를 
                                                            //전송해준다.
        }
    }
 
    public class Worker implements Runnable {        // client와 직접적으로 통신 맡을 객체
        private Socket socket;
        private BufferedReader br;    //client로 부터 입력된 정보를 읽어올 때 사용할 객체
        private PrintWriter pw;        //client에게 메세지를 전송하기 위해 사용할 객체
        private String user;
 
        public Worker(Socket socket) {
            this.socket = socket;
            user = "[" + socket.getInetAddress() + "]님 : ";
            System.out.println(user + "입장하셨습니다.");
        }
 
        public void chatting() throws IOException {
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));    //해당 soket의 inputstream을 받아온다.
            pw = new PrintWriter(socket.getOutputStream(),true);        //해당 socket의 outputstream을 받아온다.
                
            while(true){
                String message = br.readLine();        //client의 message를 읽어와 할당한다.
                if(message.equals("null") || message == null || message.equals("종료")){        //client가 server를 나갈 때의 조건
                    socket.close();                //해당 client의 socket을 끊어준다.
                    System.out.println(user + "나갔습니다.");
                    broadcast(user + "나갔습니다.");
                    clientList.remove(this);    // client와의 통신이 끝났으므로 해당 worker를 삭제한다.
                    break;    // while문을 벗어남
                }
                broadcast(socket.getInetAddress() + "님 message : " +message);    //모든 client들에게 메세지를 전송하기 위해
                                                                                                                        // broadcast를 호출한다.
            }
 
        }
        
        public void sendMessage(String message){        // worker가 client에게 message를 전송할 때 사용하는 메서드
            pw.println(message);                                        // message 전송
        }
 
        @Override
        public void run() {            //쓰레드 시작
            try {
                chatting();                //
            } catch (IOException e) {    //client측에서 의도치않은 예외가 발생했을 경우 접속을 끊어준다.
                System.out.println(user + "나갔습니다.");
                broadcast(user + "나갔습니다.");
                clientList.remove(this);
            } finally {
                closeAll();
            }
        }    //run 끝
        
        public void closeAll(){
            if( pw != null )
                pw.close();
            if( br != null )
                try{ br.close(); } catch(IOException e){ e.printStackTrace(); }
            if( socket != null )
                try{ socket.close(); } catch(IOException e){ e.printStackTrace(); }
        }
    }        //Worker class 끝
    
    
 
    public static void main(String[] args) {
        try {
            new Server().go();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}