package com.github.szberes.netbank.backend.account.management;

import java.util.Currency;
import java.util.List;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.szberes.netbank.NetbankApplication;
import com.github.szberes.netbank.backend.account.management.jpa.AccountEntity;
import com.github.szberes.netbank.backend.account.management.jpa.AccountRepository;
import com.github.szberes.netbank.backend.account.management.jpa.TransactionEntity;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@IntegrationTest
@ContextConfiguration(classes = NetbankApplication.class)
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountTransactionManagerTest {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountTransactionManager accountTransactionManager;

    @After
    public void tearDown() {
        accountRepository.deleteAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAccountDoesNotExist() throws Exception {
        accountTransactionManager.createNewTransaction("someRandomUser", 123L, 234L, 153L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testUserDoesNotOwnSourceAccount() throws Exception {
        accountRepository.save(new AccountEntity("Bela", "Bela's account", Currency.getInstance("EUR")));
        List<AccountEntity> belaAccounts = accountRepository.findAccountByOwnerId("Bela");
        accountTransactionManager.createNewTransaction("Lajos", belaAccounts.get(0).getId(), 144L, 153L);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAccountBalanceExceeded() throws Exception {
        accountTransactionManager.createNewTransaction("Bela", createEmptyAccount("Bela"), createEmptyAccount("Lajos"), 153L);
    }

    @Test
    public void testValidTransactionShouldChangeBalances() throws Exception {

        Long firstAccount = createAccount("user1", 1000);
        Long secondAccount = createAccount("user2", 500);

        accountTransactionManager.createNewTransaction("user1", firstAccount, secondAccount, 100L);

        assertThat(accountTransactionManager.getAccountHeader("user1", firstAccount).getBalance(), equalTo(900L));
        assertThat(accountTransactionManager.getAccountHeader("user2", secondAccount).getBalance(), equalTo(600L));
    }

    @Test
    public void testValidTransactionShouldBeLoggedToHistory() {

        Long firstAccount = createAccount("user1", 1000);
        Long secondAccount = createAccount("user2", 500);

        accountTransactionManager.createNewTransaction("user1", firstAccount, secondAccount, 100L);

        List<TransactionEntity> firstAccountTransactions = accountTransactionManager.getAllTransactions("user1", firstAccount);
        assertThat(firstAccountTransactions, hasSize(1));
        TransactionEntity firstAccountTransaction = firstAccountTransactions.get(0);
        assertEquals(firstAccount, firstAccountTransaction.getSourceAccount().getId());
        assertEquals(secondAccount, firstAccountTransaction.getDestinationAccount().getId());
        assertThat(firstAccountTransaction.getAmount(), equalTo(100L));

        List<TransactionEntity> secondAccountTransactions = accountTransactionManager.getAllTransactions("user2", secondAccount);
        assertThat(secondAccountTransactions, hasSize(1));
        TransactionEntity secondAccountTransaction = secondAccountTransactions.get(0);
        assertEquals(firstAccount, secondAccountTransaction.getSourceAccount().getId());
        assertEquals(secondAccount, secondAccountTransaction.getDestinationAccount().getId());
        assertThat(secondAccountTransaction.getAmount(), equalTo(100L));
    }

    @Test
    public void testListTransactionsShouldReturnEmpty() throws Exception {
        long firstAccount = createAccount("user1", 1000);
        assertThat(accountTransactionManager.getAllTransactions("user1", firstAccount), empty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testListTransactionsOfOtherUserShouldThrow() {
        createAccount("user1", 1000);
        long secondAccount = createAccount("user2", 500);
        accountTransactionManager.getAllTransactions("user1", secondAccount);
    }

    @Test
    public void testListTransactionShouldContainIncomingAndOutgoing() throws Exception {

        Long firstAccount = createAccount("user1", 1000);
        Long secondAccount = createAccount("user2", 500);

        accountTransactionManager.createNewTransaction("user1", firstAccount, secondAccount, 100L);
        List<TransactionEntity> firstAccountTransactions = accountTransactionManager.getAllTransactions("user1", firstAccount);
        assertEquals(firstAccount, firstAccountTransactions.get(0).getSourceAccount().getId());
        assertEquals(secondAccount, firstAccountTransactions.get(0).getDestinationAccount().getId());
    }

    @Test (expected = IllegalArgumentException.class)
    public void testTransactionsBetweenDifferentCurrenciesShouldThrow() throws Exception {

        Long firstAccount = createAccount("user1", 1000, "EUR");
        Long secondAccount = createAccount("user2", 500, "HUF");

        accountTransactionManager.createNewTransaction("user1", firstAccount, secondAccount, 100L);
    }

    @Test
    public void testTransactionWithNegativeValuesShouldThrow() throws Exception {

        Long firstAccount = createAccount("user1", 1000, "EUR");
        Long secondAccount = createAccount("user2", 500, "EUR");

        assertThatThrownBy(() -> accountTransactionManager.createNewTransaction("user1", firstAccount, secondAccount, -100L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Amount should be greater than zero");
    }

    @Test
    public void testCreateAccountsWithSameName() throws Exception {
        createAccount("user1", 1000, "EUR");
        assertThatThrownBy(() -> createAccount("user1", 500, "EUR"))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    private long createAccount(String ownerId, long initialBalance, String currencyCode) {
        AccountEntity entity = new AccountEntity(ownerId, ownerId + "'s account", Currency.getInstance(currencyCode));
        entity.setBalance(initialBalance);
        return accountRepository.save(entity).getId();
    }

    private long createAccount(String ownerId, long initialBalance) {
        return createAccount(ownerId, initialBalance, "EUR");
    }

    private Long createEmptyAccount(String ownerId) {
        return createEmptyAccount(ownerId, "EUR");
    }

    private Long createEmptyAccount(String ownerId, String currencyCode) {
        AccountEntity belaAccount = accountRepository.save(new AccountEntity(ownerId, ownerId + "'s account", Currency.getInstance(currencyCode)));
        return belaAccount.getId();
    }
}