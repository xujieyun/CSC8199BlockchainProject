package org.jboss.quickstarts.wfk.record;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * <p>This is a the Domain object. The Contact class represents how contact resources are represented in the application
 * database.</p>
 *
 * <p>The class also specifies how a contacts are retrieved from the database (with @NamedQueries), and acceptable values
 * for Contact fields (with @NotNull, @Pattern etc...)<p/>
 *
 * @author Joshua Wilson
 */
/*
 * The @NamedQueries included here are for searching against the table that reflects this object.  This is the most efficient
 * form of query in JPA though is it more error prone due to the syntax being in a String.  This makes it harder to debug.
 */
@Entity
@NamedQueries({
        @NamedQuery(name = Record.FIND_ALL, query = "SELECT c FROM Record c ORDER BY c.lastName ASC, c.firstName ASC"),
//        @NamedQuery(name = Record.FIND_BY_EMAIL, query = "SELECT c FROM Record c WHERE c.email = :email"),
        @NamedQuery(name = Record.FIND_BY_USERNAME, query = "SELECT c FROM Record c WHERE c.userName = :username")
})
@XmlRootElement
//@Table(name = "record", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Table(name = "record")

public class Record implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 2L;

    public static final String FIND_ALL = "Record.findAll";
//    public static final String FIND_BY_EMAIL = "Record.findByEmail";
    public static final String FIND_BY_USERNAME = "Record.findByUsername";

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    
    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-z-']+", message = "Please use a UserName without numbers or specials")
    @Column(name = "user_name")
    private String userName;
    
    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-z-']+", message = "Please use a firstName without numbers or specials")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-z-']+", message = "Please use a lastName without numbers or specials")
    @Column(name = "last_name")
    private String lastName;

/*    @NotNull
    @NotEmpty
    @Email(message = "The email address must be in the format of name@domain.com")
    private String email;*/

/*    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-z-0-9']+", message = "Please use a password without numbers or specials")
    @Column(name = "password")
    private String password;*/

	@NotNull
	@Size(min = 1, max = 25)
	@Pattern(regexp = "[A-Za-z']+", message = "Please use a Symptom without numbers or specials")
	@Column(name = "Symptom")
	private String Symptom;

    @NotNull
    @Pattern(regexp = "(19|20)[0-9][0-9]-(0[1-9]|1[0-2])-[0-9][0-9]", message = "Please a date")
//    @Pattern(regexp = "^\\([0]{\\)\\s?[0-9]{10}$")
    @Column(name = "Date")
    private String Date;

 /*   @NotNull
    @Past(message = "Birthdates can not be in the future. Please choose one from the past")
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;*/
	 @NotNull
	 @Size(min = 1, max = 25)
	 @Pattern(regexp = "[A-Za-z-']+", message = "Please use a Status without numbers or specials")
	 @Column(name = "Status")
	 private String Status;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getFindAll() {
		return FIND_ALL;
	}



	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSymptom() {
		return Symptom;
	}

	public void setSymptom(String symptom) {
		Symptom = symptom;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Record record = (Record) o;
		return Objects.equals(id, record.id) &&
				Objects.equals(userName, record.userName) &&
				Objects.equals(firstName, record.firstName) &&
				Objects.equals(lastName, record.lastName) &&
				Objects.equals(Symptom, record.Symptom) &&
				Objects.equals(Date, record.Date) &&
				Objects.equals(Status, record.Status);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userName, firstName, lastName, Symptom, Date, Status);
	}
/*@Column(name = "state")
    private String state;*/

//    @OneToMany(mappedBy = "record", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    /*@OneToMany(mappedBy="record", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<Booking> bookings;

    public List<Booking> getBookings() {
		return bookings;
	}

	public void setBookings(List<Booking> bookings) {
		this.bookings = bookings;
	}*/

	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Record other = (Record) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}*/


	

    
}
