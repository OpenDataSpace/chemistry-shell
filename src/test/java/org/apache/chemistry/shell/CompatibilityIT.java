package org.apache.chemistry.shell;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.chemistry.opencmis.commons.SessionParameter;
import org.apache.chemistry.opencmis.commons.enums.BindingType;
import org.apache.chemistry.shell.app.ChemistryApp;
import org.apache.chemistry.shell.app.Console;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(value = Parameterized.class)
public class CompatibilityIT {

	static final Logger LOGGER = LoggerFactory
			.getLogger(CompatibilityIT.class);
	private ChemistryApp app;
	private String username;
	private String password;
	private String url;
	private String reponame;
	private String bindingType;

	@Parameters(name = "{index}: user={0} password=**** binding={2} repo={3} url={4}")
	public static List<String[]> parameters() {
		final List<String[]> parameters = new ArrayList<String[]>();
		final String username = System.getProperty(SessionParameter.USER);
		final String password = System.getProperty(SessionParameter.PASSWORD);
		final String atompubSpec = System
				.getProperty(SessionParameter.ATOMPUB_URL);
		final String repository = System
				.getProperty(SessionParameter.REPOSITORY_ID);
		if (username == null) {
			LOGGER.warn("username not set");
			return parameters;
		}
		if (password == null) {
			LOGGER.warn("password not set");
			return parameters;
		}
		if (repository == null) {
			LOGGER.warn("repository ID not specified");
			return parameters;
		}
		if (atompubSpec != null) {
			try {
				final URL url = new URL(atompubSpec);
				final String browserUrl = url.toExternalForm();
				final String bindingType = BindingType.ATOMPUB.value();
				parameters.add(new String[] { username, password, bindingType,
						repository, browserUrl });
			} catch (final MalformedURLException exception) {
				LOGGER.warn(atompubSpec, exception);
			}
		} else {
			LOGGER.warn("no atompub url specified -> skipping atompub test");
		}

		final String browserSpec = System
				.getProperty(SessionParameter.BROWSER_URL);
		if (browserSpec != null) {
			try {
				final URL url = new URL(browserSpec);
				final String browserUrl = url.toExternalForm();
				final String bindingType = BindingType.BROWSER.value();
				parameters.add(new String[] { username, password, bindingType,
						repository, browserUrl });
			} catch (final MalformedURLException exception) {
				LOGGER.warn(browserSpec, exception);
			}
		} else {
			LOGGER.warn("no browserspec url specified -> skipping browserspec test");
		}

		return parameters;
	}

	public CompatibilityIT(final String username, final String password,
			final String bindingType, final String repository, final String url) {
		this.username = username;
		this.password = password;
		this.url = url;
		this.bindingType = bindingType;
		this.reponame = repository;
	}

	@Before
	public void setUp() throws Exception {
		if (bindingType.equals(BindingType.BROWSER.value())) {
			app = new ChemistryApp(BindingType.BROWSER);
		} else {
			app = new ChemistryApp();
		}
		app.login(username, password.toCharArray());
		app.connect(url);
		Console.setDefault(new Console());
		Console.getDefault().start(app);
	}

	@After
	public void tearDown() throws Exception {
		username = null;
		password = null;
		url = null;
		reponame = null;
		Console.setDefault(null);
	}

	@Test
	public void testCD() throws Exception {
		Console console = Console.getDefault();
		console.runCommand("id");
		String[] repos = console.runCommand("ls").split("\n");
		if (repos.length == 0) {
			System.err.println("Exception: repos are empty (" + repos + ")");
			throw new IllegalArgumentException("No cmis repository found");
		}
		boolean repoFound = false;
		for (String repo : repos) {
			if (repo.equalsIgnoreCase(reponame)) {
				console.runCommand("cd \"" + repo + "\"");
				repoFound = true;
				break;
			}
		}
		assertTrue(repoFound);
	}
	
	@Ignore("Not yet implemented")
	@Test
	public void testMatch() throws Exception {
		
	}

}
