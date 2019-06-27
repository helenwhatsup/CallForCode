import java.util.Collections;
import java.util.List;

public class DemandManager {

	public DemandManager() {
		super();
	}
	
	//TODO Interact with the blockchain ledger.
	void updateDemand() {
		return;
	}
	
	/**
	 * Match the demands to the supplies.
	 * @param demandList
	 */
	void matchDemandsToSupplies(List<Demand> demandList) {
		//TODO: ?maybe get the demand list in the method body instead of passing as an argument.
		Collections.sort(demandList);
		for (Demand d : demandList) {
			d.matchToSupply();
		}
	}
}
