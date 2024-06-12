package com.compulynx.compas.services;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.compulynx.compas.models.Branch;
import com.compulynx.compas.models.IssuedCard;
import com.compulynx.compas.models.User;
import com.compulynx.compas.repositories.IssuedCardRepository;

@Service
public class IssuedCardService {

	private IssuedCardRepository issuedCardRepository;
	
	@Autowired
	  public IssuedCardService(IssuedCardRepository issuedCardRepository) {
	    this.issuedCardRepository = issuedCardRepository;
	  }
	public Iterable<IssuedCard> findAll() {	
		return this.issuedCardRepository.findAll();
	}

	public List<IssuedCard> findAllByBranchAndCreatedAt(Branch branch, Date fromDate, Date toDate) {	
		return this.issuedCardRepository.findAllByBranchAndCreatedAt(branch, fromDate, toDate);
	}

	public List<IssuedCard> findAllByBranchAndUserAndCreatedAt(Branch branch, User user, Date fromDate, Date toDate) {	
		return this.issuedCardRepository.findAllByBranchAndUserAndCreatedBy(branch, user, fromDate, toDate);
	}

	public List<IssuedCard> findAllByUserAndCreatedAt(User user, Date fromDate, Date toDate) {	
		return this.issuedCardRepository.findAllByUserAndCreatedAt(user, fromDate, toDate);
	}

	public List<IssuedCard> findAllByCreatedAt(Date fromdate, Date toDate) {	
		return this.issuedCardRepository.findAllByCreatedAt(fromdate, toDate);
	}

}
