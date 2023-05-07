/*
 * Description：支持小米便签运行过程中的运行异常处理。
 */
 
package net.micode.notes.gtask.exception;
 
public class ActionFailureException extends RuntimeException {
    private static final long serialVersionUID = 4425249765923293627L;
    /*
     * serialVersionUID相当于java类的身份证。主要用于版本控制。
     * serialVersionUID作用是序列化时保持版本的兼容性，即在版本升级时反序列化仍保持对象的唯一性。
     * Made By Cuican
     */
    
    public ActionFailureException() {
        super();
    }
    /*
     * 在JAVA类中使用super来引用父类的成分，用this来引用当前对象.
     * 如果一个类从另外一个类继承，我们new这个子类的实例对象的时候，这个子类对象里面会有一个父类对象。
     * 怎么去引用里面的父类对象呢？使用super来引用
     * 也就是说，此处super()以及super (paramString)可认为是Exception ()和Exception (paramString)
     * Made By Cuican
     */
    public ActionFailureException(String paramString) {
        super(paramString);
    }
 
    public ActionFailureException(String paramString, Throwable paramThrowable) {
        super(paramString, paramThrowable);
    }
}
