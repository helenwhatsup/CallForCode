import java.util.List;

public class OrgGrade {
	Organization grader;
	//Organization receiver;
	int grade1;
	int grade2;
	int grade3;
	int grade4;
	int grade5;
	
	public OrgGrade(Organization grader, Organization receiver, int grade1, int grade2, int grade3, int grade4,
			int grade5) {
		super();
		this.grader = grader;
		//this.receiver = receiver;
		this.grade1 = grade1;
		this.grade2 = grade2;
		this.grade3 = grade3;
		this.grade4 = grade4;
		this.grade5 = grade5;
	}
}
