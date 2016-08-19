package com.magnolia.tutorial.vaadin.bean;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "team")
public class Team implements BrowsableBean {
    /** Serial id */
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;
    /** Name */
    private String name;
    /** Logo */
    private byte[] logo;
    /** Creation date */
    private Date creationDate;
    /** Update date */
    private Date updateDate;
    /** Creation user */
    private String creationUser;
    /** Update user */
    private String updateUser;

    /** List of players */
    private Collection<Player> players;

    /**
     * @return the id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
	return this.id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
	this.id = id;
    }

    /**
     * @return the name
     */
    @Column(name = "name")
    public String getName() {
	return this.name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

    /**
     * @return the logo
     */
    @Column(name = "logo")
    public byte[] getLogo() {
	return this.logo;
    }

    /**
     * @param logo
     *            the logo to set
     */
    public void setLogo(byte[] logo) {
	this.logo = logo;
    }

    /**
     * @return the creationDate
     */
    @Column(name = "creation_date")
    public Date getCreationDate() {
	return this.creationDate;
    }

    /**
     * @param creationDate
     *            the creationDate to set
     */
    public void setCreationDate(Date creationDate) {
	this.creationDate = creationDate;
    }

    /**
     * @return the updateDate
     */
    @Column(name = "update_date")
    public Date getUpdateDate() {
	return this.updateDate;
    }

    /**
     * @param updateDate
     *            the updateDate to set
     */
    public void setUpdateDate(Date updateDate) {
	this.updateDate = updateDate;
    }

    /**
     * @return the creationUser
     */
    @Column(name = "creation_user")
    public String getCreationUser() {
	return this.creationUser;
    }

    /**
     * @param creationUser
     *            the creationUser to set
     */
    public void setCreationUser(String creationUser) {
	this.creationUser = creationUser;
    }

    /**
     * @return the updateUser
     */
    @Column(name = "update_user")
    public String getUpdateUser() {
	return this.updateUser;
    }

    /**
     * @param updateUser
     *            the updateUser to set
     */
    public void setUpdateUser(String updateUser) {
	this.updateUser = updateUser;
    }

    /**
     * @return the players
     */
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    public Collection<Player> getPlayers() {
	return this.players;
    }

    /**
     * @param players
     *            the players to set
     */
    public void setPlayers(Collection<Player> players) {
	this.players = players;
    }

    @Override
    @Transient
    public String getNodeName() {
	return this.name;
    }

    @Override
    @Transient
    public long[] getIdChain() {
	long[] ids = new long[1];
	ids[0] = this.id != null ? this.id : -1;
	return ids;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transient
    public <T extends BrowsableBean> Collection<T> getChildren() {
	return (Collection<T>) this.players;
    }

    @Override
    public boolean areChildrenAllowed() {
	return true;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = (prime * result) + ((this.id == null) ? 0 : this.id.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (this.getClass() != obj.getClass()) {
	    return false;
	}
	Team other = (Team) obj;
	if (this.id == null) {
	    if (other.id != null) {
		return false;
	    }
	} else if (!this.id.equals(other.id)) {
	    return false;
	}
	return true;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	builder.append("Team [id=");
	builder.append(this.id);
	builder.append(", name=");
	builder.append(this.name);
	builder.append(", creationDate=");
	builder.append(this.creationDate);
	builder.append(", updateDate=");
	builder.append(this.updateDate);
	builder.append(", creationUser=");
	builder.append(this.creationUser);
	builder.append(", updateUser=");
	builder.append(this.updateUser);
	builder.append("]");
	return builder.toString();
    }
}