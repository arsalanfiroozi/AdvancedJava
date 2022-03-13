import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
/*      
        Course c;
        Student s;
        System.out.println("Course Spec:");
        c.PrintSpec(c);
        System.out.println("========================");
        System.out.println("Student Spec:");
        s.PrintSpec(s);
        c.AddRemStudent(s, c, "add");
        System.out.println("========================");
        System.out.println("Registered students:");
        c.PrintStudents(c);
        s.AddRemCourse(s, c, "add", 19.0);
        s.AddRemCourse(s, c, "add", 19.5);
        System.out.println("\n========================");
        System.out.println("GPA:");
        System.out.println(s.GPA(s));
*/   
        Scanner in = new Scanner(System.in);

        //Getting number of students and courses from user
        System.out.println("\nEnter number of courses: ");
        int cn = Integer.parseInt(in.nextLine());
        System.out.println("\nEnter number of students: ");
        int sn = Integer.parseInt(in.nextLine());
        System.out.println("\n");

//        int cn = 3;
//        int sn = 10;

        //List of students and courses
        Course c;
        Student s;
        ArrayList<Course>  courses  = new ArrayList<Course>();
        ArrayList<Student> students = new ArrayList<Student>();

        //Generating courses
        for(int ci=0; ci<cn; ci++){
            String name       = String.format("course%d",ci);
            String teacher    = String.format("prof. %d",ci);
            String department = String.format("%c",ci+65);
            String number     = String.format("250%02d",ci);
            String passwd     = String.format("pass%02d",ci);
            c = new Course(name, teacher, department, ci%4+1, number, passwd);
            courses.add(c);
        }

        //Generating students 
        Random rand = new Random(); 
        for(int si=0; si<sn; si++){
            String firstName  = String.format("S%02d_FN",si);
            String lastName   = String.format("S%02d_LN",si);
            String sid        = String.format("%08d",si);
            String nid        = String.format("%010d",si);
            String department = String.format("%c",si+65);
            s = new Student(firstName, lastName, sid, nid, department);
            students.add(s);

            //assigning two random courses for to each student
            int c1 = rand.nextInt(cn-1);
            int c2 = (c1 + rand.nextInt(cn-1) + 1)%cn;
            double grade1 = rand.nextDouble()*10+10; 
            double grade2 = rand.nextDouble()*10+10; 
            courses.get(c1).AddRemStudent(students.get(si), courses.get(c1), "add", grade1);
            courses.get(c2).AddRemStudent(students.get(si), courses.get(c2), "add", grade2);
            students.get(si).AddRemCourse(students.get(si), courses.get(c1), "add", grade1);
            students.get(si).AddRemCourse(students.get(si), courses.get(c2), "add", grade2);
        }

        //Printing all courses and grades
        for(int ci=0; ci<cn; ci++){
            System.out.println("");
            System.out.println("course No. "+ci);    
            courses.get(ci).PrintStudents(courses.get(ci));
            System.out.println("");
        }



        //edu!
        boolean Exit = false;
        while(!Exit){
            boolean noInput = true;
            boolean isStudent = true;
            while(noInput){
                System.out.println("Are you a student? (Y/N/exit)");
                switch(in.nextLine()){
                    case "exit":
                        in.close();
                        return;
                    case "Y":
                    case "y":
                        noInput = false;
                        break;
                    case "N":
                    case "n":
                        noInput = false;
                        isStudent = false;
                        break;
                    default: 
                }
            }

            //students log-in
            while(isStudent){
                System.out.println("\n===============================");
                System.out.println("\nEnter username or or type \"exit\" to return");
                String  user = in.nextLine();
                if(user.equals("exit")){
                    break;
                }
                int studentIndex=-1;
                for(int i=0; i<sn; i++){
                    if(students.get(i).Student_ID.equals(user)){
                        studentIndex = i;
                        break;
                    }
                }
                if(studentIndex==-1){
                    System.out.println("Invalid username");
                    continue;
                }
                System.out.println("\nEnter password: ");
                String  pass = in.nextLine();
                if(students.get(studentIndex).National_ID.equals(pass)){
                    boolean loggedIn=true;
                    System.out.println("\nSuccessful log-in.\n\n");
                    System.out.println("\n\nStudent Spec:\n");
                    students.get(studentIndex).PrintSpec(students.get(studentIndex));
                    while(loggedIn){
                        System.out.println("\nOptions:");
                        System.out.println("\t0: log out");
                        System.out.println("\t1: add a course");
                        System.out.println("\t2: remove a course");
                        System.out.println("\t3: calculate your GPA");
                        System.out.println("\t4: see courses list");

                        String cid = "0";
                        double grade = 0; 
                        int courseIndex = -1;
                        boolean flagHas=false;

                        String opt = in.nextLine();
                        switch(opt){

                            //log out
                            case "0":
                                System.out.println("Successful log-out.");
                                loggedIn = false;
                                break;

                            //add
                            case "1":
                                System.out.println("Enter the course ID to add:");
                                cid = in.nextLine();
                                grade = rand.nextDouble()*10+10; 
                                courseIndex = -1;
                                for(int i=0; i<cn; i++){
                                    if(courses.get(i).ID.equals(cid)){
                                        courseIndex = i;
                                    }
                                }
                                if(courseIndex==-1){
                                    System.out.println("invalid course number.");
                                    break;
                                } else {
                                    for (int j=0; j<students.get(studentIndex).Courses.size(); j++){
                                        if(students.get(studentIndex).Courses.get(j).ID.equals(cid)){
                                            flagHas=true;
                                            System.out.println("You already have this course. Please select another.");
                                            break;
                                        }
                                    }
                                    if(!flagHas){
                                        courses.get(courseIndex).AddRemStudent(students.get(studentIndex), courses.get(courseIndex), "add", grade);
                                        students.get(studentIndex).AddRemCourse(students.get(studentIndex), courses.get(courseIndex), "add", grade);
                                        System.out.println("\n"+courses.get(courseIndex).Name+" added successfully.");

                                        System.out.println("course No. "+courseIndex);    
                                        courses.get(courseIndex).PrintStudents(courses.get(courseIndex));
                                        System.out.println("\n\nStudent Spec:\n");
                                        students.get(studentIndex).PrintSpec(students.get(studentIndex));
                                    }
                                }
                                break;

                            //remove
                            case "2": 
                                System.out.println("Enter the course ID to remove:");
                                cid = in.nextLine();
                                courseIndex = -1;
                                for(int i=0; i<cn; i++){
                                    if(courses.get(i).ID.equals(cid)){
                                        courseIndex = i;
                                    }
                                }
                                if(courseIndex==-1){
                                    System.out.println("invalid course number.");
                                    break;
                                } else {
                                    for (int j=0; j<students.get(studentIndex).Courses.size(); j++){
                                        if(students.get(studentIndex).Courses.get(j).ID.equals(cid)){
                                            flagHas=true;
                                            break;
                                        }
                                    }
                                    if(!flagHas){
                                        System.out.println("You don't have this course, so you can't remove it.");
                                        break;
                                    } else {
                                        courses.get(courseIndex).AddRemStudent(students.get(studentIndex), courses.get(courseIndex), "rem", 0.0);
                                        students.get(studentIndex).AddRemCourse(students.get(studentIndex), courses.get(courseIndex), "rem", 0.0);
                                        System.out.println("\n"+courses.get(courseIndex).Name+" removed successfully.");

                                        System.out.println("course No. "+courseIndex);    
                                        courses.get(courseIndex).PrintStudents(courses.get(courseIndex));
                                        System.out.println("\n\nStudent Spec:\n");
                                        students.get(studentIndex).PrintSpec(students.get(studentIndex));
                                    }
                                }
                                break;

                            //GPA
                            case "3": 
                                System.out.println("Your GPA is   "
                                  +String.format("%.2f",students.get(studentIndex).GPA(students.get(studentIndex)))+"\n");
                                break;

                            //courses list
                            case "4": 
                            System.out.println("\nAvailable courses:");
                                for (Course ci: courses){
                                    System.out.println("\n");
                                    ci.PrintSpec(ci);
                                }
                                break;

                            default: 
                                break; 
                        }

                    }

                } else {
                    System.out.println("\nWrong password.\n");
                    continue;
                }
            }

            //teachers log-in
            while(!isStudent){
                boolean loggedIn = false;
                System.out.println("\n===============================");
                System.out.println("\nEnter course ID or type \"exit\" to return");
                String cid = in.nextLine();
                if(cid.equals("exit")){
                    break;
                }
                int courseIndex = -1;
                for(int i=0; i<cn; i++){
                    if(courses.get(i).ID.equals(cid)){
                        courseIndex = i;
                    }
                }
                if(courseIndex==-1){
                    System.out.println("invalid course number.");
                    continue;
                } else {
                    System.out.println("Enter course password:");
                    String pass = in.nextLine();
                    if(courses.get(courseIndex).password.equals(pass)){
                        System.out.println("\nSuccessful log-in.\n");
                        courses.get(courseIndex).PrintSpec(courses.get(courseIndex));
                        loggedIn = true;
                    } else {
                        System.out.println("\nWrong password.\n");
                        continue;
                    }

                    //after log-in
                    while(loggedIn){
                        System.out.println("\nOptions:");
                        System.out.println("\t0: log out");
                        System.out.println("\t1: modify course info");
                        System.out.println("\t2: grading students");
                        String opt = in.nextLine();
                        int Units;
                        switch(opt){

                            //log out
                            case "0":
                                System.out.println("Successful log-out.");
                                loggedIn = false;
                                break;

                            //modify
                            case "1":
                                System.out.println("\n\nEnter new details:");
                                System.out.println("Name: "+courses.get(courseIndex).Name);
                                String Name = in.nextLine();
                                System.out.println("Professor: "+courses.get(courseIndex).Prof);
                                String Professor = in.nextLine();
                                System.out.println("Department: "+courses.get(courseIndex).Department);
                                String Department = in.nextLine();
                                System.out.println("Units: "+courses.get(courseIndex).Units);
                                String Un = in.nextLine();
                                Units = Un.isEmpty() ? courses.get(courseIndex).Units : Integer.parseInt(Un);
                                System.out.println("ID: "+courses.get(courseIndex).ID); 
                                String ID = in.nextLine();
                                courses.get(courseIndex).modify(Name, Professor, Department, Units, ID);
                                break; 

                            //grading
                            case "2":   
                                System.out.println("\nCurrent grades:");    
                                courses.get(courseIndex).PrintStudents(courses.get(courseIndex));
                                System.out.println("\n\nSetting new grades:");    
                                courses.get(courseIndex).setGrades();
                                break;
                            
                            default: 
                        }
                    }
                }
            }
        }
        in.close();
    }
}
