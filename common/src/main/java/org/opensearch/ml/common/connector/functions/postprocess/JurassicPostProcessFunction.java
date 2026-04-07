/*
 * Copyright OpenSearch Contributors
 * SPDX-License-Identifier: Apache-2.0
 */

package org.opensearch.ml.common.connector.functions.postprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.opensearch.ml.common.output.model.MLResultDataType;
import org.opensearch.ml.common.output.model.ModelTensor;

public class JurassicPostProcessFunction extends ConnectorPostProcessFunction<String> {

    @Override
    public void validate(Object input) {
        if (!(input instanceof String)) {
            throw new IllegalArgumentException("Post process function input is not a String.");
        }
    }

    @Override
    public List<ModelTensor> process(String completionText, MLResultDataType dataType) {
        List<ModelTensor> modelTensors = new ArrayList<>();
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("response", completionText);
        modelTensors.add(ModelTensor.builder().name("response").dataAsMap(resultMap).build());
        return modelTensors;
    }
}