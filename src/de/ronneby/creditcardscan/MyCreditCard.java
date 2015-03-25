package de.ronneby.creditcardscan;

import io.card.payment.CreditCard;

/**
 * @author Ronneby
 *
 */
public class MyCreditCard extends CreditCard{

	private long id;

	public MyCreditCard(String number, int month, int year, String code, String postalCode)
	{
		super(number, month, year, code, postalCode);
	}
	
	public void setId(long id) 
	{
		this.id = id;
	}
	
	public long getId() 
	{
		return id;
	}
}
