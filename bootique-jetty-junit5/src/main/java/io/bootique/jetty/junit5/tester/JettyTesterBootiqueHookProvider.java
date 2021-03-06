/*
 * Licensed to ObjectStyle LLC under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ObjectStyle LLC licenses
 * this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package io.bootique.jetty.junit5.tester;

import io.bootique.jetty.server.ServerHolder;

import javax.inject.Inject;
import javax.inject.Provider;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @since 2.0
 */
public class JettyTesterBootiqueHookProvider implements Provider<JettyTesterBootiqueHook> {


    @Inject
    ServerHolder serverHolder;

    private JettyTesterBootiqueHook instance;

    public JettyTesterBootiqueHookProvider(JettyTesterBootiqueHook instance) {
        this.instance = instance;
    }

    @Override
    public JettyTesterBootiqueHook get() {
        assertNotNull(serverHolder, "ServerHolder is not initialized");
        instance.init(serverHolder.getContext(), JettyConnectorAccessor.getConnectorHolder(serverHolder));
        return instance;
    }


}
