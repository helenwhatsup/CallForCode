import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/*
 * Work for the subclasses.
 */
public class SupplyManager {
	public SupplyManager() {
		super();
	}

	// TODO
	public static List<UnprofitableSupply> getUnprofitableSupplyList(String ResourceName) {

		return null;
	}

	// TODO
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
	List<Supply> mapInUnprofitableSupplyPool(String resourceName, double amountNeeded) {
		double sum = 0;
		List<Supply> supplyList = new ArrayList<Supply>();

		List<UnprofitableSupply> unprofitableSupplyPool = getUnprofitableSupplyList(resourceName);
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
				// s.setAmount(0);
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
	double calculatePriceInProfitableSupplyPool(String resourceName, double amountNeeded) {
		// the array containing the amount of resources collected so far and the price
		// needed to pay for these resources.
		// *****double[] result;
		double price = 0, sum = 0;

		List<ProfitableSupply> profitableSupplyPool = getProfitableSupplyList(resourceName);
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
	List<Supply> mapInProfitableSupplyPool(String resourceName, double amountNeeded, double fund) {
		double price = 0, sum = 0;
		List<Supply> supplyList = new ArrayList<Supply>();

		List<ProfitableSupply> profitableSupplyPool = getProfitableSupplyList(resourceName);
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
