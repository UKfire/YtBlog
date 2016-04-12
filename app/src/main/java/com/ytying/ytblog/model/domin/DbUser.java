package com.ytying.ytblog.model.domin;

import android.content.Context;

import com.ytying.ytblog.F;
import com.ytying.ytblog.YtApp;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by UKfire on 16/3/11.
 */
public class DbUser {
    private static DbUser instance;
    private static Context appContext;
    private DaoSession mDaoSession;
    private UserDao userDao;

    private DbUser() {
    }

    public static DbUser getInstance(Context context) {
        if (instance == null) {
            instance = new DbUser();
            if (appContext == null) {
                appContext = context.getApplicationContext();
            }
            instance.mDaoSession = YtApp.getDaoSession(context);
            instance.userDao = instance.mDaoSession.getUserDao();
        }
        return instance;
    }

    public static DbUser getInstance() {
        return getInstance(F.AppContext);
    }


    public User loadUser(String funId) {
        User user = userDao.load(funId);
        return user == null ? new User(funId) : user;
    }

    /**
     * insert or update note
     *
     * @return insert or update note id
     */
    public long saveUser(User person) {
        return userDao.insertOrReplace(person);
    }

    /**
     * insert or update noteList use transaction
     *
     * @param list
     */
    public void saveUserLists(final List<User> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        userDao.getSession().runInTx(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    try {
                        User person = list.get(i);
                        userDao.insertOrReplace(person);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }

    /**
     * delete all note
     */
    public void deleteAllUser() {
        userDao.deleteAll();
    }

    /**
     * delete note by id
     *
     * @param funId
     */
    public void deleteUser(String funId) {
        userDao.deleteByKey(funId);
    }

    public void deleteUser(User person) {
        userDao.delete(person);
    }

    public QueryBuilder<User> getQueryBuilder() {
        return userDao.queryBuilder();
    }


}
