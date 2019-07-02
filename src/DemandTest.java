
	import java.io.Serializable;
	import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
	import java.util.HashMap;
	import java.util.List;
	import java.util.Map;
	
public class DemandTest {
	
	private int demandId;
	private String name;
	private String category;
	private int amountNeeded;
	private String unit;
	private int priority; // range from 1 to 3 to represent the urgency.
	private int DemanderId;
	
	public static void main(String[] args) {		
		Organization o3 = new Organization(1, "Union", 61, 3, "profitable");
		
		Organization o2 = new Organization(1, "Union", 96, 2, "profitable");
		Organization o1 = new Organization(1, "Union", 96, 1, "profitable");
		Organization o4 = new Organization(1, "Union", 96, 4, "profitable");
		
//		UnprofitableSupply s1 = new UnprofitableSupply(101, "Water", 100, "box",o4);
//		UnprofitableSupply s2 = new UnprofitableSupply(102, "Water", 50, "box", o3);
//		UnprofitableSupply s3 = new UnprofitableSupply(103, "Water", 200, "box", o2);
//		
//		UnprofitableSupply s4 = new UnprofitableSupply(104, "Water", 300, "box",o4);
//		//UnprofitableSupply s5 = new UnprofitableSupply(105, "Water", 400, "box", o3);
//		//UnprofitableSupply s6 = new UnprofitableSupply(106, "Water", 500, "box", o2);
//		List<UnprofitableSupply> waterUnList = Arrays.asList(s1, s2, s4);
		
		// PROFITABLE
//		ProfitableSupply ss1 = new ProfitableSupply(1001, "Water", 100, "box", 4.3, o4);
//		ProfitableSupply ss2 = new ProfitableSupply(1002, "Water", 50, "box", 2.2, o3);
//		ProfitableSupply ss3 = new ProfitableSupply(1003, "Water", 200, "box", 6.1, o2);
//		
//		ProfitableSupply ss4 = new ProfitableSupply(1004, "Water", 300, "box", 7.5, o4);
//		//ProfitableSupply ss5 = new ProfitableSupply(105, "Water", 400, "box", 20, o3);
//		//ProfitableSupply ss6 = new ProfitableSupply(106, "Water", 500, "box", 1, o2);
//		List<ProfitableSupply> waterProList = Arrays.asList(ss1, ss2, ss4);
//		
//		UnprofitableSupply f1 = new UnprofitableSupply(101, "Fund", 5000, "USD",o4);
//		UnprofitableSupply f2 = new UnprofitableSupply(102, "Fund", 6000, "USD", o3);
//		UnprofitableSupply f3 = new UnprofitableSupply(103, "Fund", 3000, "USD", o2);
//		List<UnprofitableSupply> fundPool = Arrays.asList(f1, f2, f3);
		
		ProfitableSupply ss1 = new ProfitableSupply(1001, "Water", 1, "box", 4.3, o4);
		ProfitableSupply ss2 = new ProfitableSupply(1002, "Water", 5, "box", 2.2, o3);
		ProfitableSupply ss3 = new ProfitableSupply(1003, "Water", 2, "box", 6.1, o2);
		
		ProfitableSupply ss4 = new ProfitableSupply(1004, "Water", 3, "box", 7.5, o4);
		//ProfitableSupply ss5 = new ProfitableSupply(105, "Water", 400, "box", 20, o3);
		//ProfitableSupply ss6 = new ProfitableSupply(106, "Water", 5, "box", 1, o2);
		List<ProfitableSupply> waterProList = Arrays.asList(ss1, ss2, ss4);
		
		UnprofitableSupply f1 = new UnprofitableSupply(101, "Fund", 50, "USD",o4);
		UnprofitableSupply f2 = new UnprofitableSupply(102, "Fund", 60, "USD", o3);
		UnprofitableSupply f3 = new UnprofitableSupply(103, "Fund", 30, "USD", o2);
		List<UnprofitableSupply> fundPool = Arrays.asList(f1, f2, f3);
		
		DemandTest demand = new DemandTest(333, "Water", "Food", 20, "box");
		System.out.println(demand);
		demand.matchToSupply(Arrays.asList(), fundPool, waterProList);

	}
	

	/*
	 * Match this demand in the supply pools.
	 */
	public void matchToSupply(List<UnprofitableSupply> waterUnList, 
			List<UnprofitableSupply> fundPool, List<ProfitableSupply> waterProList) {
		SupplyManagerTest supplyManager = new SupplyManagerTest();

		// First match with the unprofitable supply pool
		List<Supply> unprofitableSupplyList = supplyManager.mapInUnprofitableSupplyPool(this.getName(),
				this.amountNeeded, waterUnList);
		double sum = supplyManager.getTotalAmount(unprofitableSupplyList);

		double amountStillNeeded = this.amountNeeded - sum;
		if (amountStillNeeded == 0) {
			System.out.println("Unprofitable supply List:\n");
			System.out.println(unprofitableSupplyList);
			return;
			
		}

		// Calculate the price needed to pay for the available resources
		// in the profitable supply pool.
		double price = supplyManager.calculatePriceInProfitableSupplyPool(this.getName(), (int) amountStillNeeded, waterProList);
		
		double totalFund = supplyManager.getTotalAmount(fundPool);//***
		// Map in the profitable supply pool with the fund.
		double fund = price < supplyManager.getTotalAmount(fundPool) ? price : supplyManager.getTotalAmount(fundPool);
		List<Supply> profitableSupplyList = supplyManager.mapInProfitableSupplyPool(
				this.getName(), (int) amountStillNeeded, fund, waterProList);
		sum += supplyManager.getTotalAmount(profitableSupplyList);
		double fundUsed = supplyManager.getTotalPrice(profitableSupplyList);

		// Deduct the fund actually used in the unprofitable supply pool.
		List<Supply> fundList = supplyManager.mapInUnprofitableSupplyPool("Fund", fundUsed, fundPool);
		double actualFund = supplyManager.getTotalAmount(fundList);
		if (fundUsed != actualFund) {
			throw new RuntimeException();
		}
		// TODO broadcast transaction to the chain. three lists & SUM & FUND USED
		List<Supply> totalList = new ArrayList<Supply>();
		totalList.addAll(unprofitableSupplyList);
		totalList.addAll(profitableSupplyList);
		totalList.addAll(fundList);
		
		
		
		System.out.println("Unprofitable supply List:\n");
		System.out.println(unprofitableSupplyList);
		
		System.out.println("Total price in profitable supply list is: " + price);
		System.out.println("Fund actually provided is " + fund );
		System.out.println("\n Fund List:");
		System.out.println(fundList);
		
		System.out.println("\n Profitable supply List:");
		System.out.println(profitableSupplyList);
		
		System.out.println("Actually gathered amount of resources:" + sum);
		
//		if (sum < this.amountNeeded) {
//			// TODO put this unmapped demand in the demand pool
//		}
	}
	
	public String toString() {
		return "Demand [demandId=" + demandId + ", name=" + name + ", category=" + category + ", amountNeeded="
				+ amountNeeded + ", unit=" + unit + ", priority=" + priority + "]";
	}

	private String getName() {
		return this.name;
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
	
	public DemandTest() {};

	/**
	 * The constructor with the default priority.
	 * 
	 * @param name
	 * @param amount
	 * @param unit
	 */
	public DemandTest(int demandId, String name, String category, int amount, String unit) {
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
	public DemandTest(String name, String category, int amount, String unit, int priority) {
		super();
		//this.demandId = ++currentDemandId;
		this.name = name;
		this.category = category;
		this.amountNeeded = amount;
		this.unit = unit;
		this.priority = priority;
	}	

	

}
