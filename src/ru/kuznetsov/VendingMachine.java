package ru.kuznetsov;

import org.apache.log4j.Logger;
import ru.kuznetsov.drinks.*;
import ru.kuznetsov.exception.CreaseMoneyException;

import java.time.LocalDate;

/**
 * Торговый автомат
 */
public class VendingMachine {
    private static final Logger LOG = Logger.getLogger(VendingMachine.class);
    private double money = 0;
    private Product[] drinks = new Product[] {
           new Product(ColdDrinkType.COCA,34),
            new Product(ColdDrinkType.PEPSI, 10),
            new Product(ColdDrinkType.SPRITE, 1),
            new Product(ColdDrinkType.FANTA, 3),
            new Product(HotDrinkType.GREEN_TEA, 100),
            new Product(HotDrinkType.BLACK_TEA, 100),
            new Product(HotDrinkType.AMERICANO, 100),
            new Product(HotDrinkType.CAPPUCINO, 100)
    };

    /**
     * Добавление купюр в автомат
     *
     * @param money - сумма внесенных купюр
     * @return текущий остаток
     */
    public double addMoney(double money) throws CreaseMoneyException {

        double situation =  Math.random();
         if(situation<0.5){
             throw new CreaseMoneyException(money+" руб");

         }
        this.money += money;
        return this.money;
    }

    /**
     * Получает из автомата выбранный напиток
     *
     * В методе есть проверка корректности кода напитка, проверка остатков данного продукта
     * и достаточно ли денег чтобы его купить. В случае ошибок выбрасывать соответствующее исключение
     *
     * @param key код продукта
     * @return напиток;
     */
    public DrinkType giveMeADrink(int key) throws Exception {
        if (!isKeyValid(key)) {
            LOG.warn("возможна ошибка");
            throw new ArrayIndexOutOfBoundsException();
            // Неправильный код товара - товар не возвращается
        }

        Product selected = drinks[key];
        if (!isMoneyEnough(selected)) {
            // Нехватает денег - товар не возвращается
            return null;
        }
        DrinkType drink = selected.take();
        money -= drink.getPrice();
        return drink;


    }

    /**
     * Формирует список товаров
     *
     * @return строки с информацией о товаре
     */
    public String[] getDrinkTypes() {
        String[] result = new String[drinks.length];
        for (int i = 0; i < drinks.length; i++) {
            result[i] = String.format("%d - %12s: %6.2f руб", i, drinks[i].getName(),drinks[i].getPrice());
        }
        return result;

    }

    /**
     * Проверка что хватает денег на выбранный напиток
     *
     * @param selected - выбранный напиток
     * @return true если денег хватает, иначе - false
     */
    private boolean isMoneyEnough(Product selected) {
        return money >= selected.getPrice();
    }

    /**
     * Проверка что выбрали существующий тип напитка
     * Не проверяет остаток, только границы массива
     *
     * @param key номер напитка
     * @return true если есть напиток с таким номером, иначе false
     */
    private boolean isKeyValid(int key) {
        LOG.trace(" метод isKeyValid запуск");
        boolean iskey = key >=0 && key < drinks.length;
        if (key >=0 && key < drinks.length){

        } else {
            LOG.error("ошибка ");
        }
        LOG.trace("метод isKeyValid завершился");
          return iskey;
    }

    /**
     * Выдать сдачу.
     * Количество введеных купюр обнуляется
     *
     * @return остаток
     */
    public double getChange() {
        double money = this.money;
        this.money = 0;
        return money;
    }
}
