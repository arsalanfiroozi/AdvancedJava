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
    public void PrintSpec(Student obj) throws Exception {
        System.out.println("\tName: "+obj.Name);
        System.out.println("\tFamilyname: "+obj.Familyname);
        System.out.println("\tStudent_ID: "+obj.Student_ID);
        System.out.println("\tNational_ID: "+obj.National_ID);
        System.out.println("\tField: "+obj.Field);
        System.out.println("\tGrades: ");
        for(int i=0; i<obj.Courses.size();i++){
            System.out.println("\t\t"+Courses.get(i).Name+": "+String.format("%.2f",Grades.get(i)));
        }
        System.out.println("\tGDP: "+ String.format("%.2f",GPA(this))+"\n");
    }
    public void AddRemCourse(Student s, Course obj_c, String Stat, Double grade) throws Exception {        
        if(Stat == "add"){
            s.Courses.add(obj_c);
            s.Grades.add(grade);
        } else {
            s.Grades.remove(s.Courses.indexOf(obj_c));
            s.Courses.remove(obj_c);
        }
    }
    public Double GPA(Student s) throws Exception {
        Double gdp=0.0;
        Integer sunit=0;
        for (int i = 0; i < s.Courses.size(); i++){
            sunit = sunit + s.Courses.get(i).Units;
            gdp = gdp + s.Courses.get(i).Units * s.Grades.get(i);
        }
        return gdp/sunit;
    }
    public void setGrade(Course c1, Double grade) throws Exception {
        int index = Courses.indexOf(c1);   
        Grades.set(index, grade);
    }
}
