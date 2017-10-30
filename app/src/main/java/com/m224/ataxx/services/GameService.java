package com.m224.ataxx.services;

import android.content.Context;

import com.m224.ataxx.domaine.Tile;
import com.m224.ataxx.utils.IGlobalVariable;
import com.m224.ataxx.utils.Util;

import java.util.List;

/**
 * Created by 224 on 2017-10-23.
 */

public class GameService {

    private Tile[] tiles;
    private Tile selectTile = null;

    private boolean turnPlayerOne;

    private int scorePlayer1;
    private int scorePlayer2;

    public GameService(Context context) {
        this.tiles = new Tile[IGlobalVariable.MAX_TILE];

        for (int i = 0; i < tiles.length; i++)
            tiles[i] = new Tile(context, IGlobalVariable.STATE.EMPTY, i);

        turnPlayerOne = false;
    }

    public boolean isTurnPlayerOne() {
        return turnPlayerOne;
    }


    public Tile getTileAt(int i) {
        return tiles[i];
    }
    public int getScorePlayer1() {
        return scorePlayer1;
    }
    public int getScorePlayer2() {
        return scorePlayer2;
    }

    private void resetGrid() {
        for (Tile tile : tiles)
            tile.setState(IGlobalVariable.STATE.EMPTY);
    }

    private void countScore() {
        scorePlayer1=0; scorePlayer2=0;
        for (Tile tile : tiles) {
            if (tile.getState() == IGlobalVariable.STATE.PLAYER1)
                scorePlayer1++;
            if (tile.getState() == IGlobalVariable.STATE.PLAYER2)
                scorePlayer2++;
        }
    }

    public void setConfigOne() {
        resetGrid();
        tiles[0].setState(IGlobalVariable.STATE.PLAYER2);
        tiles[8].setState(IGlobalVariable.STATE.PLAYER1);
        tiles[72].setState(IGlobalVariable.STATE.PLAYER1);
        tiles[80].setState(IGlobalVariable.STATE.PLAYER2);
        countScore();
    }



    /*********************************/


    /**
     * Infect the 8 tiles around param int*/
    public void infectAround(int aroundId) {
        List<Integer> integerList = Util.getTileAround(aroundId);
        for (int id : integerList ) {
            if (tiles[id].isStatePlayer())
                tiles[id].setState(tiles[aroundId].getState());
        }
    }

    public void makeMovement(int toId) {
        int moveType = Util.confirmValidMove(selectTile.getId(),toId);

        if (moveType > 0) {
            selectTile.setSelected(false);
            tiles[toId].setState(selectTile.getState());
            infectAround(toId);

            if (moveType == 2) {
                selectTile.setState(IGlobalVariable.STATE.EMPTY);
            }
            selectTile = null;
            countScore();
        }
    }

    public void move(int toId){
        if (selectTile == null) {  // Cree la selection
            if(tiles[toId].isStatePlayer()) {
                selectTile = tiles[toId];
                selectTile.setSelected(true);
            }
        } else if (tiles[toId].isStatePlayer()) { // Change la selection
            selectTile.setSelected(false);
            selectTile = tiles[toId];
            selectTile.setSelected(true);
        } else if (tiles[toId].getState() == IGlobalVariable.STATE.EMPTY) {

            if (turnPlayerOne && selectTile.getState() == IGlobalVariable.STATE.PLAYER1 ||
                    !turnPlayerOne && selectTile.getState() == IGlobalVariable.STATE.PLAYER2) {
                makeMovement(toId);
                turnPlayerOne = !turnPlayerOne;
            }
        }
    }
}
























