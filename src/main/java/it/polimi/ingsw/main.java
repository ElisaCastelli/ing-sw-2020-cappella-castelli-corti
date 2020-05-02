package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.server.EchoServer;

import java.net.UnknownHostException;

public class main {

    public static void main(String[] args) throws UnknownHostException {

        EchoServer echoServer=new EchoServer(1234);
        Client client= new Client(1234);

    }
}
