package com.languagegame.domain;

import java.time.LocalDateTime;
import java.util.List;

public class StatisticsDTO {
    List<Integer> correct;
    List<Integer> questions;
    List<Integer> timeRemaining;
    List<String> questionLanguages;
    List<String> answerLanguages;

    List<LocalDateTime> timePlayed;

    public List<Integer> getCorrect() {
        return correct;
    }

    public void setCorrect(List<Integer> correct) {
        this.correct = correct;
    }

    public List<Integer> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Integer> questions) {
        this.questions = questions;
    }

    public List<Integer> getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(List<Integer> timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public List<LocalDateTime> getTimePlayed() {
        return timePlayed;
    }

    public void setTimePlayed(List<LocalDateTime> timePlayed) {
        this.timePlayed = timePlayed;
    }

    public List<String> getQuestionLanguages() {
        return questionLanguages;
    }

    public void setQuestionLanguages(List<String> questionLanguages) {
        this.questionLanguages = questionLanguages;
    }

    public List<String> getAnswerLanguages() {
        return answerLanguages;
    }

    public void setAnswerLanguages(List<String> answerLanguages) {
        this.answerLanguages = answerLanguages;
    }
}
