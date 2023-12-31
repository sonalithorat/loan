package com.sonali.loan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sonali.loan.constants.LoansConstants;
import com.sonali.loan.dto.LoansDto;
import com.sonali.loan.dto.ResponseDto;
import com.sonali.loan.service.ILoansService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/api", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
@Validated
@RefreshScope
public class LoansController {

	private static final Logger logger = LoggerFactory.getLogger(LoansController.class);
	@Autowired
	private ILoansService iLoansService;

	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo(@Value("${build.version}") String buildVersion) {
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}

	@GetMapping("/message")
	public ResponseEntity<String> getMessage(@Value("${accounts.message}") String buildVersion) {
		return ResponseEntity.status(HttpStatus.OK).body(buildVersion);
	}

	@GetMapping("/get")
	public String test() {
		return "hello cards";
	}

	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createLoan(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		iLoansService.createLoan(mobileNumber);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ResponseDto(LoansConstants.STATUS_201, LoansConstants.MESSAGE_201));
	}

	@GetMapping("/fetch")
	public ResponseEntity<LoansDto> fetchLoanDetails(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber,
			@RequestHeader("eazybank-correlation-id") String correlationId) {
		logger.debug("eazyBank-correlation-id found: {} ", correlationId);
		LoansDto loansDto = iLoansService.fetchLoan(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(loansDto);
	}

	@PutMapping("/update")
	public ResponseEntity<ResponseDto> updateLoanDetails(@Valid @RequestBody LoansDto loansDto) {
		boolean isUpdated = iLoansService.updateLoan(loansDto);
		if (isUpdated) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_UPDATE));
		}
	}

	@DeleteMapping("/delete")
	public ResponseEntity<ResponseDto> deleteLoanDetails(
			@RequestParam @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) {
		boolean isDeleted = iLoansService.deleteLoan(mobileNumber);
		if (isDeleted) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new ResponseDto(LoansConstants.STATUS_200, LoansConstants.MESSAGE_200));
		} else {
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
					.body(new ResponseDto(LoansConstants.STATUS_417, LoansConstants.MESSAGE_417_DELETE));
		}
	}

	@GetMapping("/contact-info")
	public ResponseEntity<String> getContactInfo() {
		logger.debug("Invoked Loans contact-info API");
		// throw new NullPointerException();

		return ResponseEntity.status(HttpStatus.OK).body("sonali thorat");

	}
}
