package com.kv.dota2matchdetails.controller;

import com.kv.dota2matchdetails.model.PlayerMatchDetails;
import com.kv.dota2matchdetails.service.PlayerMatchDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player-match-details")
@CrossOrigin(value = "*")
public class PlayerMatchDetailsController {
    @Autowired private PlayerMatchDetailsService playerMatchDetailsService;

    /**
     * This Endpoint is used for displaying Players Last Played Match Details. ex: Kills, Deaths, Assists
     * */
    @GetMapping("/last-played/dota2-account-id/{dota2AccountId}")
    public ResponseEntity<PlayerMatchDetails> getLastPlayedMatchDetails(@PathVariable String dota2AccountId) {
        return ResponseEntity.ok(playerMatchDetailsService.getLastPlayedMatchDetails(dota2AccountId));
    }
}
