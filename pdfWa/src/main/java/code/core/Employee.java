package code.core;



/**
 * A class that holds the information read from the database
 * @author Mandeep	Kaler
 *
 */
public class Employee {
	
	
	private String name = null;
	private String lastName = null;
	private String cf = null;
	private String mese = null;
	private Integer anno = null;
	private Float cifra	= null;
	
	protected Employee(){
		
	}
	
	protected Employee(String name, String lastName, String cf) {
		this.name = name;
		this.lastName = lastName;
		this.cf = cf;
	}
	
	
	
	protected Employee(String name, String lastName, String cf, String mese, Integer anno, Float cifra) {
		this.name = name;
		this.lastName = lastName;
		this.cf = cf;
		this.mese = mese;
		this.anno = anno;
		this.cifra = cifra;
	}

	protected String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
	}
	

	protected String getLastName() {
		return lastName;
	}

	protected void setLastName(String lastName) {
		this.lastName = lastName;
	}

	protected String getCf() {
		return cf;
	}

	protected void setCf(String cf) {
		this.cf = cf;
	}

	protected String getMese() {
			return mese;
	}

	protected void setMese(String mese) {
		this.mese = mese;
	}

	protected Integer getAnno() {
		return anno;
	}

	protected void setAnno(Integer anno) {
		this.anno = anno;
	}

	protected Float getCifra() {
		return cifra;
	}

	protected void setCifra(Float cifra) {
		this.cifra = cifra;
	}
	
	protected String toStringDB(){
		return "'"+name+"',"+"'"+lastName+"',"+"'"+cf+"','"+mese+"',"+anno+","+cifra;
	}
	
	
	
	
}
