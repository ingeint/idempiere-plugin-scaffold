package com.ingeint.example.process;

import static com.ingeint.example.test.util.AnnotationTestUtil.assertClassAnnotation;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.base.annotation.Process;
import org.junit.jupiter.api.Test;

public class PPrintPluginInfoTest {

	@Test
	public void containsTheAnnotation() {
		assertClassAnnotation(PPrintPluginInfo.class, Process.class);
	}

	@Test
	public void getPluginInformation() throws Exception {
		PPrintPluginInfo process = new PPrintPluginInfo();
		assertThat(process.doIt()).contains("Plugin Example");
	}

}
