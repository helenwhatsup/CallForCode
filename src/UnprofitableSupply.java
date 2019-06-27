
public class UnprofitableSupply extends Supply {

	public UnprofitableSupply(int supplyId, String name, int amount, String unit, Organization provider) {
		super(supplyId, name, amount, unit, provider);
	}


//	public int compareTo(Supply other) {
//		if (this.getProvider().getRank() > other.getProvider().getRank()) {
//			return 1;
//		} else if (this.getProvider().getRank() < other.getProvider().getRank()) {
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
