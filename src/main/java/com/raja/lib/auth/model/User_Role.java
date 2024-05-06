package com.raja.lib.auth.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
@Table(name = "auth_user_roles")
public class User_Role implements Serializable {

	  /*** 
	  */
	  private static final long serialVersionUID = 1L;
	  
	  @Id
	  private int userIdF;
		
	  @Id
	  private int roleId;
	  
}
