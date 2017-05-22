package com.fiiLicence.fiiLicence.services.bd;

import com.fiiLicence.fiiLicence.models.response.*;
import com.fiiLicence.fiiLicence.services.DatabaseService;
import com.fiiLicence.fiiLicence.services.DatabaseServiceImpl;
import org.hibernate.engine.jdbc.connections.spi.DataSourceBasedMultiTenantConnectionProviderImpl;

import javax.xml.crypto.Data;
import java.io.PrintStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        //aici ai exemplu cum functioneaza Bd ...
        BD bd = new BD();
        System.out.println(bd.isConnected());

        System.out.println(bd.inregistrare_stud("marian.gica@info.uaic.ro", "parola"));
        System.out.println(bd.verificare("73251266048443412760"));


        List<StudentGuidedListResponse> studenti = new ArrayList<StudentGuidedListResponse>();
        DatabaseServiceImpl databaseService = new DatabaseServiceImpl();
        studenti =  databaseService.getStudentGuided(1);
        GradeResponse grade = databaseService.getStudentGrade(1);
        System.out.println(grade);
         for(StudentGuidedListResponse index : studenti){
           System.out.println(index);
        }
        /*String email = "marian.gica@info.uaic.ro";
        BD b = new BD();
        String prenumeStudent = email.substring(email.indexOf('.')+ 1,email.indexOf('@'));
        String numeStudent = email.substring(0,email.indexOf('.'));
        System.out.println(prenumeStudent+"  "+numeStudent);
        //b.addStudent(1, numeStudent, prenumeStudent);*/
       databaseService.insertStudentToListProf(22,"marian.gica@info.uaic.ro");
        
    }

}