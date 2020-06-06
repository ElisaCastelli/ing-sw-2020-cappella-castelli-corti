package it.polimi.ingsw.client;

import it.polimi.ingsw.network.SendMessageToServer;
import it.polimi.ingsw.network.events.CloseConnectionFromServerEvent;
import it.polimi.ingsw.network.objects.ObjMessage;
import it.polimi.ingsw.network.VisitorClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler {
    private final ObjectInputStream inputStream;
    private final ObjectOutputStream outputStream;
    private final Socket socket;
    private View view;
    private final Object LOCK=new Object();
    private boolean closed= false;

    public ClientHandler(ObjectInputStream inputStream, ObjectOutputStream outputStream, Socket socket, View view) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.socket = socket;
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void listening () {
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
                    objMessage.accept(new VisitorClient(view));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        close();
    }

    public void sendMessage(ObjMessage objMessage){
        if(!closed) {
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

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
