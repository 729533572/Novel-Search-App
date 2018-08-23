package com.smart.novel.db

import android.util.Log
import com.smart.novel.MyApplication
import com.smart.novel.db.gen.DaoMaster
import com.smart.novel.db.gen.DaoSession
import com.smart.novel.util.Constants
import org.greenrobot.greendao.AbstractDao
import org.greenrobot.greendao.Property

/**
 * Created by JoJo on 2018/8/23.
 * wechat:18510829974
 * description:
 */
object DBManager {
    private val DB_NAME = Constants.DB_NAME
    var openHelper: DaoMaster.DevOpenHelper? = null
    var daoMaster_read: DaoMaster? = null
    var daoMaster_writer: DaoMaster? = null
    var daoSession: DaoSession? = null

    init {
        openHelper = DaoMaster.DevOpenHelper(MyApplication.context, DB_NAME, null)
        daoMaster_writer = DaoMaster(openHelper!!.writableDb)
        daoMaster_read = DaoMaster(openHelper!!.readableDb)
        Log.e("TAG", "init")
    }

    fun getReadDao(entityClass: Class<out Any>): AbstractDao<out Any, out Any> {
        daoSession = daoMaster_read!!.newSession()
        return daoSession!!.getDao(entityClass)
    }

    /**
     * 获取 writeable Dao
     *
     * @param entityClass
     * @return
     */
    fun getWriteDao(entityClass: Class<out Any>): AbstractDao<out Any, out Any> {
        daoSession = daoMaster_writer!!.newSession()
        return daoSession!!.getDao(entityClass)
    }

//    /**
//     * 插入或更新
//     *
//     * @param entityClass
//     * @param object
//     * @return
//     */
//    fun insertOrReplace(entityClass: Class<out Any>, obj: Any?): Long {
//        return if (null == obj) {
//            -1L
//        } else getWriteDao(entityClass).insertOrReplace(obj!!)
//    }

//    /**
//     * 插入单个的实体
//     *
//     * @param entityClass
//     * @return
//     */
//    fun insert(entityClass: Class<out Any>, obj: Any?): Long {
//        return if (null == obj) {
//            -1L
//        } else getWriteDao(entityClass).insert(obj)
//    }


    /**
     * 精确查找某个实体
     *
     * @param entityClass 目标表名
     * @param properties  目标属性
     * @param objects     匹配条件
     * @return
     */
    fun query(entityClass: Class<out Any>, properties: List<Property>?, objects: List<Any>): Any? {
        if (null == properties || properties.isEmpty()) {
            return null
        }
        val size = properties.size
        val queryBuilder = getWriteDao(entityClass).queryBuilder()
        for (i in 0 until size) {
            queryBuilder.where(properties[i].eq(objects[i]))
        }

        return queryBuilder.build().unique()
    }

}