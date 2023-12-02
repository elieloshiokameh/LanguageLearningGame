package com.languagegame.domain;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="played_games")
public class PlayedGame {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime playedAt;
    private int correct;
    private int questions;
    private int timeRemaining;

    public PlayedGame() {

    }

    public PlayedGame(User user, LocalDateTime playedAt, int correct, int questions, int timeRemaining) {
        this.user = user;
        this.playedAt = playedAt;
        this.correct = correct;
        this.questions = questions;
        this.timeRemaining = timeRemaining;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getPlayedAt() {
        return playedAt;
    }

    public void setPlayedAt(LocalDateTime playedAt) {
        this.playedAt = playedAt;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getQuestions() {
        return questions;
    }

    public void setQuestions(int questions) {
        this.questions = questions;
    }

    public int getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(int timeTaken) {
        this.timeRemaining = timeTaken;
    }
}
