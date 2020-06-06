package it.polimi.ingsw.client;

import it.polimi.ingsw.client.GUI.ViewGUIController;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;

public class Client {
    private static String ip;
    private static int portNumber;
    private static View viewClient;

    public Client() {
        //ip = InetAddress.getByName("localhost");
        //Client.portNumber =portNumber;
    }

    public static void  main(String[] args) {
        HashMap<String,String> hashMap = new HashMap<>();

        final int N_ARGUMENT = 6;
        int i=0;
        String arg;

        while (i < args.length && args[i].startsWith("-")) {
            arg = args[i++];

            if (i < args.length) {
                hashMap.put(arg.substring(1), args[i++]);
            } else {
                System.err.println("-" + arg + " requires an argument");
            }
        }

        if (N_ARGUMENT != args.length)
            System.err.println("Usage: ParseCmdLine [-ip] address [-port] port [-view] view");
        else
            System.out.println("Success!");
        parseClient(hashMap);

        //inizializzazione fatta senza main
        /*try {
            ip = InetAddress.getByName("localhost");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        portNumber = 1234;*/

        // establish the connection with server port portnumber
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(ip, portNumber);
        } catch (IOException e) {
            System.out.println("connection refused");
        }
        if(clientSocket != null) {
            // obtaining input and out streams
            ObjectOutputStream outputStream = null;
            try {
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ObjectInputStream inputStream = null;
            try {
                inputStream = new ObjectInputStream(clientSocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            ClientHandler clientHandler = new ClientHandler(inputStream, outputStream, clientSocket, viewClient);
            clientHandler.listening();
        }
    }

    private static void parseClient(HashMap<String, String> hashMap) {
        boolean redo = false;

        String ipAddress = hashMap.get("ip");
        if(ipAddress != null){
            //controlla validità indirizzo
            ip = ipAddress;
        }else{
            redo = true;
        }

        String port = hashMap.get("port");
        if(port != null ){
            int socketPort = Integer.parseInt(port);
            //controlla validità porta
            portNumber = socketPort;
        }else{
            redo = true;
        }
        String view = hashMap.get("view");
        if(view != null ){
            switch (view){
                case "gui":
                    viewClient = new ViewGUIController();
                    break;
                case "cli":
                    viewClient = new CLIView();
                    break;
                default:
                    redo = true;
            }
        }else{
            redo = true;
        }

        if(redo){
            System.err.println("Usage: ParseCmdLine [-ip] address [-port] port [-view] view");
        }else
            System.out.println("Success!");
    }

}/*
class ParseCmdLine {
    public static void main(String[] args) {


             use this type of check for arguments that require arguments
            if (arg.equals("-ip")) {
                if (i < args.length) {
                    ip = args[i++];
                    j++;
                }else
                    System.err.println("-output requires a ip address");
            }else if (arg.equals("-port")) {
                if (i < args.length) {
                    //controllo lunghezza porta
                    j++;
                    port = Integer.parseInt(args[i++]);
                }else
                    System.err.println("-output requires a integer");
            }else if (arg.equals("-view")) {
                if (i < args.length) {
                    view = args[i++];
                    j++;
                }else
                    System.err.println("-output requires -cli or -gui");
            }else{
                vflag = true;
            }
        }
}*/
