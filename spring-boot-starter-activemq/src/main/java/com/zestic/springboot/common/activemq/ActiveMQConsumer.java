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

package com.zestic.springboot.common.activemq;

import com.zestic.common.entity.Message;
import com.zestic.common.exception.ApplicationException;
import com.zestic.common.exception.ApplicationRuntimeException;
import com.zestic.springboot.common.Consumer;
import com.zestic.springboot.common.activemq.config.ActiveMQProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.activemq.ActiveMQMessageConsumer;
import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;

/**
 * Acquire a JMS destination
 * Create a JMS consumer
 * Create a JMS consumer
 * Optionally register a JMS message listener
 */
@Getter
@Setter
public class ActiveMQConsumer extends ActiveMQ implements Consumer {

    private static final Logger logger = LoggerFactory.getLogger(ActiveMQConsumer.class.getSimpleName());

    private ActiveMQMessageConsumer consumer = null;

    public ActiveMQConsumer(ActiveMQProperties properties) {
        super(properties);
    }

    /**
     * The JMS API also provides the ability to asynchronously receive messages. The JMS
     * provider will push messages to the consumer.
     *
     * @throws ApplicationRuntimeException
     */
    public void create() throws ApplicationRuntimeException {
        super.create();
        try {
            destination = (ActiveMQQueue) session.createQueue(this.properties.getConsumer().getQueueName());
            consumer = (ActiveMQMessageConsumer) session.createConsumer(destination);
            consumer.start();
        } catch (JMSException e) {
            throw new ApplicationRuntimeException(new ApplicationException(
                    Constants.RTE_UNABLE_CREATE_CONSUMER.getCode(),
                    Constants.RTE_UNABLE_CREATE_CONSUMER.getMessage()));
        }
    }

    @SneakyThrows
    public void close() {
        consumer.close();
        super.close();
    }

    @SneakyThrows
    public Message receive() {
        Message message = null;
        javax.jms.Message jmsMessage = null;
        try {
            if (session.isRunning() && consumer.getMessageSize() >= 1) {
                jmsMessage = consumer.receive(5);
            }
        } catch (JMSException ex) {
            logger.error("", ex);
        } finally {
            if (jmsMessage != null) {
                jmsMessage.acknowledge();
            }
        }
        return message;
    }
}
