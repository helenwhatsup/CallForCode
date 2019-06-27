
public class ProfitableSupply extends Supply {
	private double unitPrice;

	public ProfitableSupply(int supplyId, String name, int amount, String unit, int unitPrice, Organization provider) {
		super(supplyId, name, amount, unit, provider);
		this.unitPrice = unitPrice;
	}
	
	
	@Override
	public String toString() {
		return super.toString() + "ProfitableSupply [unitPrice=" + unitPrice + "]";
	}


	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	
	public int compareTo(ProfitableSupply other) {
		if (this.getProvider().getRank() > other.getProvider().getRank()) {
			return -1;
		} else if (this.getProvider().getRank() < other.getProvider().getRank()) {
			return 1;
		} else {
			if ((this.getAmount() / this.getUnitPrice()) > (other.getAmount() / other.getUnitPrice())) {
				return -1;
			} else if ((this.getAmount() / this.getUnitPrice()) < (other.getAmount() / other.getUnitPrice())) {
				return +1;
			}
		}
		return 0;
	}

}
