	package com.raja.lib.auth.model;
	
	import java.io.Serializable;
	
	import org.springframework.stereotype.Component;
	
	import jakarta.persistence.CascadeType;
	import jakarta.persistence.Column;
	import jakarta.persistence.Entity;
	import jakarta.persistence.FetchType;
	import jakarta.persistence.GeneratedValue;
	import jakarta.persistence.GenerationType;
	import jakarta.persistence.Id;
	import jakarta.persistence.OneToOne;
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
	@Table(name = "auth_general_members")
	public class GeneralMember implements Serializable {
	
	    private static final long serialVersionUID = 1L;
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    @Column(name = "memberId")
	    private int memberId;
	
	    @Column(name = "firstName")
	    private String firstName;
	
	    @Column(name = "middleName")
	    private String middleName;
	
	    @Column(name = "lastName")
	    private String lastName;
	
	    @Column(name = "registerDate")
	    private String registerDate;
	
	    @Column(name = "adharCard")
	    private String adharCard;
	
	    @Column(name = "memberAddress")
	    private String memberAddress;
	
	    @Column(name = "dateOfBirth")
	    private String dateOfBirth;
	
	    @Column(name = "memberEducation")
	    private String memberEducation;
	
	    @Column(name = "memberOccupation")
	    private String memberOccupation;
	
	    @Column(name = "confirmDate")
	    private String confirmDate;
	
	    @Column(name = "isBlock", columnDefinition = "char(1) default 'N'")
	    private char isBlock;
	
	    @Column(name = "libGenMembNo")
	    private String libGenMembNo;
	    
	    @OneToOne(mappedBy = "generalMember", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    private User user;
	
	    @Override
	    public int hashCode() {
	        return Integer.hashCode(memberId);
	    }
	
	    @Override
	    public boolean equals(Object obj) {
	        if (this == obj) return true;
	        if (obj == null || getClass() != obj.getClass()) return false;
	        GeneralMember other = (GeneralMember) obj;
	        return memberId == other.memberId;
	    }
	}
