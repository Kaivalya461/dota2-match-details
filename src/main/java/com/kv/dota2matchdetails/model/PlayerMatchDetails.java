package com.kv.dota2matchdetails.model;

import lombok.Data;

import java.time.ZonedDateTime;

@Data
public class PlayerMatchDetails {
    private long matchId;
    private ZonedDateTime matchDateTime;
    private boolean playerWin;
    private int kills;
    private int deaths;
    private int assists;
}
