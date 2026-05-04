package com.example.demo.application.shared.command;

public record RegisterJobCommand(String name, String group, String cronExpression, String jobType) {
}
