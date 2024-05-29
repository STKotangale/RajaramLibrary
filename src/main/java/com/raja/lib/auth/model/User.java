package com.raja.lib.auth.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
@Entity
@Table(name = "auth_users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private int userId;

    @NotBlank
    @Size(max = 100)
    @Column(name = "username")
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    @Column(name = "useremail")
    private String useremail;

    @NotBlank
    @Size(max = 100)
    @Column(name = "userpassword")
    private String userpassword;

    @Column(name = "isBlock", columnDefinition = "char(1) default 'N'")
    private char isBlock;

    
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "memberidF", referencedColumnName = "memberId")
	private GeneralMember generalMember;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "auth_user_roles", joinColumns = @JoinColumn(name = "userIdF"), inverseJoinColumns = @JoinColumn(name = "roleIdF"))
	private Set<Role> roles = new HashSet<>();
	
	
	public User(String username, String useremail, String userpassword, char isBlock) {
	    this.username = username;
	    this.useremail = useremail;
	    this.userpassword = userpassword;
	    this.isBlock = isBlock;
	}
}
