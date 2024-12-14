package org.group14.services.zipcode;

import org.group14.domain.exceptions.zipcode.EmptyZipCodeException;
import org.group14.domain.exceptions.zipcode.InvalidZipCodeException;
import org.group14.domain.exceptions.zipcode.ZipCodeNotFound;
import org.group14.domain.models.ZipCode;

public class ZipCodeController {

    private static final String ZIPCODE_PATTERN = "^[1-9][0-9]{3}[A-Z]{2}$";

    private ZipCodeServiceEnums defaultZipCodeService = ZipCodeServiceEnums.DATABASE;
    private IZipCodeService zipCodeService;

    public ZipCodeController() {
        setDefaultZipCodeService(defaultZipCodeService);
    }

    public ZipCodeController(ZipCodeServiceEnums defaultZipCodeService) {
        this.defaultZipCodeService = defaultZipCodeService;
        setDefaultZipCodeService(defaultZipCodeService);
    }

    public void setDefaultZipCodeService(ZipCodeServiceEnums defaultZipCodeService) {
        this.zipCodeService = ZipCodeServiceFactory.createService(defaultZipCodeService);
    }

    public ZipCodeController(IZipCodeService zipCodeService) {
        this.zipCodeService = zipCodeService;
    }

    public ZipCode getZipCode(String zipCode) throws ZipCodeNotFound, InvalidZipCodeException, EmptyZipCodeException {
        if (zipCode.isBlank()) {
            throw new EmptyZipCodeException(zipCode);
        }
        if (!zipCode.matches(ZIPCODE_PATTERN)) {
            throw new InvalidZipCodeException(zipCode);
        }
        return zipCodeService.getCoordiantesFromZipCode(zipCode);
    }
    
}
