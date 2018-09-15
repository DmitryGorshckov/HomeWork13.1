import ru.kuznetsov.CreaseMoneyException;
import ru.kuznetsov.VendingMachine;
import ru.kuznetsov.drinks.DrinkType;
import ru.kuznetsov.drinks.NotFoundDrinkException;

import java.util.Scanner;

public class Main {
    private static VendingMachine vm = new VendingMachine();

    public static void main(String[] args) {

        System.out.println("Наши напитки: ");
        for (String line : vm.getDrinkTypes()) {
            System.out.println(line);
        }

        Scanner scan = new Scanner(System.in);
        printHelp();
        while (scan.hasNext()) {
            String command = scan.next();
            switch (command) {
                case "add": {
                    int money = scan.nextInt();
                    processAddMoney(money);
                    break;
                }
                case "get": {
                    int key = scan.nextInt();
                    processGetDrink(key);
                    break;
                }
                case "end": {
                    processEnd();
                    return;
                }
                default:
                    System.out.println("Команда не определена");
            }
            scan.nextLine();
        }
    }

    /**
     * обработка добавления денег в автомат
     * @param money - сумма
     */
    private static void processAddMoney(int money) {

        try {
            System.out.println("Текущий баланс: " + vm.addMoney(money));
        } catch (CreaseMoneyException e) {
            System.out.println("куппюра замята, заберите обратно "+e.getMessage());
        }
    }

    /**
     * обработка получения напитка
     * @param key - код напитка в автомате
     */
    private static void processGetDrink(int key) {

        try {
        DrinkType drinkType = vm.giveMeADrink(key);
        System.out.println("Ваш напиток " + drinkType.getName() + "!");
        }
        catch (NullPointerException n) {
            System.out.println("напиток не выдан.не хватает денег. закиньте больше денег ");

        }
        catch (ArrayIndexOutOfBoundsException a) {
            System.out.println("напиток не выдан.не правильно выбрана кнопка.выберите кнопку из списка");

        }
        catch (NotFoundDrinkException a) {
            System.out.println("напиток не выдан.напитка нет в наличии.иди в другой автомат");

        }
        catch (Exception e){
            System.out.println("еще какая то ошибка");
        }
        /*if (drinkType != null) {
            System.out.println("Ммм! " + drinkType.getName() + "!");
        } else {
            System.out.println("Напиток почему-то не получен...");
        }*/

    }

    /**
     * обработка получения сдачи
     */
    private static void processEnd() {
        System.out.println("Ваша сдача: " + vm.getChange());
    }

    /**
     * выводит подсказку по доступным командам
     */
    private static void printHelp() {
        System.out.println( "Введите 'add <количество>' для добавления купюр" );
        System.out.println( "Введите 'get <код напитка>' для получения напитка" );
        System.out.println( "Введите 'end' для получения сдачи" );
    }
}
