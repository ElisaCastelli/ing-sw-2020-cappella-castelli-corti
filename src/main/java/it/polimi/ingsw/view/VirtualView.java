package it.polimi.ingsw.view;

import it.polimi.ingsw.model.ProxyGameModel;

public class VirtualView implements Observer {
    private ProxyGameModel gameModel;
    //Controller?

    public VirtualView(ProxyGameModel gameModel){
        this.gameModel=gameModel;
    }
    @Override
    public void subscribe() {
        gameModel.subscribeObserver(this);
    }

    @Override
    public void updateObserver() {

    }
}
