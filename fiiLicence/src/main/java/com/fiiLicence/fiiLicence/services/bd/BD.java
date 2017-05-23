package com.fiiLicence.fiiLicence.services.bd;

import com.fiiLicence.fiiLicence.models.response.GradeResponse;
import com.fiiLicence.fiiLicence.models.response.ProfsFromCommitte;
import com.fiiLicence.fiiLicence.models.response.StudentGrade;
import com.fiiLicence.fiiLicence.models.response.StudentGuidedListResponse;
import com.fiiLicence.fiiLicence.services.DatabaseService;
import com.fiiLicence.fiiLicence.services.DatabaseServiceImpl;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BD {

    private boolean connected = false;
    private Connection conexiune;
    @SuppressWarnings("unused")
    private String domeniu = "";
    private AccessBD access;
    private boolean loged = false;

    private void createAccess(String username) {

        String apel = " { ? = call get_type( ? ) }";
        int rezultat;
        int idCont;


        try {
            Statement stmt = conexiune.createStatement();
            ResultSet rs = stmt.executeQuery("Select ID from CONTURI where username = '" + username + "'");
            rs.next();
            idCont = rs.getInt(1);
            CallableStatement statement = conexiune.prepareCall(apel);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, username);
            statement.execute();
            rezultat = statement.getInt(1);

            if (rezultat == 0) {
                UserBD utilizator = new UserBD();
                utilizator.setId(0);
                utilizator.setTip("Admin");
                utilizator.setUsername("Admin");
                this.access = new AccessAdminBD(conexiune, utilizator);
                this.access.setIdCont(idCont);
            } else if (rezultat == 1) {
                UserBD utilizator = new UserBD();
                PreparedStatement statement2 = conexiune.prepareStatement("Select STUDENTI.ID FROM STUDENTI JOIN CONTURI on STUDENTI.ID_CONT=CONTURI.ID WHERE CONTURI.USERNAME=?");
                statement2.setString(1, username);
                ResultSet result = statement2.executeQuery();
                result.next();
                utilizator.setId(result.getInt(1));
                utilizator.setTip("Student");
                utilizator.setUsername(username);

                this.access = new AccessAdminBD(conexiune, utilizator);
                this.access.setIdCont(idCont);

            } else if (rezultat == 2) {
                UserBD utilizator = new UserBD();
                PreparedStatement statement2 = conexiune.prepareStatement("Select PROFESORI.ID FROM PROFESORI JOIN CONTURI on PROFESORI.ID_CONT=CONTURI.ID WHERE CONTURI.USERNAME=?");
                statement2.setString(1, username);
                ResultSet result = statement2.executeQuery();
                result.next();
                utilizator.setId(result.getInt(1));
                utilizator.setTip("Profesor");
                utilizator.setUsername(username);
                this.access = new AccessAdminBD(conexiune, utilizator);
                this.access.setIdCont(idCont);

            } else {
                UserBD utilizator = new UserBD();
                PreparedStatement statement2 = conexiune.prepareStatement("Select PROFESORI.ID FROM PROFESORI JOIN CONTURI on PROFESORI.ID_CONT=CONTURI.ID WHERE CONTURI.USERNAME=?");
                statement2.setString(1, username);
                ResultSet result = statement2.executeQuery();
                result.next();
                utilizator.setId(result.getInt(1));
                utilizator.setTip("Secretar");
                utilizator.setUsername(username);
                this.access = new AccessAdminBD(conexiune, utilizator);
                this.access.setIdCont(idCont);

            }

        } catch (Exception e) {
            System.out.println("Exceptie la createAccess: " + e.getMessage());
        }

    }

    @SuppressWarnings("unused")
    private int sendEmail(String adresa, String mesaj) {

        return 0;
    }

    public BD() {
        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.conexiune = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "Licente", "ADMIN");
            this.connected = true;

        } catch (Exception e) {

            System.out.println("Exceptie la conectare: " + e.getMessage());
            this.connected = false;

        }
    }

    public boolean isLoged() {
        return loged;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setDomeniu(String domeniu) {
        this.domeniu = domeniu;
    }

    public AccessBD getAccess() {
        if (loged == false || connected == false)
            return null;
        else
            return access;
    }

    public int login(String username, String hashparola) {
        String apel = "{ ? = call login( ?, ? ) }";
        int rezultat;
        try {

            CallableStatement statement = conexiune.prepareCall(apel);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, username);
            statement.setString(3, hashparola);
            statement.execute();
            rezultat = statement.getInt(1);

            if (rezultat == 0) {
                createAccess(username);
                loged = true;
            } else
                loged = false;

            return rezultat;
        } catch (Exception e) {
            System.out.println("Exceptie la login: " + e.getMessage());
            return -7;
        }

    }

    public IntrareConturi getContByToken(String token) {
        IntrareConturi cont = new IntrareConturi();
        try {
            Statement stmt = conexiune.createStatement();
            ResultSet rs = stmt.executeQuery("Select Count(*) from conturi where token='" + token + "'");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("Intrare Inexistenta");
                return null;
            }

            Statement statement = conexiune.createStatement();
            ResultSet result = statement.executeQuery("Select * from conturi where token='" + token + "'");
            result.next();
            cont.setId(result.getInt(1));
            cont.setUsername(result.getString(2));
            cont.setHashparola(result.getString(3));
            cont.setEmail(result.getString(4));
            cont.setTipUtilizator(result.getString(5));
            cont.setStatus(result.getInt(6));
            cont.setCodActivare(result.getString(7));
            cont.setToken(result.getString(8));
            return cont;
        } catch (Exception e) {
            System.out.println("Exceptie la getContByToken: " + e.getMessage());
            return null;
        }

    }

    public int setTokenByIdCont(int id, String token) {
        try {
            Statement stmt = conexiune.createStatement();
            ResultSet rs = stmt.executeQuery("Select Count(*) from conturi where id=" + id);
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("Intrare Inexistenta");
                return -1;
            }

            Statement statement = conexiune.createStatement();
            statement.executeUpdate("UPDATE CONTURI SET Token = '" + token + "' Where id=" + id);
            return 0;
        } catch (Exception e) {
            System.out.println("Exceptie la setTokenByIdCont: " + e.getMessage());
            return 0;
        }
    }

    public int setTokenByIdCont(String email, String token) {
        try {
            Statement stmt = conexiune.createStatement();
            ResultSet rs = stmt.executeQuery("Select Count(*) from conturi where email= '" + email + "'");
            rs.next();
            if (rs.getInt(1) == 0) {
                System.out.println("Intrare Inexistenta");
                return -1;
            }

            Statement statement = conexiune.createStatement();
            statement.executeUpdate("UPDATE CONTURI SET Token = '" + token + "' Where email='" + email + "'");
            return 0;
        } catch (Exception e) {
            System.out.println("Exceptie la setTokenByIdCont: " + e.getMessage());
            return 0;
        }
    }


    public int verificare(String hashcod) {
        String apel = "{ ? = call verificare( ? ) }";
        int rezultat;
        try {

            CallableStatement statement = conexiune.prepareCall(apel);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, hashcod);
            statement.execute();
            rezultat = statement.getInt(1);
            return rezultat;
        } catch (Exception e) {
            System.out.println("Exceptie la login: " + e.getMessage());
            return -7;
        }

    }

    public int inregistrare_stud(String email, String hashparola) {
        Random random;
        String hashcod;
        boolean unic;
        int rezultat = 0;

        try {
            String username = email.split("@")[0];
            if (!email.split("@")[1].equals("info.uaic.ro") || username.split("\\.")[0].equals(username))
                return -1;
        } catch (Exception e) {
            System.out.println("Email invalid inregistrare_stud: " + e.getMessage());
            return -1;
        }

        String apel = "SELECT COUNT(ID) FROM CONTURI WHERE COD_ACTIVARE='";

        do {
            random = new Random();
            unic = true;
            hashcod = "";
            for (int i = 0; i < 20; i++)
                hashcod = hashcod + random.nextInt(9);
            try {
                Statement statement = conexiune.createStatement();
                ResultSet resultSet = statement.executeQuery(apel + hashcod + "'");

                resultSet.next();
                if (resultSet.getInt(1) != 0)
                    unic = false;
            } catch (Exception e) {
                System.out.println("Exceptie la generare cod random la inregistrare_stud: " + e.getMessage());
                return -7;
            }
        } while (!unic);

        apel = "{ ? = call inregistrare_stud( ?, ?, ? ) }";
        try {
            CallableStatement statement = conexiune.prepareCall(apel);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, email.split("@")[0]);
            statement.setString(3, hashparola);
            statement.setString(4, hashcod);
            statement.execute();
            rezultat = statement.getInt(1);

            MailSender mailSender = new MailSender(email,hashcod);


            return rezultat;
        } catch (Exception e) {
            System.out.println("Exceptie la inregistrare_stud: " + e.getMessage());
            return -7;
        }
    }

    public int inregistrare_prof(String username, String hashparola) {
        String apel = " { ? = call inregistrare_prof( ? , ? ) }";
        int rezultat;
        try {
            CallableStatement statement = conexiune.prepareCall(apel);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, username);
            statement.setString(3, hashparola);
            statement.execute();
            rezultat = statement.getInt(1);
            return rezultat;
        } catch (Exception e) {
            System.out.println("Exceptie inregistrare_prof: " + e.getMessage());
            return -7;
        }
    }

    private String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }

    public String getHas(String email, String password) {
        email.concat(password);
        return MD5(email);
    }

    public IntrareStudenti getStudentByID(int idProf) {
        IntrareStudenti intrare = new IntrareStudenti();
        try {
            Statement statement = conexiune.createStatement();
            ResultSet result = statement.executeQuery("Select * from studenti");
            while (result.next()) {
                intrare.setId(result.getInt(1));
                intrare.setIdCont(result.getInt(2));
                intrare.setNrMatricol(result.getString(3));
                intrare.setNume(result.getString(4));
                intrare.setPrenume(result.getString(5));
                intrare.setId_comisie(result.getInt(6));
                intrare.setIdSesiune(result.getInt(7));
            }
            return intrare;
        } catch (Exception e) {
            System.out.println("Exceptie la selectProfesori :" + e.getMessage());
            return null;
        }

    }

    public IntrareStudenti selectStudentByIdCont(int idCont) {
        String apel = " Select * from studenti where id_cont = ? ";
        try {
            PreparedStatement statement = conexiune.prepareStatement(apel);
            statement.setInt(1, idCont);
            ResultSet result = statement.executeQuery();
            IntrareStudenti intrare = new IntrareStudenti();


            while (result.next()) {
                intrare.setId(result.getInt(1));
                intrare.setIdCont(result.getInt(2));
                intrare.setNrMatricol(result.getString(3));
                intrare.setNume(result.getString(4));
                intrare.setPrenume(result.getString(5));
                intrare.setId_comisie(result.getInt(6));
                intrare.setIdSesiune(result.getInt(7));

            }
            return intrare;
        } catch (Exception e) {
            System.out.println("Exceptie la selectStudenti:" + e.getMessage());
            return null;
        }
    }


    public StudentGrade getAllGrade(int idStudent) {
        StudentGrade student = new StudentGrade();
        String apel = "SELECT id_profesor, id_comisie, nota_1_oral, nota_2_oral, nota_3_oral, nota_4_oral_dizertatie, nota_5_oral_coordonator, nota_1_PROIECT, NOTA_2_PROIECT, NOTA_3_PROIECT, NOTA_4_PROIECT_DIZERTATIE, NOTA_5_PROIECT_COORDONATOR,tip FROM licente JOIN DETALII_LICENTE ON LICENTE.ID = DETALII_LICENTE.ID where LICENTE.ID_STUDENT = ?";
        try {
            PreparedStatement statement = conexiune.prepareStatement(apel);
            statement.setInt(1, idStudent);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                student.setIdProfesor(result.getInt(1));
                student.setIdComisie(result.getInt(2));
                student.setNota1Oral(result.getInt(3));
                student.setNota2Oral(result.getInt(4));
                student.setNota3Oral(result.getInt(5));
                student.setNota4Oral(result.getInt(6));
                student.setNota5Oral(result.getInt(7));
                student.setNota1Project(result.getInt(8));
                student.setNota2Project(result.getInt(9));
                student.setNota3Project(result.getInt(10));
                student.setNota4Project(result.getInt(11));
                student.setNota5Project(result.getInt(12));
                student.setTipLicenta(result.getString(13));
            }
            return student;
        } catch (Exception e) {
            System.out.println("Exceptie la obtinerea notelor: " + e.getMessage());
            return null;
        }
    }

    public ProfsFromCommitte getProfFromCommitte(int idComisie) {
        ProfsFromCommitte profesori = new ProfsFromCommitte();
        String apel = "select ID_PROF1,ID_PROF2,ID_PROF3,ID_PROF4_DIZERTATIE from comisii where id = ?";
        try {
            PreparedStatement statement = conexiune.prepareStatement(apel);
            statement.setInt(1, idComisie);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                profesori.setProfesor1(result.getInt(1));
                profesori.setProfesor2(result.getInt(2));
                profesori.setProfesor3(result.getInt(3));
                profesori.setProfesor4(result.getInt(4));
            }
            return profesori;
        } catch (Exception e) {
            System.out.println("Exceptie la obtinerea Profesorilor Din Comisie: " + e.getMessage());
            return null;
        }
    }
    //15.functie : luam toti studentii in functie de un profesor

    public List<StudentGuidedListResponse> getStudentsOfATeacher(int idTeacher) {
        List<StudentGuidedListResponse> studentOfATeacher = new ArrayList<>();
        String apel = "SELECT nota_1_oral, nota_2_oral, nota_3_oral, nota_4_oral_dizertatie,nota_5_oral_coordonator, nota_1_PROIECT, NOTA_2_PROIECT, NOTA_3_PROIECT, NOTA_4_PROIECT_DIZERTATIE, NOTA_5_PROIECT_COORDONATOR, STUDENTI.ID, STUDENTI.NUME, STUDENTI.PRENUME FROM licente JOIN DETALII_LICENTE ON LICENTE.ID = DETALII_LICENTE.ID join STUDENTI on LICENTE.ID_STUDENT = STUDENTI.ID WHERE LICENTE.ID_PROFESOR = ?";
        try {
            PreparedStatement statement = conexiune.prepareStatement(apel);
            statement.setInt(1, idTeacher);
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                StudentGuidedListResponse student = new StudentGuidedListResponse();
                DatabaseServiceImpl databaseService = new DatabaseServiceImpl();
                student.setNota1oral(result.getInt(1));
                student.setNota2oral(result.getInt(2));
                student.setNota3oral(result.getInt(3));
                student.setNota4oral(result.getInt(4));
                student.setNota5oral(result.getInt(5));
                student.setNota1project(result.getInt(6));
                student.setNota2project(result.getInt(7));
                student.setNota3project(result.getInt(8));
                student.setNota4project(result.getInt(9));
                student.setNota5project(result.getInt(10));
                student.setIdStudent(result.getInt(11));
                student.setNumeStudent(result.getString(12));
                student.setPrenumStudent(result.getString(13));
                GradeResponse grade = databaseService.getStudentGrade(student.getIdStudent());
                student.setNotaFinala(grade.getGrade());
                studentOfATeacher.add(student);
            }
            return studentOfATeacher;
        } catch (Exception e) {
            System.out.println("Exceptie la obtinerea studentilor: " + e.getMessage());
            return null;
        }

        //inca nu e functionala functia asta, nu imi dau seama de ce nu-mi intra in while...

    }


    //16.functie: un profesor poate sa adauge un student

    public int getIdStudentByName(String numeStudent, String prenumeStudent) {
        String apelSelect = "Select id from studenti where nume = ? and prenume = ?";
        int idStudent = 0;
        try {
            PreparedStatement statementSelect = conexiune.prepareStatement(apelSelect);
            statementSelect.setString(1, numeStudent);
            statementSelect.setString(2, prenumeStudent);
            ResultSet result = statementSelect.executeQuery();
            while (result.next()) {
                idStudent = result.getInt(1);
            }
            return idStudent;
        } catch (Exception e) {
            System.out.println("Exceptie la obtinerea studentilor: " + e.getMessage());
            return 0;
        }
    }

    public boolean addStudent(int idTeacher, String numeStudent, String prenumeStudent) {
        String apel = " Update licente set ID_PROFESOR = ? where  ID_STUDENT = ? ";
        int idStudent = getIdStudentByName(numeStudent, prenumeStudent);
        try {
            PreparedStatement statement = conexiune.prepareStatement(apel);
            statement.setInt(1, idTeacher);
            statement.setInt(2, idStudent);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Exceptie la obtinerea studentilor: " + e.getMessage());
            return false;
        }
    }


    //17.functie:un profesor poate scoate un student din lista sa

    public boolean removeStudent(int idTeacher, int idStudent) {
        String apel = " Update licente set ID_PROFESOR = null where ID_Profesor = ? and id_student = ? ";
        try {

            PreparedStatement statement = conexiune.prepareStatement(apel);
            statement.setInt(1, idTeacher);
            statement.setInt(2, idStudent);
            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.out.println("Exceptie la obtinerea studentilor: " + e.getMessage());
            return false;
        }
    }

    //18. functie: se poate edita data de examinare a unei comisii
    public boolean editExaminationDate(int idComisie, String beginDate, String endDate) {
        String apel = "update  evaluari e set e.INCEPUT_EVALUARE=to_date(?,'dd-mm-yyyy'),e.sfarsit_evaluare=to_date(?,'dd-mm-yyyy')where e.id_comisie=?";
        try {

            PreparedStatement statement = conexiune.prepareStatement(apel);
            statement.setString(1, beginDate);
            statement.setString(2, endDate);
            statement.setInt(3, idComisie);
            statement.executeQuery();

            return true;
        } catch (Exception e) {
            System.out.println("Exceptie la obtinerea studentilor: " + e.getMessage());
            return false;
        }
    }


    //15 inca nu e functionala, iar 19 si 20 le pun cand e gata si baza de date
}

