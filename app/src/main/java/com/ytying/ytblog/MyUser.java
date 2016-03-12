package com.ytying.ytblog;

import com.ytying.ytblog.constants.SpKey;
import com.ytying.ytblog.model.domin.DbUser;
import com.ytying.ytblog.model.domin.User;
import com.ytying.ytblog.model.domin.UserDao;
import com.ytying.ytblog.utils.SpUtil;

import java.util.List;

import de.greenrobot.dao.query.QueryBuilder;

/**
 * Created by UKfire on 16/3/10.
 */
public class MyUser {
    private static User self;

    public static User getSelf() {
        if (self == null || self.getFunId().length() == 0)
            updateSelf();
        return self;
    }

    public static void updateSelf() {
        QueryBuilder<User> qb = DbUser.getInstance(F.AppContext).getQueryBuilder();
        qb.where(UserDao.Properties.FunId.eq(loadUid()));
        List<User> list = qb.list();
        if (list != null && list.size() > 0)
            self = list.get(0);
        else
            self = new User();
    }

    public static String loadUid() {
        return SpUtil.loadString(SpKey.USER_FUNID);
    }



}
