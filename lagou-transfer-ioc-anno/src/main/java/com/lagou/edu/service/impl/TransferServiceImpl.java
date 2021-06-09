package com.lagou.edu.service.impl;

import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author jingjiejiang
 * @history Jun 9, 2021
 *
 */
@Service("transferService")
public class TransferServiceImpl implements TransferService {

    // 最佳状态
    // @Autowired 按照类型注入 ,如果按照类型无法唯一锁定对象，可以结合@Qualifier指定具体的id
    @Autowired
    @Qualifier("accountDao")
    private AccountDao accountDao;

    @Autowired
    private TransferIncreImpl transferIncreImpl;

    @Autowired
    private TranserDecreImpl transferDecreImpl;

    @Override
//    @Transactional(rollbackFor = { Exception.class })
    @Transactional
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {

        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);

        transferIncreImpl.transferIncre(to, money);
        transferDecreImpl.transferDecre(from, money);
    }
}
