// EchoClient.java
import java.net.*;
import java.io.*;

public class TCPClient{
  public static final int PORT = 1667;
  public static void main(String[] args){
	  Socket clientSocket = null;
    try{ // create socket
      clientSocket = new Socket("localhost", PORT);
    }
    catch (Exception e){
      System.err.println("Error Creating Socket");
    }

    try{
		  PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true);
      BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
      BufferedReader inputData = new BufferedReader(new InputStreamReader(System.in));
      while(true) {
        System.out.print("enter number 1 (to end just press enter):");
        String inputNum = inputData.readLine();
        output.write(inputNum + "\r\n");
        output.flush();
        if (inputNum.equals("")){
          break;
        }

        System.out.print("enter number 2 (to end just press enter):");
        inputNum = inputData.readLine();
        output.write(inputNum + "\r\n");
        output.flush();
        if (inputNum.equals("")){
          break;
        }
        String answer = input.readLine();
        System.out.println(answer);
      }
      input.close();
      output.close();
      clientSocket.close();
	  } catch (Exception e){
      System.err.println("Closing Socket connection");
      if (clientSocket != null)
      try {
        clientSocket.close();
      } catch (IOException ex) {

      }
    }
  }
}

