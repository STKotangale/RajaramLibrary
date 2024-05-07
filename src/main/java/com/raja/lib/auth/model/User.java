package com.raja.lib.auth.model;

import java.util.HashSet;
import java.util.Set;

import com.raja.lib.invt.model.GeneralMember;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "auth_users", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "useremail") })
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userId")
	private int id;

	@NotBlank
	@Size(max = 20)
	@Column(name = "username")
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	@Column(name = "useremail")
	private String email;

	@NotBlank
	@Size(max = 120)
	@Column(name = "userpassword")
	private String password;

	@Column(name = "isBlock", columnDefinition = "char(1) default 'N'")
	private char isBlock;

	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "memberidF", referencedColumnName = "memberId")
	private GeneralMember generalMember;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "auth_user_roles", joinColumns = @JoinColumn(name = "userIdF"), inverseJoinColumns = @JoinColumn(name = "roleIdF"))
	private Set<Role> roles = new HashSet<>();

	public User() {
	}

	public User(String username, String email, String password, char isBlock, Set<Role> roles) {
		this.username = username;
		this.email = email;
		this.password = password;
		this.isBlock = isBlock;
		this.roles = roles;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public char isBlock() {
		return isBlock;
	}

	public void setBlock(char isBlock) {
		this.isBlock = isBlock;
	}

	public GeneralMember getGeneralMember() {
		return generalMember;
	}

	public void setGeneralMember(GeneralMember generalMember) {
		this.generalMember = generalMember;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
}
