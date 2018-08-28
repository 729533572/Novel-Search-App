package com.smart.novel.db

import com.smart.novel.db.bean.TestBean
import com.smart.novel.db.gen.TestBeanDao
import com.smart.novel.db.manager.DbManager
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
        fun getLoginUser(): TestBean? {
            val properties = ArrayList<Property>()
            properties.add(TestBeanDao.Properties.Msg)
            // 匹配条件
            val objects = ArrayList<Any>()
//        objects.add(testBean.getMsg()) //存储的主键
            var userInfo: TestBean? = null
            try {
                userInfo = DbManager.getInstance().query(TestBean::class.java, properties, objects) as TestBean
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return userInfo
        }
    }

}