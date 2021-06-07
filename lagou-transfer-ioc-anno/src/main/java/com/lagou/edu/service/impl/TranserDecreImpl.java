package com.lagou.edu.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.pojo.Account;

/**
 *
 * @author jingjiejiang
 * @history Jun 7, 2021
 *
 */
public class TranserDecreImpl {

    @Autowired
    AccountDao accountDao;

    /**
     *
     * Transfer money from a specified account.
     *
     * @param account
     * @param money
     * @throws Exception
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void transferDecre(Account account, int money) throws Exception {

        account.setMoney(account.getMoney() - money);
        accountDao.updateAccountByCardNo(account);
    }
}
