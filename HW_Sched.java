package Ass2;// Annie Kuo

import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
    int deadline;
    int completiontime;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline, int completiontime) {
		this.number = number;
		this.weight = weight;
        this.deadline = deadline;
        this.completiontime = completiontime;
	}
	
	
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * Return -1 if a1 > a2
	 * Return 1 if a1 < a2
	 * Return 0 if a1 = a2 
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		// order by weight/hour
		double wph1 = ((double) a1.weight) / a1.completiontime;
		double wph2 = ((double) a2.weight) / a2.completiontime;

		if (wph1 > wph2) {
			// a1 has priority
			return -1;
		} else if (wph1 < wph2) {
			// a2 has priority
			return 1;
		} else {

			// order by deadlines
			if (a1.deadline < a2.deadline) {
				// a1 has priority
				return -1;
			} else if (a1.deadline > a2.deadline) {
				// a2 has priority
				return 1;
			} else {

				// order by completion time
				if (a1.completiontime < a2.completiontime) {
					// a1 has priority
					return -1;
				} else if (a1.completiontime > a2.completiontime){
					// a2 has priority
					return 1;
				} else {
					// same level of priority
					return 0;
				}
			}
		}
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
    int lastDeadline = 0;
    double grade = 0.0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int[] completiontimes, int size) throws Exception {
        if(size==0){
            throw new Exception("There is no assignment.");
        }
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i], completiontimes[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m =size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public ArrayList<Integer> SelectAssignments() {
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());

		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th timeslot in the homeworkPlan is empty

		ArrayList<Integer> homeworkPlan = new ArrayList<>();
		for (int i=0; i < lastDeadline; ++i) {
			homeworkPlan.add(-1);
		}

		int hwCompleted = 0;
		for (int i=0; i < lastDeadline; hwCompleted++) {
			Assignment next = Assignments.get(hwCompleted);
			// add asmt to the plan if the deadline has not passed yet
			// and if it won't be submitted 10 hours late (-100%)
			if (i < next.deadline && (i + next.completiontime) - next.deadline < 10) {
				for (int j = 0; j < next.completiontime; j++) {
					if (homeworkPlan.size() > i+j) {
						homeworkPlan.set(i + j, next.number);
					} else {
						homeworkPlan.add(next.number);
					}
				}
				// update grade
				double newGrade = next.weight;
				if (i + next.completiontime > next.deadline) {
					newGrade -= 0.1*((i + next.completiontime) - next.deadline)*newGrade;
				}
				if (newGrade>0) {
					this.grade += newGrade;
				}
			}

			i += next.completiontime;
		}

		return homeworkPlan;
	}

}
	



