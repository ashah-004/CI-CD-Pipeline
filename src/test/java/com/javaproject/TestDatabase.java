package com.javaproject;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import com.javaproject.beans.BoardGame;
import com.javaproject.database.DatabaseAccess;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {DatabaseAccess.class})
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
