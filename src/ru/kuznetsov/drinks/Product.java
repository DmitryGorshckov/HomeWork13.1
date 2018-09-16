package ru.kuznetsov.drinks;

import org.apache.log4j.Logger;
import ru.kuznetsov.exception.NotFoundDrinkException;

/**
 * Класс-обертка "Информация по товару"
 *
 * Добавляет к типу товара дополнительное поле: количество
 */
public class Product {
    private static final Logger LOG = Logger.getLogger(Product.class);
    private final DrinkType drinkType;
    private int quantity;


    public Product(DrinkType drinkType, int quantity) {
        this.drinkType = drinkType;
        this.quantity = quantity;
    }

    /**
     * Изъятие напитка из хранилища
     * Меняет количество товара в хранлище
     *
     * @return тип напитка
     */
    public DrinkType take() throws Exception {

           if (quantity<=0) {
               LOG.warn("возможно ошибка");
               throw new NotFoundDrinkException();
           } else{
             quantity--;
            return drinkType;
           }

    }

    public String getName() {
        return drinkType.getName();
    }
    public double getPrice() {
        return drinkType.getPrice();
    }
}
