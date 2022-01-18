package me.shinkhan;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class Client {
    private Socket socket; // 서버와 연결할 socket
    private BufferedReader br; // 서버로부터 message를 받을 BufferedReader
    private PrintWriter pw; // 서버로 message를 보낼 PrintWriter
    private BufferedReader consoleBr; // console에서 message를 입력받을 BufferedReader
    InetAddress ia;
 
    /*
     * go 메서드
     * console에서 입력하는 메세지를 받아와서, 서버로 보내는 메서드이자
     * thread를 실행하는 메서드이다.
     */
    public void go() throws UnknownHostException, IOException {
    	ia = InetAddress.getByName("localhost");
        socket = new Socket(ia, 5555);                // 5555포트의 서버로 연결할 소켓
        
        // socket의 입력스트림을 받아와서, 서버가 보내는 메세지를 받을 수 있게 br을 초기화
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // socket의 출력스트림을 받아와 서버로 메세지를 전송할 수 있게 pw를 초기화
        // 여기서 true는 auto flush를 true해 주겠다는 것이고, 명시하지 않으면 default로 false로 설정된다.
        // autoflush는 버퍼에 데이터가 들어오자마자 바로바로 비워주는 것으로
        // 만약 autoflush를 false로 하면 메서드가 종료될 때까지 버퍼에 데이터를 입력받다가 
        // 종료되면 버퍼를 비워준다.
        // 이렇게 되면 메서드 종료시까지 자신이 보낸 메세지는 서버로 전달되지 않기 때문에 자신이 메세지를
        // 입력하자마자 버퍼를 비워줌으로써 서버로 데이터를 전송할 수 있다.
        pw = new PrintWriter(socket.getOutputStream(), true);
        
        // 콘솔에서 입력하는 메세지를 받아올 수 있게 consoleBr을 초기화
        consoleBr = new BufferedReader(new InputStreamReader(System.in));
        Thread t = new Thread(new Worker());
        
        // Thread를 daemonThread로 해줌으로써 메인쓰레드가 종료시 함께 종료되도록 설정
        t.setDaemon(true);
        
        // Thread 실행준비
        t.start();
 
        
        //이 while문은 서버로 계속 메세지를 전송할 수 있게 만들어 준다.
        while (true) {
            String message = null;
            message = consoleBr.readLine();
            pw.println(message);
            if (message == null || message.equals("null")
                    || message.equals("종료")) {
                System.out.println("클라이언트 종료");
                break;
            }
 
        }
    }
 
    public class Worker implements Runnable {
 
        /*
         * 콘솔로부터 자신이 입력한 메세지를 계속 받아오는 메서드로 
         * 쓰레드를 시작하는 부분이다.
         */
        @Override
        public void run() {    
            while (true) {    
                try {
                    String message = br.readLine();        // 콘솔에 입력한 데이터를 message에 할당
                    if (message == null || message.equals("null")    
                            || message.equals("종료")) {            // 채팅 종료 조건
                        System.out.println("클라이언트 종료");
                        break;
                    }
                    System.out.println(message);        //본인이 쓴 메세지가 본인에게도 보여야되기 때문에
                                                                    //본인의 화면에도 출력해준다.
                    
                } catch (IOException e) {
                    e.printStackTrace();
                } 
            }
 
        }
    }
 
    public void closeAll() {
        if (pw != null)
            pw.close();
        if (br != null)
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (consoleBr != null)
            try {
                consoleBr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (socket != null)
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
 
    public static void main(String[] args) {
        try {
            new Client().go();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            new Client().closeAll();
        }
    }
}