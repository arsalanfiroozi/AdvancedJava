import java.util.ArrayList;

public class Course {
    String Name = "Unkown";
    String Prof = "Unknowny";
    String Department = "0";
    String Units = "0";
    String ID = "0";
    ArrayList<Student> Students = new ArrayList<Student>();
    
    public Course(String Name_val,String Prof_val,String Department_val,String Units_val,String ID_val){
        Name = Name_val;
        Prof = Prof_val;
        Department = Department_val;
        Units = Units_val;
        ID = ID_val;
        System.out.println("Course Created!");
    }
    public static void PrintSpec(Course obj) throws Exception {
        System.out.println("Name: "+obj.Name);
        System.out.println("Professor: "+obj.Prof);
        System.out.println("Department: "+obj.Department);
        System.out.println("Units: "+obj.Units);
        System.out.println("ID: "+obj.ID);
    }
    public static void PrintStudents(Course obj) throws Exception {        
        System.out.println("Number of Students: "+obj.Students.size());
        for (int i = 0; i < obj.Students.size(); i++)
            System.out.print("Student "+i+" => "+"Name: "+obj.Students.get(i).Name + " "+obj.Students.get(i).Familyname);
    }
    public static void AddRemStudent(Student s, Course obj_c, String Stat) throws Exception {        
        if(Stat == "add")
            obj_c.Students.add(s);
        else
            obj_c.Students.remove(s);
    }
}
