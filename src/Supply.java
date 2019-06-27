
public class Supply implements Comparable<Supply>, Cloneable {
	private int supplyId;
	private String name;
	private double amount;
	private String unit;
	private Organization provider;

	public Supply() {
	};

	public Supply(int supplyId, String name, int amount, String unit, Organization provider) {
		super();
		this.supplyId = supplyId;
		this.name = name;
		this.amount = amount;
		this.unit = unit;
		this.provider = provider;
	}

	// TODO
	public void updateSupply() {

	}

	@Override
	public int compareTo(Supply other) {
		if (this.provider.getRank() > other.provider.getRank()) {
			return 1;
		} else if (this.provider.getRank() < other.provider.getRank()) {
			return -1;
		} else {
			if (this.getAmount() > other.getAmount()) {
				return 1;
			} else if (this.getAmount() < other.getAmount()) {
				return -1;
			}
			return 0;
		}
	}

	@Override
	public Object clone() {
		Supply s = null;
		try {
			s = (Supply) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return s;
	}

	public int getSupplyId() {
		return supplyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void deductAmount(double amountToDeduct) {
		this.amount -= amountToDeduct;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Organization getProvider() {
		return provider;
	}

	public void setProvider(Organization provider) {
		this.provider = provider;
	}
}
