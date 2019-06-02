package com.ingeint.settings;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ingeint.settings.Settings;

import static org.assertj.core.api.Assertions.assertThat;

public class SettingsTest {

    public static final String VALUE = "VALUE";
    private static final String KEY = "KEY";
    private Settings settings;

    @BeforeEach
    void setUp() {
        settings = Settings.getInstance();
    }

    @Test
    void shouldInvokeSameInstance() {
        assertThat(Settings.getInstance())
                .isSameAs(Settings.getInstance());
    }

    @Test
    public void shouldGetCorrectSetting() {
        settings.set(KEY, VALUE);
        assertThat(settings.get(KEY))
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldGetDefaultSettingInCaseThatNotExistKey() {
        String result = settings.get("", VALUE);
        assertThat(result)
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldGetDefaultSettingInCaseThatKeyIsNull() {
        String result = settings.get(null, VALUE);
        assertThat(result)
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldReturnNullWhenKeyIsNull() {
        assertThat(settings.get(null))
                .isNull();
    }

    @Test
    public void shouldNotGetDefaultSettingInCaseThatExistKey() {
        settings.set(KEY, VALUE);
        assertThat(settings.get(KEY, "anything"))
                .isEqualTo(VALUE);
    }

    @Test
    public void shouldRemoveProperty() {
        settings.set(KEY, VALUE);
        assertThat(settings.get(KEY))
                .isEqualTo(VALUE);

        settings.set(KEY, null);
        assertThat(settings.get(KEY))
                .isNull();
    }

}
