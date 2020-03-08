package cn.sbx0.zhibei.service;

import cn.sbx0.zhibei.tool.StringTools;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公用基础 服务层
 *
 * @param <T>  实体类类型
 * @param <ID> 一般为Integer
 */
public abstract class BaseService<T, ID> {
    private static final Integer PAGE_SIZE = 10;
    private static final String[] NOT_NULL_METHODS = {}; // 对象指定对象的get方法

    /**
     * 获取数据层 子类必须重写
     *
     * @return 对应的数据层
     */
    public abstract PagingAndSortingRepository<T, ID> getDao();

    /**
     * 获取一个空的实体
     *
     * @return 对应的实体
     */
    public abstract T getEntity();

    /**
     * 获取对象属性类型，属性名
     */
    public List getAttribute() {
        Field[] fields = getEntity().getClass().getDeclaredFields();
        List<Map> list = new ArrayList();
        for (int i = 0; i < fields.length; i++) {
            if (!fields[i].getName().equals("serialVersionUID")) {
                Map infoMap = new HashMap();
                infoMap.put("type", fields[i].getType().getSimpleName());
                infoMap.put("name", fields[i].getName());
                list.add(infoMap);
            }
        }
        return list;
    }

    /**
     * 检查对象指定属性是否为空字符或NULL 需要为属性设置get方法
     *
     * @param object 需要判断的对象
     * @return true为空或NULL
     */
    public static boolean checkObjectFieldsIsNull(Object object) {
        // 获取对象下所有方法名
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            for (String notNullMethod : NOT_NULL_METHODS) {
                // 匹配指定不为空的属性的get方法
                if (method.getName().equals(notNullMethod)) {
                    try {
                        Object[] args = new Object[0];
                        // 执行get方法并获取返回结果
                        Object result = method.invoke(object, args);
                        // 判断返回结构是否为null 或 空字符串
                        if (StringTools.checkNullStr(result.toString())) {
                            return false;
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    /**
     * 保存
     *
     * @param entity 实体类
     * @return 操作是否成功
     */
    @Transactional
    public boolean save(T entity) {
        try {
            getDao().save(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    /**
     * 批量保存
     *
     * @param entities 实体类列表
     * @return 操作是否成功
     */
    @Transactional
    public boolean saveAll(Iterable<T> entities) {
        try {
            getDao().saveAll(entities);
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    /**
     * 根据ID查询实体
     *
     * @param id Id
     * @return 实体
     */
    public T findById(ID id) {
        try {
            return getDao().findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据ID查询存不存在
     *
     * @param id ID
     * @return 操作是否成功
     */
    public boolean existsById(ID id) {
        return getDao().existsById(id);
    }

    /**
     * 查询全部
     *
     * @return 查询列表
     */
    public Iterable<T> findAll() {
        try {
            return getDao().findAll();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据ID列表查询全部
     *
     * @param ids id列表
     * @return 查询结果
     */
    public Iterable<T> findAllById(Iterable<ID> ids) {
        try {
            return getDao().findAllById(ids);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 计数
     *
     * @return 计数
     */
    public long count() {
        return getDao().count();
    }

    /**
     * 根据ID删除实体类
     *
     * @param id ID
     * @return 操作是否成功
     */
    @Transactional
    public boolean deleteById(ID id) {
        try {
            getDao().deleteById(id);
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    /**
     * 删除指定的实体
     *
     * @param entity 实体类
     * @return 操作是否成功
     */
    @Transactional
    public boolean delete(T entity) {
        try {
            getDao().delete(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    /**
     * 根据实体类删除
     *
     * @param entities 实体类列表
     * @return 操作是否成功
     */
    @Transactional
    public boolean deleteAll(Iterable<? extends T> entities) {
        try {
            getDao().deleteAll(entities);
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    /**
     * 删除所有
     *
     * @return 操作是否成功
     */
    @Transactional
    public boolean deleteAll() {
        try {
            getDao().deleteAll();
            return true;
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }
    }

    /**
     * 分页查询全部
     *
     * @param pageable 分页
     * @return 已经分页的
     */
    public Page<T> findAll(Pageable pageable) {
        return getDao().findAll(pageable);
    }

    /**
     * 排序查询全部
     *
     * @param sort Sort
     * @return 排序列表
     */
    public Iterable<T> findAll(Sort sort) {
        return getDao().findAll(sort);
    }

    /**
     * 拼接Pageable
     *
     * @param page
     * @param size
     * @param attribute
     * @param direction
     * @return
     */
    public static Pageable buildPageable(Integer page, Integer size, String attribute, String direction) {
        if (page == null) page = 1;
        if (size == null) size = 10;
        if (attribute == null) attribute = "id";
        if (direction == null) direction = "desc";
        Sort sort = buildSort(attribute, direction);
        // 页数控制
        if ((page - 1) >= 0) page = page - 1;
        else page = 0;
        // 条数控制
        if (size > 999) size = 999;
        if (size < 1) size = PAGE_SIZE;
        // 分页配置
        return PageRequest.of(page, size, sort);
    }

    /**
     * 构建Sort
     *
     * @param attribute 属性
     * @param direction 排序
     * @return Sort
     */
    public static Sort buildSort(String attribute, String direction) {
        switch (direction) {
            case "ASC": // 升序
                return Sort.by(Sort.Direction.ASC, attribute);
            case "DESC": // 降序
                return Sort.by(Sort.Direction.ASC, attribute);
            default: // 默认降序
                return Sort.by(Sort.Direction.ASC, attribute);
        }
    }

}