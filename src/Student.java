public class Student {
    private String studentName;
    private String studentID;
    private Module module;

    public Student(String studentID,String studentName){
        this.studentName=studentName;
        this.studentID=studentID;
        this.module=new Module();
    }


    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentID() {
        return studentID;
    }

    public Module getModule() {
        return module;
    }
}
