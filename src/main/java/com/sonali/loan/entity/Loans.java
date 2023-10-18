package com.sonali.loan.entity;

import java.sql.Date;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString @AllArgsConstructor @NoArgsConstructor
public class Loans {
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO,generator="native")
	@GenericGenerator(name = "native",strategy = "native")
	private Long loanId;

	private String mobileNumber;

	private String loanNumber;

	private String loanType;

	private int totalLoan;

	private int amountPaid;

	private int outstandingAmount;
}
