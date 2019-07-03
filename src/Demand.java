import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demand implements Comparable<Demand>, Serializable {
	private int demandId;
	private String name;
	private String category;
	private int amountNeeded;
	private String unit;
	private int priority; // range from 1 to 3 to represent the urgency.
	private int DemanderId;

	private static int currentDemandId = 1;
	private static final long serialVersionUID = 20190625050327L;

	public static void main(String[] args) {	
		Organization o3 = new Organization(1, "Union", 61, 3, "profitable");
		Organization o2 = new Organization(1, "Union", 96, 2, "profitable");
		Organization o1 = new Organization(1, "Union", 96, 1, "profitable");
		//demand attributes
		int demandId = 1;
		String name = "milk";
		String category = "Food";
		int amountNeeded = 50;
		String unit = "box";
		int priority = 1;
		Demand d1 = new Demand(demandId, name, category, amountNeeded, unit, priority);
		
		//supply attributes
		int supplyId = 301;
		String name2 = "milk";
		double amount2 =  30;
		String unit2 = "box";
		int providerId = 401;
		UnprofitableSupply s1 = new UnprofitableSupply(supplyId, name2, amount2, unit2, providerId);
		s1.uplinkUnprofitableSupply(supplyId, name2, amount2, unit2, providerId);
		
		d1.matchToSupply();
	}
	/*
	 * The map that matches a category to its corresponding priority.
	 */
	private static Map<String, Integer> priorityForCategoryMap = new HashMap<String, Integer>() {
		{
			put("Food", 1);
			put("Medical", 2);
			put("Rescue", 3);
			put("Epidemic Prevention", 4);
			put("Construction", 6);
		}
	};


	/**
	 * The constructor with the default priority.
	 * 
	 * @param name
	 * @param amount
	 * @param unit
	 */
	public Demand(int demandId, String name, String category, int amount, String unit) {
		super();
		this.demandId = demandId;
		this.name = name;
		this.category = category;
		this.amountNeeded = amount;
		this.unit = unit;
		this.priority = priorityForCategoryMap.get(category);
	}

	/**
	 * The constructor with user defined priority.
	 * 
	 * @param name
	 * @param amount
	 * @param unit
	 * @param priority
	 */
	public Demand(int demandId,String name, String category, int amount, String unit, int priority) {
		super();
		this.demandId = demandId;
		this.name = name;
		this.category = category;
		this.amountNeeded = amount;
		this.unit = unit;
		this.priority = priority;
	}

	/*
	 * Match this demand in the supply pools.
	 */
	public void matchToSupply() {
		SupplyManager supplyManager = new SupplyManager();

		// First match with the unprofitable supply pool
		List<Supply> unprofitableSupplyList = supplyManager.mapInUnprofitableSupplyPool(this.getName(),
				this.amountNeeded);
		
		List<Supply> totalList = new ArrayList<Supply>();
		totalList.addAll(unprofitableSupplyList);
		
		double sum = supplyManager.getTotalAmount(unprofitableSupplyList);
		double amountStillNeeded = this.amountNeeded - sum;
		if (amountStillNeeded > 0) {
			// Calculate the price needed to pay for the available resources
			// in the profitable supply pool.
			double price = supplyManager.calculatePriceInProfitableSupplyPool(this.getName(), (int) amountStillNeeded);
			
			// Map in the profitable supply pool with the fund.
			double fund = price < supplyManager.getTotalFund() ? price : supplyManager.getTotalFund();
			List<Supply> profitableSupplyList = supplyManager.mapInProfitableSupplyPool(
					this.getName(), (int) amountStillNeeded, fund);
			sum += supplyManager.getTotalAmount(profitableSupplyList);
			double fundUsed = supplyManager.getTotalPrice(profitableSupplyList);

			// Deduct the fund actually used in the unprofitable supply pool.
			List<Supply> fundList = supplyManager.mapInUnprofitableSupplyPool("Fund", fundUsed);
			if (fundUsed != supplyManager.getTotalAmount(fundList)) {
				throw new RuntimeException();
			}
			// TODO broadcast transaction to the chain. three lists & SUM & FUND USED
			
			totalList.addAll(profitableSupplyList);
			totalList.addAll(fundList);
		}

		if (sum < this.amountNeeded) {
			// TODO put this unmapped demand in the demand pool
		}
	}

	@Override
	public int compareTo(Demand other) {
		if (this.priority < other.priority) {
			return 1;
		} else if (this.priority > other.priority) {
			return -1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "Demand [demandId=" + demandId + ", name=" + name + ", category=" + category + ", amountNeeded="
				+ amountNeeded + ", unit=" + unit + ", priority=" + priority + "]";
	}

	// The getters and setters
	public int getDemandId() {
		return demandId;
	}

	public void setDemandId(int demandId) {
		this.demandId = demandId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAmount() {
		return amountNeeded;
	}

	public void setAmount(int amount) {
		this.amountNeeded = amount;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

}
