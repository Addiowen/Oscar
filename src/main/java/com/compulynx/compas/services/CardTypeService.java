package com.compulynx.compas.services;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.compulynx.compas.models.CardPrint;
import com.compulynx.compas.models.CardType;
import com.compulynx.compas.repositories.CardPrintRepository;
import com.compulynx.compas.repositories.CardTypeRepository;

@Service
public class CardTypeService {
@Autowired
private CardTypeRepository cardTypeRepository;
@Autowired
private CardPrintRepository cardPrintRepository;
	public CardType addCardType(CardType cardType) {
		// TODO Auto-generated method stub
		return cardTypeRepository.save(cardType);
	}
	public CardType updCardType(CardType cardType) {
		CardType ct=new CardType();	
		ct.setId(cardType.getId());
		ct.setCode(cardType.getCode());
		ct.setDescription(cardType.getDescription());
		ct.setName(cardType.getName());	
		return cardTypeRepository.save(ct);
	}

	public List<CardType> getCardTypes() {
		// TODO Auto-generated method stub
		return cardTypeRepository.findAll();
	}

	public CardPrint saveCard(CardPrint cardPrint) {
		CardPrint cp=new CardPrint();
		cp.setAccountName(cardPrint.getAccountName().toUpperCase());
		cp.setCardFormatId(cardPrint.getCardFormatId());
		cp.setPrintedBy(cardPrint.getPrintedBy());
		cp.setUserId(cardPrint.getPrintedBy());
		cp.setBranchId(cardPrint.getBranchId());
		cp.setValid_thru(cardPrint.getValid_thru());
		cp.setActive(cardPrint.isActive());
		cp.setDatePrinted(new Timestamp(System.currentTimeMillis()));
		return cardPrintRepository.save(cp);
		
	}
	  public List<CardPrint> getIssuedCardByUser(String fromDate, String toDate, String userProp) {
			// TODO Auto-generated method stub
			return cardPrintRepository.getCardsByUser(fromDate, toDate, userProp);
		}

	public List<CardPrint> getIssuedCardByBranch(String fromDate, String toDate, String branch) {
		// TODO Auto-generated method stub
		return cardPrintRepository.getAllCardByBranch(fromDate, toDate, branch);
	}

	public List<CardPrint> getIssuedCardByDate(String fromDate, String toDate) {
		// TODO Auto-generated method stub
		return cardPrintRepository.getAllCardByDate(fromDate, toDate);
	}
	/**
	 * @param name
	 * @return
	 */
	public CardType findByName(String name) {
		// TODO Auto-generated method stub
		return cardTypeRepository.findByName(name);
	}

}
