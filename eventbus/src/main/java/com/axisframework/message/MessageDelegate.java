package com.axisframework.message;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;

/**
 * Message delegate to provide transport and consumer for ActiveMQ.
 * @author luoqi
 */
public class MessageDelegate {

	private static final Logger logger = Logger.getLogger(MessageDelegate.class);

	private boolean transacted = false;
	private Connection connection;
	private Session session;
	private MessageConsumer consumer;
	private Destination destination = null;

	public MessageDelegate(String uri, String subject, String user, String password) {
		ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(user, password, uri);
		connectionFactory.setProducerWindowSize(16384);
		try {
			connection = connectionFactory.createConnection();
			connection.start();
			session = connection.createSession(transacted, Session.CLIENT_ACKNOWLEDGE);
			destination = session.createQueue(subject);
			consumer = session.createConsumer(destination);
		} catch (JMSException e) {
			e.printStackTrace();
			logger.error("fail to connect to MQ!", e);
			if (session != null) {
				try {
					session.close();
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	public void setListener(MessageListener listener) {
		try {
			if (listener != null) {
				if (consumer == null) {
					logger.error("consumer is null.");
				}
				consumer.setMessageListener(listener);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

	public MessageListener getListener() {
		try {
			return consumer.getMessageListener();
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void close() {
		try {
			if (this.connection != null) {
				this.connection.close();
			}
			if (this.consumer != null) {
				this.consumer.close();
			}
			if (this.session != null) {
				this.session.close();
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}

}