package com.magnolia.tutorial.vaadin.dao;

import java.util.Collection;

import com.magnolia.tutorial.vaadin.bean.Player;
import com.magnolia.tutorial.vaadin.bean.Position;
import com.magnolia.tutorial.vaadin.bean.Team;

public interface FootballTeamDao {
    /**
     * Return the list of all teams
     *
     * @return The list of all teams
     */
    Collection<Team> getAllTeams();

    /**
     * Return the list of players linked to the team
     *
     * @param idTeam
     *            The id team
     * @return The list of players linked to the team
     */
    Collection<Player> getTeamPlayers(Long idTeam);

    /**
     * Return the list of position linked of the player
     *
     * @param idPlayer
     *            The player id
     * @return The list of position linked of the player
     */
    Collection<Position> getPlayerPositions(Long idPlayer);

    /**
     * Returns the list of team/players according to the given filter
     *
     * @param filter
     *            The search filter
     * @return The list of team/players according to the given filter
     */
    Collection<Object[]> getListElements(String filter);
}