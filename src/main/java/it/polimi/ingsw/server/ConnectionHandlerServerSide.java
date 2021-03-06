package it.polimi.ingsw.server;

import it.polimi.ingsw.network.events.AskWantToPlayEvent;

import it.polimi.ingsw.network.events.CloseConnectionFromClientEvent;
import it.polimi.ingsw.network.objects.ObjHeartBeat;
import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.network.VisitorMessageFromClient;

import java.io.*;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This class handles all the clients that get connected
 */
public class ConnectionHandlerServerSide extends Thread {

    /**
     * This attribute is the index that the client occupies in the clientArray of EchoServer class
     */
    int indexClientArray;
    private final ObjectOutputStream outputStream;
    private final ObjectInputStream inputStream;
    private final VirtualView virtualView;
    private final Socket socket;
    /**
     * This attribute identifies if the client is closed or not
     */
    private boolean closed = false;

    /**
     * Constructor of the class
     *
     * @param socket           Server socket
     * @param outputStream     output stream of the socket
     * @param inputStream      input stream of the socket
     * @param virtualView      view server's side
     * @param indexClientArray index client
     */
    public ConnectionHandlerServerSide(Socket socket, ObjectOutputStream outputStream, ObjectInputStream inputStream, VirtualView virtualView, int indexClientArray) {
        this.socket = socket;
        this.outputStream = outputStream;
        this.inputStream = inputStream;
        this.virtualView = virtualView;
        this.indexClientArray = indexClientArray;
    }

    /**
     * Is closed getter
     *
     * @return true if is closed
     */

    public boolean isClosed() {
        return closed;
    }

    /**
     * Index client setter
     *
     * @param indexClientArray index client
     */

    public void setIndexClientArray(int indexClientArray) {
        this.indexClientArray = indexClientArray;
    }

    /**
     * This method sends an heartbeat and starts the timer
     */
    public void sendHeartBeat() {
        Timer t = new Timer();
        TimerTask tt = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (!closed) {
                        sendUpdate(new ObjHeartBeat());
                    } else {
                        t.cancel();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        t.scheduleAtFixedRate(tt, 50000, 60000);
    }

    /**
     * This method sends a message to the new client and waits to receives messages
     */
    @Override
    public void run() {
        sendUpdate(new AskWantToPlayEvent(indexClientArray));
        sendHeartBeat();
        listening();
    }

    /**
     * This method receives all the messages from the client
     */
    public void listening() {
        boolean beforeStart = false;
        try {
            while (!closed) {
                ObjMessage objMessage = null;

                try {
                    objMessage = (ObjMessage) inputStream.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    closed = true;

                }
                if (objMessage instanceof CloseConnectionFromClientEvent) {
                    closed = true;
                    beforeStart = objMessage.isBeforeStart();
                }
                if (objMessage != null)
                    objMessage.accept(new VisitorMessageFromClient(virtualView));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        virtualView.controlStillOpen(indexClientArray, beforeStart);
        close();
    }

    /**
     * This method sends a message to the client
     *
     * @param objMessage object message to send
     */
    public void sendUpdate(ObjMessage objMessage) {
        objMessage.setClientIndex(indexClientArray);

        try {
            outputStream.writeObject(objMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            outputStream.reset();
        } catch (IOException e) {
            System.out.println("Connection reset");
            e.printStackTrace();
        }
    }

    /**
     * This method closes the connection of the client
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
