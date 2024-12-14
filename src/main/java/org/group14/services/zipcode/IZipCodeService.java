package org.group14.services.zipcode;

import org.group14.domain.exceptions.zipcode.ZipCodeNotFound;
import org.group14.domain.models.ZipCode;

public interface IZipCodeService {
    ZipCode getCoordiantesFromZipCode(String zipCode) throws ZipCodeNotFound;
}
