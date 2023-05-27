package ru.netology.javaqadiplom;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CreditAccountTest {

    @Test

    public void shouldGetCreditLimit() {
        CreditAccount account = new CreditAccount(0, 10_000, 15);
        Assertions.assertEquals(10_000, account.getCreditLimit());
    }

    @Test
    public void shouldGetBalance() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void shouldGetRate() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        Assertions.assertEquals(15, account.getRate());
    }

    @Test
    public void testChangeRate() {
        // rate > 0; Изменение процентной ставки
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        account.setRate(10);
        Assertions.assertEquals(10, account.getRate());
    }

    @Test
    public void shouldAddToZeroInitialBalance() {
        // initialBalance = 0, amount > 0
        CreditAccount account = new CreditAccount(
                0,
                5_000,
                15
        );

        account.add(3_000);

        Assertions.assertEquals(3_000, account.getBalance());
    }

    @Test
    public void shouldAddPositiveAmountToNegativeBalance() {
        // initialBalance < 0, amount > 0
        CreditAccount account = new CreditAccount(
                -1_000,
                5_000,
                15
        );

        account.add(3_000);

        Assertions.assertEquals(2_000, account.getBalance());
    }

    @Test
    public void shouldAddPositiveAmountIfInitialBalanceIsAboveZero() {
        // initialBalance > 0, amount > 0
        CreditAccount account = new CreditAccount(
                1_000,
                5_000,
                15
        );

        account.add(4_000);

        Assertions.assertEquals(5_000, account.getBalance());
    }

    @Test
    public void shouldAddZeroAmountToPositiveBalance() {
        // initialBalance = 0, amount = 0
        CreditAccount account = new CreditAccount(
                0,
                5_000,
                15
        );

        account.add(0);

        Assertions.assertEquals(0, account.getBalance());
    }

    @Test
    public void shouldAddNegativeAmountToPositiveInitialBalance() {
        // initialBalance = 0, amount < 0
        CreditAccount account = new CreditAccount(
                1_000,
                5_000,
                15
        );

        account.add(-3_000); // добавляем отрицательную сумму на положительный баланс счёта

        Assertions.assertEquals(1_000, account.getBalance());
    }

    @Test
    public void testYearChangeIfBalanceIsNegative() {
        // balance < 0; rate > 0
        CreditAccount account = new CreditAccount(-1_000, 5_000, 15);
        Assertions.assertEquals(-150, account.yearChange());
    }

    @Test
    public void testYearChangeWithZeroBalance() {
        // initialBalance = 0; rate > 0
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        Assertions.assertEquals(0, account.yearChange());
    }

    @Test
    public void testYearChangeIfBalanceIsPositive() {
        // balance > 0
        CreditAccount account = new CreditAccount(1_000, 5_000, 15);
        Assertions.assertEquals(0, account.yearChange());
    }

    @Test
    // покупка на отрицательную сумму
    public void testPayByAmountBelowZero() {
        Account account = new CreditAccount(5_000, 5_000, 15);
        assertFalse(account.pay(-1_000)); // опреция не должна осуществиться
        assertEquals(5_000, account.getBalance());
    }

    //покупка на сумму равную нулю
    @Test
    public void PayByZeroAmount() {
        Account account = new CreditAccount(5_000, 5_000, 15);
        assertFalse(account.pay(0)); // опреция не должна осуществиться
        assertEquals(5_000, account.getBalance());
    }

    @Test
    public void PayByZeroAbove() {
        CreditAccount account = new CreditAccount(5_000, 5_000, 15);
        Boolean expected = true;
        Boolean actual = account.pay(1_000);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void PayByAllBalanceEqual() {
        CreditAccount account = new CreditAccount(5_000, 5_000, 15);
        Boolean expected = true;
        Boolean actual = account.pay(10_000);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void PayByAllBalanceAbove() {
        CreditAccount account = new CreditAccount(5_000, 5_000, 15);
        Boolean expected = false;
        Boolean actual = account.pay(15_000);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void PayByBalanceBelow() {
        CreditAccount account = new CreditAccount(5_000, 5_000, 15);
        Boolean expected = true;
        Boolean actual = account.pay(4_000);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void PayByBalanceEqual() {
        CreditAccount account = new CreditAccount(5_000, 5_000, 15);
        Boolean expected = true;
        Boolean actual = account.pay(5_000);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void PayByCreditEqual() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        Boolean expected = true;
        Boolean actual = account.pay(5_000);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void PayByCreditAbove() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        Boolean expected = false;
        Boolean actual = account.pay(6_000);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void PayByCreditBelow() {
        CreditAccount account = new CreditAccount(0, 5_000, 15);
        Boolean expected = true;
        Boolean actual = account.pay(4_000);

        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void AllBalanceBelowAmount() {
        Account account = new CreditAccount(4_000, 5_000, 15);
        assertFalse(account.pay(10_000));
        assertEquals(4_000, account.getBalance());
    }

    @Test
    public void AllBalanceEqualAmount() {
        Account account = new CreditAccount(4_000, 5_000, 15);
        assertTrue(account.pay(9_000));
        assertEquals(-5_000, account.getBalance());
    }

    @Test
    public void BalanceEqualAmount() {
        Account account = new CreditAccount(4_000, 5_000, 15);
        assertTrue(account.pay(4_000));
        assertEquals(0, account.getBalance());
    }

    @Test
    public void BalanceAboveAmount() {
        Account account = new CreditAccount(4_000, 6_000, 15);
        assertTrue(account.pay(3_000));
        assertEquals(1_000, account.getBalance());
    }

    @Test
    public void BalanceBelowAmount() {
        Account account = new CreditAccount(4_000, 6_000, 15);
        assertTrue(account.pay(5_000));
        assertEquals(-1_000, account.getBalance());
    }

    @Test
    public void AllBalanceAboveAmountAndBalanceIsZero() {
        Account account = new CreditAccount(0, 4_000, 15);
        assertTrue(account.pay(4_000));
        assertEquals(-4_000, account.getBalance());
    }

    @Test
    public void AllBalanceAboveAmountAndBalanceIsAboveZero() {
        Account account = new CreditAccount(1_000, 4_000, 15);
        assertTrue(account.pay(4_000));
        assertEquals(-3_000, account.getBalance());
    }

    @Test
    public void BalanceIsNegativeCreditAboveAmount() {
        Account account = new CreditAccount(-1_000, 5_000, 15);
        assertTrue(account.pay(4_000));
        assertEquals(-5_000, account.getBalance());
    }

    @Test
    public void BalanceIsNegativeCreditEqualAmount() {
        Account account = new CreditAccount(-1_000, 4_000, 15);
        assertTrue(account.pay(4_000));
        assertEquals(-5_000, account.getBalance());
    }

    @Test
    public void BalanceBelowCredit() {
        Account account = new CreditAccount(4_000, 5_000, 15);
        assertTrue(account.pay(4_500));
        assertEquals(-500, account.getBalance());
    }


    @Test
    public void testExceptionIfNegativeRate() {
        // rate < 0

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(100, 5_000, -15);
        });
    }

    @Test
    public void testExceptionIfRateIsAboveMax() {
        // rate > max

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(100, 5_000, 41);
        });
    }

    @Test
    public void testExceptionIfCreditLimitIsNegative() {
        // creditLimit < 0
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(2_000, -5_000, 15);
        });
    }

    @Test
    public void shouldGetCreditNegative() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new CreditAccount(10, -1, 10);
        });
    }

    @Test
    public void shouldGetCreditZero() {
        CreditAccount account = new CreditAccount(0, 0, 10);
        Assertions.assertEquals(0, account.getCreditLimit());
    }

}



