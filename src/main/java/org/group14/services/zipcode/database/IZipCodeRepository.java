package org.group14.services.zipcode.database;

import org.group14.domain.exceptions.zipcode.ZipCodeNotFound;
import org.group14.domain.models.ZipCode;

public interface IZipCodeRepository {
    ZipCode findByCode(String code) throws ZipCodeNotFound;
}
