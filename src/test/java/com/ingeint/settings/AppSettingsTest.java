package com.ingeint.settings;

import com.ingeint.AppSettings;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppSettingsTest {

    public static final String VALUE = "VALUE";
    private static final String KEY = "KEY";
    private AppSettings appSettings;

    @BeforeEach
    void setUp() {
        appSettings = AppSettings.getInstance();
    }

    @Test
    void shouldInvokeSameInstance() {
        assertThat(AppSettings.getInstance())
                .isSameAs(AppSettings.getInstance());
    }

    @Test
    public void shouldGetCorrectSetting() {
        appSettings.set(KEY, VALUE);
        assertThat(appSettings.get(KEY))
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldGetDefaultSettingInCaseThatNotExistKey() {
        String result = appSettings.get("", VALUE);
        assertThat(result)
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldGetDefaultSettingInCaseThatKeyIsNull() {
        String result = appSettings.get(null, VALUE);
        assertThat(result)
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldReturnNullWhenKeyIsNull() {
        assertThat(appSettings.get(null))
                .isNull();
    }

    @Test
    public void shouldNotGetDefaultSettingInCaseThatExistKey() {
        appSettings.set(KEY, VALUE);
        assertThat(appSettings.get(KEY, "anything"))
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldRemoveProperty() {
        appSettings.set(KEY, VALUE);
        assertThat(appSettings.get(KEY))
                .isEqualTo(VALUE);

        appSettings.set(KEY, null);
        assertThat(appSettings.get(KEY))
                .isNull();
    }

}
