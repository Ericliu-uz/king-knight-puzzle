package util.guice;

import com.google.inject.persist.PersistService;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Jpa Initializer
 */
@Singleton
public class JpaInitializer {

    /**
     * Jpa Initializer constructor
     * @param persistService start service
     */
    @Inject
    public JpaInitializer (PersistService persistService) {
        persistService.start();
    }

}
