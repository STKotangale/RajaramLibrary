package com.raja.lib.auth.model;

import jakarta.persistence.*;

@Entity
@Table(name = "auth_roles")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name="roleId")
  private int id;

  @Enumerated(EnumType.STRING)
  @Column(name="roleName")
  private ERole name;
  
  @Column(name = "isBlock", columnDefinition = "char(1) default 'N'")
  private char isBlock;

  public Role() {

  }

  public Role(ERole name) {
    this.name = name;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public ERole getName() {
    return name;
  }

  public void setName(ERole name) {
    this.name = name;
  }

public char getIsBlock() {
	return isBlock;
}

public void setIsBlock(char isBlock) {
	this.isBlock = isBlock;
}
  
  
}