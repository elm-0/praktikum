package delivery.food_delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import delivery.User;
import delivery.Admin; 		
import delivery.Dish;
import delivery.Employee;
import delivery.ShoppingCart;

import java.util.Scanner;

import javax.smartcardio.CardTerminal;


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

		scanner.close();

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
		Scanner scanner = new Scanner(System.in);
		
		ShoppingCart cart = new ShoppingCart(); 
		System.out.println("User menu open");
		//izpisva parametrite na obektite na klasa dish i nqkak trqbva da se vurje funkciq za dobavqne v kolichka pri user input

		int numOfItemsInMenu = 5; //placeholder, vzima se ot bd
		for (int i = 0; i > numOfItemsInMenu; i++){
			 // getName, getPrice, getAvailable na vseki artikul ot list na menuto
		}
	

		//tova nadolu moje da go otdelq ot tozi metod i da si ima otdelen metod order, a openMenu da e samo za printirane na menuto, no zasega neka e tuk
		System.out.println("Please choose what to do next:");
		System.out.println("1 - Choose an item to order");
		System.out.println("2 - Remove an item from your order");
		System.out.println("3 - Finish your order");
		
		//Dish pizza = new FoodItem("Pizza", 12.99);    //neshto podobno trqbva da dobavq neshtata v menuto, nz kak i kude shte stava
        //Dish burger = new FoodItem("Burger", 8.49);
        //Dish cola = new FoodItem("Cola", 2.50);


		String menuChoiceInput = scanner.nextLine();
		switch (Integer.parseInt(menuChoiceInput)) {
			case 1 -> {
				//cart.addItem(item); //raboti s dadeniq item ot menuto, koito e izbran chrez id/ime ili drug input
				cart.getTotal();
                }
			case 2 -> {
				//cart.removeItem(item); //raboti s dadeniq item ot menuto, koito e izbran chrez id/ime ili drug input
				cart.getTotal();
                }
			case 3 -> {
				cart.getTotal();
				System.out.println("Order sent! Your order will arrive shortly!");
				
				// broq poruchani hrani trqbva da se mahat ot broq v menuto ot bazata danni
				//ne sum dobavil kod v klasa Dish za broi, moje da se naloji vuobshte da nqma takuv feature
                }
			default -> {
                            System.out.println("Wrong option input, please try again");
                            break;
                }
		}

		scanner.close();
	}

	public static void openAdminPanel(){
		System.out.println("Admin panel open");
	}

	public static void openEmployeeMenu(){
		System.out.println("Employee menu open");
	}

}



