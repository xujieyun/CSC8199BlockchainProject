package org.jboss.quickstarts.wfk.customer;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

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
        @NamedQuery(name = Customer.FIND_ALL, query = "SELECT c FROM Customer c ORDER BY c.lastName ASC, c.firstName ASC"),
        @NamedQuery(name = Customer.FIND_BY_EMAIL, query = "SELECT c FROM Customer c WHERE c.email = :email"),
        @NamedQuery(name = Customer.FIND_BY_USERNAME, query = "SELECT c FROM Customer c WHERE c.userName = :username")
})
@XmlRootElement
@Table(name = "customer", uniqueConstraints = @UniqueConstraint(columnNames = "email"))

public class Customer implements Serializable {
    /** Default value included to remove warning. Remove or modify at will. **/
    private static final long serialVersionUID = 2L;

    public static final String FIND_ALL = "Customer.findAll";
    public static final String FIND_BY_EMAIL = "Customer.findByEmail";
    public static final String FIND_BY_USERNAME = "Customer.findByUsername";

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
//    @Pattern(regexp = "[A-Za-z-*']+", message = "Please use a firstName without numbers or specials")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @Size(min = 1, max = 25)
//    @Pattern(regexp = "[A-Za-z-*']+", message = "Please use a lastName without numbers or specials")
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @NotEmpty
    @Email(message = "The email address must be in the format of name@domain.com")
    private String email;

    @NotNull
    @Size(min = 1, max = 25)
    @Pattern(regexp = "[A-Za-z-0-9']+", message = "Please use a password without numbers or specials")
    @Column(name = "password")
    private String password;

	@NotNull
	@Size(min = 1, max = 25)
//	@Pattern(regexp = "[A-Za-z-*']+", message = "Please choose DOCTOR or PATIENT")
	@Column(name = "Role")
	private String Role;

	@NotNull
	@Size(min = 1)
	@Column(name = "privateKey1")
	private String privateKey1;

	@NotNull
	@Size(min = 1)
	@Column(name = "privateKey2")
	private String privateKey2;

	@NotNull
	@Size(min = 1)
	@Column(name = "privateKey3")
	private String privateKey3;

	@NotNull
	@Size(min = 1)
	@Column(name = "privateKey4")
	private String privateKey4;

	@NotNull
	@Size(min = 1)
	@Column(name = "publicKey")
	private String publicKey;

	@NotNull
	@Size(min = 1)
	@Column(name = "messageEn1")
	private String messageEn1;

	@NotNull
	@Size(min = 1)
	@Column(name = "messageEn2")
	private String messageEn2;

/*    @NotNull
    @Pattern(regexp = "^\\([2-9][0-8][0-9]\\)\\s?[0-9]{3}\\-[0-9]{4}$")
//    @Pattern(regexp = "^\\([0]{\\)\\s?[0-9]{10}$")
    @Column(name = "phone_number")
    private String phoneNumber;

    @NotNull
    @Past(message = "Birthdates can not be in the future. Please choose one from the past")
    @Column(name = "birth_date")
    @Temporal(TemporalType.DATE)
    private Date birthDate;*/

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public static String getFindAll() {
		return FIND_ALL;
	}

	public static String getFindByEmail() {
		return FIND_BY_EMAIL;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRole() {
		return Role;
	}

	public void setRole(String role) {
		Role = role;
	}

	public String getPrivateKey1() {
		return privateKey1;
	}

	public void setPrivateKey1(String privateKey1) {
		this.privateKey1 = privateKey1;
	}

	public String getPrivateKey2() {
		return privateKey2;
	}

	public void setPrivateKey2(String privateKey2) {
		this.privateKey2 = privateKey2;
	}

	public String getPrivateKey3() {
		return privateKey3;
	}

	public void setPrivateKey3(String privateKey3) {
		this.privateKey3 = privateKey3;
	}

	public String getPrivateKey4() {
		return privateKey4;
	}

	public void setPrivateKey4(String privateKey4) {
		this.privateKey4 = privateKey4;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getMessageEn1() {
		return messageEn1;
	}

	public void setMessageEn1(String messageEn1) {
		this.messageEn1 = messageEn1;
	}

	public String getMessageEn2() {
		return messageEn2;
	}

	public void setMessageEn2(String messageEn2) {
		this.messageEn2 = messageEn2;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return Objects.equals(id, customer.id) &&
				Objects.equals(userName, customer.userName) &&
				Objects.equals(firstName, customer.firstName) &&
				Objects.equals(lastName, customer.lastName) &&
				Objects.equals(email, customer.email) &&
				Objects.equals(password, customer.password) &&
				Objects.equals(Role, customer.Role) &&
				Objects.equals(privateKey1, customer.privateKey1) &&
				Objects.equals(privateKey2, customer.privateKey2) &&
				Objects.equals(privateKey3, customer.privateKey3) &&
				Objects.equals(privateKey4, customer.privateKey4) &&
				Objects.equals(publicKey, customer.publicKey) &&
				Objects.equals(messageEn1, customer.messageEn1) &&
				Objects.equals(messageEn2, customer.messageEn2);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userName, firstName, lastName, email, password, Role, privateKey1, privateKey2, privateKey3, privateKey4, publicKey, messageEn1, messageEn2);
	}

	/*@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Customer customer = (Customer) o;
		return Objects.equals(id, customer.id) &&
				Objects.equals(userName, customer.userName) &&
				Objects.equals(firstName, customer.firstName) &&
				Objects.equals(lastName, customer.lastName) &&
				Objects.equals(email, customer.email) &&
				Objects.equals(password, customer.password) &&
				Objects.equals(Role, customer.Role);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, userName, firstName, lastName, email, password, Role);
	}*/




	

    
}
