/*
 * Version:  1.0.0
 *
 * Authors:  Kumar <Deebendu Kumar>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.zestic.springboot.common.activemq.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
//@PropertySource("classpath:activemq.properties")
@ConfigurationProperties(prefix = "spring.activemq")
public class ActiveMQProperties {

    private String primary = "tcp://127.0.0.1:61616";
    private String secondary = "tcp://127.0.0.1:61616";
    private String username = "admin";
    private String password = "admin";

    private Boolean useTransaction = false;

    private ConsumerProperties consumer;
    private ProducerProperties producer;
}
