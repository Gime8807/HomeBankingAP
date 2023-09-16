package com.mindhub.utils;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CardUtilsTest {

    @Test
    public void getRandomNumberCvv() {
        Integer cvv= CardUtils.getRandomNumberCvv(0,999);
            assertThat(cvv, greaterThanOrEqualTo(0));
            assertThat(cvv, lessThanOrEqualTo(999));

    }

    @Test
    public void getRandomNumberCard() {
      String numberCard = CardUtils.getRandomNumberCard();
      assertThat(numberCard,is(not(emptyOrNullString())));
    }

    @Test
    public void cvvNumberIsCreated(){
        int cvv = CardUtils.getRandomNumberCvv(0,999);
        assertThat(cvv,is(not(nullValue())));
    }
}