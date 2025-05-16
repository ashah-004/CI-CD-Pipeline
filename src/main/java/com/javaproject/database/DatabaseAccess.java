package com.javaproject.database;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.javaproject.beans.BoardGame;
import com.javaproject.beans.Review;

@Repository
public class DatabaseAccess {

    @Autowired
    private NamedParameterJdbcTemplate jdbc;

    public List<String> getAuthorities() {
        String query = "SELECT DISTINCT authority FROM authorities";
        return jdbc.queryForList(query, new MapSqlParameterSource(), String.class);
    }

    public List<BoardGame> getBoardGames() {
        String query = "SELECT * FROM boardgames";
        return jdbc.query(query, new BeanPropertyRowMapper<>(BoardGame.class));
    }

    public BoardGame getBoardGame(Long id) {
        String query = "SELECT * FROM boardgames WHERE id = :id";
        List<BoardGame> games = jdbc.query(query,
                new MapSqlParameterSource("id", id),
                new BeanPropertyRowMapper<>(BoardGame.class));
        return games.isEmpty() ? null : games.get(0);
    }

    public List<Review> getReviews(Long id) {
        String query = "SELECT * FROM reviews WHERE gameId = :id";
        List<Review> reviews = jdbc.query(query,
                new MapSqlParameterSource("id", id),
                new BeanPropertyRowMapper<>(Review.class));
        return reviews.isEmpty() ? null : reviews;
    }

    public Long addBoardGame(BoardGame boardgame) {
        String query = "INSERT INTO boardgames (name, level, minPlayers, maxPlayers, gameType) " +
                "VALUES (:name, :level, :minPlayers, :maxPlayers, :gameType)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("name", boardgame.getName())
                .addValue("level", boardgame.getLevel())
                .addValue("minPlayers", boardgame.getMinPlayers())
                .addValue("maxPlayers", boardgame.getMaxPlayers())
                .addValue("gameType", boardgame.getGameType());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbc.update(query, params, keyHolder);
        return (keyHolder.getKey() != null) ? keyHolder.getKey().longValue() : 0;
    }

    public int addReview(Review review) {
        String query = "INSERT INTO reviews (gameId, text) VALUES (:gameId, :text)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("gameId", review.getGameId())
                .addValue("text", review.getText());
        return jdbc.update(query, params);
    }

    public int deleteReview(Long id) {
        String query = "DELETE FROM reviews WHERE id = :id";
        return jdbc.update(query, new MapSqlParameterSource("id", id));
    }

    public Review getReview(Long id) {
        String query = "SELECT * FROM reviews WHERE id = :id";
        List<Review> reviews = jdbc.query(query,
                new MapSqlParameterSource("id", id),
                new BeanPropertyRowMapper<>(Review.class));
        return reviews.isEmpty() ? null : reviews.get(0);
    }

    public int editReview(Review review) {
        String query = "UPDATE reviews SET text = :text WHERE id = :id";
        return jdbc.update(query,
                new MapSqlParameterSource()
                        .addValue("text", review.getText())
                        .addValue("id", review.getId()));
    }
}
