package com.magnolia.tutorial.vaadin.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.magnolia.tutorial.vaadin.bean.BrowsableBean;
import com.magnolia.tutorial.vaadin.bean.Team;
import com.magnolia.tutorial.vaadin.dao.FootballTeamDao;
import com.magnolia.tutorial.vaadin.util.CastUtil;

@Repository("TeamService")
public class TeamServiceImpl implements TeamService {

    /** The Football team DAO */
    @Autowired
    private FootballTeamDao dao;

    @Override
    public Collection<Team> getAll() {
	return this.dao.getAllTeams();
    }

    @Override
    public void delete(long[] idChain) {
	// TODO Auto-generated method stub

    }

    @Override
    public void deleteAll() {
	// TODO Auto-generated method stub

    }

    @Override
    public Collection<Object[]> getListElements(String searchFilter) {
	return this.dao.getListElements(searchFilter);
    }

    @Override
    public Collection<Object[]> getListElements() {
	return this.dao.getListElements(null);
    }

    @Override
    public void move(long[] playerIdChain, long[] teamIdChain) {
	// TODO Auto-generated method stub
    }

    @Override
    public Collection<BrowsableBean> getRootElements() {
	return CastUtil.castList(this.getAll());
    }

    @Override
    public Collection<BrowsableBean> getChildrenElements(long[] idChain) {
	if (idChain.length == 1) {
	    return CastUtil.castList(this.dao.getTeamPlayers(idChain[0]));
	} else if (idChain.length == 1) {
	    return CastUtil.castList(this.dao.getPlayerPositions(idChain[1]));
	} else {
	    return null;
	}
    }
}