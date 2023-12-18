

/**
 * Сourse: java core 

 * @Author Student Oksana Askerova

 */

import java.util.Random;
import java.util.Scanner;


class Account
{

	static Scanner input = new Scanner(System.in);		//доступный для всех методов


	//Fields for bank account.
	private String accHolderName;				//Содержит имя владельца учетной записи.
	private int accHolderPhoneNo;				//Содержит номер телефона владельца учетной записи.
	private String accHolderIFSCCode;			//Уникальный код для каждого владельца учетной записи, сгенерированный случайным образом.
	private double balanceAmount;				//хранит сумму, внесенную держателем.
	private double withdrawAmount;				//Сумма, подлежащая снятию владельцем.
	private boolean accountExists;				//Устанавливается в значение true только в том случае, если учетная запись успешно создана.

	//Constants.
	static final double MINIMUM_BALANCE = 1000.0;		//Минимальный остаток средств для открытия и ведения банковского счета.
	static final double DAILY_WITHDRAW_LIMIT = 5000.0;	//Максимальная сумма, которую можно выводить ежедневно.
								//соответственно.
	//Конструктор банковских счетов.
	BankAccount()
	{
		this.balanceAmount = 0.0;		//Первоначальный
		this.withdrawAmount = 0.0;		//Состояние.
		this.accountExists = false;
	}


		public String getName()
	{
		return accHolderName;
	}
	//Конец


	//Создание новой учетной записи пользователя.
	public void createNewAccount()
	{
		getUserDetails();		//Для получения входных данных от пользователя.
	}


	public void getUserDetails()		//Получает все необходимые сведения о пользователе.
	{

		double tempDeposit;		//Временный дубль, строка 
		int tempPhoneNo;		//и целочисленные переменные для передачи
		String tempName;		//их в качестве аргументов.

		tempName = readAccHolderName(); 		//Считывает имя пользователя учетной записи и возвращает то же самое.
		setName(tempName);		//Считывает имя пользователя учетной записи.

		tempPhoneNo = readAccHolderPhoneNo();		//Считывает имя пользователя учетной записи и возвращает то же самое.
		setPhoneNo(tempPhoneNo);			//Устанавливает номер телефона пользователя учетной записи.

		tempDeposit = readInitialDepositAmount();	//Для ознакомления с первоначальным депозитом, внесенным пользователем.
		if(tempDeposit<MINIMUM_BALANCE)		//Проверка на внесение минимального депозита произведена.
		{
			System.out.println("\n---------------Пожалуйста, попробуйте еще раз и введите минимальную сумму депозита---------------\n");
			return;
		}

		setBalanceAmount(tempDeposit);	//Если это сделано, то устанавливается начальная сумма депозита.

		setAccountTruth(true);		//Учетная запись теперь существует.

		printSuccessMessage();		//Выводит сообщение после успешного завершения создания учетной записи.

	}
	//Конец


	//Принимает имя владельца учетной записи.
	public String readAccHolderName()
	{

		String tempName;		//Временная строка для хранения имени.
		System.out.print("\nВведите имя владельца учетной записи : ");
		tempName = input.next();
		
		return tempName;

	}
	//Конец


	//Принимает номер телефона владельца учетной записи,
	public int readAccHolderPhoneNo()
	{
	
		int tempPhoneNo;		//Временное целое число для хранения телефонного номера.
		System.out.print("\nВведите номер телефона владельца : ");
		tempPhoneNo = input.nextInt();
		
		return tempPhoneNo;

	}
	//Конец


	//Способы внесения депозита
	public void deposit()
	{
		double tempDeposit;
		tempDeposit = readDepositAmount();
		this.balanceAmount += tempDeposit;
		successfulTransactionMessage();
	}

	
	public double readInitialDepositAmount()	//Для начинающих пользователей.
	{

		System.out.print("\nМинимальный депозит" + MINIMUM_BALANCE + " является обязательным");

		return readDepositAmount();

	}

	
	public double readDepositAmount()	//Для обычных пользователей.
	{

		double tempDeposit;

		System.out.print("\nВведите сумму депозита : ");
		tempDeposit = input.nextDouble();

		return tempDeposit;

	}
	//Конец


	//Способы вывода средств.
	public void withdraw()
	{
		double tempWithdraw;
		tempWithdraw = readWithdrawAmount();
		
		if(checkWithdrawalConditions(tempWithdraw)) //Осуществляет вывод средств только при соблюдении условий.
		{
			this.executeWithdrawal(tempWithdraw);
			successfulTransactionMessage();
		}

		else		//Отменяет транзакцию при не соблюдении условий.
		{
			unSuccessfulTransactionMessage();
		}

	}


	public double readWithdrawAmount()		//Считывает и возвращает запрос на вывод средств, запрошенный пользователем.
	{

		double readWithdraw;

		System.out.print("Введите сумму вывода : ");
		readWithdraw = input.nextDouble();
		
		return readWithdraw;
	}


	public boolean checkWithdrawalConditions(double tempWithdraw)		//Проверяет условия вывода средств.
	{

		boolean withdrawalPossibility;

		if(tempWithdraw>MINIMUM_BALANCE)		//Условие минимального баланса.
		{
			System.out.println("Недостаточный баланс!");
			withdrawalPossibility = false;
		}

		else if(tempWithdraw+this.withdrawAmount>DAILY_WITHDRAW_LIMIT || tempWithdraw>DAILY_WITHDRAW_LIMIT)	//Условие превышения дневного лимита.
		{
		{
			System.out.println("Превышен дневной лимит.");
			withdrawalPossibility = false;
		}
		
		else			//В остальном верно.
		{
			withdrawalPossibility = true;
		}

		return withdrawalPossibility;

	}


	public void executeWithdrawal(double tempWithdraw)		//Осуществляет вывод средств только при соблюдении условий.
	{

		this.balanceAmount -= tempWithdraw;
		this.withdrawAmount += tempWithdraw;
	
	}
	//Окончание методов вывода средств.

	
	

	public static void printWelcomeMessage()
	{
		System.out.println("\n\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-ДОБРО ПОЖАЛОВАТЬ-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n\n");
	}

	////Сообщение об успешном создании учетной записи.
	public void printSuccessMessage()
	{
		System.out.println("\n------------------------------------------\n\tУчетная запись успешно создана!\n------------------------------------------");
	}

	//Сообщение об успешной транзакции.
	public void successfulTransactionMessage()
	{
		System.out.println("\n------------------------------------------\nТранзакция прошла успешно!\n------------------------------------------");
	}

	//Сообщение о неудачной транзакции.
	public void unSuccessfulTransactionMessage()
	{
		System.out.println("\n------------------------------------------\nТранзакция не удалась!\n------------------------------------------");

	
	}
	
	//Отображает баланс.
	public void displayBalance()
	{
		System.out.println("Ваш баланс равен : " + getBalanceAmount());
	}

	//Отобразить меню для пользовательского интерфейса.
	public static void showMenu()
	{

		System.out.println("\n------------------------------------------");
		System.out.println("\t1. Создайте новую учетную запись.");
		System.out.println("\t2. Отобразить сумму баланса.");
		System.out.println("\t3. Внесите деньги на депозит.");
		System.out.println("\t4. Снять деньги.");
		System.out.println("\t5. Выход.");
		System.out.println("------------------------------------------");
	}
	//Конец


	//Методы получения.
	public int getPhoneNo()
	{
		return accHolderPhoneNo;
	}


	public double getBalanceAmount()
	{
		return balanceAmount;
	}


	public String getIFSCCode()
	{
		return accHolderIFSCCode;
	}


	public boolean getAccountTruth()
	{
		return accountExists;
	}
	//Конец


	//Методы настройки.
	public void setName(String tempName)
	{
		this.accHolderName = tempName;
	}


	public void setPhoneNo(int tempPhoneNo)
	{
		this.accHolderPhoneNo = tempPhoneNo;
	}


	public void setBalanceAmount(double tempBalance)
	{
		this.balanceAmount = tempBalance;
	}

	
	public void setIFSCCode(String tempIFSC)
	{
		this.accHolderName = tempIFSC;
	}


	public void setAccountTruth(boolean truthValue)
	{
		this.accountExists = truthValue;
	}
	//Конец

}//Конец класса


class BankAccountMainClass
{
	static Scanner input = new Scanner(System.in);


	//main метод
	public static void main(String[] args)
	{

		BankAccount userAccount = new BankAccount();
		
		int choice, userCounter = 0;
		boolean accountCreated =  false;

		userAccount.showMenu();

		while(true)
		{
			System.out.println("\n\n------------------------------------------");	
			System.out.print("\tВВедите свой выбор  ---->  ");
			choice = input.nextInt();

			switch(choice)
			{
				case 1:
					userAccount = new BankAccount();
					userAccount.createNewAccount();
					break;
		
				case 2:
					if(userAccount.getAccountTruth()==true)
						userAccount.displayBalance();
					else
						System.out.println("Учетная запись не существует");
					break;

				case 3:
					if(userAccount.getAccountTruth()==true)
						userAccount.deposit();
					else
						System.out.println("Учетная запись не существует");
					break;

				case 4:
					if(userAccount.getAccountTruth()==true)
						userAccount.withdraw();
					else
						System.out.println("Учетная запись не существует");
					break;

				case 5:
					System.out.println("Благодарим Вас за то, что вы воспользовались нашими услугами");
					System.exit(0);
			}
		}
	}
}
