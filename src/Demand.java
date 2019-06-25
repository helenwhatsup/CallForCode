import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Demand implements Comparable<Demand>, Serializable{
	private int demandId;
	private String name;
	private String category;
	private int amount;
	private String unit;
	private int priority; // range from 1 to 3 to represent the urgency.

	private static int currentDemandId=1;
	private static final long serialVersionUID = 20190625050327L;
	
	private static Map<String, Integer> priorityForCategoryMap = new HashMap<String, Integer>() {{
		put("Food", 1);
		put("Medical", 2);
		put("Rescue", 3);
		put("Epidemic Prevention", 4);
		put("Construction", 6);
	}};
	
	/**
	 * The constructor with the default priority.
	 * 
	 * @param name
	 * @param amount
	 * @param unit
	 */
	public Demand(String name, String category, int amount, String unit) {
		super();
		this.demandId = ++currentDemandId;
		this.name = name;
		this.category = category;
		this.amount = amount;
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
	public Demand(String name, String category, int amount, String unit, int priority) {
		super();
		this.demandId = ++currentDemandId;
		this.name = name;
		this.category = category;
		this.amount = amount;
		this.unit = unit;
		this.priority = priority;
	}
	
	@Override
	public int compareTo(Demand other) {
		if (this.priority < other.priority) {
			return -1;
		} else if (this.priority > other.priority) {
			return 1;
		} 
		return 0;
	}
	
	public matchToSupply() {
		
	}
	
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
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
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
