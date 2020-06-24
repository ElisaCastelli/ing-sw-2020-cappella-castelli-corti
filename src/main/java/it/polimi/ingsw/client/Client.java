package it.polimi.ingsw.client;

import it.polimi.ingsw.client.GUI.ViewGUIController;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

/**
 * This class is used to create the connection of a client with the server
 */
public class Client {
    /**
     * String attribute that contains the client ip number
     */
    private static String ip;
    /**
     * Integer attribute that contains the server port number the users wants to connect to
     */
    private static int portNumber;
    /**
     * View  attribute that contains an istance of the type of view (CliView or GUIControllerView) that the user choose
     */
    private static View viewClient;

    /**
     * This is the launch method for the client
     *
     * @param args is an Array of strings that contains arguments specified by the player when the client is launched
     */
    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();

        final int N_ARGUMENT = 6;
        int i = 0;
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

        Socket clientSocket = null;
        try {
            clientSocket = new Socket(ip, portNumber);
        } catch (IOException e) {
            System.out.println("connection refused");
        }
        if (clientSocket != null) {
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

    /**
     * This method function is to parse the input args to instantiate the client ip, the server port and the view type the users choose
     *
     * @param hashMap that contains the args allowed
     */
    private static void parseClient(HashMap<String, String> hashMap) {
        boolean redo = false;

        String ipAddress = hashMap.get("ip");
        if (ipAddress != null) {
            //controlla validità indirizzo
            ip = ipAddress;
        } else {
            redo = true;
        }

        String port = hashMap.get("port");
        if (port != null) {
            int socketPort = Integer.parseInt(port);
            //controlla validità porta
            portNumber = socketPort;
        } else {
            redo = true;
        }
        String view = hashMap.get("view");
        if (view != null) {
            switch (view) {
                case "gui":
                    viewClient = new ViewGUIController();
                    break;
                case "cli":
                    viewClient = new CLIView();
                    break;
                default:
                    redo = true;
            }
        } else {
            redo = true;
        }

        if (redo) {
            System.err.println("Usage: ParseCmdLine [-ip] address [-port] port [-view] view");
        } else
            System.out.println("Success!");
    }

}