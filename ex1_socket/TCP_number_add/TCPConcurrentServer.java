// EchoConcurrentServer.java
import java.net.*;
import java.io.*;

public class TCPConcurrentServer
{
  public static final int PORT = 1667;
  public static void main(String[] args)
  {
    ServerSocket serverSocket = null;
    Socket serviceSocket = null;
    int port = PORT;

    try{ // create socket
        if (args.length == 1) // user specify port number
            port = Integer.parseInt(args[0]);
        else if (args.length > 1) { // error
            System.out.println("Usage: Java EchoServer [port number]");
            System.exit(1);
        }
        if (port <= 1023 || port > 65535)
            throw new NumberFormatException();
        serverSocket = new ServerSocket(PORT);
    }catch (IOException e){
      System.err.println("Error Creating Socket");
      System.exit(1);
    }catch (NumberFormatException e) {
        System.out.println("Port number must be integer between 1024 and 65535");
        System.exit(3);
    }

    while (true)
    {
        try{
            // wait for connection then create streams
            System.out.println("Waiting for client connection at port number " + port);
            serviceSocket = serverSocket.accept();
            EchoThread echoThread = new EchoThread(serviceSocket);
            echoThread.start();

	    } catch (IOException e){
            System.err.println("Closing Socket connection");
            System.exit(1);
      	}
    }
  }
}
class EchoThread extends Thread {
    private Socket serviceSocket;

    public EchoThread(Socket serviceSocket) {
        this.serviceSocket = serviceSocket;
    }
    public void run() {
        OutputStreamWriter output = null;
        BufferedReader input = null;
        try {
            output = new OutputStreamWriter(serviceSocket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(serviceSocket.getInputStream()));
            int[] nums = new int[3];
            while(true) {
		        String firstNum = input.readLine();
		        if (!firstNum.equals(""))
                {
                    nums[0] = Integer.parseInt(firstNum);
                }else {
                    output.close();
                    input.close();
                    serviceSocket.close();
                    break;
                }
                String secondNum = input.readLine();
		        if (!secondNum.equals(""))
                {
                    nums[1] = Integer.parseInt(secondNum);
                }else {
                    output.close();
                    input.close();
                    serviceSocket.close();
                    break;
                }
                nums[2] = nums[0] + nums[1];
                output.write(nums[2] + "\r\n");
                output.flush();
            }
        }catch (IOException e) {
            System.err.println("Closing Socket connection");
        }
        finally {
            try {
                if (input != null)
                    input.close();
                if (output != null)
                    output.close();
                if (serviceSocket != null)
                    serviceSocket.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

