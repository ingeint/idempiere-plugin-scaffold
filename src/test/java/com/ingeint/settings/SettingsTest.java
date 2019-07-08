package com.ingeint.settings;

import org.junit.jupiter.api.Test;

import java.time.Year;

import static org.assertj.core.api.Assertions.assertThat;

public class SettingsTest {

    public static final String VALUE = "VALUE";
    private static final String KEY = "KEY";

    @Test
    public void shouldGetCorrectSetting() {
        Settings.set(KEY, VALUE);
        assertThat(Settings.get(KEY))
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldGetDefaultSettingInCaseThatNotExistKey() {
        String result = Settings.get("", VALUE);
        assertThat(result)
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldGetDefaultSettingInCaseThatKeyIsNull() {
        String result = Settings.get(null, VALUE);
        assertThat(result)
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldReturnNullWhenKeyIsNull() {
        assertThat(Settings.get(null))
                .isNull();
    }

    @Test
    public void shouldNotGetDefaultSettingInCaseThatExistKey() {
        Settings.set(KEY, VALUE);
        assertThat(Settings.get(KEY, "anything"))
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldRemoveProperty() {
        Settings.set(KEY, VALUE);
        assertThat(Settings.get(KEY))
                .isEqualTo(VALUE);

        Settings.set(KEY, null);
        assertThat(Settings.get(KEY))
                .isNull();
    }

    @Test
    void shouldGetCurrentYear() {
        assertThat(Settings.get("year")).isEqualTo(Year.now().toString());
    }
}
