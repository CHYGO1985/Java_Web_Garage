package com.lagou.edu.service.impl;

import com.lagou.edu.dao.AccountDao;
import com.lagou.edu.pojo.Account;
import com.lagou.edu.service.TransferService;
import com.lagou.edu.utils.ConnectionUtils;
import com.lagou.edu.utils.TransactionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 应癫
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
    @Transactional(rollbackFor = { Exception.class })
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {

        /*try{
            // 开启事务(关闭事务的自动提交)
            TransactionManager.getInstance().beginTransaction();*/

//            Account from = accountDao.queryAccountByCardNo(fromCardNo);
//            Account to = accountDao.queryAccountByCardNo(toCardNo);
//
//            from.setMoney(from.getMoney()-money);
//            to.setMoney(to.getMoney()+money);
//
//            accountDao.updateAccountByCardNo(to);
//            //int c = 1/0;
//            accountDao.updateAccountByCardNo(from);

        /*    // 提交事务

            TransactionManager.getInstance().commit();
        }catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            TransactionManager.getInstance().rollback();

            // 抛出异常便于上层servlet捕获
            throw e;

        }*/

        try {
            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

//            from.setMoney(from.getMoney()-money);
//            to.setMoney(to.getMoney()+money);

//            accountDao.updateAccountByCardNo(to);
//            int c = 1/0;
//            accountDao.updateAccountByCardNo(from);

            transferIncreImpl.transferIncre(to, money);
            transferDecreImpl.transferDecre(from, money);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


    }
}
