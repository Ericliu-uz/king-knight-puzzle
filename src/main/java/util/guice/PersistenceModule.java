package util.guice;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;

/**
 * Persistence Module
 */
public class PersistenceModule extends AbstractModule {

    private String jpaUnit;

    /**
     * Persistence Module constructor
     * @param jpaUnit get jpa unit
     */
    public PersistenceModule(String jpaUnit) {
        this.jpaUnit = jpaUnit;
    }

    @Override
    protected void configure() {
        install(new JpaPersistModule(jpaUnit));
        bind(JpaInitializer.class).asEagerSingleton();
    }

}
