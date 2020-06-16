package technou.com.utilities;

import java.io.Serializable;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import technou.com.model.Address;
import technou.com.model.Cart;
import technou.com.model.Customer;
import technou.com.model.Role;
import technou.com.model.Wishlist;
import technou.com.model.product.Laptop;
import technou.com.model.product.Printer;


public class DatabaseManager {

	protected static SessionFactory sessionFactory;
	
	private static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

	protected static void setup() {
		sessionFactory = HibernateAnnotationUtil.getSessionFactory();
	}

	protected static void exit() {
		sessionFactory.close();
	}

	protected static void createCustomer(String firstname, String lastname, String gender, String phone, 
			String email, GregorianCalendar date_of_birt, String username, String password, int active, Set<Role> roles,
			String city, String state, String street, int number, String pinCode) {

		Customer customer = new Customer();

		customer.setFirstname(firstname);
		customer.setLastname(lastname);
		customer.setGender(gender);
		customer.setPhone(phone);
		customer.setEmail(email);
		customer.setDate_of_birth(date_of_birt);
		customer.setUsername(username);
		customer.setPassword(password);
		customer.setActive(active);
		customer.setRoles(roles);
				
		Address address = new Address();
				
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setNumber(number);
		address.setPincode(pinCode);
							
		customer.setAddress(address);
		customer.setAcceptTerms(true);
		customer.setPassword_confirmation(password);
		
		
		Cart cart = new Cart();
		customer.setCart(cart);
		cart.setCustomer(customer);
		
		Wishlist wishlist = new Wishlist();
		customer.setWishlist(wishlist);
		wishlist.setCustomer(customer);
				
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Serializable customerId = session.save(customer);
		
		roles
			.forEach(role-> {
				role.setCustomerId((int)customerId);
				session.save(role);
			});
		
		
		cart.setCustomerId((int)customerId);
		wishlist.setCustomerId((int)customerId);
		
		session.save(cart);
		session.save(wishlist);


		session.getTransaction().commit();
		session.close();	
	}
	
	
	protected static int createPrinter(String productName, String productDescription, 
			int quantityInStock, double buyPrice, int sale, String imageUrl) {

		Printer prinetr = new Printer();
		
		prinetr.setProductName(productName);
		prinetr.setProductDescription(productDescription);
		prinetr.setImageUrl(imageUrl);
		prinetr.setQuantityInStock(quantityInStock);
		prinetr.setBuyPrice(buyPrice);
		prinetr.setSale(sale);
		
		
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		
		Serializable mainProductId = session.save(prinetr);

		
		session.getTransaction().commit();
		session.close();	
		
		return (int)mainProductId;
	}
	
	protected static int createLaptop(String productName, String productDescription, 
							int quantityInStock, double buyPrice, int sale, String imageUrl) {

		Laptop laptop = new Laptop();

		laptop.setProductName(productName);
		laptop.setProductDescription(productDescription);
		laptop.setImageUrl(imageUrl);
		laptop.setQuantityInStock(quantityInStock);
		laptop.setBuyPrice(buyPrice);
		laptop.setSale(sale);
	
		Session session = sessionFactory.openSession();
		session.beginTransaction();
	
		Serializable mainProductId = session.save(laptop);

		session.getTransaction().commit();
		session.close();
		
		return (int)mainProductId;
	}
	
	
	/**
	 * To create some users with their authorities at the beginning 
	 */
	public static void createSampleCustomers() {
		
			Role roleUser = new Role();
			roleUser.setRole("USER");
			HashSet<Role> roleUserSet = new HashSet<Role>();
			roleUserSet.add(roleUser);
			
			createCustomer(
					"Marry", "Doll", "Female", "555222444", "mary@yahoo.com",
					new GregorianCalendar(2000,11,14), "mary", passwordEncoder.encode("mary123"), 1, roleUserSet,
					"Golling", "Salzburg", "Mark", 50, "5440"
					);
			
			createCustomer(
					"Noura", "Bensaber", "Female", "555111999", "noura@company.com",
					new GregorianCalendar(1995,6,10), "noura", passwordEncoder.encode("noura123"), 1, roleUserSet,
					"Hallein", "Salzburg", "Straße", 12, "5000"
					);
			
			createCustomer(
					"Joe", "Smith", "Male", "555777555", "joe@gmail.com",
					new GregorianCalendar(1993,3,30), "joe", passwordEncoder.encode("joe123"), 1, roleUserSet,
					"zell am see", "Salzburg", "Markt", 18, "6000"
					);
			
			createCustomer(
					"Eric", "Ben", "Male", "555444333", "eric@yahoo.com",
					new GregorianCalendar(1990,10,4), "eric", passwordEncoder.encode("eric123"), 1, roleUserSet,
					 "Elsbethen", "Salzburg", "flohMarkt", 220, "7000"
					);
			
			createCustomer(
					"Linda", "Spar", "Female", "555999666", "linda@yahoo.com",
					new GregorianCalendar(1998,8,8), "linda", passwordEncoder.encode("linda123"), 1, roleUserSet,
					 "Elsbethen", "Salzburg", "Maria", 75, "7000"
					);

	}
	
	
	/**
	 * To create some products
	 */
	public static void createSamplePrinter() {
		
		createPrinter("CANON", "CANON Multifunktionsdrucker PIXMA TS3350 schwarz, Tinte (3771C006)", 
				24, 49, 10, "https://www.stern.de/vergleich/wp-content/uploads/2019/01/canon-maxify-mb2750-4-in-1-farbtintenstrahl-multifunktionsgeraet-schwarz.jpg");
		
		createPrinter("HP LaserJet", "HP LaserJet Pro P1102w Drucker Software- und Treiber", 77, 89, 0,			
				"https://ssl-product-images.www8-hp.com/digmedialib/prodimg/lowres/c02994825.png");
		
		createPrinter("HP DeskJet", "HP Multifunktionsdrucker DeskJet 3764, weiß/rot (T8X27B)", 45, 129, 25,
				"https://m.media-amazon.com/images/S/aplus-media/vc/214d8969-6011-4cac-a2de-aa7d864e743d._CR0,0,2928,1200_PT0_SX1464__.jpg");
		
		createPrinter("Epsone", "Epson Epson Printers - All-in-One, Wide Format, Photo & Label Printers ...", 15, 99, 0,
				"https://mediaserver.goepson.com/ImConvServlet/imconv/542041b37c7ea56d2f0bf609154c32ad9c53cfdd/original?use=banner&assetDescr=product-categories-homepage-carousel-printers-et2750");
	
	}
	
	public static void createSampleLaptop() {
		
		createLaptop("Siemens Fujitsu", "Fujitsu Lifebook S751 | 14 | i5-2520M", 20, 800, 10,
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQnkaP55i7R3Y8bhdGtEZaeUA6L5w7wC3O8HuLZlF3tggEvj_0a9sztwCu5jA&usqp=CAc");
		
		createLaptop("Acer Aspire", "3 A315-56-5048 Notebook", 35, 550, 0,
						"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQgAO1Ja0ASGcVoqG4dCdQCgsH20q0xKFv0rF8m-hPPfhgR32fXKASCCYL1riKEhJqFZDkmm6E&usqp=CAc");
		
		createLaptop("Dell", "XPS 13\"-4K-Laptop der 10. Generation", 42, 480, 25,
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcTrRS86o4kO_D5TzEkXK0VTMqCfrpR4o6mTlQSfag4arwRvDyYbEb2s8kTbXw&usqp=CAc");
		
		createLaptop("HP", "HP EliteBook 2570P | i7-3520M | 12.5", 27, 799, 0,
				"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQzCdYjqtyFblw6hGvar1bn7m38cX3SxdzVo1nDQ6NImie0l29ZTf89tTX9Q1ZMEDe_KEnTP50C&usqp=CAc");
		
		createLaptop("Sony", "Sony Vaio EJ2E1E/W 44 cm Laptop weiß", 33, 1200, 37,
						"https://images-na.ssl-images-amazon.com/images/I/61H3uDzNHCL._AC_SX466_.jpg");

	}
	
	/**
	 * Activate this code only to create some data inside the database
	 */
	/*public static void main(String[] args) {

		setup();

		createSampleCustomers();
		
		createSampleLaptop();
		
		createSamplePrinter();

		exit();
	}*/
	//----------------------------------------------------------------------------------
}
