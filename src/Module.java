public class Module {

    private int[] moduleMarks=new int[3];

    public int[] getModuleMarks() {
        return moduleMarks;
    }

    public void setModuleMarks1(int Marks1) {
        this.moduleMarks[0] = Marks1;
    }
    public void setModuleMarks2(int Marks2) {
        this.moduleMarks[1] = Marks2;
    }
    public void setModuleMarks3(int Marks3) {
        this.moduleMarks[2] = Marks3;
    }

    public int total(){
        int total=moduleMarks[0]+moduleMarks[1]+moduleMarks[2];
        return total;
    }

    public float average(){
        int totalMarks=total();
        float average=(float)totalMarks/3;
        return average;
    }

    public String grade(){
        float average=average();

        if(average>=80){
            return "Distinction";
        }else if(average>=70){
            return "Merit";
        }else if(average>=40){
            return "Pass";
        }else{
            return "Fail";
        }
    }

}