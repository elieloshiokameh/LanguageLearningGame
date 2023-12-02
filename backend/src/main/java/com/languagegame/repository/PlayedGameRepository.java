package com.languagegame.repository;

import com.languagegame.domain.PlayedGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayedGameRepository extends JpaRepository<PlayedGame, Long> {

}
