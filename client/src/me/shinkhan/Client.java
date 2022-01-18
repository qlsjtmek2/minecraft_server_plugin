package me.shinkhan;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class Client {
    private Socket socket; // ������ ������ socket
    private BufferedReader br; // �����κ��� message�� ���� BufferedReader
    private PrintWriter pw; // ������ message�� ���� PrintWriter
    private BufferedReader consoleBr; // console���� message�� �Է¹��� BufferedReader
    InetAddress ia;
 
    /*
     * go �޼���
     * console���� �Է��ϴ� �޼����� �޾ƿͼ�, ������ ������ �޼�������
     * thread�� �����ϴ� �޼����̴�.
     */
    public void go() throws UnknownHostException, IOException {
    	ia = InetAddress.getByName("localhost");
        socket = new Socket(ia, 5555);                // 5555��Ʈ�� ������ ������ ����
        
        // socket�� �Է½�Ʈ���� �޾ƿͼ�, ������ ������ �޼����� ���� �� �ְ� br�� �ʱ�ȭ
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        // socket�� ��½�Ʈ���� �޾ƿ� ������ �޼����� ������ �� �ְ� pw�� �ʱ�ȭ
        // ���⼭ true�� auto flush�� true�� �ְڴٴ� ���̰�, ������� ������ default�� false�� �����ȴ�.
        // autoflush�� ���ۿ� �����Ͱ� �����ڸ��� �ٷιٷ� ����ִ� ������
        // ���� autoflush�� false�� �ϸ� �޼��尡 ����� ������ ���ۿ� �����͸� �Է¹޴ٰ� 
        // ����Ǹ� ���۸� ����ش�.
        // �̷��� �Ǹ� �޼��� ����ñ��� �ڽ��� ���� �޼����� ������ ���޵��� �ʱ� ������ �ڽ��� �޼�����
        // �Է����ڸ��� ���۸� ��������ν� ������ �����͸� ������ �� �ִ�.
        pw = new PrintWriter(socket.getOutputStream(), true);
        
        // �ֿܼ��� �Է��ϴ� �޼����� �޾ƿ� �� �ְ� consoleBr�� �ʱ�ȭ
        consoleBr = new BufferedReader(new InputStreamReader(System.in));
        Thread t = new Thread(new Worker());
        
        // Thread�� daemonThread�� �������ν� ���ξ����尡 ����� �Բ� ����ǵ��� ����
        t.setDaemon(true);
        
        // Thread �����غ�
        t.start();
 
        
        //�� while���� ������ ��� �޼����� ������ �� �ְ� ����� �ش�.
        while (true) {
            String message = null;
            message = consoleBr.readLine();
            pw.println(message);
            if (message == null || message.equals("null")
                    || message.equals("����")) {
                System.out.println("Ŭ���̾�Ʈ ����");
                break;
            }
 
        }
    }
 
    public class Worker implements Runnable {
 
        /*
         * �ַܼκ��� �ڽ��� �Է��� �޼����� ��� �޾ƿ��� �޼���� 
         * �����带 �����ϴ� �κ��̴�.
         */
        @Override
        public void run() {    
            while (true) {    
                try {
                    String message = br.readLine();        // �ֿܼ� �Է��� �����͸� message�� �Ҵ�
                    if (message == null || message.equals("null")    
                            || message.equals("����")) {            // ä�� ���� ����
                        System.out.println("Ŭ���̾�Ʈ ����");
                        break;
                    }
                    System.out.println(message);        //������ �� �޼����� ���ο��Ե� �����ߵǱ� ������
                                                                    //������ ȭ�鿡�� ������ش�.
                    
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