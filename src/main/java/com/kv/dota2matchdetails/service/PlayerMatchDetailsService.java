package com.kv.dota2matchdetails.service;

import com.kv.dota2matchdetails.model.PlayerMatchDetails;
import com.kv.matchdetails.dto.MatchDetailsDto;
import com.kv.matchdetails.dto.MatchesDto;
import com.kv.player.dto.PlayerDto;
import com.kv.service.CommonUtilityService;
import com.kv.service.SteamWebApiQueryService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Optional;

@Service
@Log4j2
public class PlayerMatchDetailsService {
    @Autowired private SteamWebApiQueryService steamWebApiQueryService;

    public PlayerMatchDetails getLastPlayedMatchDetails(String dota2AccountId) {
        Optional<MatchesDto> lastPlayedMatchOpt = getLastPlayedMatchFromSteamApi(dota2AccountId);

        if (lastPlayedMatchOpt.isEmpty()) {
            throw new RuntimeException("Player's Last Played DOTA2 Match NOT FOUND");
        }

        long dota2AccountIdLong = Long.parseLong(dota2AccountId);
        MatchDetailsDto lastPlayedMatchDetails = steamWebApiQueryService.getMatchDetails(String.valueOf(lastPlayedMatchOpt.get().getMatch_id()));

        PlayerDto playerDto = lastPlayedMatchDetails.getPlayers()
                .stream()
                .filter(player -> dota2AccountIdLong == player.getAccount_id())
                .findFirst()
                .orElse(new PlayerDto());

        return prepareAndGetUIModel(playerDto, lastPlayedMatchDetails);
    }

    private Optional<MatchesDto> getLastPlayedMatchFromSteamApi(String dota2AccountId) {
        try {
            return steamWebApiQueryService.getMatchesForDota2AccountId(dota2AccountId, 0)
                    .stream()
                    .max(Comparator.comparing(MatchesDto::getStart_time));
        } catch (Exception e) {
            log.error("Error in MatchDetailsService::getLastPlayedMatchFromSteamApi for dota2AccountId: {} ----> ErrorMessage: {}, Error StackTrace: {}",
                    dota2AccountId, e.getMessage(), e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    private PlayerMatchDetails prepareAndGetUIModel(PlayerDto playerDtoFromSteamAPI, MatchDetailsDto lastPlayedMatchDetails) {
        PlayerMatchDetails targetResponseModel = new PlayerMatchDetails();

        BeanUtils.copyProperties(playerDtoFromSteamAPI, targetResponseModel);

        targetResponseModel.setMatchId(lastPlayedMatchDetails.getMatch_id());
        targetResponseModel.setMatchDateTime(
                CommonUtilityService.convertToZonedUtc(lastPlayedMatchDetails.getStart_time())
        );
        targetResponseModel.setPlayerWin(
                CommonUtilityService.hasPlayerWonTheMatch(lastPlayedMatchDetails.isRadiant_win(), playerDtoFromSteamAPI.getPlayer_slot())
        );

        return targetResponseModel;
    }
}