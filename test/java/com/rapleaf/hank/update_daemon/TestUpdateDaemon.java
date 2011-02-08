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
package com.rapleaf.hank.update_daemon;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.Set;
import java.util.SortedSet;

import junit.framework.TestCase;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.rapleaf.hank.config.DomainConfig;
import com.rapleaf.hank.config.DomainConfigVersion;
import com.rapleaf.hank.config.DomainGroupConfig;
import com.rapleaf.hank.config.DomainGroupConfigVersion;
import com.rapleaf.hank.config.MockUpdateDaemonConfigurator;
import com.rapleaf.hank.config.PartDaemonAddress;
import com.rapleaf.hank.config.PartDaemonConfigurator;
import com.rapleaf.hank.config.RingConfig;
import com.rapleaf.hank.config.RingGroupConfig;
import com.rapleaf.hank.config.UpdateDaemonConfigurator;
import com.rapleaf.hank.coordinator.Coordinator;
import com.rapleaf.hank.coordinator.DaemonState;
import com.rapleaf.hank.coordinator.DaemonType;
import com.rapleaf.hank.coordinator.RingState;
import com.rapleaf.hank.exception.DataNotFoundException;
import com.rapleaf.hank.partitioner.Partitioner;
import com.rapleaf.hank.storage.OutputStreamFactory;
import com.rapleaf.hank.storage.Reader;
import com.rapleaf.hank.storage.StorageEngine;
import com.rapleaf.hank.storage.Updater;
import com.rapleaf.hank.storage.Writer;

public class TestUpdateDaemon extends TestCase {
  static {
    Logger.getRootLogger().setLevel(Level.ALL);
  }

  public void testStateChange() throws Exception {
    final MockUpdater mockUpdater = new MockUpdater();

    final StorageEngine mockStorageEngine = new StorageEngine() {
      @Override
      public Writer getWriter(OutputStreamFactory streamFactory, int partNum,
          int versionNumber, boolean base) throws IOException {
        return null;
      }

      @Override
      public Updater getUpdater(UpdateDaemonConfigurator configurator, int partNum) {
        return mockUpdater;
      }

      @Override
      public Reader getReader(PartDaemonConfigurator configurator, int partNum)
          throws IOException {
        return null;
      }
    };

    final RingConfig mockRingConfig = new RingConfig() {
      @Override
      public Set<Integer> getDomainPartitionsForHost(
          PartDaemonAddress hostAndPort, int domainId)
      throws DataNotFoundException {
        return Collections.singleton(0);
      }

      @Override
      public Set<PartDaemonAddress> getHosts() {
        return null;
      }

      @Override
      public Set<PartDaemonAddress> getHostsForDomainPartition(
          int domainId, int partId) {
        return null;
      }

      @Override
      public RingGroupConfig getRingGroupConfig() {
        return null;
      }

      @Override
      public int getRingNumber() {
        return 0;
      }

      @Override
      public RingState getState() {
        return null;
      }
    };

    final DomainGroupConfig mockDomainGroupConfig = new DomainGroupConfig() {
      @Override
      public SortedSet<DomainGroupConfigVersion> getVersions() {
        return null;
      }

      @Override
      public String getName() {
        return "myDomainGroup";
      }

      @Override
      public DomainGroupConfigVersion getLatestVersion() {
        return new DomainGroupConfigVersion() {
          @Override
          public int getVersionNumber() {
            return 0;
          }

          @Override
          public DomainGroupConfig getDomainGroupConfig() {
            return null;
          }

          @Override
          public Set<DomainConfigVersion> getDomainConfigVersions() {
            DomainConfigVersion arg0 = new DomainConfigVersion() {
              @Override
              public int getVersionNumber() {
                return 0;
              }

              @Override
              public DomainConfig getDomainConfig() {
                return new DomainConfig() {
                  @Override
                  public String getName() {
                    return "myDomain";
                  }

                  @Override
                  public int getNumParts() {
                    return 1;
                  }

                  @Override
                  public Partitioner getPartitioner() {
                    return new Partitioner() {
                      @Override
                      public int partition(ByteBuffer key) {
                        return 0;
                      }
                    };
                  }

                  @Override
                  public StorageEngine getStorageEngine() {
                    return mockStorageEngine;
                  }

                  @Override
                  public int getVersion() {
                    return 0;
                  }
                };
              }
            };
            return Collections.singleton(arg0);
          }
        };
      }

      @Override
      public int getDomainId(String domainName) throws DataNotFoundException {
        // TODO Auto-generated method stub
        return 0;
      }
      
      @Override
      public DomainConfig getDomainConfig(int domainId)
          throws DataNotFoundException {
        // TODO Auto-generated method stub
        return null;
      }
    };
    
    final RingGroupConfig mockRingGroupConfig = new RingGroupConfig() {
      @Override
      public DomainGroupConfig getDomainGroupConfig() {
        return mockDomainGroupConfig;
      }

      @Override
      public String getName() {
        return "myRingGroup";
      }

      @Override
      public RingConfig getRingConfig(int ringNumber)
          throws DataNotFoundException {
        return null;
      }

      @Override
      public RingConfig getRingConfigForHost(PartDaemonAddress hostAddress)
          throws DataNotFoundException {
        return mockRingConfig;
      }

      @Override
      public Set<RingConfig> getRingConfigs() {
        // TODO Auto-generated method stub
        return null;
      }
    };

    final Coordinator mockCoordinator = new Coordinator() {
      private DaemonState daemonState;

      @Override
      public void addDaemonStateChangeListener(String ringGroupName,
          int ringNumber, PartDaemonAddress hostAddress, DaemonType type,
          DaemonStateChangeListener listener) {
      }

      @Override
      public void addDomainChangeListener(String domainName,
          DomainChangeListener listener) throws DataNotFoundException {
      }

      @Override
      public void addDomainGroupChangeListener(String domainGroupName,
          DomainGroupChangeListener listener) throws DataNotFoundException {
      }

      @Override
      public void addRingGroupChangeListener(String ringGroupName,
          RingGroupChangeListener listener) throws DataNotFoundException {
      }

      @Override
      public DaemonState getDaemonState(String ringGroupName, int ringNumber,
          PartDaemonAddress hostAddress, DaemonType type) {
        return daemonState;
      }

      @Override
      public DomainConfig getDomainConfig(String domainName)
          throws DataNotFoundException {
        return null;
      }

      @Override
      public DomainGroupConfig getDomainGroupConfig(String domainGroupName)
          throws DataNotFoundException {
        return null;
      }

      @Override
      public RingConfig getRingConfig(String ringGroupName, int ringNumber)
          throws DataNotFoundException {
        return null;
      }

      @Override
      public RingGroupConfig getRingGroupConfig(String ringGroupName)
          throws DataNotFoundException {
        return mockRingGroupConfig;
      }

      @Override
      public void setDaemonState(String ringGroupName, int ringNumber,
          PartDaemonAddress hostAddress, DaemonType type, DaemonState state) {
        daemonState = state;
      }

      @Override
      public int updateDomain(String domainName) throws DataNotFoundException {
        return 0;
      }
    };

    MockUpdateDaemonConfigurator mockConfigurator = new MockUpdateDaemonConfigurator(1, null, 12345, mockCoordinator, "myRingGroup", 1);

    UpdateDaemon ud = new UpdateDaemon(mockConfigurator, "localhost");

    // should move smoothly from updateable to idle
    ud.onDaemonStateChange(null, 0, null, null, DaemonState.UPDATEABLE);
    assertEquals("Daemon state is now in IDLE",
        DaemonState.IDLE,
        mockCoordinator.getDaemonState(null, 0, new PartDaemonAddress("localhost", 12345), null));
    assertTrue("update() was called on the storage engine", mockUpdater.isUpdated());
  }
}
