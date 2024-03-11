import java.io.*;
import java.net.*;

class Servidor
{
   private static int portaServidor = 9875;

   public static void main(String argv[]) throws Exception
   {
      //Efetua as primitivas socket e bind, respectivamente
      ServerSocket socket = new ServerSocket(portaServidor);

      while(true)
      {
         //Efetua as primitivas de listen e accept, respectivamente
         Socket conexao = socket.accept();

         //Efetua a primitiva receive
         System.out.println("Aguardando datagrama do cliente....");
         BufferedReader entrada =  new BufferedReader(new InputStreamReader(conexao.getInputStream()));

         //Operacao com os dados recebidos e preparacao dos a serem enviados
         String str = entrada.readLine();
         System.out.println("Received: " + str);

         str = str.toUpperCase();

         //Efetua a primitiva send
         DataOutputStream saida = new DataOutputStream(conexao.getOutputStream());
         boolean isSalvo = salva_arquivo(str);
         if(isSalvo) saida.writeBytes("Salvo com sucesso!");
         else saida.writeBytes("Erro ao salvar");
         //Efetua a primitiva de close
         conexao.close();
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
