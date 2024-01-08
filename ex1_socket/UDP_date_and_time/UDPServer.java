import java.net.*;
import java.util.Date;

class UDPServer {
  public static void main(String args[]) throws Exception{
    try (DatagramSocket serverSocket = new DatagramSocket(9876);) {
      byte[] receiveData = new byte[1024];
      byte[] sendData  = new byte[1024];
      while(true)
      {
        System.out.println("The server is waiting ");
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        //String sentence = new String(receivePacket.getData());
        InetAddress IPAddress = receivePacket.getAddress();
        int port = receivePacket.getPort();

        Date now = new Date();
        String dateAndTimeString = now.toString();

        sendData = dateAndTimeString.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
        serverSocket.send(sendPacket);
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }
}