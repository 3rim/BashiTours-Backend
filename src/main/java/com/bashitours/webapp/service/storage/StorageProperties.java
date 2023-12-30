package com.bashitours.webapp.service.storage;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("storage")
@Data
public class StorageProperties {
    /**
     * Folder root-location for stored files
     */
    private String location ="upload-dir";
    private String visitedCountriesDirLocation = location+"/visited-countries-dir";
}
