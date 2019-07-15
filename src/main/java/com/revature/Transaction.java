package com.revature;

public class Transaction {
	private String userName;
	private String action;
	private int account;
	private int accountTo;
	private int amount;
	private int balance;
	private String timeStamp;
	
	public Transaction(){
		
	}

	public Transaction(String userName, String action, int account, int accountTo, int amount, int balance,
			String timeStamp) {
		super();
		this.userName = userName;
		this.action = action;
		this.account = account;
		this.accountTo = accountTo;
		this.amount = amount;
		this.balance = balance;
		this.timeStamp = timeStamp;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public int getAccountTo() {
		return accountTo;
	}

	public void setAccountTo(int accountTo) {
		this.accountTo = accountTo;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public int getBalance() {
		return balance;
	}

	public void setBalance(int balance) {
		this.balance = balance;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "Transaction [userName=" + userName + ", action=" + action + ", account=" + account + ", accountTo="
				+ accountTo + ", amount=" + amount + ", balance=" + balance + ", timeStamp=" + timeStamp + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + account;
		result = prime * result + accountTo;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + amount;
		result = prime * result + balance;
		result = prime * result + ((timeStamp == null) ? 0 : timeStamp.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (account != other.account)
			return false;
		if (accountTo != other.accountTo)
			return false;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (amount != other.amount)
			return false;
		if (balance != other.balance)
			return false;
		if (timeStamp == null) {
			if (other.timeStamp != null)
				return false;
		} else if (!timeStamp.equals(other.timeStamp))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	
	
}
