import java.net.*;
import java.io.*;
public class Worker extends Thread{
    private Socket connection = null;
    private BufferedReader inFromClient;
    private DataOutputStream outToClient;
    private String data;
    private Integer tickets;
    Reservation booking;

    public Worker(Socket newSocket, Reservation show){
        connection = newSocket;
        booking = show;
        try{
            inFromClient = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            outToClient = new DataOutputStream(connection.getOutputStream());
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run(){

            try {
                tickets = booking.checkTickets();
                Thread.sleep(1000);

                if(tickets == -1){
                    outToClient.writeBytes("Tickets are finished");
                    connection.close();
                }
                else{
                    //send data to client and close connection
                    outToClient.writeBytes("Tickets available: " + tickets);
                    System.out.println("[+] Connection closed with: " + connection.getInetAddress() + ":" + connection.getPort());

                    connection.close();
                    System.out.println("\n[+] Waiting for connection");
                }


            }
            catch(IOException | InterruptedException e){
                e.printStackTrace();
            }
    }
}
