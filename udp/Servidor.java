import java.io.*;
import java.net.*;

class Servidor
{
   private static int portaServidor = 9875;
   private static byte[] receiveData = new byte[1024];
   private static byte[] sendData = new byte[1024];

   public static void main(String args[]) throws Exception
   {
      DatagramSocket serverSocket = new DatagramSocket(portaServidor);

      while(true) 
      {
         DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

         System.out.println("Aguardando datagrama do cliente....");
         serverSocket.receive(receivePacket);
         
         String parsedData = new String(receivePacket.getData());
         System.out.println("RECEIVED: " + parsedData);
         boolean isSalvo = salva_arquivo(parsedData);
         if(isSalvo) sendData = "Salvo com sucesso!".getBytes();
         else sendData = "Erro ao salvar".getBytes();
         InetAddress ipCliente = receivePacket.getAddress();
         int portaCliente = receivePacket.getPort();

         DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipCliente, portaCliente);
         serverSocket.send(sendPacket);
         System.out.println("Enviado...");
      }
   }

   public static boolean salva_arquivo(String str){
      try{
         System.out.println(str);
         FileWriter writer = new FileWriter("inputs.txt");
         writer.write(str);
         writer.close();
         return true;
      }catch(IOException e) {
           System.out.println("Ocorreu um erro ao criar o arquivo.");
           return false;
      }
   }
}
