import main.java.org.example.cfc.InvokeBCP;

public class UnprofitableSupply extends Supply {
	//block chain connection profile
	private final static String chainCode = "go_package8";

	
	public UnprofitableSupply(int supplyId, String name, double amount, String unit, int providerID) {
		super(supplyId, name, amount, unit, providerID);
	}

    public void uplinkUnprofitableSupply() {
    	InvokeBCP invoke = new InvokeBCP();
		String[] invokeArgs = new String[]{String.valueOf(this.getSupplyId()),String.valueOf(this.getName()),
						String.valueOf(this.getAmount()),String.valueOf(this.getUnit()),String.valueOf(this.getProviderId())};
		try {
			invoke.invoke(chainCode,"initUnprofitablesupply",invokeArgs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// TODO Implement the update supply method
	public void updateUnprofitableSupply() {
		InvokeBCP invoke = new InvokeBCP();
		String[] invokeArgs = new String[]{String.valueOf(this.getSupplyId()),String.valueOf(this.getAmount())};
		try {
			invoke.invoke(chainCode,"updateUnprofitablesupplyamount",invokeArgs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
