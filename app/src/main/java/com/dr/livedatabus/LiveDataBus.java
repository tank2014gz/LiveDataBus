package com.dr.livedatabus;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * 项目名称：LiveDataBus
 * 类描述：
 * 创建人：yuliyan
 * 创建时间：2019/4/6 8:48 PM
 * 修改人：yuliyan
 * 修改时间：2019/4/6 8:48 PM
 * 修改备注：
 */
public class LiveDataBus {
    //创建一个数据装载数据的map
    private Map<String, BusMutableLiveData<Object>> bus;
    
    private LiveDataBus() {
        bus = new HashMap<>();
    }
    
    private static class SingleonHolder {
        private static final LiveDataBus DEFAULT_BUS = new LiveDataBus();
    }
    
    public static LiveDataBus get() {
        
        return SingleonHolder.DEFAULT_BUS;
    }
    
    public synchronized <T> MutableLiveData<T> with(String key, Class<T> type) {
        if (!bus.containsKey(key)) {
            bus.put(key, new BusMutableLiveData<Object>());
        }
        return (BusMutableLiveData<T>) bus.get(key);
    }
    
    
    public static class BusMutableLiveData<T> extends MutableLiveData<T> {
        
        @Override
        public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
            super.observe(owner, observer);
            try {
                hookObserver(observer);
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
        
        private void hookObserver(Observer<? super T> observer) throws NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
            //获得LivaData的Class
            Class<LiveData> classLiveData = LiveData.class;
            //通反射技术获取class里面的mObserver属性对象
            Field fieldMObservers = classLiveData.getDeclaredField("mObservers");
            //设置属性的可被访问
            fieldMObservers.setAccessible(true);
            //获取字段是this这个对象中的值，他的值是一个Map集合
            Object objectObservers = fieldMObservers.get(this);
            
            //得到map对象类型SafeIterableMap.getClass()
            Class<?> classObsevers = objectObservers.getClass();
            
            //获取map对象中所有的get方法
            Method methodGet = classObsevers.getDeclaredMethod("get", Object.class);
            //设置get方法的访问权限
            methodGet.setAccessible(true);
            //执行get方法传入objectObservers对象，然后传入observer作为key获得取值
            Object objectWrapperEntry = methodGet.invoke(objectObservers, observer);
            //定义一个空的Object对象
            Object objectWrapper = null;
            
            //判断objectWrapperEntry是否是Map.Entry类型
            if (objectWrapperEntry instanceof Map.Entry) {
                //获取map中的Obsever的值
                objectWrapper = ((Map.Entry) objectWrapperEntry).getValue();
            }
            if (objectWrapper == null) {
                throw new NullPointerException("Wrapper can not be null");
            }
            //如果不是空，就得到Object对象的父类
            Class<?> classObserverWrapper = objectWrapper.getClass().getSuperclass();
            //通过父类的class对象获取mLastVersion字段
            Field fieldLastVersion = classObserverWrapper.getDeclaredField("mLastVersion");
            //设置mLastVersion字段被访问
            fieldLastVersion.setAccessible(true);
            //获得mVersion的字段对象
            Field filedVersion=classLiveData.getDeclaredField("mVersion");
            filedVersion.setAccessible(true);
            //获取mVersion的值
            Object objectVersion=filedVersion.get(this);
            //将mLastVersion的值设置为mVersion
            fieldLastVersion.set(objectWrapper, objectVersion);
            
        }
        
    }
    
    
}
