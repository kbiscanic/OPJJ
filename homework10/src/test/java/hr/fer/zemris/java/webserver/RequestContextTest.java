package hr.fer.zemris.java.webserver;

import static org.junit.Assert.*;

import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class RequestContextTest {

	@Test
	public void testParameter() {
		Map<String, String> parametri = new HashMap<>();
		parametri.put("ime", "1");
		parametri.put("prezime", "2");
		Set<String> set = new HashSet<>();
		set.add("ime");
		set.add("prezime");
		RequestContext rc = new RequestContext(System.out, parametri, null,
				null);

		Assert.assertEquals("1", rc.getParameter("ime"));
		Assert.assertEquals(set, rc.getParameterNames());
	}

	@Test
	public void testPersistentParameter() {
		Map<String, String> parametri = new HashMap<>();
		parametri.put("ime", "1");
		parametri.put("prezime", "2");
		Set<String> set = new HashSet<>();
		set.add("ime");
		set.add("prezime");
		RequestContext rc = new RequestContext(System.out, null, parametri,
				null);

		Assert.assertEquals("1", rc.getPersistentParameter("ime"));
		Assert.assertEquals(set, rc.getPersistentParameterNames());
		rc.setPersistentParameter("ime", "-1");
		Assert.assertEquals("-1", rc.getPersistentParameter("ime"));
		rc.removePersistentParameter("ime");
		set.remove("ime");
		Assert.assertEquals(set, rc.getPersistentParameterNames());
	}

	@Test
	public void testGetTemporaryParameter() {
		Set<String> set = new HashSet<>();
		set.add("ime");
		set.add("prezime");
		RequestContext rc = new RequestContext(System.out, null, null, null);
		rc.setTemporaryParameter("ime", "1");
		rc.setTemporaryParameter("prezime", "0");

		Assert.assertEquals("1", rc.getTemporaryParameter("ime"));
		Assert.assertEquals(set, rc.getTemporaryParameterNames());
		rc.removeTemporaryParameter("ime");
		set.remove("ime");
		Assert.assertEquals(set, rc.getTemporaryParameterNames());
	}

	@Test
	public void testWriteString() {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		RequestContext rc = new RequestContext(os, null, null, null);
		rc.setEncoding("UTF-8");
		rc.setMimeType("text/plain");
		rc.setStatusCode(123);
		rc.setStatusText("test");
		rc.addRCCookie(new RCCookie("ime", "1", null, "localhost", "/"));
		try {
			rc.write("1");

			ByteArrayOutputStream target = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(target);
			dos.writeBytes("HTTP/1.1 123 test\r\nContent-Type: text/plain; charset=UTF-8\r\n\r\n1");
			dos.flush();

			Assert.assertEquals(target.toString(), os.toString());
		} catch (IOException e) {
			fail("Exception");
		}
	}

}
