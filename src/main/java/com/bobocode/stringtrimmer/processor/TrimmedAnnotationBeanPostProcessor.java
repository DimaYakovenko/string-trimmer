package com.bobocode.stringtrimmer.processor;

import com.bobocode.stringtrimmer.annotation.Trimmed;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class TrimmedAnnotationBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Trimmed.class)) {
            return createTrimmedBeanProxy(bean);
        }
        return bean;
    }

    private Object createTrimmedBeanProxy(Object bean) {
        var enhancer = new Enhancer();
        final Class<?> beanType = bean.getClass();
        enhancer.setSuperclass(beanType);
        enhancer.setInterfaces(beanType.getInterfaces());
        MethodInterceptor callback = (obj, method, args, proxy) -> {
            for (int i = 0; i < args.length; i++) {
                if (args[i] instanceof String) {
                    args[i] = ((String) args[i]).trim();
                }
            }
            if (method.getReturnType() == String.class) {
                return ((String) proxy.invokeSuper(obj, args)).trim();
            } else {
                return proxy.invokeSuper(bean, args);
            }
        };
        enhancer.setCallback(callback);
        return beanType.cast(enhancer.create());
    }
}
