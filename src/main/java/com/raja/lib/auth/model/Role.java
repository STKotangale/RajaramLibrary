package com.raja.lib.auth.model;

import java.io.Serializable;

import org.springframework.stereotype.Component;
import jakarta.persistence.*;
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
@Table(name = "auth_roles")
public class Role implements Serializable {

  /*** 
  */
  private static final long serialVersionUID = 1L;
 
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int roleId;

  @NotBlank
  @Size(max = 100)
  private String roleName;

  @Column(name = "isBlock", columnDefinition = "char(1) default 'N'")
  private char isBlock;

}