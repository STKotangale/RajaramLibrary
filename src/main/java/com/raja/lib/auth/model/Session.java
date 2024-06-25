package com.raja.lib.auth.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "auth_session")
@Data
public class Session {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sessionId")
    private int sessionId;
    
    @Column(name = "sessionName")
    private String sessionName;
    
    @Column(name = "sessionFromDt")
    private String sessionFromDt;
    
    @Column(name = "sessionToDt")
    private String sessionToDt;
    
    @Column(name = "isBlock")
    private char isBlock;

}
