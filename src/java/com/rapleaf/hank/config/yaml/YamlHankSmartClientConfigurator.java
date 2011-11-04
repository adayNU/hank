/**
 *  Copyright 2011 Rapleaf
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.rapleaf.hank.config.yaml;

import com.rapleaf.hank.config.HankSmartClientConfigurator;
import com.rapleaf.hank.config.InvalidConfigurationException;

import java.io.FileNotFoundException;

public class YamlHankSmartClientConfigurator extends BaseYamlConfigurator implements HankSmartClientConfigurator {

  private static final String HANK_SMART_CLIENT_SECTION_KEY = "hank_smart_client";
  private static final String RING_GROUP_NAME_KEY = "ring_group_name";
  private static final String NUM_CONNECTIONS_PER_HOST_KEY = "num_connections_per_host";
  private static final String QUERY_TIMEOUT_MS_KEY = "query_timeout_ms";

  public YamlHankSmartClientConfigurator(String configurationPath) throws FileNotFoundException, InvalidConfigurationException {
    super(configurationPath);
  }

  @Override
  public void validate() throws InvalidConfigurationException {
    super.validate();
    getRequiredSection(HANK_SMART_CLIENT_SECTION_KEY);
    getRequiredString(HANK_SMART_CLIENT_SECTION_KEY, RING_GROUP_NAME_KEY);
    getRequiredInteger(HANK_SMART_CLIENT_SECTION_KEY, NUM_CONNECTIONS_PER_HOST_KEY);
    getRequiredInteger(HANK_SMART_CLIENT_SECTION_KEY, QUERY_TIMEOUT_MS_KEY);
  }

  @Override
  public String getRingGroupName() {
    return getString(HANK_SMART_CLIENT_SECTION_KEY, RING_GROUP_NAME_KEY);
  }

  @Override
  public int getNumConnectionsPerHost() {
    return getInteger(HANK_SMART_CLIENT_SECTION_KEY, NUM_CONNECTIONS_PER_HOST_KEY);
  }

  @Override
  public int getQueryTimeoutMS() {
    return getInteger(HANK_SMART_CLIENT_SECTION_KEY, QUERY_TIMEOUT_MS_KEY);
  }
}
