package com.sonali.loan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sonali.loan.entity.Loans;

@Repository
public interface LoansRepository extends JpaRepository<Loans, Long> {
	Optional<Loans> findByMobileNumber(String mobileNumber);

	Optional<Loans> findByLoanNumber(String loanNumber);
}
