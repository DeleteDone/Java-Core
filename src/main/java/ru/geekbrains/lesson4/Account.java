class Account
{
    private string clientLastName;
    private int accountNumber;
    private double procent;
    private double amount;
 
    /// <summary>
    /// Конструктор класса
    /// </summary>
    /// <param name="clientLastName">Фамилия клиента</param>
    /// <param name="accountNumber">Номер счета</param>
    /// <param name="procent">Процент по депозиту</param>
    /// <param name="amount">Сумма на счету</param>
    public Account(string clientLastName, int accountNumber, double procent, double amount) 
    {
        this.clientLastName = clientLastName;
        this.accountNumber = accountNumber;
        this.procent = procent;
        this.amount = amount;
    }
 
    /// <summary>
    /// Метод списания денег со счета
    /// </summary>
    /// <param name="money">Сумма, которую нужно списать</param>
    public void writeOffMoneyFromAccount(double money) 
    { 
        
    }
 
    /// <summary>
    /// Метод зачисления денег на счет
    /// </summary>
    /// <param name="money">Сумма, которую нужно зачислить</param>
    public void enlistMoneyToAccount(double money) 
    {
    
    }
 
    /// <summary>
    /// Метод смены владельца счета
    /// </summary>
    /// <param name="newLastName">Новая фамилия клиента</param>
    public void changeClient(string newLastName)
    {
 
    }
 
    /// <summary>
    /// Метод перевода суммы на счету в доллары
    /// </summary>
    /// <param name="rate">Курс доллара</param>
    public void convertMoneyToDollar(double rate)
    {
 
    }
 
    /// <summary>
    /// Метод перевода суммы на счету в евро
    /// </summary>
    /// <param name="rate">Курс евро</param>
    public void convertMoneyToEuro(double rate)
    {
 
    }
    
    /// <summary>
    /// Метод начисления процента по депозиту
    /// </summary>
    public void enlistProcent() 
    { 
    
    }
 
    /// <summary>
    /// Метод возврата объекта ввиде строки
    /// </summary>
    /// <returns>Возвращает полную информацию о счете</returns>
    public override string ToString()
    {
        return 
            String.Format(
                "Номер счета - {0} | Фамилия владельца - {1} | Процент по депозиту - {2} | Сумма на счету - {3}",
                accountNumber, clientLastName, procent, amount
            );
    }
}
