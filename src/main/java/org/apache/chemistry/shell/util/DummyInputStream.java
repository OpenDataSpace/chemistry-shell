package org.apache.chemistry.shell.util;

import java.io.IOException;
import java.io.InputStream;

public class DummyInputStream extends InputStream {
	private static final String LOREM_IPSUM = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.\n";
	private long size = -1;
	private DummyFileType type = DummyFileType.ZEROS;
	private int pos = 0;

	public DummyInputStream(long size, DummyFileType type) {
		if (size < 0)
			throw new IllegalArgumentException(
					"dummy file stream size must be larger then zero");
		this.size = size;
		this.type = type;
	}

	@Override
	public int read() throws IOException {
		if (size <= 0)
			return -1;
		size--;
		switch (type) {
		case ONES:
			return 255;
		case RANDOM:
			return (int) (Math.random() * 255);
		case TEXT:
			char c = LOREM_IPSUM.charAt(pos);
			pos++;
			if (pos >= LOREM_IPSUM.length())
				pos = 0;
			return (int) c;
		default:
			return 0;
		}
	}

}
