/**
 * Copyright StrongAuth, Inc. All Rights Reserved.
 *
 * Use of this source code is governed by the Gnu Lesser General Public License 2.3.
 * The license can be found at https://github.com/StrongKey/fido2/LICENSE
 */

package com.strongkey.skfs.fido.policyobjects;

import com.strongkey.skfs.utilities.skfsCommon;
import com.strongkey.skfs.utilities.skfsConstants;
import javax.json.JsonObject;

public class CounterPolicyOptions {
    private final Boolean isCounterRequired;
    private final Boolean isCounterIncreaseRequired;
    
    private CounterPolicyOptions(Boolean isCounterRequired, Boolean isCounterIncreaseRequired){
        this.isCounterRequired = isCounterRequired;
        this.isCounterIncreaseRequired = isCounterIncreaseRequired;
    }

    public Boolean getIsCounterRequired() {
        return isCounterRequired;
    }

    public Boolean getIsCounterIncreaseRequired() {
        return isCounterIncreaseRequired;
    }
    
    public static CounterPolicyOptions parse(JsonObject counterJson) {
        return new CounterPolicyOptions.CounterPolicyOptionsBuilder(
                skfsCommon.handleNonExistantJsonBoolean(counterJson, skfsConstants.POLICY_COUNTER_REQUIRECOUNTER),
                skfsCommon.handleNonExistantJsonBoolean(counterJson, skfsConstants.POLICY_COUNTER_REQUIRECOUNTERINCREASE))
                .build();
    }
    
    public static class CounterPolicyOptionsBuilder{
        private final Boolean builderIsCounterRequired;
        private final Boolean builderIsCounterIncreaseRequired;
        
        public CounterPolicyOptionsBuilder(Boolean isCounterRequired, Boolean isCounterIncreaseRequired){
            this.builderIsCounterRequired = isCounterRequired;
            this.builderIsCounterIncreaseRequired = isCounterIncreaseRequired;
        }
        public CounterPolicyOptions build(){
            return new CounterPolicyOptions(builderIsCounterRequired, builderIsCounterIncreaseRequired);
        }
    }
}
