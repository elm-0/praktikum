package delivery.food_delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import delivery.User;
//import delivery.Admin; 		//dava greshka na importite po nqkakva prichina, no trqq da sa pravilni
//import delivery.Dish;
//import delivery.Employee;

import java.util.Scanner;


@SpringBootApplication
public class FoodDeliveryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodDeliveryApplication.class, args);

		while(!login()){}
		

		//pri dobavqne na baza danni toq kod shte stane bezpolezen i ne trqbva da go ima
		//inache tozi kod trqbva da izbira kakuv obekt da suzdade v zavisimost ot tova kakuv e lognatiq chovek, ama neshto ne raboti kato horata i ne mi haresva kak sum go napisal
		/* 
		int asd = 1;
		if(asd == 1){
		User currentUser = new User(); }
		else if(asd == 2){
		Admin currentUser = new Admin();
		}
		else if(asd == 3){
		Employee currentUser = new Employee();
		}
		else{
			System.out.println("Error");
		}
		*/

		User currentUser = new User();


		//bi trqbvalo da se suzdava edin user, koito kato izberem dali e admin/employee/user da go suzdade kato takuv i da se otvori specialnoto menu za nego

		
		if(currentUser instanceof  User){
			openMenu();
		}
		/* 
		else if (currentUser instanceof Admin){
			openAdminPanel();
		}
		else if(currentUser instanceof Employee){
			openEmployeeMenu();
		}
		*/

	}

	public static boolean login(){
		Scanner scanner = new Scanner(System.in); 

		//usera da izbira dali e user, employee ili admin
		//po-dobriq variant e da e vurzano s bazata danni i da ne se izbira ot polzvashtiq programata, obache dokato ne potrugne drugoto nqma da se zanimavam s tova
		
		System.out.println("Input username");
		String username = scanner.nextLine();
		System.out.println("Input password");
		String password = scanner.nextLine();

		//da se dobavi proverka za user i pass s baza danni dali sushtestvuvat
		//registraciq nqmam ideq kak shte stava i dali vuobshte shte se pravi

		boolean loginSuccessful = true; //tva neshto sum go slojil za da e usable programata sega, kato se dobavq security i t.n. da se mahne i da se zamesti s nqkva validaciq kato horata

		if (loginSuccessful){
			System.out.println("Welcome, " + username);
			return true;
		}
		else{
			System.out.println("Login unsuccessful. Please try again");
			return false;
		}
	}

	public static void openMenu(){
		System.out.println("User menu open");
		//izpisva parametrite na obektite na klasa dish i nqkak trqbva da se vurje funkciq za dobavqne v kolichka pri user input
	}

	public static void openAdminPanel(){
		System.out.println("Admin panel open");
	}

	public static void openEmployeeMenu(){
		System.out.println("Employee menu open");
	}

}



