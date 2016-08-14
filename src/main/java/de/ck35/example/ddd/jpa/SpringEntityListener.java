package de.ck35.example.ddd.jpa;

import javax.persistence.EntityListeners;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * Entity listener which allows dependency injection inside entities.
 * The listener can be registered via {@link EntityListeners} annotation.
 * 
 * Dependency injection annotations like {@link Autowired} are supported.
 * 
 * @author Christian Kaspari
 * @since 1.0.0
 */
public class SpringEntityListener {

    private static final Logger LOG = LoggerFactory.getLogger(SpringEntityListener.class);
    
    private static final SpringEntityListener INSTANCE = new SpringEntityListener();
    
    private volatile AutowireCapableBeanFactory beanFactory;
    
    public static SpringEntityListener get() {
        return INSTANCE;
    }
    public AutowireCapableBeanFactory getBeanFactory() {
        return beanFactory;
    }
    public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    
    @PostLoad
    @PostPersist
    public void inject(Object object) {
        AutowireCapableBeanFactory beanFactory = get().getBeanFactory();
        if(beanFactory == null) {
            LOG.warn("Bean Factory not set! Depdendencies will not be injected into: '{}'", object);
            return;
        }
        LOG.debug("Injecting dependencies into entity: '{}'.", object);
        beanFactory.autowireBean(object);
    }
    
}