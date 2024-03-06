package com.ingeint.example.process;

import static com.ingeint.example.test.assertion.Annotations.assertClassAnnotation;

import org.adempiere.base.annotation.Process;
import org.junit.jupiter.api.Test;

public class PPrintPluginInfoTest {
	
	@Test
	public void containsTheAnnotation() {
		assertClassAnnotation(PPrintPluginInfo.class, Process.class);
	}

}
