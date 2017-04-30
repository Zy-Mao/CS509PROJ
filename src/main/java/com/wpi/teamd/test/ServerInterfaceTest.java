package com.wpi.teamd.test;

import com.wpi.teamd.dao.ServerInterface;
import org.junit.Test;

/**
 * Unit test class for ServerInterface class.
 *
 * @author Mao
 * @version 1.0
 * @since 2017-04-28
 *
 *
 *
 */
public class ServerInterfaceTest {

	@Test
	public void lock() throws Exception {
		assert (ServerInterface.lock());
	}

	@Test
	public void unlock() throws Exception {
		ServerInterface.lock();
		assert (ServerInterface.unlock());
	}

}