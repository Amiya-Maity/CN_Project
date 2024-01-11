import java.net.*;

public class Host2 {
  public static void main(String[] args) {
    try {
      DatagramSocket socket = new DatagramSocket();
      InetAddress h1Address = InetAddress.getByName("127.0.0.1"); // Replace with the actual IP address of H1
      int h1Port = 5555;
      int i = 1;
      byte[] sendData = new byte[1024];
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, h1Address, h1Port);
      socket.send(sendPacket);
      String data;
      do {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        data = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("line " + i++ + ": " + data);
        sendData = new byte[1024];
        sendPacket = new DatagramPacket(sendData, sendData.length, h1Address, h1Port);
        socket.send(sendPacket);
      } while (!data.substring(0, 3).equals("end"));
      socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}