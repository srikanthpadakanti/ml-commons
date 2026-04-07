/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.ml.common.connector.functions.postprocess;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.opensearch.ml.common.output.model.ModelTensor;

public class JurassicPostProcessFunctionTest {
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    JurassicPostProcessFunction function;

    @Before
    public void setUp() {
        function = new JurassicPostProcessFunction();
    }

    @Test
    public void process_WrongInput_NotString() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Post process function input is not a String.");
        function.apply(Arrays.asList(1, 2, 3), null);
    }

    @Test
    public void process_WrongInput_Integer() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Post process function input is not a String.");
        function.apply(123, null);
    }

    @Test
    public void process_NullInput() {
        exceptionRule.expect(IllegalArgumentException.class);
        exceptionRule.expectMessage("Can't run post process function as model output is null");
        function.apply(null, null);
    }

    @Test
    public void process_CorrectInput() {
        List<ModelTensor> result = function.apply("The meaning of life is 42.", null);
        assertEquals(1, result.size());
        assertEquals("response", result.get(0).getName());
        assertNotNull(result.get(0).getDataAsMap());
        assertEquals("The meaning of life is 42.", result.get(0).getDataAsMap().get("response"));
    }

    @Test
    public void process_EmptyString() {
        List<ModelTensor> result = function.apply("", null);
        assertEquals(1, result.size());
        assertEquals("response", result.get(0).getName());
        assertEquals("", result.get(0).getDataAsMap().get("response"));
    }
}