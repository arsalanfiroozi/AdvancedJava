import java.util.*;

public class Course {
    String Name = "Unkown";
    String Prof = "Unknowny";
    String Department = "0";
    Integer Units = 0;
    String ID = "0";
    ArrayList<Student> Students = new ArrayList<Student>();
    ArrayList<Double> grades = new ArrayList<Double>();
    String password = "pass";
    Scanner in = new Scanner(System.in);
    double newGrade = 0.0;

    
    public Course(String Name_val,String Prof_val,String Department_val,Integer Units_val,String ID_val, String pass_val){
        Name = Name_val;
        Prof = Prof_val;
        Department = Department_val;
        Units = Units_val;
        ID = ID_val;
        System.out.println("Course Created!");
        password = pass_val;
    }
    public void PrintSpec(Course obj) throws Exception {
        System.out.println("\tName: "+obj.Name);
        System.out.println("\tProfessor: "+obj.Prof);
        System.out.println("\tDepartment: "+obj.Department);
        System.out.println("\tUnits: "+obj.Units);
        System.out.println("\tID: "+obj.ID);
    }
    public void PrintStudents(Course obj) throws Exception {        
        System.out.println("Number of Students: "+obj.Students.size());
        for (int i = 0; i < obj.Students.size(); i++)
            System.out.print("Student "+i+" => "+"Name: "+obj.Students.get(i).Name +
             " "+obj.Students.get(i).Familyname+"  grade:"+String.format("%.2f",grades.get(i))+"\n");
    }
    public void AddRemStudent(Student s, Course obj_c, String Stat, double gr) throws Exception {        
        if(Stat == "add"){
            obj_c.Students.add(s);
            grades.add(gr);
        }
        else {
            grades.remove(obj_c.Students.indexOf(s));
            obj_c.Students.remove(s);
        }
    }
    public void modify(String newName, String newProf, String newDepartment,Integer newUnits,String newID) throws Exception{
        if(!newName.isEmpty())       Name = newName;
        if(!newProf.isEmpty())       Prof = newProf;
        if(!newDepartment.isEmpty()) Department = newDepartment;
        if(!newID.isEmpty())         ID = newID;
        Units = newUnits;
        System.out.println("Course modified successfuly! new parameters: ");
        PrintSpec(this);
    }
    public void setGrades() throws Exception{
        for(int i=0; i<Students.size(); i++){
            System.out.print("\nStudent "+i+" => "+"Name: "+Students.get(i).Name +
             " "+Students.get(i).Familyname+"  grade:"+String.format("%.2f",grades.get(i))+"\n");
            System.out.print("Enter new grade or leave blank to keep previous grade: ");
            String ng = in.nextLine();
            if(!ng.isEmpty()){
                newGrade = Double.parseDouble(ng);
                grades.set(i, newGrade);
                Students.get(i).setGrade(this, newGrade);
            }
        }
        System.out.print("New grades: ");
        PrintStudents(this);
    }
}
