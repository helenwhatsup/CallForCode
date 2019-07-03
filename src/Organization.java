import java.util.List;

import com.alibaba.fastjson.JSONObject;

import main.java.org.example.cfc.QueryBCP;

public class Organization {
	private int orgId;
	private String name;
	private int score; // range from 0 to 100
	private int rank;
	private String orgType;
	//block chain connection profile
	private final static String chainCode = "go_package8";
	private final static String fcnName = "queryByKey";
	
	public Organization(int orgId, String name, int score, int rank, String orgType) {
		super();
		this.orgId = orgId;
		this.name = name;
		this.score = score;
		this.rank = rank;
		this.orgType = orgType;
	}
	
	private void feedback(List<OrgGrade> gradeList) {

		int avg1 = (int) (gradeList.stream().mapToDouble(s -> s.grade1).sum() / gradeList.size());
		int avg2 = (int) gradeList.stream().mapToDouble(s -> s.grade2).sum() / gradeList.size();
		int avg3 = (int) gradeList.stream().mapToDouble(s -> s.grade3).sum() / gradeList.size();
		int avg4 = (int) gradeList.stream().mapToDouble(s -> s.grade4).sum() / gradeList.size();
		int avg5 = (int) gradeList.stream().mapToDouble(s -> s.grade5).sum() / gradeList.size();

		this.updateOrganization(avg1, avg2, avg3, avg4, avg5);
		
	}
	
	public static int getRankById(int id) {
		QueryBCP query = new QueryBCP();
		String[] queryArgs = new String[]{Integer.toString(id)};
		int rank = -1;
		try {
			String jsonStr = query.query(chainCode,fcnName,queryArgs);
			JSONObject json = JSONObject.parseObject(jsonStr);
			System.out.println("id: "+ id + "rank: " + json.getString("rank"));
			rank = Integer.parseInt(json.getString("rank"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rank;
	}
	
	public void updateOrganization(int avg1, int avg2, int avg3, int avg4, int avg5) {};

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
public static void main(String args[]){
	getRankById(401);
}
}