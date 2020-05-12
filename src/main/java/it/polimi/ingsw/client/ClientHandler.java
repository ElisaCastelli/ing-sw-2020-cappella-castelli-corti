package it.polimi.ingsw.client;

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
    private final View view;

    public ClientHandler(ObjectInputStream inputStream, ObjectOutputStream outputStream, Socket socket, View view) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.socket = socket;
        this.view = view;
    }

    public View getView() {
        return view;
    }

    public void listening() {
        while(true){
            ObjMessage objMessage = null;
            try {
                objMessage = (ObjMessage)inputStream.readObject();
                objMessage.accept(new VisitorClient(this));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(ObjMessage objMessage){
        try {
            outputStream.writeObject(objMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
