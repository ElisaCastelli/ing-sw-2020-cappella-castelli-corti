package it.polimi.ingsw.client;

import it.polimi.ingsw.network.SendMessageToServer;
import it.polimi.ingsw.network.events.CloseConnectionFromServerEvent;
import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.network.VisitorMessageFromServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class is used to managed the client connection to the serve
 */
public class ConnectionHandlerClientSide {
    /**
     * Stream Object used to receive Message
     */
    private final ObjectInputStream inputStream;
    /**
     * Stream Object used to send Message
     */
    private final ObjectOutputStream outputStream;
    /**
     * Socket Object used to create a connection with the server
     */
    private final Socket socket;
    /**
     * View Object that contains the view instance the user has choose
     */
    private View view;
    /**
     * Object use to guarantee the mutual exclusion to access a piece of code
     */
    private final Object LOCK = new Object();
    /**
     * Boolean attribute used to control if the connection is close
     */
    private boolean closed = false;

    /**
     * Constructor with parameters that characterize a client instance
     *
     * @param inputStream  input stream of the socket
     * @param outputStream output stream of the socket
     * @param socket       client socket
     * @param view         view client's side
     */
    public ConnectionHandlerClientSide(ObjectInputStream inputStream, ObjectOutputStream outputStream, Socket socket, View view) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.socket = socket;
        this.view = view;
    }

    /**
     * Getter method for the View
     *
     * @return view client's side
     */
    public View getView() {
        return view;
    }

    /**
     * The first time the method is launched it links a SendMessageToServer object to the view.
     * Then there's a loop ending only when it received a CloseConnectionFromServerEvent object and this is used for listening
     * to the input stream and for reading objects once received. If the message received isn't null
     * it will be accepted and read from the VisitorClient
     */
    public void listening() {
        SendMessageToServer sendMessageToServer = new SendMessageToServer(this);
        view.setSendMessageToServer(sendMessageToServer);
        try {
            while (!closed) {
                ObjMessage objMessage = null;
                try {
                    objMessage = (ObjMessage) inputStream.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    closed = true;
                }
                if (objMessage instanceof CloseConnectionFromServerEvent)
                    closed = true;
                if (objMessage != null)
                    objMessage.accept(new VisitorMessageFromServer(view));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        close();
    }

    /**
     * This method is used to send message to the server using the output stream object
     * until the closed condition is true. Everytime it sends a message it clears the output stream
     *
     * @param objMessage is the object we want to send
     */
    public void sendMessage(ObjMessage objMessage) {
        if (!closed) {
            synchronized (LOCK) {
                try {
                    outputStream.writeObject(objMessage);
                    outputStream.reset();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * This method is used to close the client connection with the server established with the socket objectw
     */
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
