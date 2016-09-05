package datamanagement;

public class Cgctl {

	
	Cgui cgui;
	String cuc = null;
	Integer currentStudentID = null;
	boolean changed = false;

	public Cgctl() {
	}

	public void execute() {
		cgui = new Cgui(this);
		cgui.setState1(false);

		cgui.setState2(false);
		cgui.setState3(false);
		cgui.setState4(false);
		cgui.setState5(false);
		cgui.setState6(false);
		cgui.Refresh3();

		ListUnitsCTL luCTL = new ListUnitsCTL();
		luCTL.listUnits(cgui);
		cgui.setVisible(true);
		cgui.setState1(true);
	}

	public void unitSelected(String code) {

		if (code.equals("NONE"))
			cgui.setState2(false);
		else {
			ListStudentsCTL lsCTL = new ListStudentsCTL();
			lsCTL.listStudents(cgui, code);
			cuc = code;
			cgui.setState2(true);
		}
		cgui.setState3(false);
	}

	public void studentSelected(Integer id) {
		currentStudentID = id;
		if (currentStudentID.intValue() == 0) {
			cgui.Refresh3();
			cgui.setState3(false);
			cgui.setState4(false);
			cgui.setState5(false);
			cgui.setState6(false);
		}

		else {
			IStudent s = StudentManager.get().getStudent(id);

			IStudentUnitRecord r = s.getUnitRecord(cuc);

			cgui.setRecord(r);
			cgui.setState3(true);
			cgui.setState4(true);
			cgui.setState5(false);
			cgui.setState6(false);
			changed = false;

		}
	}

	public String checkGrade(float f, float g, float h) {
		IUnit u = UnitManager.UM().getUnit(cuc);
		String s = u.getGrade(f, g, h);
		cgui.setState4(true);
		cgui.setState5(false);
		if (changed) {
			cgui.setState6(true);
		}
		return s;
	}

	public void enableChangeMarks() {
		cgui.setState4(false);
		cgui.setState6(false);
		cgui.setState5(true);
		changed = true;
	}

	public void saveGrade(float asg1, float asg2, float exam) {

		@SuppressWarnings("unused")
		IUnit u = UnitManager.UM().getUnit(cuc);
		IStudent s = StudentManager.get().getStudent(currentStudentID);

		IStudentUnitRecord r = s.getUnitRecord(cuc);
		r.setAsg1(asg1);
		r.setAsg2(asg2);
		r.setExam(exam);
		StudentUnitRecordManager.instance().saveRecord(r);
		cgui.setState4(true);
		cgui.setState5(false);
		cgui.setState6(false);
	}
}
