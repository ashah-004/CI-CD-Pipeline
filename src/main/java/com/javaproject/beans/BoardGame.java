package com.javaproject.beans;

import java.util.List;
import lombok.Data;

@Data
public class BoardGame {

    private Long id;
    private String name;
    private int level;
    private int minPlayers;
    private int maxPlayers;
    private String gameType;

    // This should not be mapped from DB directly
    private List<Review> reviews;
}
