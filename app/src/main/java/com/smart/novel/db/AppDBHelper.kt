package com.smart.novel.db

import com.smart.framework.library.common.utils.AppSharedPreferences
import com.smart.novel.MyApplication
import com.smart.novel.bean.UserBean
import com.smart.novel.db.gen.UserBeanDao
import com.smart.novel.db.manager.DbManager
import com.smart.novel.util.SharePreConstants
import org.greenrobot.greendao.Property
import java.util.*

/**
 * Created by JoJo on 2018/8/28.
 * wechat:18510829974
 * description: GreenDao存储 app本地缓存Hepler类
 */
class AppDBHelper {
    companion object {
        /**
         * 测试GreenDao
         *
         * @return
         */
        fun getLoginUser(): UserBean? {
            val sharePre = AppSharedPreferences(MyApplication.context)
            val properties = ArrayList<Property>()
            properties.add(UserBeanDao.Properties.Id)
            // 匹配条件
            val objects = ArrayList<Any>()
            objects.add(sharePre.getString(SharePreConstants.USER_ID)) //存储的主键
            var userInfo: UserBean? = null
            try {
                userInfo = DbManager.getInstance().query(UserBean::class.java, properties, objects) as UserBean
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return userInfo
        }
    }

}