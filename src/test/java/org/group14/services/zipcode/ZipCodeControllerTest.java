package org.group14.services.zipcode;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.group14.domain.exceptions.zipcode.EmptyZipCodeException;
import org.group14.domain.exceptions.zipcode.InvalidZipCodeException;
import org.group14.domain.exceptions.zipcode.ZipCodeNotFound;
import org.group14.domain.models.Coordinate;
import org.group14.domain.models.ZipCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

public class ZipCodeControllerTest {

    @Mock
    private IZipCodeService zipCodeService;

    private ZipCode zipCode = new ZipCode(
            "6229HD",
            new Coordinate(
                    50.0,
                    5.0));

    @BeforeEach
    public void setUp() throws ZipCodeNotFound {
        MockitoAnnotations.openMocks(this);
        Mockito.when(zipCodeService.getCoordiantesFromZipCode("6229HD")).thenReturn(zipCode);
    }

    @Test
    public void testGetZipCode() throws ZipCodeNotFound, InvalidZipCodeException, EmptyZipCodeException {
        ZipCodeController zipCodeController = new ZipCodeController(zipCodeService);
        assertEquals(zipCodeController.getZipCode("6229HD"), zipCode);
    }

}