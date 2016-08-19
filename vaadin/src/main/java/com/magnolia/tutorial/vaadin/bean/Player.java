package com.magnolia.tutorial.vaadin.bean;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "player")
public class Player implements BrowsableBean {
    /** Serial id */
    private static final long serialVersionUID = 1L;

    /** Id */
    private Long id;
    /** Full name */
    private String fullName;
    /** First name */
    private String firstName;
    /** Last name */
    private String lastName;
    /** Birth date */
    private Date birthDate;
    /** Nationality */
    private String nationality;
    /** Picture */
    private byte[] picture;
    /** Creation date */
    private Date creationDate;
    /** Update date */
    private Date updateDate;
    /** Creation user */
    private String creationUser;
    /** Update user */
    private String updateUser;

    /** Related position */
    private Collection<Position> positions;
    /** Related team */
    private Team team;

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
     * @return the fullName
     */
    @Column(name = "full_name")
    public String getFullName() {
	return this.fullName;
    }

    /**
     * @param fullName
     *            the fullName to set
     */
    public void setFullName(String fullName) {
	this.fullName = fullName;
    }

    /**
     * @return the firstName
     */
    @Column(name = "first_name")
    public String getFirstName() {
	return this.firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
     */
    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    @Column(name = "last_name")
    public String getLastName() {
	return this.lastName;
    }

    /**
     * @param lastName
     *            the lastName to set
     */
    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    /**
     * @return the birthDate
     */
    @Column(name = "birth_date")
    public Date getBirthDate() {
	return this.birthDate;
    }

    /**
     * @param birthDate
     *            the birthDate to set
     */
    public void setBirthDate(Date birthDate) {
	this.birthDate = birthDate;
    }

    /**
     * @return the nationality
     */
    @Column(name = "nationality")
    public String getNationality() {
	return this.nationality;
    }

    /**
     * @param nationality
     *            the nationality to set
     */
    public void setNationality(String nationality) {
	this.nationality = nationality;
    }

    /**
     * @return the picture
     */
    @Column(name = "picture")
    public byte[] getPicture() {
	return this.picture;
    }

    /**
     * @param picture
     *            the picture to set
     */
    public void setPicture(byte[] picture) {
	this.picture = picture;
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
     * @return the positions
     */
    @ManyToMany
    @JoinTable(name = "player_position", joinColumns = @JoinColumn(name = "id_player", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "id_position", referencedColumnName = "id"))
    public Collection<Position> getPositions() {
	return this.positions;
    }

    /**
     * @param positions
     *            the positions to set
     */
    public void setPositions(Collection<Position> positions) {
	this.positions = positions;
    }

    /**
     * @return the team
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_team")
    public Team getTeam() {
	return this.team;
    }

    /**
     * @param team
     *            the team to set
     */
    public void setTeam(Team team) {
	this.team = team;
    }

    @Override
    @Transient
    public long[] getIdChain() {
	long[] ids = new long[2];
	ids[0] = ((this.team != null) && (this.team.getId() != null)) ? this.team.getId() : -1;
	ids[1] = this.id != null ? this.id : -1;
	return ids;
    }

    @Override
    @Transient
    public String getNodeName() {
	return this.fullName;
    }

    @Override
    @Transient
    public <T extends BrowsableBean> Collection<T> getChildren() {
	return null;
    }

    @Override
    public boolean areChildrenAllowed() {
	return false;
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
	Player other = (Player) obj;
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
	builder.append("Player [id=");
	builder.append(this.id);
	builder.append(", fullName=");
	builder.append(this.fullName);
	builder.append(", firstName=");
	builder.append(this.firstName);
	builder.append(", lastName=");
	builder.append(this.lastName);
	builder.append(", birthDate=");
	builder.append(this.birthDate);
	builder.append(", nationality=");
	builder.append(this.nationality);
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