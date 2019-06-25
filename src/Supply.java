
public class Supply implements Comparable<Supply>{
	private int supplyId;
	private String name;
	private int amount;
	private String unit;
	private int unitPrice;
	private Organization provider;
	
	public Supply(int supplyId, String name, int amount, String unit, int unitPrice, Organization provider) {
		super();
		this.supplyId = supplyId;
		this.name = name;
		this.amount = amount;
		this.unit = unit;
		this.unitPrice = unitPrice;
		this.provider = provider;
	}

	@Override
	public int compareTo(Supply other) {
		if (this.provider.getRank() > other.provider.getRank()) {
			return -1;
		} else if (this.provider.getRank() < other.provider.getRank()) {
			return 1;
		}
		return 0;
	}
	
	
}
