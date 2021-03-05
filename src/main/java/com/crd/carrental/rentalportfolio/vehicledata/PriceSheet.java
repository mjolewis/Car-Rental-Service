package com.crd.carrental.rentalportfolio.vehicledata;

import java.math.BigDecimal;

/**********************************************************************************************************************
 * A set of predefined vehicle prices. Used by the Vehicle enum to ensure system consistency.
 *
 * @author Michael Lewis
 *********************************************************************************************************************/
public enum PriceSheet {
    HIGH_END(new BigDecimal("125.00")),
    MID_TIER(new BigDecimal("100.00")),
    LOW_END(new BigDecimal("75.00"));

    private final BigDecimal price;

    PriceSheet(final BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
