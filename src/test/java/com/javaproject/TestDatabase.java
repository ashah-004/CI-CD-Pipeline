package com.javaproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.javaproject.beans.BoardGame;
import com.javaproject.beans.Review;
import com.javaproject.database.DatabaseAccess;

@DataJpaTest
class TestDatabase {

    @Autowired
    private DatabaseAccess da;

    @Test
    public void testDatabaseAddBoardGame() {
        BoardGame boardGame = new BoardGame();
        boardGame.setName("onecard");
        boardGame.setLevel(1);
        boardGame.setMinPlayers(2);
        boardGame.setMaxPlayers("+");
        boardGame.setGameType("Party Game");

        int originalSize = da.getBoardGames().size();
        da.addBoardGame(boardGame);
        int newSize = da.getBoardGames().size();

        assertEquals(originalSize + 1, newSize);
    }
}
