
public class ProfitableSupply extends Supply{
	private int amount; // There is no fund in the profitable supply pool, so the amount should be an integer.
	private double unitPrice;

	public ProfitableSupply(int supplyId, String name, int amount, String unit, double unitPrice, int providerId) {
		super(supplyId, name, amount, unit, providerId);
		this.unitPrice = unitPrice;
	}
	
	public void uplinkProfitableSupply() {
		
	}
	
	// TODO Implement the update supply method
	public void updateProfitableSupply() {

	}
	
	@Override
	public int compareTo(Supply other) {
		if (Organization.getRankById(this.getProviderId()) > Organization.getRankById(other.getProviderId())) {
			return -1; 
		} else if (Organization.getRankById(this.getProviderId()) <Organization.getRankById(other.getProviderId())) {
			return 1;
		} else { 
			if (this.getUnitPrice() < ((ProfitableSupply) other).getUnitPrice()) {
				return -1;
			} else if (this.getUnitPrice() > ((ProfitableSupply) other).getUnitPrice()) {
				return 1;
		}else { 
				if (this.getAmount() > other.getAmount()) {
					return -1;
				} else if (this.getAmount() < other.getAmount()) {
					return +1;
				}
			}
		}
		return 0;
	}
	
	@Override
	public String toString() {		
		return "ProfitableSupply [supplyId=" + this.getSupplyId() + ", name=" + 
				this.getName() + ", amount=" + this.getAmount() + ", unitPrice=" + this.getUnitPrice() +
				" with provider rank" + Organization.getRankById(this.getProviderId()) + "]";
	}


	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
}
