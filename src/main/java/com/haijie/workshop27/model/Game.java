package com.haijie.workshop27.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.bson.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private int gid;
    private String name;
    private int year;
    private int ranking;
    private int usersRated;
    private String url;
    private String image;

    //conver document to game object
    public static Game create(Document doc){
        Game g = new Game();
        g.setGid(doc.getInteger("gid"));
        g.setName(doc.getString("name"));
        g.setYear(doc.getInteger("year"));
        g.setRanking(doc.getInteger("ranking"));
        g.setUsersRated(doc.getInteger("users_rated"));
        g.setUrl(doc.getString("url"));
        g.setImage(doc.getString("image"));

        return g;
    }
}
