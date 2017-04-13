package savingsAccount;

import java.io.Serializable;

//CSIS1410
//class SavingsAccount
//uses static field annualInterestRate

public class SavingsAccount implements Serializable
{
	private double annualInterestRate = .04;
	private double savingsBalance;
	private String accountNumber;
	private String saverName;

	public SavingsAccount(double newBalance, String acctNum, String name)
	{
		if (newBalance > 0)
			savingsBalance = newBalance;
		setAccountNumber(acctNum);
		setSaverName(name);
	}// end constructor

	public double getAnnualInterestRate()
	{
		return annualInterestRate;
	}

	// debit, credit and get savingsBalance
	public void credit(double deposit)
	{
		savingsBalance += deposit;
	}

	public void debit(double withdrawal)
	{
		savingsBalance -= withdrawal;
	}

	public double getSavingsBalance()
	{
		return savingsBalance;
	}

	// set and get accountNumber
	public void setAccountNumber(String acctNum)
	{
		accountNumber = acctNum;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	// set and get saverName
	public void setSaverName(String name)
	{
		saverName = name;
	}

	public String getSaverName()
	{
		return saverName;
	}

	// calculate this month's interest and add to savings balance
	public double calculateMonthlyInterest()
	{
		double monthlyInterest = savingsBalance * annualInterestRate / 12;
		savingsBalance += monthlyInterest;
		return monthlyInterest;
	}// end calculateMonthlyInterest

	// set the interest rate - all saver accounts automatically updated
	public void modifyInterestRate(double newRate)
	{
		annualInterestRate = newRate;
	}// end modifyInterestRate

	@Override
	public String toString()
	{
		return String.format("Savings balance for %s is %.2f", getSaverName(), getSavingsBalance());
	}
}// end class SavingsAccount
