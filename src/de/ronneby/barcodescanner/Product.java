package de.ronneby.barcodescanner;

/**
 * @author Ronneby
 *
 */
public class Product 
{
	private String Productname;
	private double Productprice;
	private String Productcode;
	private long id;
	
	public Product(){
		this.Productname = "Nothing";
		this.Productprice = 0.0;
		this.Productcode = "00000000";
	}
	
	public Product(String Productname, double Productprice, String Productcode)
	{
		this.Productname = Productname;
		this.Productprice = Productprice;
		this.Productcode = Productcode;
	}
	
	public void setName(String Name)
	{
		this.Productname = Name;
	}
	
	public String getName()
	{
		return Productname;
	}
	
	public void setPrice(double Price)
	{
		this.Productprice = Price;
	}
	
	public double getPrice()
	{
		return Productprice;
	}
	
	public void setCode(String Code)
	{
		this.Productcode = Code;
	}
	
	public String getCode()
	{
		return Productcode;
	}

	public long getId() 
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}
}
