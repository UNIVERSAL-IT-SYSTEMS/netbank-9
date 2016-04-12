/*
 * Copyright 2016 Szabolcs Balazs Beres.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.szberes.netbank.backend.controllers;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.assertj.core.api.iterable.Extractor;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

public class RestTransactionRequestTest {

    private Validator validator;

    @Before
    public void init() {
        ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
        validator = vf.getValidator();
    }

    @Test
    public void testPropertiesCannotBeNull() {
        RestTransactionRequest restTransactionRequest = request(null, null, null);
        Set<ConstraintViolation<RestTransactionRequest>> violations = this.validator.validate(restTransactionRequest);
        assertThat(violations)
                .extracting(constrainViolationPathExtractor())
                .contains("sourceAccountId", "destinationAccountId", "amount");
    }

    @Test
    public void testIdsCannotBeNegative() {
        RestTransactionRequest restTransactionRequest = request(-1L, -2L, 42L);
        Set<ConstraintViolation<RestTransactionRequest>> violations = this.validator.validate(restTransactionRequest);
        assertThat(violations)
                .extracting(constrainViolationPathExtractor())
                .contains("sourceAccountId", "destinationAccountId");
    }

    @Test
    public void testAmountMustBeGreaterThanZero() {
        RestTransactionRequest restTransactionRequest = request(2L, 1L, 0L);
        Set<ConstraintViolation<RestTransactionRequest>> violations = this.validator.validate(restTransactionRequest);
        assertThat(violations)
                .extracting(constrainViolationPathExtractor())
                .containsOnly("amount");
    }

    @Test
    public void testValidRequest() {
        RestTransactionRequest restTransactionRequest = request(2L, 1L, 5L);
        Set<ConstraintViolation<RestTransactionRequest>> violations = this.validator.validate(restTransactionRequest);
        assertTrue(violations.isEmpty());
    }

    private Extractor<ConstraintViolation<RestTransactionRequest>, String> constrainViolationPathExtractor() {
        return violation -> violation.getPropertyPath().toString();
    }

    private RestTransactionRequest request(Long sourceAccountId, Long destinationAccountId, Long amount) {
        RestTransactionRequest restTransactionRequest = new RestTransactionRequest();
        restTransactionRequest.setSourceAccountId(sourceAccountId);
        restTransactionRequest.setDestinationAccountId(destinationAccountId);
        restTransactionRequest.setAmount(amount);
        return restTransactionRequest;
    }
}