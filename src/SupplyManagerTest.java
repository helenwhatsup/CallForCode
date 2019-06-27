import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/*
 * Work for the subclasses.
 */
public class SupplyManagerTest {
	public SupplyManagerTest() {
		super();
	}
	
	public static void main(String[] args) {
		SupplyManagerTest supplyManager = new SupplyManagerTest();
		
		Organization o3 = new Organization(1, "Union", 61, 3);
		Organization o2 = new Organization(1, "Union", 96, 2);
		Organization o1 = new Organization(1, "Union", 96, 1);
		Organization o4 = new Organization(1, "Union", 96, 4);
		
		//List<Supply> mapInUnprofitableSupplyPool(String resourceName, double amountNeeded, 
		// List<UnprofitableSupply> unprofitableSupplyPool) {
		// Supply(int supplyId, String name, int amount, String unit, Organization provider);
		UnprofitableSupply s1 = new UnprofitableSupply(101, "Water", 100, "box",o4);
		UnprofitableSupply s2 = new UnprofitableSupply(102, "Water", 50, "box", o3);
		UnprofitableSupply s3 = new UnprofitableSupply(103, "Water", 200, "box", o2);
		
		UnprofitableSupply s4 = new UnprofitableSupply(104, "Water", 300, "box",o4);
		UnprofitableSupply s5 = new UnprofitableSupply(105, "Water", 400, "box", o3);
		UnprofitableSupply s6 = new UnprofitableSupply(106, "Water", 500, "box", o2);
		
		
		// PROFITABLE
		ProfitableSupply ss1 = new ProfitableSupply(101, "Water", 100, "box", 4, o4);
		ProfitableSupply ss2 = new ProfitableSupply(102, "Water", 50, "box", 2, o3);
		ProfitableSupply ss3 = new ProfitableSupply(103, "Water", 200, "box", 6, o2);
		
		ProfitableSupply ss4 = new ProfitableSupply(104, "Water", 300, "box", 20, o4);
		ProfitableSupply ss5 = new ProfitableSupply(105, "Water", 400, "box", 20, o3);
		ProfitableSupply ss6 = new ProfitableSupply(106, "Water", 500, "box", 1, o2);
		
		// test unprofitable
//		List<Supply> resultList1 = supplyManager.mapInUnprofitableSupplyPool("Water", 300, Arrays.asList(s3, s2, s1));
//		System.out.println(resultList1);
		
		//test unprofitable
		List<UnprofitableSupply> l1 = Arrays.asList(s3, s2, s1, s5, s4, s6);
		List<Supply> resultList2 = supplyManager.mapInUnprofitableSupplyPool("Water", 2000, l1);
		System.out.println(resultList2);
		System.out.println("\n");
		
		List<ProfitableSupply> l2 = Arrays.asList(ss3, ss2, ss1, ss5, ss4, ss6);
		double price = supplyManager.calculatePriceInProfitableSupplyPool("Water", 2000, l2);
		System.out.println(price);
		System.out.println("\n");
		
		List<Supply> resultList3 = supplyManager.mapInProfitableSupplyPool("Water", 2000, 18000, l2);
		System.out.println(resultList3);
		
		
	}
	

	// TODO get the corresponding list of supplies
	public static List<UnprofitableSupply> getUnprofitableSupplyList(String ResourceName) {

		return null;
	}

	// TODO get the corresponding list of supplies
	public List<ProfitableSupply> getProfitableSupplyList(String ResourceName) {

		return null;
	}

	/**
	 * 
	 * @param list
	 * @return 
	 */
	public double getTotalAmount(List<Supply> list) {
		double total = list.stream().mapToDouble(s -> s.getAmount()).sum();
		return total;
	}
	
	public double getTotalPrice(List<Supply> profitableSupplyList) {
		double totalPrice = 0;

		for (Supply s : profitableSupplyList) {
			totalPrice += ((ProfitableSupply)s).getUnitPrice() * s.getAmount();
		}
		return totalPrice;
	}
	
	public double getTotalFund() {
		List<UnprofitableSupply> fundList =  getUnprofitableSupplyList("Fund");
		
		double totalFund = fundList.stream().mapToDouble(s -> s.getAmount()).sum();
		return totalFund;
	}

	/**
	 * Map the demand in the unprofitable supply pool.
	 * 
	 * @param amountNeeded
	 * @param supplyList
	 * @return the list of supplies that's mapped to the demand.
	 */
	List<Supply> mapInUnprofitableSupplyPool(String resourceName, double amountNeeded, List<UnprofitableSupply> unprofitableSupplyPool) {
		double sum = 0;
		List<Supply> supplyList = new ArrayList<Supply>();

		//List<UnprofitableSupply> unprofitableSupplyPool = getUnprofitableSupplyList(resourceName);
		Collections.sort(unprofitableSupplyPool);

		for (UnprofitableSupply s : unprofitableSupplyPool) {
			if (sum == amountNeeded) {
				break;
			}
			double amountStillNeeded = amountNeeded - sum;
			if (s.getAmount() < amountStillNeeded) {
				UnprofitableSupply s1 = (UnprofitableSupply) s.clone();
				supplyList.add(s1);

				sum += s.getAmount();
				s.setAmount(0);
				s.updateSupply();// TODO update s1 data
			} else if (s.getAmount() >= amountStillNeeded) {
				UnprofitableSupply s1 = (UnprofitableSupply) s.clone();
				supplyList.add(s1);

				s1.setAmount(amountStillNeeded);
				sum = amountNeeded;
				s.deductAmount(amountStillNeeded);
				// TODO update s1 data
			}
		}
		return supplyList;
	}

	/**
	 * Calculate the price needed to pay for the most optimal amount of resources in the 
	 * profitable supply pool.
	 * 
	 * @param resourceName
	 * @param amountNeeded
	 * @return the price for supplies in the profitable supply pool.
	 */
	double calculatePriceInProfitableSupplyPool(String resourceName, double amountNeeded, List<ProfitableSupply> profitableSupplyPool) {
		double price = 0, sum = 0;

		//List<ProfitableSupply> profitableSupplyPool = getProfitableSupplyList(resourceName);
		Collections.sort(profitableSupplyPool);

		for (ProfitableSupply s : profitableSupplyPool) {
			if (sum == amountNeeded) {
				break;
			}

			double amountStillNeeded = amountNeeded - sum;
			if (s.getAmount() < amountStillNeeded) {
				sum += s.getAmount();
				price += s.getUnitPrice() * s.getAmount();
			} else if (s.getAmount() >= amountStillNeeded) {
				sum = amountNeeded;
				price += s.getUnitPrice() * amountStillNeeded;
			}
		}

		return price;
	}

	/**
	 * Map in the profitable supply pool with the given amount of fund.
	 * 
	 * @param resourceName
	 * @param amountNeeded
	 * @param fund
	 * @return the list of supplies that's mapped to the demand.
	 */
	List<Supply> mapInProfitableSupplyPool(String resourceName, double amountNeeded, double fund, List<ProfitableSupply> profitableSupplyPool) {
		double price = 0, sum = 0;
		List<Supply> supplyList = new ArrayList<Supply>();

		// List<ProfitableSupply> profitableSupplyPool = getProfitableSupplyList(resourceName);
		Collections.sort(profitableSupplyPool);

		for (ProfitableSupply s : profitableSupplyPool) {
			double amountStillNeeded = amountNeeded - sum;
			if (s.getAmount() < amountStillNeeded && price + s.getUnitPrice() * s.getAmount() <= fund) {
				ProfitableSupply s1 = (ProfitableSupply) s.clone();
				supplyList.add(s1);

				sum += s.getAmount();
				s.setAmount(0);
				price += s.getUnitPrice() * s.getAmount();
				// TODO update s data
			} else if (s.getAmount() >= amountStillNeeded && price + s.getUnitPrice() * amountStillNeeded <= fund) {
				ProfitableSupply s1 = (ProfitableSupply) s.clone();
				s1.setAmount(amountStillNeeded);
				supplyList.add(s1);

				sum = amountNeeded;
				s.deductAmount(amountStillNeeded);
				price += s.getUnitPrice() * amountStillNeeded;
				// TODO update s data
			}
		}

		return supplyList;
	}
	

}


