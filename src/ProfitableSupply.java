
public class ProfitableSupply extends Supply {
	private double unitPrice;

	public ProfitableSupply(int supplyId, String name, int amount, String unit, int unitPrice, Organization provider) {
		super(supplyId, name, amount, unit, provider);
		this.unitPrice = unitPrice;
	}
	
//	public int compareTo(Supply other) {
//		if (this.provider.getRank() > other.provider.getRank()) {
//			return 1;
//		} else if (this.provider.getRank() < other.provider.getRank()) {
//			return -1;
//		} else {
//			if (this.getAmount() > other.getAmount()) {
//				return 1;
//			} else if (this.getAmount() < other.getAmount()) {
//				return -1;
//			}
//			return 0;
//		}
//	}

}
