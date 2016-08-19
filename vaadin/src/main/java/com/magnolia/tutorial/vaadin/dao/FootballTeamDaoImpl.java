
package com.magnolia.tutorial.vaadin.dao;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.magnolia.tutorial.vaadin.bean.Player;
import com.magnolia.tutorial.vaadin.bean.Position;
import com.magnolia.tutorial.vaadin.bean.Team;

@Repository("FootballTeamDao")
public class FootballTeamDaoImpl implements FootballTeamDao {
    /** The football team persistence unit */
    public static final String PERSISTENCE_UNIT_NAME = "football_team";

    /** The entity manager */
    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Team> getAllTeams() {
	Query query = this.em.createQuery("SELECT t FROM Team t");
	return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Player> getTeamPlayers(Long idTeam) {
	Query query = this.em.createQuery("SELECT p FROM Player p WHERE p.team.id=:idTeam");
	query.setParameter("idTeam", idTeam);
	return query.getResultList();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Position> getPlayerPositions(Long idPlayer) {
	Query query = this.em.createQuery("SELECT p FROM Position p");
	return query.getResultList();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Object[]> getListElements(String filter) {
	StringBuilder builder = new StringBuilder();
	builder.append(
		"SELECT concat(t.id, '_', p.id) as id, t.name as teamName, p.fullName, p.birthDate, p.nationality FROM Team t INNER JOIN t.players p");
	if (filter != null) {
	    builder.append(" WHERE t.name LIKE :filter OR p.fullName LIKE :filter");
	}

	Query query = this.em.createQuery(builder.toString());
	if (filter != null) {
	    query.setParameter("filter", "%" + filter + "%");
	}

	return query.getResultList();
    }
}