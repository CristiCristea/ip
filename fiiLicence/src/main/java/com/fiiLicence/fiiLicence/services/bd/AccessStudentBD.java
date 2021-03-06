package com.fiiLicence.fiiLicence.services.bd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class AccessStudentBD extends  AccessBD{
	
	AccessStudentBD( Connection conexiune, UserBD user )
	{
		this.conexiune = conexiune;
		this.tip  = "Access_Student";
		this.user = user;
	}
	
	public List<IntrareDetaliiLicente> selectDetaliiLicente(){
		List<IntrareDetaliiLicente> rezultat = new ArrayList<IntrareDetaliiLicente>();
		try{
			
			Statement statement=conexiune.createStatement();
			ResultSet result   =statement.executeQuery("Select * from detalii_licente"); 
			while(result.next()){
				IntrareDetaliiLicente intrare = new IntrareDetaliiLicente();
				intrare.setId(result.getInt(1));
				intrare.setIdComisie(result.getInt(2));
				intrare.setNota1Oral(result.getInt(3));
				intrare.setNota1Proiect(result.getInt(4));
				intrare.setNota2Oral(result.getInt(5));
				intrare.setNota2Proiect(result.getInt(6));
				intrare.setNota3Oral(result.getInt(7));
				intrare.setNota3Proiect(result.getInt(8));
				intrare.setNota4Oral(result.getInt(9));
				intrare.setNota4Proiect(result.getInt(10));
				intrare.setNota5Oral(result.getInt(11));
				intrare.setNota5Proiect(result.getInt(12));
				intrare.setDataOraSustinerii(result.getTimestamp(13));
				
				rezultat.add(intrare);
			}
			
			return rezultat;
		}
		catch( Exception e ){
			System.out.println("Exceptie la selectDetaliiLicente: "+e.getMessage());
			return null;
		}
	}
	
	
	public List<IntrareLicente> selectLicente(){
		List<IntrareLicente> rezultat = new ArrayList<IntrareLicente>();
		try{
			
			Statement statement=conexiune.createStatement();
			ResultSet result   =statement.executeQuery("Select * from licente"); 
			while(result.next()){
				IntrareLicente intrare = new IntrareLicente();
				intrare.setId(result.getInt(1));
				intrare.setTitlu(result.getString(2));
				intrare.setIdProfesor(result.getInt(3));
				intrare.setIdStudent(result.getInt(4));
				intrare.setMaterialeLicenta(result.getString(5));
				intrare.setIdSesiune(result.getInt(6));
				intrare.setTipLucrare(result.getString(7));
				
				rezultat.add(intrare);
			}
			
			return rezultat;
		}
		catch( Exception e ){
			System.out.println("Exceptie la selectLicente: "+e.getMessage());
			return null;
		}
	}
	
	public List<IntrareSesiuni> selectSesiuni(){
		List<IntrareSesiuni> rezultat = new ArrayList<IntrareSesiuni>();
		try{
			
			Statement statement=conexiune.createStatement();
			ResultSet result   =statement.executeQuery("Select * from sesiuni"); 
			while(result.next()){
				IntrareSesiuni intrare = new IntrareSesiuni();
				intrare.setId(result.getInt(1));
				intrare.setInceputSesiune(result.getTimestamp(2));
				intrare.setSfarsitSesiune(result.getTimestamp(3));
				rezultat.add(intrare);
			}
			return rezultat;
		}
		catch( Exception e ){
			System.out.println("Exceptie la selectSesiuni: "+e.getMessage());
			return null;
		}	
	}
	
	public List<IntrareMesaje> selectMesaje(){
		List<IntrareMesaje> rezultat = new ArrayList<IntrareMesaje>();
		try{
			Statement statement=conexiune.createStatement();
			ResultSet result   =statement.executeQuery("Select * from mesaje"); 
			while(result.next()){
				IntrareMesaje intrare = new IntrareMesaje();
				intrare.setId(result.getInt(1));
				intrare.setIdEmitator(result.getInt(2));
				intrare.setIdDestinatar(result.getInt(3));
				intrare.setMesaj(result.getString(4));
				rezultat.add(intrare);
			}
			return rezultat;
		}
		catch( Exception e ){
			System.out.println("Exceptie la selectMesaje: "+e.getMessage());
			return null;
		}
		
	}

	public List<IntrareStudenti> selectStudenti(){
		List<IntrareStudenti> rezultat = new ArrayList<IntrareStudenti>();
		try{
			Statement statement=conexiune.createStatement();
			ResultSet result   =statement.executeQuery("Select * from studenti"); 
			while(result.next()){
				IntrareStudenti intrare = new IntrareStudenti();
				intrare.setId(result.getInt(1));
				intrare.setIdCont(result.getInt(2));
				intrare.setNrMatricol(result.getString(3));
				intrare.setNume(result.getString(4));
				intrare.setPrenume(result.getString(5));
				intrare.setId_comisie(result.getInt(6));
				intrare.setIdSesiune(result.getInt(7));
				rezultat.add(intrare);
			}
			return rezultat;
		}
		catch( Exception e ){
			System.out.println("Exceptie la selectStudenti:"+e.getMessage());
			return null;
		}		
	}

	public List<IntrareProfesori> selectProfesori(){
		List<IntrareProfesori> rezultat = new ArrayList<IntrareProfesori>();
		try{
			Statement statement=conexiune.createStatement();
			ResultSet result   =statement.executeQuery("Select * from profesori"); 
			while(result.next()){
				IntrareProfesori intrare = new IntrareProfesori();
				intrare.setId(result.getInt(1));
				intrare.setIdCont(result.getInt(2));
				intrare.setNume(result.getString(3));
				intrare.setPrenume(result.getString(4));
				intrare.setGradDidactic(result.getString(5));
				intrare.setFunctieComisie(result.getString(6));
				rezultat.add(intrare);
			}
			return rezultat;
		}
		catch( Exception e ){
			System.out.println("Exceptie la selectProfesori :"+e.getMessage());
			return null;
		}		
	}
	
	public List<IntrareEvaluari> selectEvaluari(){
		List<IntrareEvaluari> rezultat = new ArrayList<IntrareEvaluari>();
		try{
			
			Statement statement=conexiune.createStatement();
			ResultSet result   =statement.executeQuery("Select * from evaluari"); 
			while(result.next()){
				IntrareEvaluari intrare = new IntrareEvaluari();
				intrare.setId(result.getInt(1));
				intrare.setIdSesiune(result.getInt(2));
				intrare.setIdComisie(result.getInt(3));
				intrare.setInceputEvaluare(result.getTimestamp(4));
				intrare.setSfarsitEvaluare(result.getTimestamp(5));
				intrare.setSala(result.getString(6));
				rezultat.add(intrare);
			}
			return rezultat;
		}
		catch( Exception e ){
			System.out.println("Exceptie la selectEvaluari: "+e.getMessage());
			return null;
		}
	}

	public int updateStudent( IntrareStudenti intrare ){
		if(intrare.getId()==0) return -1;
		String apel=" Update studenti set ID_CONT = ? , NR_MATRICOL = ? , NUME = ? ,  PRENUME=? , ID_COMISIE = ? , ID_SESIUNE=? where id = ? ";
		try{
			
			Statement  stmt = conexiune.createStatement();
			ResultSet  rs   = stmt.executeQuery("Select Count(*) from studenti where id ="+intrare.getId());
			rs.next();
			if( rs.getInt(1) == 0 ) {
				System.out.println("Intrare Inexistenta");
				return -1;
			}
			
			PreparedStatement statement = conexiune.prepareStatement(apel);
			statement.setInt(1, intrare.getIdCont());
			statement.setString(2, intrare.getNrMatricol());
			statement.setString(3, intrare.getNume());
			statement.setString(4, intrare.getPrenume());
			statement.setInt(5, intrare.getIdSesiune());
			statement.setInt(6, intrare.getId_comisie());
			statement.setInt(7, intrare.getId());
			statement.executeUpdate();	
			return 0;
		}
		catch( Exception e ){
			System.out.println("Exceptie la updateStudent" + e.getMessage());
			return -7;
		}	
	}
	
	public int updateLicenta( IntrareLicente intrare){
		if(intrare.getId()==0) return -1;
		String apel=" Update licente set titlu = ?, id_profesor = ?, id_student = ?, materiale_licenta = ?, id_sesiune = ?, tip = ? where id = ? ";
		try{			
			Statement  stmt = conexiune.createStatement();
			ResultSet  rs   = stmt.executeQuery("Select Count(*) from licente where id ="+intrare.getId());
			rs.next();
			if( rs.getInt(1) == 0 ) {
				System.out.println("Intrare Inexistenta");
				return -1;
			}
			
			PreparedStatement statement = conexiune.prepareStatement(apel);
			statement.setString(1, intrare.getTitlu());
			statement.setInt(2, intrare.getIdProfesor());
			statement.setInt(3, intrare.getIdStudent());
			statement.setString(4, intrare.getMaterialeLicenta());
			statement.setInt(5, intrare.getIdSesiune());
			statement.setString(6, intrare.getTipLucrare());
			statement.setInt(7, intrare.getId());
			statement.executeUpdate();
			conexiune.commit();
			
			return 0;
		}
		catch( Exception e ){
			System.out.println("Exceptie la updateLicenta" + e.getMessage());
			return -7;
		}	
		
	}

	public int insertDetaliiLicenta( IntrareDetaliiLicente intrare ){
		String apel = new String();	
		try{
			
			if(intrare.getId()==0){
				apel = " Insert into Detalii_licente Values(Detalii_SEQ.NEXTVAL, ?, ? ,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement statement = conexiune.prepareStatement(apel);
				statement.setInt(1, intrare.getIdComisie());
				statement.setInt(2, intrare.getNota1Oral());
				statement.setInt(3, intrare.getNota1Proiect());
				statement.setInt(4, intrare.getNota2Oral());
				statement.setInt(5, intrare.getNota2Proiect());
				statement.setInt(6, intrare.getNota3Oral());
				statement.setInt(7, intrare.getNota3Proiect());
				statement.setInt(8, intrare.getNota4Oral());
				statement.setInt(9, intrare.getNota4Proiect());
				statement.setInt(10,  intrare.getNota5Oral());
				statement.setInt(11, intrare.getNota5Proiect());
				statement.setTimestamp(12, intrare.getDataOraSustinerii());
				statement.executeUpdate();
				conexiune.commit();
				
				Statement  stmt = conexiune.createStatement();
				ResultSet  rs   = stmt.executeQuery("Select Detalii_SEQ.CURRVAL from dual");
				rs.next();
				intrare.setId(rs.getInt(1));
				
				return 0;
			}
			else{
				
				Statement  stmt = conexiune.createStatement();
				ResultSet  rs   = stmt.executeQuery("Select Count(*) from detalii_licente where id = "+intrare.getId());
				rs.next();
				if( rs.getInt(1) > 0 ) {
					System.out.println("Intrare Existenta. Update?");
					return -1;
				}
				
				apel = " Insert into detalii_licente Values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement statement = conexiune.prepareStatement(apel);
				statement.setInt(1, intrare.getId());
				statement.setInt(2, intrare.getIdComisie());
				statement.setInt(3, intrare.getNota1Oral());
				statement.setInt(4, intrare.getNota1Proiect());
				statement.setInt(5, intrare.getNota2Oral());
				statement.setInt(6, intrare.getNota2Proiect());
				statement.setInt(7, intrare.getNota3Oral());
				statement.setInt(8, intrare.getNota3Proiect());
				statement.setInt(9, intrare.getNota4Oral());
				statement.setInt(10, intrare.getNota4Proiect());
				statement.setInt(11,  intrare.getNota5Oral());
				statement.setInt(12, intrare.getNota5Proiect());
				statement.setTimestamp(13, intrare.getDataOraSustinerii());
				statement.executeUpdate();
				conexiune.commit();
				
				return 0;
			}
		}
		catch( Exception e ){
			System.out.println("Exceptie la insertDetaliiLicente: "+e.getMessage());
			return -7;
		}
	}
    
	public int insertLicenta( IntrareLicente intrare ){
		String apel;	
		try{
			if(intrare.getId()==0){
				apel = " Insert into Licente Values(Licente_SEQ.NEXTVAL, ?, ? ,?, ?, ?, ?)";
				PreparedStatement statement = conexiune.prepareStatement(apel);
				statement.setString(1, intrare.getTitlu());
				statement.setInt(2, intrare.getIdProfesor());
				statement.setInt(3, intrare.getIdStudent());
				statement.setString(4, intrare.getMaterialeLicenta());
				statement.setInt(5, intrare.getIdSesiune());
				statement.setString(6, intrare.getTipLucrare());
				statement.executeUpdate();
				conexiune.commit();
				
				Statement  stmt = conexiune.createStatement();
				ResultSet  rs   = stmt.executeQuery("Select LICENTE_SEQ.CURRVAL from dual");
				rs.next();
				intrare.setId(rs.getInt(1));
				
				return 0;
			}
			else{
				
				Statement  stmt = conexiune.createStatement();
				ResultSet  rs   = stmt.executeQuery("Select Count(*) from LICENTE where id ="+intrare.getId());
				rs.next();
				if( rs.getInt(1) > 0 ) {
					System.out.println("Intrare Existenta. Update?");
					return -1;
				}
				
				apel = " Insert into LICENTE Values(?, ?, ?, ?, ?, ?, ?)";
				PreparedStatement statement = conexiune.prepareStatement(apel);
				statement.setInt(1,intrare.getId());
				statement.setString(2, intrare.getTitlu());
				statement.setInt(3, intrare.getIdProfesor());
				statement.setInt(4, intrare.getIdStudent());
				statement.setString(5, intrare.getMaterialeLicenta());
				statement.setInt(6, intrare.getIdSesiune());
				statement.setString(7, intrare.getTipLucrare());
				statement.executeUpdate();
				conexiune.commit();
				
				return 0;
			}
		}
		catch( Exception e ){
			System.out.println("Exceptie la insertLicenta: "+e.getMessage());
			return -7;
		}
	}

	public int insertMesaj( IntrareMesaje intrare ){
		String apel;	
		try{
			
			if(intrare.getId()==0){
				apel = " Insert into Mesaje Values(Mesaje_SEQ.NEXTVAL, ?, ? ,?)";
				PreparedStatement statement = conexiune.prepareStatement(apel);
				statement.setInt(1, intrare.getIdEmitator());
				statement.setInt(2, intrare.getIdDestinatar());
				statement.setString(3, intrare.getMesaj());
				statement.executeUpdate();
				
				Statement  stmt = conexiune.createStatement();
				ResultSet  rs   = stmt.executeQuery("Select MESAJE_SEQ.CURRVAL from dual");
				rs.next();
				intrare.setId(rs.getInt(1));
				
				return 0;
			}
			else{
				
				Statement  stmt = conexiune.createStatement();
				ResultSet  rs   = stmt.executeQuery("Select Count(*) from MESAJE where id ="+intrare.getId());
				rs.next();
				if( rs.getInt(1) > 0 ) {
					System.out.println("Intrare Existenta. Update?");
					return -1;
				}
				
				apel = " Insert into Mesaje Values(?, ?, ? ,?)";
				PreparedStatement statement = conexiune.prepareStatement(apel);
				statement.setInt(1,intrare.getId());
				statement.setInt(2, intrare.getIdEmitator());
				statement.setInt(3, intrare.getIdDestinatar());
				statement.setString(4, intrare.getMesaj());
				statement.executeUpdate();
				
				return 0;
			}
		}
		catch( Exception e ){
			System.out.println("Exceptie la insertMesaj: "+e.getMessage());
			return -7;
		}
	
}
}