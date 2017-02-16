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
	
	public Employee(){
		
	}
	
	public Employee(String name, String lastName, String cf) {
		this.name = name;
		this.lastName = lastName;
		this.cf = cf;
	}
	
	
	
	public Employee(String name, String lastName, String cf, String mese, Integer anno, Float cifra) {
		this.name = name;
		this.lastName = lastName;
		this.cf = cf;
		this.mese = mese;
		this.anno = anno;
		this.cifra = cifra;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public String getMese() {
			return mese;
	}

	public void setMese(String mese) {
		this.mese = mese;
	}

	public Integer getAnno() {
		return anno;
	}

	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	public Float getCifra() {
		return cifra;
	}

	public void setCifra(Float cifra) {
		this.cifra = cifra;
	}
	
	public String toStringDB(){
		return "'"+name+"',"+"'"+lastName+"',"+"'"+cf+"','"+mese+"',"+anno+","+cifra;
	}

	@Override
	public String toString() {
		return "Employee [name=" + name + ", lastName=" + lastName + ", cf=" + cf + ", mese=" + mese + ", anno=" + anno
				+ ", cifra=" + cifra + "]";
	}
	
	
	
	
}
