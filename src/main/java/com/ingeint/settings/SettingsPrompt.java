package com.ingeint.settings;

public class SettingsPrompt {
    private String prompt;
    private String key;
    private String value;

    public SettingsPrompt(String prompt, String key, String value) {
        this.prompt = prompt;
        this.key = key;
        this.value = value;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.format("SettingsPrompt [prompt=%s, key=%s, value=%s]", prompt, key, value);
    }

}
