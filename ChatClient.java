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

//2 threads, 1 for input 1 for putput
//"-b" is backspace when printed to the cancle
//carriage return \r\033[2k

//add thread for input



public class ChatClient
{
    private static InetAddress host;
    private static final int PORT = 1234;
    private static Socket link;
    //private static BufferedReader in;
    private static InputReader in;
    private static PrintWriter out;
    private static BufferedReader keyboard;
    public static String name;
    private static int key = -1;
    public static int mode;
    public static int clientNum;
    
    
    public static void main(String [] args) throws IOException
    {
        try
        {
            InetAddress host = InetAddress.getLocalHost();
            link = new Socket(host, PORT);
            //link = new Socket("127.0.0.1",PORT);
            
            //in = new BufferedReader(new InputStreamReader(link.getInputStream()));
            in = new InputReader(link);
            in.start();
            
            out = new PrintWriter(link.getOutputStream(), true);
            keyboard = new BufferedReader(new InputStreamReader(System.in));
            CeasarCipher cipher = new CeasarCipher();
            
            System.out.print("\nEnter your client name: ");
            name = keyboard.readLine();
            out.println(name);
            
            System.out.print("\nConnection Modes:\n1: Unencrypted connection to unencrypted pool.\n2: Encrypted connection to encrypted pool.\n3: Encrypted connection to encrypted pair pool.\n\nNote: To test section x, set all client modes to x.\n");
            
            System.out.print("\nEnter your connection mode: ");
            String mode_ = keyboard.readLine();
            
            /* Filter input */
            if (mode_.charAt(0) - 48 >= 1 && mode_.charAt(0) - 48 <= 3)
                mode = Integer.parseInt(mode_);
            else
                mode = 0;

            out.println(mode);
            //System.out.print("\n");
            key = Integer.parseInt(in.readLine());
            System.out.println("\nYour name: " + name + "\t\tConnection mode: " + mode + "\t\tKey: " + key);
            
            String message, response;
            
            do
            {
                System.out.print("Enter message (BYE to exit): ");
                
                //encrypt outgoing message
                message = cipher.encrypt(key, keyboard.readLine());
                out.println(message);
                
                /*//decrypt incoming message
                response = cipher.decrypt(key, in.readLine());
                //need to echo from -usr-
                System.out.println(response);*/
            }
            while(!message.equals("BYE"));
        }
        catch(UnknownHostException e)
        {
            System.out.println("Unknown host");
            System.exit(1);
        }
        catch(IOException e)
        {
            System.out.println("Cannot connect to host");
            System.exit(1);
        }
        finally
        {
            try
            {
                if (link != null)
                {
                    System.out.println("Closing down connection ...");
                    link.close();
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}

class InputReader extends Thread
{
    private BufferedReader in;
    private CeasarCipher cipher;
    private int key;
    
    public InputReader (Socket link)
    {
        this.key = key;
        cipher = new CeasarCipher();
        in = new BufferedReader(new InputStreamReader(link.getInputStream()));
    }
    
    
    public void run()
    {
        //decrypt incoming message
        String response = cipher.decrypt(key, in.readLine());
        //need to echo from -usr-
        System.out.println(response);
    }
    
    
    public BufferedReader getIn()
    {
        return in;
    }
    
}
