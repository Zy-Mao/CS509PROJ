package com.wpi.teamd.test;

import com.wpi.teamd.dao.ServerInterface;
import org.junit.Test;

/**
 * Created by mao on 28/04/2017.
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