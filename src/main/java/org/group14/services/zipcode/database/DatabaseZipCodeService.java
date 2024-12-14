package org.group14.services.zipcode.database;

import org.group14.domain.exceptions.zipcode.ZipCodeNotFound;
import org.group14.domain.models.ZipCode;
import org.group14.services.datasource.DataSourceFactory;
import org.group14.services.zipcode.IZipCodeService;

public class DatabaseZipCodeService implements IZipCodeService {

    private final DatabaseZipCodeRepository repository;
    
    public DatabaseZipCodeService() {
        this.repository = new DatabaseZipCodeRepository(DataSourceFactory.createDataSource());
    }

    @Override
    public ZipCode getCoordiantesFromZipCode(String zipCode) throws ZipCodeNotFound {
        return repository.findByCode(zipCode);
    }
    
}
