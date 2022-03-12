import java.util.ArrayList;

public class Student {
    String Name = "Unkown";
    String Familyname = "Unknowny";
    String National_ID = "0";
    String Student_ID = "0";
    String Field = "EE";
    ArrayList<Course> Courses = new ArrayList<Course>();
    ArrayList<Double> Grades = new ArrayList<Double>();
    
    public Student(String Name_val, String Familyname_val, String StudentID_val, String NationalID_val, String Field_val){
        Name = Name_val;
        Familyname = Familyname_val;
        Student_ID = StudentID_val;
        National_ID = NationalID_val;
        Field = Field_val;
        System.out.println("Student Created!");
    }
    public static void PrintSpec(Student obj) throws Exception {
        System.out.println("Name: "+obj.Name);
        System.out.println("Familyname: "+obj.Familyname);
        System.out.println("Student_ID: "+obj.Student_ID);
        System.out.println("National_ID: "+obj.National_ID);
        System.out.println("Field: "+obj.Field);
    }
    public static void AddRemCourse(Student s, Course obj_c, String Stat, Double grade) throws Exception {        
        if(Stat == "add"){
            s.Courses.add(obj_c);
            s.Grades.add(grade);
        } else {
            s.Grades.remove(s.Courses.indexOf(obj_c));
            s.Courses.remove(obj_c);
        }
    }
    public static Double GDP(Student s) throws Exception {
        Double gdp=0.0;
        Integer sunit=0;
        for (int i = 0; i < s.Courses.size(); i++){
            sunit = sunit + s.Courses.get(i).Units;
            gdp = gdp + s.Courses.get(i).Units * s.Grades.get(i);
        }
        return gdp/sunit;
    }
}
