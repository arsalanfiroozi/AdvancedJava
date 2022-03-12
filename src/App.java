public class App {
    public static void main(String[] args) throws Exception {
        Course c = new Course("AdvancedJAVA", "Prof. Matin Hashemi", "EE", 1, "25537");
        Student s = new Student("Arsalan", "Firoozi", "97102225", "0521183456", "EE");
        System.out.println("Course Spec:");
        c.PrintSpec(c);
        System.out.println("========================");
        System.out.println("Student Spec:");
        s.PrintSpec(s);
        c.AddRemStudent(s, c, "add");
        System.out.println("========================");
        System.out.println("Registered Students:");
        c.PrintStudents(c);
        s.AddRemCourse(s, c, "add", 19.0);
        s.AddRemCourse(s, c, "add", 19.5);
        System.out.println("\n========================");
        System.out.println("GDP:");
        System.out.println(s.GDP(s));
    }
}
