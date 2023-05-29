package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SavingAccountTest {

    @Test
    // Баг № 1: не выводит итоговый баланс при пополнении счета в диапазоне допустимых значений
    public void shouldAddLessThanMaxBalance() {
        SavingAccount account = new SavingAccount(
                2_000,
                1_000,
                10_000,
                5
        );

        account.add(3_000);

        assertEquals(2_000 + 3_000, account.getBalance());
    }

    //Тест проверки операции пополнения счета с некорректными параметрами
    @Test
    public void testAddWithInvalidAmount() {
        SavingAccount savingAccount = new SavingAccount(1000, 500, 2000, 10);
        assertFalse(savingAccount.add(-500));
        assertEquals(1000, savingAccount.getBalance());
    }

    //Тест проверки операции пополнения счета, когда счет уже на максимальном балансе
    @Test
    public void testAddWhenAccountAtMaxBalance() {
        SavingAccount savingAccount = new SavingAccount(2000, 500, 2000, 10);
        assertFalse(savingAccount.add(500));
        assertEquals(2000, savingAccount.getBalance());
    }


    //Тест проверки создания объекта SavingAccount с корректными параметрами
    @Test
    public void testSavingAccountCreationWithValidParams() {
        SavingAccount account = new SavingAccount(1_000, 500, 2_000, 10);
        assertEquals(1_000, account.getBalance());
        assertEquals(500, account.getMinBalance());
        assertEquals(2_000, account.getMaxBalance());
        assertEquals(10, account.getRate());
    }

    @Test
    public void testSavingAccountCreationWithZeroParams() {
        SavingAccount account = new SavingAccount(0, 0, 0, 0);
        assertEquals(0, account.getBalance());
        assertEquals(0, account.getMinBalance());
        assertEquals(0, account.getMaxBalance());
        assertEquals(0, account.getRate());
    }

    @Test
    //Тест с отрицательным значении minBalance
    //баг № 2 - Исключение IllegalArgumentException не срабатывает при отрицательном значении minBalance
    public void minBalanceIsNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            SavingAccount account =
            new SavingAccount(2_000, -1_000, 4_000, 10);
        });
    }


    @Test
    //Тест со значением minBalance большим, чем значение maxBalance
    //баг № 3 - Исключение IllegalArgumentException не срабатывает при значении minBalance большем, чем значение maxBalance
    public void minBalanceIsMoreMaxBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            SavingAccount account =
            new SavingAccount(1_000, 2_000, 1_500, 15);
        });
    }

    @Test
    //Тест с отрицательным значении maxBalance
    //баг № 4 - Исключение IllegalArgumentException не срабатывает при отрицательном значении maxBalance
    public void maxBalanceIsNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            SavingAccount account =
            new SavingAccount(10_000, 0, -100, 10);
        });
    }

    @Test
    //Тест с отрицательным значении initialBalance
    //баг № 5 - Исключение IllegalArgumentException не срабатывает при отрицательном значении initialBalance
    public void initialBalanceIsNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            SavingAccount account =
            new SavingAccount(-10, 0, 1_000, 10);
        });
    }

    @Test
    //Тест со значением initBalance большим, чем значение maxBalance
    //баг № 6 - Исключение IllegalArgumentException не срабатывает при значении initBalance большем, чем значение maxBalance
    public void initBalanceIsMoreMaxBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            SavingAccount account =
            new SavingAccount(10_000, 2_000, 5_000, 12);
        });
    }

    @Test
    public void testCreateAccountWithNegativeRate() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            SavingAccount account =
            new SavingAccount(0, 0, 100, -5);
        });
    }

    @Test
    //Тест со значением initBalance меньшим, чем значение minBalance
    //баг № 7 - Исключение IllegalArgumentException не срабатывает при значении initBalance меньшем, чем значение minBalance
    public void initialBalanceIsLessMinBalance() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
//            SavingAccount account =
            new SavingAccount(100, 1_000, 10_000, 10);
        });
    }

    @Test
    public void payAmountIsMoreInitialBalance() {
        SavingAccount account = new SavingAccount(3_000, 1_000, 10_000, 12);
        Boolean expected = false;
        Boolean actual = account.pay(4_000);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void payAmountIsMoreMaxBalance() {
        SavingAccount savingAccount = new SavingAccount(3_000, 1_000, 10_000, 12);
        Boolean expected = false;
        Boolean actual = savingAccount.pay(12_000);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void payAmountIsBig() {
        SavingAccount savingAccount = new SavingAccount(3_000, 1_000, 10_000, 12);
        Boolean expected = false;
        Boolean actual = savingAccount.pay(12_000);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void payAmountIsZero() {
        SavingAccount account = new SavingAccount(3_000, 1_000, 5_000, 9);
        account.pay(0);
        Assertions.assertEquals(3_000, account.getBalance());
    }

    @Test
    public void payAmountIsNegative() {
        SavingAccount account = new SavingAccount(3_000, 1_000, 5_000, 4);
        account.pay(-10);
        Assertions.assertEquals(3_000, account.getBalance());
    }

    @Test
    public void payAmountIsNormal() {
        SavingAccount account = new SavingAccount(3_000, 1_000, 5_000, 10);
        Boolean expected = true;
        Boolean actual = account.pay(1050);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    //Тест на сумму покупки amount превышающем значение initialBalance
    //баг № 8 - Отрицательное значение баланса при покупке превышающей значение стоимости initialBalance
    public void payAmountIsMore() {
        SavingAccount account = new SavingAccount(3_000, 1_000, 5_000, 1);
        account.pay(4_000);
        Assertions.assertEquals(3_000, account.getBalance());
    }

    //Тест корректности работы метода yearChange() с неограниченной ставкой
    @Test
    //Тест корректности работы метода yearChange() с неограниченной ставкой
    //баг № 9 - Переменная rate в функции yearChange не имеет ограничение на максимально допустимое значение
    public void testYearChangeWithNonMaxRate() {
        SavingAccount account = new SavingAccount(2_000, 1_000, 3_000, 2_000);
        assertEquals(40_000, account.yearChange());
    }

    @Test
    public void testAddZeroAmount() {
        SavingAccount account = new SavingAccount(1000, 0, 2000, 10);
        assertFalse(account.add(0));
        assertEquals(1000, account.getBalance());
    }

    @Test
    public void testAddAmountExceedingMaxBalance() {
        SavingAccount account = new SavingAccount(1500, 0, 2000, 10);
        assertFalse(account.add(600)); // Операция не должна пройти
        assertEquals(1500, account.getBalance());
    }

    //Тест пополнения на сумму больше, чем допустимый максимум
    @Test
    public void testAddWithTooLargeAmount() {
        SavingAccount account = new SavingAccount(1000, 0, 2000, 10);
        assertFalse(account.add(5000)); // попытка пополнения на сумму больше, чем допустимый максимум
        assertEquals(1000, account.getBalance());
    }

    //Тест корректности работы метода yearChange() с нулевой ставкой
    @Test
    public void testYearChangeWithZeroRate() {
        SavingAccount account = new SavingAccount(1000, 0, 2000, 0);
        assertEquals(0, account.yearChange());
    }


    //Тесты проверяющий корректность операции оплаты с карты
    @Test
    public void testPaySuccess() {
        SavingAccount account = new SavingAccount(100, 10, 200, 10);
        account.pay(50);
        Assertions.assertEquals(50, account.getBalance());
    }

    //Тест попытки оплаты с корректными значениями - баланс должен уменьшиться на сумму покупки
    @Test
    public void testPayWithNormalAmount() {
        SavingAccount account = new SavingAccount(1000, 0, 2000, 10);
        assertTrue(account.pay(999));
        assertEquals(1, account.getBalance());
    }

    //Тест попытки оплаты с отрицательной суммой - баланс должен быть неизменен
    @Test
    public void testPayWithNegativeAmount() {
        SavingAccount account = new SavingAccount(1000, 0, 2000, 10);
        account.pay(-100);
        Assertions.assertEquals(1000, account.getBalance());
    }

    //Тест на неуспешное снятие средств, когда запрашиваемая сумма отрицательна
    @Test
    public void testPayNegativeAmount() {
        SavingAccount account = new SavingAccount(1000, 500, 2000, 20);
        assertFalse(account.pay(-200));
        assertEquals(1000, account.getBalance());
    }

    //Тест на неуспешное снятие средств, когда остаток на счете становится ниже минимального значения
    @Test
    //Тест на неуспешное снятие средств, когда остаток на счете становится ниже минимального значения
    //баг № 9 - Значение баланса при покупке меньше минимального возможного баланса minBalance
    public void testPayBelowMinBalance() {
        SavingAccount account = new SavingAccount(1000, 500, 2000, 10);
        assertFalse(account.pay(600));
        assertEquals(1000, account.getBalance());
    }

    @Test
    //Тест на неуспешное снятие средств, когда запрашиваемая сумма больше остатка на счете
    //баг № 10 - Значение величины снятия средств превышает величину остатка на счете initialBalance
    public void testPayOverdraft() {
        SavingAccount account = new SavingAccount(1000, 500, 2000, 20);
        assertFalse(account.pay(2000));
        assertEquals(1000, account.getBalance());
    }

    //Добавление отрицательной суммы
    @Test
    public void testAddNegativeAmount() {
        SavingAccount account = new SavingAccount(1000, 100, 2000, 20);
        assertFalse(account.add(-500));
        assertEquals(1000, account.getBalance());
    }

    //Тест с нулевой ставкой и положительным балансом
    @Test
    public void testYearChangeWithZeroRateAndPositiveBalance() {
        SavingAccount account = new SavingAccount(1500, 100, 2000, 0);
        assertEquals(0, account.yearChange());
    }

    //Тест с нулевой ставкой и нулевым балансом
    @Test
    public void testYearChangeWithZeroRateAndZeroBalance() {
        SavingAccount account = new SavingAccount(1000, 100, 2000, 0);
        assertEquals(0, account.yearChange());
    }

    @Test
    public void testAddWithNegativeAmount() {
        SavingAccount account = new SavingAccount(100, 0, 1000, 10);
        assertFalse(account.add(-100));
        assertEquals(100, account.getBalance());
    }

    @Test
    public void testAddAtMaxBalance() {
        SavingAccount account = new SavingAccount(900, 0, 1000, 10);
        assertTrue(account.add(100));
        assertEquals(1000, account.getBalance());
    }

    @Test
    public void yearChangeIsZero() {
        SavingAccount account = new SavingAccount(1_000, 800, 5_000, 0);
        Assertions.assertEquals(0, account.yearChange());
    }

    @Test
    public void yearChangeSolve() {
        SavingAccount account = new SavingAccount(1_000, 800, 5_000, 10);
        Assertions.assertEquals(100, account.yearChange());
    }


    @Test
    public void testGetMinBalance() {
        SavingAccount account = new SavingAccount(1000, 500, 2000, 2);
        int expectedMinBalance = 500;
        assertEquals(expectedMinBalance, account.getMinBalance());
    }

    @Test
    public void testGetMaxBalance() {
        SavingAccount account = new SavingAccount(1000, 500, 5000, 10);
        assertEquals(5000, account.getMaxBalance());
    }

}