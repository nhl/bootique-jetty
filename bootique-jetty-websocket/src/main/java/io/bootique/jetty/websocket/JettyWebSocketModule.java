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
package io.bootique.jetty.websocket;

import io.bootique.ConfigModule;
import io.bootique.config.ConfigurationFactory;
import io.bootique.di.Binder;
import io.bootique.di.Injector;
import io.bootique.di.Provides;
import io.bootique.jetty.JettyModule;
import io.bootique.jetty.request.RequestMDCManager;
import org.eclipse.jetty.websocket.api.WebSocketPolicy;

import javax.inject.Singleton;
import java.util.Set;

/**
 * @since 1.0.RC1
 */
public class JettyWebSocketModule extends ConfigModule {

    public static JettyWebSocketModuleExtender extend(Binder binder) {
        return new JettyWebSocketModuleExtender(binder);
    }

    @Override
    public void configure(Binder binder) {
        JettyModule.extend(binder).addContextHandlerExtender(JettyWebSocketConfigurator.class);
        extend(binder).initAllExtensions();
    }

    @Provides
    @Singleton
    JettyWebSocketConfigurator provideWebSocketConfigurator(
            Injector injector,
            WebSocketPolicy policy,
            Set<EndpointKeyHolder> endpointKeys,
            RequestMDCManager mdcManager) {

        return new JettyWebSocketConfigurator(injector, policy, endpointKeys, mdcManager);
    }

    @Provides
    @Singleton
    WebSocketPolicy provideWebSocketPolicy(ConfigurationFactory configFactory) {
        return config(WebSocketPolicyFactory.class, configFactory).createPolicy();
    }

    @Provides
    @Singleton
    WebSocketMDCManager provideWebSocketMDCManager() {
        return new WebSocketMDCManager();
    }
}
