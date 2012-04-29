package com.facetime.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

public class IvyFileGenreator {
	public static void main(String[] args) throws Exception {
		Assert.isTrue((args.length == 4) || (args.length == 5), "This program must have 4 or 5 arguments!");
		String ivyfile = null;
		if (args.length == 4) {
			ivyfile = "ivy.ftl";
		} else {
			ivyfile = args[4];
		}
		Map<String, String> root = new HashMap<String, String>();
		root.put("org", args[0]);
		root.put("name", args[1]);
		root.put("rev", args[2]);

		String filename = args[3];
		FreemarkerHelper.create(ivyfile, filename, root);
	}
}