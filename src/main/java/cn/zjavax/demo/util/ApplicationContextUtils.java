//package cn.zjavax.demo.util;
//
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//
///**
// * 容器bean调用工具
// */
//public class ApplicationContextUtils {
//    private static ApplicationContext applicationContext = null;
//
//    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        if (ApplicationContextUtils.applicationContext == null) {
//            ApplicationContextUtils.applicationContext = applicationContext;
//        }
//    }
//
//    /**
//     * 获取应用上下文
//     * @return
//     */
//    public static ApplicationContext getApplicationContext() {
//        return applicationContext;
//    }
//
//    /**
//     * 根据bean的name获取bean
//     * @param name
//     * @return
//     */
//    public static Object getBean(String name) {
//
//        return getApplicationContext().getBean(name);
//
//    }
//
//    /**
//     * 根据class获取bean
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public static <T> T getBean(Class<T> clazz) {
//        return getApplicationContext().getBean(clazz);
//    }
//
//    /**
//     * 根据name和class获取bean
//     * @param name
//     * @param clazz
//     * @param <T>
//     * @return
//     */
//    public static <T> T getBean(String name, Class<T> clazz) {
//        return getApplicationContext().getBean(name, clazz);
//    }
//}
