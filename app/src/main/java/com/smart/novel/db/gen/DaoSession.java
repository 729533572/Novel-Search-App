package com.smart.novel.db.gen;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.smart.novel.bean.SearchHistoryBean;
import com.smart.novel.bean.TestBean;
import com.smart.novel.bean.UserBean;
import com.smart.novel.bean.ChapterBean;

import com.smart.novel.db.gen.SearchHistoryBeanDao;
import com.smart.novel.db.gen.TestBeanDao;
import com.smart.novel.db.gen.UserBeanDao;
import com.smart.novel.db.gen.ChapterBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig searchHistoryBeanDaoConfig;
    private final DaoConfig testBeanDaoConfig;
    private final DaoConfig userBeanDaoConfig;
    private final DaoConfig chapterBeanDaoConfig;

    private final SearchHistoryBeanDao searchHistoryBeanDao;
    private final TestBeanDao testBeanDao;
    private final UserBeanDao userBeanDao;
    private final ChapterBeanDao chapterBeanDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        searchHistoryBeanDaoConfig = daoConfigMap.get(SearchHistoryBeanDao.class).clone();
        searchHistoryBeanDaoConfig.initIdentityScope(type);

        testBeanDaoConfig = daoConfigMap.get(TestBeanDao.class).clone();
        testBeanDaoConfig.initIdentityScope(type);

        userBeanDaoConfig = daoConfigMap.get(UserBeanDao.class).clone();
        userBeanDaoConfig.initIdentityScope(type);

        chapterBeanDaoConfig = daoConfigMap.get(ChapterBeanDao.class).clone();
        chapterBeanDaoConfig.initIdentityScope(type);

        searchHistoryBeanDao = new SearchHistoryBeanDao(searchHistoryBeanDaoConfig, this);
        testBeanDao = new TestBeanDao(testBeanDaoConfig, this);
        userBeanDao = new UserBeanDao(userBeanDaoConfig, this);
        chapterBeanDao = new ChapterBeanDao(chapterBeanDaoConfig, this);

        registerDao(SearchHistoryBean.class, searchHistoryBeanDao);
        registerDao(TestBean.class, testBeanDao);
        registerDao(UserBean.class, userBeanDao);
        registerDao(ChapterBean.class, chapterBeanDao);
    }
    
    public void clear() {
        searchHistoryBeanDaoConfig.clearIdentityScope();
        testBeanDaoConfig.clearIdentityScope();
        userBeanDaoConfig.clearIdentityScope();
        chapterBeanDaoConfig.clearIdentityScope();
    }

    public SearchHistoryBeanDao getSearchHistoryBeanDao() {
        return searchHistoryBeanDao;
    }

    public TestBeanDao getTestBeanDao() {
        return testBeanDao;
    }

    public UserBeanDao getUserBeanDao() {
        return userBeanDao;
    }

    public ChapterBeanDao getChapterBeanDao() {
        return chapterBeanDao;
    }

}
