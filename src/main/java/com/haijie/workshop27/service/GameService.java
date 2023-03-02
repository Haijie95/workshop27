package com.haijie.workshop27.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.haijie.workshop27.model.Game;
import com.haijie.workshop27.repository.GameRepo;

@Service
public class GameService {
    
    @Autowired
    GameRepo grepo;

    public List<Game> getGameById(int gid) {

        //create a list of game that goes by the game id
        return grepo.getGameById(gid).stream()
        .map(g->Game.create(g))
        .toList();
    }

}
