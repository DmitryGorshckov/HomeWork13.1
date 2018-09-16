import org.apache.log4j.Logger;
import ru.kuznetsov.exception.CreaseMoneyException;
import ru.kuznetsov.VendingMachine;
import ru.kuznetsov.drinks.DrinkType;
import ru.kuznetsov.exception.NotFoundDrinkException;

import java.util.Scanner;

public class Main {
    private static final Logger LOG = Logger.getLogger(Main.class);
    private static VendingMachine vm = new VendingMachine();

    public static void main(String[] args) {
        LOG.trace("запуск программы");
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
        LOG.trace("конец программы ");
    }

    /**
     * обработка добавления денег в автомат
     * @param money - сумма
     */

    private static void processAddMoney(int money) {
        LOG.trace("запуск метода processAddMoney ");

        try {
            System.out.println("Текущий баланс: " + vm.addMoney(money));
        } catch (CreaseMoneyException e) {
            System.out.println("куппюра замята, заберите обратно "+e.getMessage());
            LOG.error("ошибка замятия купюры",e);
        }
        LOG.trace(" метод processAddMoney завершился");
    }

    /**
     * обработка получения напитка
     * @param key - код напитка в автомате
     */
    private static void processGetDrink(int key) {
        LOG.trace("запуск метода processGetDrink");

        try {
        DrinkType drinkType = vm.giveMeADrink(key);
        System.out.println("Ваш напиток " + drinkType.getName() + "!");
        }
        catch (NullPointerException n) {
            System.out.println("напиток не выдан.не хватает денег. закиньте больше денег ");
            LOG.error("ошибка",n);

        }
        catch (ArrayIndexOutOfBoundsException a) {
            System.out.println("напиток не выдан.не правильно выбрана кнопка.выберите кнопку из списка");
            LOG.error("ошибка",a);

        }
        catch (NotFoundDrinkException a) {
            System.out.println("напиток не выдан.напитка нет в наличии.иди в другой автомат");
            LOG.error("ошибка",a);

        }
        catch (Exception e){
            System.out.println("еще какая то ошибка");
            LOG.error("ошибка",e);
        }
        /*if (drinkType != null) {
            System.out.println("Ммм! " + drinkType.getName() + "!");
        } else {
            System.out.println("Напиток почему-то не получен...");
        }*/
        LOG.trace(" метод processGetDrink завершился");
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
