
public class Organization {
	private int orgId;
	private String name;
	private int score; // range from 0 to 100
	private int rank;

	public Organization(int orgId, String name, int score, int rank) {
		super();
		this.orgId = orgId;
		this.name = name;
		this.score = score;
		this.rank = rank;
	}

	public int getOrgId() {
		return orgId;
	}

	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

}
