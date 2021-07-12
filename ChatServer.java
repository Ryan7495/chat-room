/*
 Institution: Dalhousie University
 Class: CSCI 3171 Network Computing
 Instructor: Shrini Sampali
 Assignment: 4 Chat Server

 Author: Ryan
 Date: November 25 2019
 
 TODO: file desription
 
*/

import java.net.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;


public class ChatServer
{
    private static ServerSocket serverSock;
    private static final int PORT = 1234;
    
    
    public static void main(String [] args) throws IOException
    {
        try
        {
            serverSock = new ServerSocket(PORT);
        }
        catch (IOException e)
        {
            System.out.println("Can't listen on " + PORT);
            System.exit(1);
        }
        do
        {
            Socket client = null;
            System.out.println("Listening for connection...");
            try
            {
                client = serverSock.accept();
                System.out.println("New client accepted");
                ClientHandler handler = new ClientHandler(client);
                handler.start();
                
                /* ======= */
                
            
            }
            catch (IOException e)
            {
                System.out.println("Accept failed");
                System.exit(1);
            }
            System.out.println("Connection successful");
            System.out.println("Listening for input ...");
        }
        while(true);
    }
    
}


/* TODO: 2 versions of client handler?
 1 for output
 1 for input
 
 2 for each clinet????
 */
class ClientHandler extends Thread
{
    /* Attributes */
    private Socket client;
    private BufferedReader in;
    private PrintWriter out;
    private String name;
    private int key;
    public int mode;
    public static final int numClients = 4;
    private static int encrypted_pool_key = new Random().nextInt(25) + 1;
    public String[] clientNames = new String[numClients];
    public int clientNum;
    
    private CeasarCipher cipher = new CeasarCipher();
    
    /* Section 1 pool */
    public static ArrayList<PrintWriter> unencrypted_pool = new ArrayList<PrintWriter>();
    
    /* Section 2 pool */
    public static ArrayList<PrintWriter> encrypted_pool = new ArrayList<PrintWriter>();
    
    /* encrypted pair matching queue */
    public static ArrayList<PrintWriter> pair_q = new ArrayList<PrintWriter>();
    
    
    /* Constructor */
    public ClientHandler(Socket socket)
    {
        client = socket;
        try
        {
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(client.getOutputStream(),true);
            
            name = in.readLine();
            mode = Integer.parseInt(in.readLine());
            System.out.println("mode: " + mode);
            switch(mode)
            {
                case 1:
                    key = 0;
                    //client_channels.add(handler);
                    unencrypted_pool.add(out);
                    break;
                
                case 2:
                    key = encrypted_pool_key;
                    //client_channels.add(handler);
                    encrypted_pool.add(out);
                    break;
                    
                case 3:
                    key = new Random().nextInt(26);
                    //client_channels.add(handler);
                    pair_q.add(out);
                    break;
                    
                default:
                    key = 0;
                    //client_channels.add(handler);
                    unencrypted_pool.add(out);
            }
            
            //give client its key
            out.println(key);
            
            System.out.println("\nNew client: " + name + "\t\tConnection mode: " + mode + "\t\tKey: " + key);
            
            
            /* Not too sure about this part */
            String clientName = getName().substring(getName().length()-1);
            int clientNum = Integer.parseInt(clientName);
            clientNames[clientNum] = name;
            //TODO: fix
            System.out.println("\nclientName: " + clientName + "\nclientNum: " + clientNum);
            
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    /* Get functions */
    public BufferedReader getIn()
    {
        return in;
    }
    public PrintWriter getOut()
    {
        return out;
    }
    public int getMode()
    {
        return mode;
    }
    
    /* Other methods */
    
    public void run()
    {
        try
        {
            String received;
            do
            {
                //Receive input from a client
                received = in.readLine();
                String clientName=getName().substring(getName().length()-1);
                int clientNum = Integer.parseInt(clientName);
                System.out.println("Encrypted message from " + clientNames[clientNum] + ": " + received);
                
                System.out.println("up size: " + unencrypted_pool.size());
                
                switch(getMode())
                {
                    case 1:
                        for (int i = 0; i < unencrypted_pool.size(); i++)
                        {
                            //if (
                            received = "Message from " +  clientNames[clientNum] + ": " + received;
                            unencrypted_pool.get(i).println(received);
                            //break;
                        }
                        
                        break;
                        
                    case 2:
                        for (int i = 0; i < unencrypted_pool.size(); i++)
                        {
                            //if (
                            received = "Message from " +  clientNames[clientNum] + ": " + received;
                            encrypted_pool.get(i).println(received);
                            //break;
                        }
                        break;
                        
                    case 3:
                        for (int i = 0; i < unencrypted_pool.size(); i++)
                        {
                            //if (
                            received = "Message from " +  clientNames[clientNum] + ": " + received;
                            pair_q.get(i).println(received);
                            //break;
                        }
                        break;
                        
                    default:
                        System.out.println("ERROR");
                        
                }
                
                
                //out.println("ECHO: " + received);
                //out.println(received);
                //print message to every other person in that list
                
                
                
                
                /*for (int i = 0; i < arraylist.size(); i++)
                    print Message from -usr-: message
                    if -usr- is not sender
                */
                
                
                
                
 
            }
            while (!received.equals("BYE"));
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                if(client != null)
                {
                    System.out.println("Closing down connection...");
                    client.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
}


