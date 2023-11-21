package de.ostfalia.bootablejarstarter.dvd_rental_customer.rest;

import java.io.IOException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import lombok.Getter;

/**
 * Klasse, um die Base URLs zur Verfügung zu stellen
 */
@Getter
@ApplicationScoped
public class UrlProperties {
    private Properties props;
    private String customerBase;
    private String filmBase;
    private String storeBase;

    /**
     * Setzt die Base-Urls der einzelnen Services, abhängig davon, ob der Service lokal oder in einem Docker-Container läuft.
     * @throws IOException
     */
    @PostConstruct
    public void init() throws IOException {
        props = new Properties();
        if(this.getClass().getResourceAsStream("/urlsLocal.properties") != null) {
            props.load(this.getClass().getResourceAsStream("/urlsLocal.properties"));
        }
        customerBase = props.getProperty("customerBase");
    }

}