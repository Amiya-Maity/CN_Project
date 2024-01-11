import java.net.*;

public class Host1 {
  public static void main(String[] args) {
    try {
      DatagramSocket socket = new DatagramSocket();// from ds used for udp
      DatagramSocket socket1 = new DatagramSocket(5555);// sent to h2

      InetAddress serverAddress = InetAddress.getByName("127.0.0.1");
      int serverPort = 9876;
      byte[] sendData = new byte[1024];
      DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPort);
      socket.send(sendPacket);
      byte[] receiveData = new byte[1024];
      String dataFromDS;
      int i = 1;
      byte[] receiveData1 = new byte[1024];
      DatagramPacket receivePacket1 = new DatagramPacket(receiveData1, receiveData1.length);
      socket1.receive(receivePacket1);
      InetAddress clientAddress = receivePacket1.getAddress();
      int clientPort = receivePacket1.getPort();
      System.err.println(clientAddress + ":" + clientPort);
      do {
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        socket.receive(receivePacket);
        dataFromDS = new String(receivePacket.getData(), 0, receivePacket.getLength());
        System.out.println("line " + i++ + " : " + dataFromDS);
        byte[] dataToHost2 = dataFromDS.getBytes();
        DatagramPacket sendPacket1 = new DatagramPacket(dataToHost2, dataToHost2.length, clientAddress,
            clientPort);
        socket1.send(sendPacket1);
        receiveData1 = new byte[1024];
        receivePacket1 = new DatagramPacket(receiveData1, receiveData1.length);
        socket1.receive(receivePacket1);
        if (!dataFromDS.substring(0, 3).equals("end")) {
          byte[] valid = new byte[1024];
          sendPacket = new DatagramPacket(valid, valid.length, serverAddress, serverPort);
          socket.send(sendPacket);
        }
      } while (!dataFromDS.substring(0, 3).equals("end"));
      socket.close();
      socket1.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
